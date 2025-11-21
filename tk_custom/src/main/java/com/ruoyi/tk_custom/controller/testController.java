package com.ruoyi.tk_custom.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.houbb.sensitive.word.api.IWordCheck;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.core.SensitiveWordHelper;
import com.github.houbb.sensitive.word.support.check.WordChecks;
import com.github.houbb.sensitive.word.support.tag.WordTags;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.tk_custom.utils.KingdeeUtil;
import com.ruoyi.tk_custom.utils.WXUtil;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//[[100001, CTK01, 塑封原材料仓], [100003, CTK02, 玻封原材料仓], [100004, CTK03, 辅材仓], [100005, CTK04, 客供料仓],
// [100006, CTK07, 资产仓], [100007, CTK08, 现场仓], [100008, CTK10, 塑封成品仓], [100009, CTK12, 玻封成品仓],
// [100010, CTK13, 外购成品仓], [100011, CTK09, OEM成品仓], [100012, CTK14, 样品仓]]
public class testController {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private String username = "";
    private static List<List<Object>> stockLocs = null;

    //即时库存中不为0的数据
    public static ArrayList<JSONObject> getDataFromKuCun(String materialNumber) {
        ArrayList<JSONObject> list = queryList("STK_Inventory",
                "FStockLocId,FStockStatusId.FNumber,FStockId.FNumber,FLot.FNumber,FBaseQty,FMaterialId.FNumber,FMaterialId.FName,FMaterialId.FSpecification,FStockId.FName" + getStockLocList(true),
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
    public static void main(String[] args) {
        String str="你 妈 的";
        System.out.println(WXUtil.veryWords(str));

    }

}
