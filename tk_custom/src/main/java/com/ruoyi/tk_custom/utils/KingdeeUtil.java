package com.ruoyi.tk_custom.utils;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.ruoyi.tk_custom.controller.WMSController;
import com.ruoyi.tk_custom.domain.KingdeeRequestResult;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KingdeeUtil {
    public static void main(String[] args) throws IOException {
        ArrayList<JSONObject> arrayList = selectPartChuKuDataPart("2025-07-03");
        System.out.println(arrayList.size());
        for (JSONObject jsonObject : arrayList) {
            System.out.println(jsonObject);
        }

    }
    public static JsonObject audit(String formid,String params){
        K3CloudApi kd = new K3CloudApi(false);
        JsonObject jb =null;
        try {
            //{"Result":{"ResponseStatus":{"IsSuccess":true,"Errors":[],"SuccessEntitys":[{"Id":105016,"Number":"TK202504030001","DIndex":0}],"SuccessMessages":[{"FieldName":null,"Message":"单据编号为“AR00004867”的应收单，审核成功！","DIndex":0}],"MsgCode":0}}}
//            {"Result":{"ResponseStatus":{"ErrorCode":500,"IsSuccess":false,"Errors":[{"FieldName":"","Message":"单据编号为“TK202504030001”的销售出库单，单据已经审核！","DIndex":0}],"SuccessEntitys":[],"SuccessMessages":[],"MsgCode":11}}}
            kd.submit(formid, params);
            String audit = kd.audit(formid, params);
            JsonObject jsonObject = new Gson().fromJson(audit, JsonObject.class);
            JsonObject result = jsonObject.get("Result").getAsJsonObject();
            JsonObject respObj = result.get("ResponseStatus").getAsJsonObject();
            boolean isSuccess = respObj.get("IsSuccess").getAsBoolean();
            if(isSuccess){
                JsonArray successEntitys = respObj.getAsJsonArray("SuccessMessages");
                 jb = successEntitys.get(0).getAsJsonObject();
                jb.addProperty("success",1);
            }else{
                JsonArray errors = respObj.getAsJsonArray("Errors");
                 jb = errors.get(0).getAsJsonObject();
                 jb.addProperty("success",0);
            }
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jb;
    }
    //生产订单结案用
    public static ArrayList<KingdeeRequestResult> getRequestResultObj(String s,boolean isarr){
        ArrayList<KingdeeRequestResult> list = new ArrayList<KingdeeRequestResult>();
        JsonObject jsonObject = new Gson().fromJson(s, JsonObject.class);
        JsonObject result = jsonObject.get("Result").getAsJsonObject();
        JsonObject respObj = result.get("ResponseStatus").getAsJsonObject();
            JsonArray errors = respObj.getAsJsonArray("Errors");
            for (JsonElement error : errors) {
                KingdeeRequestResult entity = new KingdeeRequestResult();
                entity.setMessage(error.getAsJsonObject().get("Message").getAsString());
                list.add(entity);
            }

            JsonArray successS = respObj.getAsJsonArray("SuccessMessages");
            for (JsonElement success : successS) {
                KingdeeRequestResult entity = new KingdeeRequestResult();
                entity.setMessage(success.getAsJsonObject().get("Message").getAsString());
                list.add(entity);
            }
            JsonArray successEntitys = respObj.getAsJsonArray("SuccessEntitys");
            for (JsonElement el : successEntitys) {
                KingdeeRequestResult entity = new KingdeeRequestResult();
                entity.setMessage(el.toString());
                list.add(entity);
            }

        return list;
    }
    public static JsonObject getRequestResultObj(String s){
        JsonObject jb =null;
        JsonObject jsonObject = new Gson().fromJson(s, JsonObject.class);
        JsonObject result = jsonObject.get("Result").getAsJsonObject();
        JsonObject respObj = result.get("ResponseStatus").getAsJsonObject();
        boolean isSuccess = respObj.get("IsSuccess").getAsBoolean();
        if(isSuccess){
            JsonArray successEntitys = respObj.getAsJsonArray("SuccessMessages");
            if(successEntitys.size()==0){
                 successEntitys = respObj.getAsJsonArray("SuccessEntitys");
            }
            if(successEntitys.size()==0){
                jb = successEntitys.get(0).getAsJsonObject();
                jb.addProperty("success",1);
            }

        }else{
            JsonArray errors = respObj.getAsJsonArray("Errors");
            jb = errors.get(0).getAsJsonObject();
            jb.addProperty("success",0);
        }
        return jb;
    }
    public static JSONObject queryOne(String formid,String columns,String filterStr){
        return queryOne(formid,columns,filterStr,"");
    }
    public static JSONObject queryOne(String formid,String columns,String filterStr,String orderstr){//"FISNEWREC desc"
        K3CloudApi kd = new K3CloudApi(false);
        JSONObject jb=new JSONObject();;
        String jsonData = "{\"FormId\":\""+formid+"\",\"FieldKeys\":\""+columns+"\",\"FilterString\":"+filterStr+",\"OrderString\":\""+orderstr+"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":1000,\"SubSystemId\":\"\"}";
        try {
            //调用接口
            List<List<Object>> lists = kd.executeBillQuery(jsonData);
            if(lists.size()>0){
                List<Object> objects = lists.get(0);
                String[] split = columns.split(",");
                for (int i = 0; i < objects.size(); i++) {
                    jb.put(split[i],objects.get(i));
                }
            }

        } catch (Exception e) {
            System.out.println("接口调用失败，返回结果: " + e.getMessage());
        }
        return jb;
    }
    public static ArrayList<JSONObject> queryList(String formid,String columns,String filterStr){
        K3CloudApi kd = new K3CloudApi(false);
        ArrayList<JSONObject> jbList = new ArrayList<>();
        String jsonData = "{\"FormId\":\""+formid+"\",\"FieldKeys\":\""+columns+"\",\"FilterString\":"+filterStr+",\"OrderString\":\"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":10000,\"SubSystemId\":\"\"}";
        try {
            //调用接口
            List<List<Object>> lists = kd.executeBillQuery(jsonData);
            if(lists.size()>0){
                for (List<Object> list : lists) {
                    JSONObject jb=new JSONObject();
                    String[] split = columns.split(",");
                    for (int i = 0; i < list.size(); i++) {
                        jb.put(split[i],list.get(i));
                    }
                    jbList.add(jb);
                }

            }
// todo 超过1W条数据 待处理
        } catch (Exception e) {
            System.out.println("接口调用失败，返回结果: " + e.getMessage());
        }
        return jbList;
    }
    public static String getList(){
        K3CloudApi kd = new K3CloudApi(false);
        String Data="";
        try {
            Data = kd.getSysReportData("UNW_MOOScheduleRpt", "{\n" +
                    "    \"FieldKeys\": \"FMONUMBER\",\n" +
                    "    \"SchemeId\": \"\",\n" +
                    "    \"StartRow\": 0,\n" +
                    "    \"Limit\": 2000,\n" +
                    "    \"IsVerifyBaseDataField\": \"true\",\n" +
                    "    \"FilterString\": [],\n" +
                    "    \"Model\": {\n" +
                    "        \"FPrdOrgId\": [\n" +
                    "            {\n" +
                    "                \"FNumber\": \"100\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"FProcessOrgId\": [\n" +
                    "            {\n" +
                    "                \"FNumber\": \"100\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"FWorkShopIDS\": \"\",\n" +
                    "        \"FProWorkShopIDS\": \"\",\n" +
                    "        \"FBeginPlanStartDate\": \"2024-01-01\",\n" +
                    "        \"FEndPlanStartDate\": \"9999-01-01\",\n" +
                    "        \"FBeginPlanFinishDate\": \"2024-01-01\",\n" +
                    "        \"FEndPlanFinishDate\": \"9999-01-01\",\n" +
                    "        \"FBillTypeId\": \"\",\n" +
                    "        \"FOperationStatus\": \"\",\n" +
                    "        \"FBeginMONumber\": \"\",\n" +
                    "        \"FEndMONumber\": \"\",\n" +
                    "        \"FBeginMaterialId\": {\n" +
                    "            \"FNumber\": \"\"\n" +
                    "        },\n" +
                    "        \"FEndMaterialId\": {\n" +
                    "            \"FNumber\": \"\"\n" +
                    "        },\n" +
                    "        \"FMTONoFrom\": \"\",\n" +
                    "        \"FMTONoTo\": \"\",\n" +
                    "        \"FSaleOrdNoFrom\": \"\",\n" +
                    "        \"FSaleOrdNoTo\": \"\",\n" +
                    "        \"FWorkCenterId\": [\n" +
                    "            {\n" +
                    "                \"FNumber\": \"\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"FProcessId\": [\n" +
                    "            {\n" +
                    "                \"FNumber\": \"\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"FScheduleStatus\": \"\",\n" +
                    "        \"FIsKeyOper\": \"\",\n" +
                    "        \"FIsSuspend\": \"\",\n" +
                    "        \"FTimeUnit\": \"\",\n" +
                    "        \"FWorkShopId\": {\n" +
                    "            \"FNUMBER\": \"BM000005\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Data;
    }
    public static List<List<Object>> getData(String formid,String columns,String filterStr,String orderStr,String stratrow,String limit){
        K3CloudApi kd = new K3CloudApi(false);
        List<List<Object>> lists=null;
        String jsonData = "{\"FormId\":\""+formid+"\",\"FieldKeys\":\""+columns+"\",\"FilterString\":"+filterStr+",\"OrderString\":\""+orderStr+"\",\"TopRowCount\":0,\"StartRow\":"+stratrow+",\"Limit\":"+limit+",\"SubSystemId\":\"\"}";
        try {
            //调用接口
            lists = kd.executeBillQuery(jsonData);
        } catch (Exception e) {
            System.out.println("接口调用失败，返回结果: " + e.getMessage());
        }
        return lists;
    }
    //将金蝶系统中的出库信息插入到内部系统（全量）
    public static ArrayList<JSONObject> selectAllChuKuData(){
        ArrayList<JSONObject> arrayList1 = queryList("SAL_OUTSTOCK",
                "FDocumentStatus,FEntity_FEntryId,FBillNo,FMaterialID.FNumber,FMaterialName,FMateriaModel,FRealQty,FApproveDate,FStockID.FNumber,FStockID.FName,FStockLocID,FEntrynote"+ WMSController.getStockLocList(false),
                "[{  \"Left\": \"\",  \"FieldName\": \"FCustomerID.FNumber\",  \"Compare\": \"17\",  \"Value\": \"CUST0858\",  \"Right\": \"\",  \"Logic\": 0}]");
        ArrayList<JSONObject> arrayList = queryList("SAL_RETURNSTOCK",
                "FDocumentStatus,FEntity_FEntryId,FBillNo,FMaterialID.FNumber,FMaterialName,FMaterialModel,FRealQty,FApproveDate,FStockID.FNumber,FStockID.FName,FStockLocID,FNote"+ WMSController.getStockLocList(false),
                "[{  \"Left\": \"\",  \"FieldName\": \"FRetcustId.FNumber\",  \"Compare\": \"17\",  \"Value\": \"CUST0858\",  \"Right\": \"\",  \"Logic\": 0}]");
        for (JSONObject jsonObject : arrayList) {
            BigDecimal fRealQty = jsonObject.getBigDecimal("FRealQty");
            jsonObject.put("FRealQty",fRealQty.negate());
            jsonObject.put("FEntrynote",jsonObject.getString("FNote"));
            jsonObject.put("FMateriaModel",jsonObject.getString("FMaterialModel"));
            arrayList1.add(jsonObject);
        }
        return arrayList1;
    }
    //将金蝶系统中的出库信息插入到内部系统（差异）
    public static ArrayList<JSONObject> selectPartChuKuDataPart(String date){// todo 根据审核日期判断
        if("".equals(date)||date==null){
            date="2025-06-01 00:00:00";
        }
        ArrayList<JSONObject> arrayList1 = queryList("SAL_OUTSTOCK",
                "FDocumentStatus,FEntity_FEntryId,FBillNo,FMaterialID.FNumber,FMaterialName,FMateriaModel,FRealQty,FApproveDate,FStockID.FNumber,FStockID.FName,FStockLocID,FEntrynote"+ WMSController.getStockLocList(false),
                "[{\"Left\":\"(\",\"FieldName\":\"FModifyDate\",\"Compare\":\"39\",\"Value\":\""+date+"\",\"Right\":\"\",\"Logic\":1},{\"Left\":\"\",\"FieldName\":\"FApproveDate\",\"Compare\":\"39\",\"Value\":\""+date+"\",\"Right\":\")\",\"Logic\":0},{\"Left\":\"\",\"FieldName\":\"FCustomerID.FNumber\",\"Compare\":\"67\",\"Value\":\"CUST0858\",\"Right\":\"\",\"Logic\":0}]");
        ArrayList<JSONObject> arrayList = queryList("SAL_RETURNSTOCK",
                "FDocumentStatus,FEntity_FEntryId,FBillNo,FMaterialID.FNumber,FMaterialName,FMaterialModel,FRealQty,FApproveDate,FStockID.FNumber,FStockID.FName,FStockLocID,FNote"+ WMSController.getStockLocList(false),
                "[{\"Left\":\"(\",\"FieldName\":\"FApproveDate\",\"Compare\":\"39\",\"Value\":\""+date+"\",\"Right\":\"\",\"Logic\":1},{\"Left\":\"\",\"FieldName\":\"FModifyDate\",\"Compare\":\"39\",\"Value\":\""+date+"\",\"Right\":\")\",\"Logic\":0},{\"Left\":\"\",\"FieldName\":\"FRetcustId.FNumber\",\"Compare\":\"67\",\"Value\":\"CUST0858\",\"Right\":\"\",\"Logic\":0}]");
        for (JSONObject jsonObject : arrayList) {
            BigDecimal fRealQty = jsonObject.getBigDecimal("FRealQty");
            jsonObject.put("FRealQty",fRealQty.negate());
            jsonObject.put("FEntrynote",jsonObject.getString("FNote"));
            jsonObject.put("FMateriaModel",jsonObject.getString("FMaterialModel"));
            arrayList1.add(jsonObject);
        }
        return arrayList1;
    }
    //下推工序汇报
    public static void save_gxjl(){
        K3CloudApi kd = new K3CloudApi(false);
        try {
            String sfc_operationPlanning = kd.view("SFC_OperationPlanning", "{\"CreateOrgId\":100,\"Number\":\"OP011464\",\"Id\":\"\",\"IsSortBySeq\":\"false\"}");
            JsonObject jsonObject = new Gson().fromJson(sfc_operationPlanning, JsonObject.class);
            JsonObject result = jsonObject.get("Result").getAsJsonObject();
            JsonObject data = result.get("Result").getAsJsonObject();
            System.out.println(data);
//            String sfc_operationPlanning1 = kd.push("SFC_OperationPlanning", new String(Files.readAllBytes(Paths.get("D:\\ruoyi\\RuoYi-Vue-master\\tk_custom\\src\\main\\resources\\xx.json"))));
//            System.out.println(sfc_operationPlanning1);
//            String sfc_operationReport = kd.save("SFC_OperationReport", new String(Files.readAllBytes(Paths.get("D:\\ruoyi\\RuoYi-Vue-master\\tk_custom\\src\\main\\resources\\yy.json"))));
//            System.out.println(sfc_operationReport);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
