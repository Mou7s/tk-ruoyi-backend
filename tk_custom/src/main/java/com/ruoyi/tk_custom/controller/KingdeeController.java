package com.ruoyi.tk_custom.controller;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.JsonObject;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.tk_custom.domain.KingdeeEntity;
import com.ruoyi.tk_custom.utils.DBUtil;
import com.ruoyi.tk_custom.utils.ExcelUtil;
import com.ruoyi.tk_custom.utils.KingdeeUtil;
import org.apache.commons.dbutils.DbUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 金蝶云星空接口，2024-12-28
 */
@RestController
@RequestMapping("/kingdee")
public class KingdeeController {
    //    FID,FEntity_FEntryID,FSubEntity_FDetailID,FBillNo,FMONumber,FRouteId_Name,FOperQty,FQualifiedQty,FProductId,FProductName,FProSpecification，FProcessId
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @PostMapping("/updateTimeOfGXLL")
    public int updateTimeOfGXLL(@RequestBody JSONObject jsonObject) {
        String dateStr = (String) jsonObject.get("dateStr");
        if ("".equals(dateStr) || dateStr == null) {
            return 0;
        }
        dateStr = dateStr.substring(0, 7);
        return DBUtil.executeUpdateGXLL(dateStr);
    }
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @PostMapping("/handleCBData")
    public int handleCBData() {
        DBUtil.handleCBData();
        return 1;
    }
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @PostMapping("/writeToMO")
    public int writeToMO() {
        DBUtil.writeToMO();
        return 1;
    }
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @PostMapping("/buildIndexOfKingdee")
    public int buildIndexOfKingdee() {
        DBUtil.buildIndexOfKingdee();
        return 1;
    }
    //TODO 获取金蝶工序加工委外发出数据，并导出为excel
    @PreAuthorize("@ss.hasRole('excel_wf')")
    @PostMapping("/exportWFData/{billno}")
    public void exportWFData(@PathVariable("billno") String billnoStr, HttpServletResponse response) {
        String[] billnos = billnoStr.split("&");
        K3CloudApi kd = new K3CloudApi(false);
        ArrayList<JSONObject> jbList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int billnos_len = billnos.length;
        for (int i = 0; i < billnos.length; i++) {
            String billno = billnos[i];
            if (!"".equals(sb.toString())) {
                sb.append(",");
            }
            if (billnos_len > 1) {
                if (i == 0) {//第一个
                    sb.append("{\"Left\":\"(\",\"FieldName\":\"FBillNo\",\"Compare\":\"67\",\"Value\":\"" + billno + "\",\"Right\":\"\",\"Logic\":1}");
                } else if (i == billnos_len - 1) {//最后一个
                    sb.append("{\"Left\":\"\",\"FieldName\":\"FBillNo\",\"Compare\":\"67\",\"Value\":\"" + billno + "\",\"Right\":\")\",\"Logic\":0}");
                } else {
                    sb.append("{\"Left\":\"\",\"FieldName\":\"FBillNo\",\"Compare\":\"67\",\"Value\":\"" + billno + "\",\"Right\":\"\",\"Logic\":1}");
                }
            }else{
                sb.append("{\"Left\":\"\",\"FieldName\":\"FBillNo\",\"Compare\":\"67\",\"Value\":\"" + billno + "\",\"Right\":\"\",\"Logic\":0}");
            }
        }
        String columns = "FSupplier.FShortName,FBillNo,FCreatorId.FName,FDate,FOplanInFo,FMaterialName,FMaterialSpec,FMOBaseUnitId.FName," +//弹夹盒
                "F_UNW_Text_qtr,FOutSendQtyFake,FRateValQty,FRateOperQty,F_UNW_Base_re5";
        String filterStr = "[" +sb.toString()+
                ",{\"Left\":\"\",\"FieldName\":\"FLineType\",\"Compare\":\"29\",\"Value\":\"A\",\"Right\":\"\",\"Logic\":0}]";
        String jsonData = "{\"FormId\":\"SFC_OperOutGroupSend\",\"FieldKeys\":\"" + columns + "\",\"FilterString\":" + filterStr + ",\"OrderString\":\"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":10000,\"SubSystemId\":\"\"}";
        try

        {
            List<List<Object>> list = kd.executeBillQuery(jsonData);
            String path = ExcelUtil.fillToTemplete(list,billnoStr);
            File file = new File(path);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            // 清空 response
            response.reset();
            response.setCharacterEncoding("UTF-8");

            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            response.setContentType("application/octet-stream");
            OutputStream outputStream=null;
            // 将文件读到输入流中
            try (InputStream is = new BufferedInputStream(Files.newInputStream(file.toPath()))) {

                outputStream = new BufferedOutputStream(response.getOutputStream());

                byte[] buffer = new byte[1024];
                int len;

                //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
                while((len = is.read(buffer)) > 0){
                    outputStream.write(buffer, 0, len);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                outputStream.close();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }



    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @PostMapping("/deletePZ")
    public int deletePZ(@RequestBody JSONObject jsonObject) {
        int success = 0;
        String dateStr = (String) jsonObject.get("dateStr");
        if ("".equals(dateStr) || dateStr == null) {
            return success;
        }
        String year = dateStr.substring(0, 4);
        String month = dateStr.substring(5, dateStr.length());
        String billnos = getDataForQMZCP(year, month);
        if ("".equals(billnos) || billnos == null)
            return success;
        K3CloudApi kd = new K3CloudApi(false);
        try {
            String resp = kd.unAudit("CB_ENDPROCOSTADJ", "{\"Numbers\":[" + billnos + "]}");
            JsonObject requestResultObj = KingdeeUtil.getRequestResultObj(resp);
            int success_unAudit = requestResultObj.get("success").getAsInt();
            if (success_unAudit == 1) {
                String cb_endprocostadj = kd.delete("CB_ENDPROCOSTADJ", "{\"Numbers\":[" + billnos + "]}");
                JsonObject requestResultObj1 = KingdeeUtil.getRequestResultObj(cb_endprocostadj);
                success = requestResultObj1.get("success").getAsInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public static void main(String[] args) throws Exception {
        String MoNumber="MO000565";
        int Seq = 1;
        int OperNumber = 10;
        String param =
                "{FieldKeys:\"FID,FEntity_FEntryId,FBillNo,FEntity_FSeq,FNumerator,FDenominator,FMaterialID2.FNumber,FOwnerID.FNumber,FOwnerTypeId.FNumber,FMaterialID.FNumber\"," +
                        "FilterString:[{\"Left\":\"\",\"FieldName\":\"FMOBillNO\",\"Compare\":\"17\",\"Value\":\""+ MoNumber + "\",\"Right\":\"\",\"Logic\":0}," +
                        "{\"Left\":\"\",\"FieldName\":\"FMOEntrySeq\",\"Compare\":\"76\",\"Value\":"+ Seq + ",\"Right\":\"\",\"Logic\":0}," +
                        "{\"Left\":\"\",\"FieldName\":\"FOperID\",\"Compare\":\"76\",\"Value\":"+ OperNumber + ",\"Right\":\"\",\"Logic\":0}]," +
                        "FormId:\"PRD_PPBOM\"," +
                        "Limit:2000," +
                        "OrderString:\"\"," +
                        "StartRow:0," +
                        "SubSystemId:\"\"," +
                        "TopRowCount:0}";
        K3CloudApi kd = new K3CloudApi(false);
        List<List<Object>> list = kd.executeBillQuery(param);
        for (List<Object> objects : list) {
            System.out.println(objects);
        }

    }
    public static String getDataForQMZCP(String year, String month) {
        List<List<Object>> data = KingdeeUtil.getData("CB_ENDPROCOSTADJ", "FBillNo",
                "[{\"Left\":\"\",\"FieldName\":\"FYEAR\",\"Compare\":\"76\",\"Value\":" + year + ",\"Right\":\"\",\"Logic\":0}," +
                        "{\"Left\":\"\",\"FieldName\":\"FPERIOD\",\"Compare\":\"76\",\"Value\":" + month + ",\"Right\":\"\",\"Logic\":0}]",
                "", "0", "1000000");
        StringBuilder sb = new StringBuilder();
        System.out.println(data.size());
        for (List<Object> datum : data) {

            if (!"".equals(sb.toString()))
                sb.append(",");
            sb.append("\""+((String) datum.get(0))+"\"");
        }
        return sb.toString();
    }

    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @PostMapping("/getMOData")
    public ArrayList<JSONObject> getMOData(@RequestBody JSONObject jsonObject) {
       return DBUtil.getMOData(jsonObject);
    }
    @GetMapping("/gxjh/{billno}")
    public JSONObject getData_gongxujihua(@PathVariable("billno") String billno) {
        String processId = "111634";
        return KingdeeUtil.queryOne("SFC_OperationPlanning",
                "FID,FEntity_FEntryID,FSubEntity_FDetailID,FBillNo,FMONumber,FOperQty,FQualifiedQty,FProductId,FProductName,FProSpecification",
                "[{\"Left\":\"(\",\"FieldName\":\"FBillNo\",\"Compare\":\"67\",\"Value\":\"" + billno + "\",\"Right\":\")\",\"Logic\":\"0\"},{\"Left\":\"(\",\"FieldName\":\"FProcessId\",\"Compare\":\"67\",\"Value\":\"" + processId + "\",\"Right\":\")\",\"Logic\":\"0\"}]");
    }

    //下推工序汇报
    @PostMapping("/save_gxhb")
    public String save_gxhb(KingdeeEntity kingdeeEntity) {
//        JSONObject jb = kingdeeEntity.getData();
//        System.out.println(jb);
        K3CloudApi kd = new K3CloudApi(false);
        String execute = "";
        try {
            execute = kd.execute("Kingdee.K3.MFG.WebApi.ServicesStub.OptPlanOptRtpApiService.Push,Kingdee.K3.MFG.WebApi.ServicesStub", new Object[]{
                    "{\n" +
                            "  \"AutoAudit\":\"true\",\n" +
                            "  \"FBillTypeID\":\"\",\n" +
                            "  \"Datas\":\n" +
                            "  [ {\n" +
                            "    \"Id\":\"111603\",\n" +
                            "    \"DetailIds\":\n" +
                            "    [ {\n" +
                            "      \"EntryId\":\"111616\",\n" +
                            "      \"DetailId\":\"226835\",\n" +
                            "      \"QuaQty\":10,\n" +
                            "      \"ProcessFailQty\":0,\n" +
                            "      \"MaterialFailQty\":0,\n" +
                            "      \"ReworkQty\":0,\n" +
                            "      \"WastageQty\":0,\n" +
                            "      \"FinishQty\":10,\n" +
                            "      \"ProcessStartTime\":\"2025-01-04 15:57:55\",\n" +
                            "      \"ProcessEndTime\":\"2025-01-04 15:57:58\"\n" +
                            "    } ]\n" +
                            "}"
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return execute;
    }

}
