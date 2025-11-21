package com.ruoyi.tk_custom.controller;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.tk_custom.utils.KingdeeUtil;
import com.ruoyi.tk_custom.utils.WXUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//[[100001, CTK01, 塑封原材料仓], [100003, CTK02, 玻封原材料仓], [100004, CTK03, 辅材仓], [100005, CTK04, 客供料仓],
// [100006, CTK07, 资产仓], [100007, CTK08, 现场仓], [100008, CTK10, 塑封成品仓], [100009, CTK12, 玻封成品仓],
// [100010, CTK13, 外购成品仓], [100011, CTK09, OEM成品仓], [100012, CTK14, 样品仓]]
@RestController
@RequestMapping("/wms")
public class WMSController {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private String username = "";
    private static List<List<Object>> stockLocs = null;

    @PreAuthorize("@ss.hasPermi('tk_custom:chuku_xs:add')")
    @ResponseBody
    @GetMapping("/ck/list/{billno}")
    public ArrayList getCkList(@PathVariable("billno") String billno) {
        ArrayList list = getCkListFromKingdee(billno);
        return list;
    }

    @PreAuthorize("@ss.hasPermi('tk_custom:chuku_xs:add')")
    @ResponseBody
    @GetMapping("/ck/getStock")
    public ArrayList getStockXX() {
        ArrayList list = getAllStockLoc();
        return list;
    }

    @PreAuthorize("@ss.hasPermi('tk_custom:chuku_xs:add')")
    @ResponseBody
    @GetMapping("/ck/getStockLoc/{billno}")
    public ArrayList getStockLocXX(@PathVariable("billno") String billno) {
        ArrayList stockLoc = getStockLocByStock(billno);
        return stockLoc;
    }

    @PreAuthorize("@ss.hasPermi('tk_custom:chuku_xs:add')")
    @ResponseBody
    @GetMapping("/ck/queryKuCun/{billno}")
    public ArrayList queryKuCun(@PathVariable("billno") String billno) {
        return getDataFromKuCun(billno);
    }

    //审核出库单/退库单
    @PreAuthorize("@ss.hasPermi('tk_custom:chuku_xs:add')")
    @ResponseBody
    @GetMapping("/ck/audit_out/{billno}")
    public JSONObject auditOut(@PathVariable("billno") String billno) {
        boolean isChuKu = billno.startsWith("TK");
        JsonObject sal_outstock;
        if(isChuKu)
            sal_outstock= KingdeeUtil.audit("SAL_OUTSTOCK",
                    "{ \"Numbers\": [\"" + billno + "\"]}");
        else
            sal_outstock= KingdeeUtil.audit("SAL_RETURNSTOCK",
                    "{ \"Numbers\": [\"" + billno + "\"]}");
        username = SecurityUtils.getUsername();
        //发送消息提醒到企业微信
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                sendWXMsg(sal_outstock, billno, 0);
            }
        });
        JSONObject jsonObject = JSONObject.parseObject(sal_outstock.toString());
        return jsonObject;
    }

    /**
     * 修改盘点
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:chuku_xs:edit')")
    @Log(title = "库存调整保存", businessType = BusinessType.UPDATE)
    @PutMapping("/ck/save")
    public AjaxResult save(@RequestBody JSONObject jb) {
        K3CloudApi k3CloudApi = new K3CloudApi();
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("success",0);
        try {
            String response = k3CloudApi.save("STK_TransferDirect", "{\n" +
                    "  \"Model\": {\n" +
                    "    \"FBillEntry\": [\n" +
                    "      {\n" +
                    "        \"FMaterialId\": {\n" +
                    "          \"FNumber\": \"" + jb.get("FMaterialId.FNumber") + "\"\n" +
                    "        },\n" +
                    "        \"FQty\": " + jb.get("number") + ",\n" +
                    "        \"FLot\": {\n" +
                    "          \"FNumber\": \"" + jb.get("FLot.FNumber") + "\"\n" +
                    "        },\n" +
                    "        \"FSrcStockId\": {\n" +
                    "          \"FNumber\": \"" + jb.get("FStockId.FNumber") + "\"\n" +
                    "        },\n" +
                    "        \"FSrcStockLocId\": {\n" +
                    "          \"FSRCSTOCKLOCID__FF" + getStockIdbyFnumber((String) jb.get("FStockId.FNumber")) + "\": {\n" +
                    "            \"FNumber\": \""+jb.get("FStockLocId.FNumber")+"\"\n" +
                    "          }\n" +
                    "        },\n" +
                    "        \"FDestStockId\": {\n" +
                    "          \"FNumber\": \"" + jb.get("newCangku") + "\"\n" +
                    "        },\n" +
                    "        \"FDestStockLocId\": {\n" +
                    "          \"FDESTSTOCKLOCID__FF" + getStockIdbyFnumber((String) jb.get("newCangku")) + "\": {\n" +
                    "            \"FNumber\": \"" + jb.get("newCangwei") + "\"\n" +
                    "          }\n" +
                    "        }\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}");
            JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
            JsonObject result = jsonObject.get("Result").getAsJsonObject();
            JsonObject respObj = result.get("ResponseStatus").getAsJsonObject();
            boolean asBoolean = respObj.get("IsSuccess").getAsBoolean();
            if(asBoolean){
                JsonArray errors = respObj.getAsJsonArray("SuccessEntitys");
                JsonObject successObj = errors.get(0).getAsJsonObject();
                try {
                    k3CloudApi.submit("STK_TransferDirect", "{ \"Numbers\": [\"" + successObj.get("Number").getAsString() + "\"]}");
                    String audit = k3CloudApi.audit("STK_TransferDirect", "{ \"Numbers\": [\"" + successObj.get("Number").getAsString() + "\"]}");
                }catch (Exception e){
                    ajaxResult.put("success",0);
                    ajaxResult.put("errMsg","提交审核失败，请到电脑端查看处理！");
                }
                ajaxResult.put("success",1);
            }
            else{
                ajaxResult.put("success",0);
                ajaxResult.put("errMsg","错误");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            ajaxResult.put("success",0);
        }
        return ajaxResult;
    }

    //即时库存中不为0的数据
    public static ArrayList<JSONObject> getDataFromKuCun(String materialNumber) {
        ArrayList<JSONObject> list = queryList("STK_Inventory",
                "FStockId.FNumber,FLot.FNumber,FBaseQty,FMaterialId.FNumber,FMaterialId.FName,FMaterialId.FSpecification,FStockId.FName" + getStockLocList(true),
                "[" +
                        "{\"Left\":\"(\",\"FieldName\":\"FMaterialId.FNumber\",\"Compare\":\"67\",\"Value\":\"" + materialNumber + "\",\"Right\":\")\",\"Logic\":\"0\"}," +
                        "{\"Left\":\"(\",\"FieldName\":\"FBaseQty\",\"Compare\":\"21\",\"Value\":\"0.0\",\"Right\":\")\",\"Logic\":\"0\"}" +
                        "]");
        return list;
    }

    public static ArrayList<JSONObject> queryList(String formid, String columns, String filterStr) {
        K3CloudApi kd = new K3CloudApi(false);
        ArrayList<JSONObject> jbList = new ArrayList<>();
        String jsonData = "{\"FormId\":\"" + formid + "\",\"FieldKeys\":\"" + columns + "\",\"FilterString\":" + filterStr + ",\"OrderString\":\"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":10000,\"SubSystemId\":\"\"}";
        try {
            //调用接口
            List<List<Object>> lists = kd.executeBillQuery(jsonData);
            if (lists.size() > 0) {
                for (List<Object> list : lists) {
                    JSONObject jb = new JSONObject();
                    String[] split = columns.split(",");
                    for (int i = 0; i < list.size(); i++) {
                        if (i < 8)
                            jb.put(split[i], list.get(i));
                        else {
                            if (list.get(i) != null && !"".equals(list.get(i))) {
                                if (split[i].contains("FNumber"))
                                    jb.put("FStockLocId.FNumber", list.get(i));
                                if (split[i].contains("FName"))
                                    jb.put("FStockLocId.FName", list.get(i));

                            }
                        }
                    }
                    jbList.add(jb);
                }

            }
            System.out.println(jbList);
        } catch (Exception e) {
            System.out.println("接口调用失败，返回结果: " + e.getMessage());
        }
        return jbList;
    }

    //根据仓库Fnumber获取仓位
    public static ArrayList<JSONObject> getStockLocByStock(String parentFnumber) {
        if (stockLocs == null)
            getStockLocList(false);
        ArrayList<JSONObject> arr = new ArrayList<>();
        for (List<Object> stockLoc : stockLocs) {
            if (parentFnumber.equals(stockLoc.get(1))) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fnumber", stockLoc.get(3));
                jsonObject.put("name", stockLoc.get(4));
                arr.add(jsonObject);
            }
        }
        return arr;
    }

    //根据fnumber获取fid
    public static String getStockIdbyFnumber(String fnumber) {
        String fid = "";
        if ("".equals(fnumber) || fnumber == null)
            return fid;
        if (stockLocs == null)
            getStockLocList(false);
        for (List<Object> stockLoc : stockLocs) {
            if (fnumber.equals(stockLoc.get(1))) {
                fid = "" + stockLoc.get(0);
                break;
            }
        }
        return fid;
    }

    //根据仓库fnumber获取对应的仓位列表
    public static ArrayList<JSONObject> getAllStockLoc() {
        if (stockLocs == null)
            getStockLocList(false);
        ArrayList<JSONObject> arr = new ArrayList<>();
        for (List<Object> stockLoc : stockLocs) {
            boolean isExist = false;
            for (JSONObject jsonObject : arr) {
                if (jsonObject.get("fnumber").equals(stockLoc.get(1))) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                JSONObject jb = new JSONObject();
                jb.put("fid", stockLoc.get(0));
                jb.put("fnumber", stockLoc.get(1));
                jb.put("name", stockLoc.get(2));
                arr.add(jb);
            }

        }

        return arr;
    }

    //获取仓库仓位信息
    public static String getStockLocList(boolean containsFNumber) {
        if (stockLocs == null) {
            stockLocs = KingdeeUtil.getData("BD_FLEXVALUES",
                    "FID,FNUMBER,FNAME,FFlexValueNumber,FFlexValueName",
                    "",
                    "", "0", "200");
        }
        StringBuilder sb = new StringBuilder();
        for (List<Object> obj : stockLocs) {
            List obj1 = obj;
            if (containsFNumber)
                sb.append(",FStockLocID.FF" + obj1.get(0) + ".FNumber");
            sb.append(",FStockLocID.FF" + obj1.get(0) + ".FName");
        }
        return sb.toString();
    }

    public void sendWXMsg(JsonObject sal_outstock, String billno, int times) {
        if (times > 30) {
            System.out.println("发送消息到企业微信失败，请检查！！");
            return;
        }
        boolean isChuku = billno.startsWith("TK");
        HashMap<String, Object> hp = new HashMap<>();
        hp.put("chatid", "cangkuchuhuo");//88888888    666666
        hp.put("msgtype", "text");
        HashMap<String, String> hashMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        String success = sal_outstock.get("success").getAsString();
        if ("1".equals(success)){
            if(isChuku)
                sb.append("审核成功,出库单号:" + billno + "\n系统信息");
            else
                sb.append("审核成功,退库单号:" + billno + "\n系统信息");
        } else{
            if(isChuku)
                sb.append("【审核失败】,【出库单号】:" + billno + "\n【系统信息】");
            else
                sb.append("【审核失败】,【退库单号】:" + billno + "\n【系统信息】");
        }

        sb.append(": " + sal_outstock.get("Message").getAsString() + "\n");
        sb.append("【审核人】: " + username);
        hashMap.put("content", sb.toString());
        hp.put("text", hashMap);
//        String s = WXUtil.sendMsg("itGongChengShi-WenFengSheng", sb.toString());
        String s = WXUtil.sendMsgToChatRoom(hp);
        times++;
        if (!"ok".equals(new Gson().fromJson(s, JsonObject.class).get("errmsg").getAsString())) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendWXMsg(sal_outstock, billno, times);
        }
    }

    public static void main(String[] args) {
        String billno="XSTHD001891";
        List<List<Object>> list = KingdeeUtil.getData("SAL_RETURNSTOCK",
                "FBillNo,FDocumentStatus,FMaterialID.FNumber,FMaterialName,FMaterialModel,FMustQty,FRealQty,FLot.FName,FNote,FStockID.FName" + getStockLocList(false),
                "[" +
                        "{\"Left\":\"\",\"FieldName\":\"FBillNo\",\"Compare\":\"67\",\"Value\":\"" + billno + "\",\"Right\":\"\",\"Logic\":0}" +
                        "]",
                "", "0", "10000"
        );
        System.out.println(list);
        for (List<Object> objects : list) {
            System.out.println(objects);
        }
    }
    //TODO 以下是静态方法
    public static ArrayList getCkListFromKingdee(String billno) {
        List<List<Object>> list=null;
        boolean isChuKu = billno.startsWith("TK");
        if(isChuKu){//出库单
            list = KingdeeUtil.getData("SAL_OUTSTOCK",
                    "FBillNo,FDocumentStatus,FMaterialID.FNumber,FMaterialName,FMateriaModel,FMustQty,FRealQty,FLot.FName,FEntrynote,FStockID.FName" + getStockLocList(false),
                    "[" +
                            "{\"Left\":\"\",\"FieldName\":\"FBillNo\",\"Compare\":\"67\",\"Value\":\"" + billno + "\",\"Right\":\"\",\"Logic\":0}" +
                            "]",
                    "", "0", "10000"
            );
        }else{//退库单
           list = KingdeeUtil.getData("SAL_RETURNSTOCK",
                    "FBillNo,FDocumentStatus,FMaterialID.FNumber,FMaterialName,FMaterialModel,FMustQty,FRealQty,FLot.FName,FNote,FStockID.FName" + getStockLocList(false),
                    "[" +
                            "{\"Left\":\"\",\"FieldName\":\"FBillNo\",\"Compare\":\"67\",\"Value\":\"" + billno + "\",\"Right\":\"\",\"Logic\":0}" +
                            "]",
                    "", "0", "10000"
            );
        }

        ArrayList<JSONObject> arr = new ArrayList<>();
        for (Object obj : list) {
            JSONObject jb = new JSONObject();
            List<Object> list1 = (List<Object>) obj;
            jb.put("billno", ((String) list1.get(0)));
            jb.put("auditstatus", (String) list1.get(1));
            jb.put("fnumber", ((String) list1.get(2)));
            jb.put("fmaterialname", ((String) list1.get(3)));
            jb.put("fmateriamodel", ((String) list1.get(4)));
            jb.put("fmustqty", ((Double) list1.get(5)));
            jb.put("frealqty", ((Double) list1.get(6)));
            jb.put("flot", ((String) list1.get(7)));
            jb.put("fentrynote", ((String) list1.get(8)));
            jb.put("stockname", ((String) list1.get(9)));
            for (int i = 10; i < list1.size(); i++) {
                String s = (String) list1.get(i);
                if (s != null) {
                    jb.put("stocklocname", s);
                    break;
                }
            }
            jb.put("isselect", false);
            arr.add(jb);
        }
        return arr;
    }
}
