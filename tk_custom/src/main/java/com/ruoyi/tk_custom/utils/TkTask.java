package com.ruoyi.tk_custom.utils;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.tk_custom.domain.*;
import com.ruoyi.tk_custom.service.IKucunService;
import com.ruoyi.tk_custom.service.IMsgtowxService;
import com.ruoyi.tk_custom.service.IWxApproveService;
import com.ruoyi.tk_custom.service.IWxuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 定时任务调度
 * 
 * @author wfs
 */
@Component("TkTask")
public class TkTask
{
    @Autowired
    private IKucunService kucunService;
    @Autowired
    private IMsgtowxService iMsgtowxService;
    @Autowired
    private IWxApproveService iWxApproveService;
    @Autowired
    private IWxuserService iWxuserService;
    @Autowired
    private SysDeptMapper deptMapper;
    public static void ShengChanJieAn()
    {
        ArrayList<JSONObject> list = KingdeeUtil.queryList("PRD_MO",
                "FID,FTreeEntity_FEntryId,FBILLNO,FRepQuaAuxQty,FQty",
                "[{\"Left\":\"\",\"FieldName\":\"FStatus\",\"Compare\":\"106\",\"Value\":\"6\",\"Right\":\"\",\"Logic\":0},"+
                        "{\"Left\":\"\",\"FieldName\":\"FStatus\",\"Compare\":\"106\",\"Value\":\"7\",\"Right\":\"\",\"Logic\":0},"+
                        "{\"Left\":\"\",\"FieldName\":\"FStockInQuaAuxQty\",\"Compare\":\"21\",\"Value\":\"0\",\"Right\":\"\",\"Logic\":0},"+
                        "{\"Left\":\"\",\"FieldName\":\"F_isend\",\"Compare\":\"67\",\"Value\":\"1\",\"Right\":\"\",\"Logic\":0}]");
        if(list.size()==0)
            return;
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> map = new HashMap<>();
        for (JSONObject jsonObject : list) {
            Double repQuaAuxQty = (Double) jsonObject.get("FRepQuaAuxQty");
            Double fQty = (Double) jsonObject.get("FQty");
            double v = fQty - repQuaAuxQty;
            if(v>0&&(v*100/fQty)>10)//未交料大于10%
                continue;
            String fid = ""+jsonObject.get("FID");
            String entryId = ""+jsonObject.get("FTreeEntity_FEntryId");
            if(map.containsKey(fid)){
                String oldValue=map.get(fid);
                map.put(fid,oldValue+","+entryId);
            }else{
                map.put(fid,entryId);
            }
        }
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            if(!StringUtils.isEmpty(sb))
                sb.append(",");
            sb.append("{\"Id\":\""+next.getKey()+"\",\"EntryIds\":\""+next.getValue()+"\"}");
        }
        try {
            K3CloudApi client = new K3CloudApi();
            String requestresult = client.excuteOperation("PRD_MO", "ForceClose",
                    "{\n" +
                            "  \"PkEntryIds\": ["+sb+"]\n" +
                            "}");
            ArrayList<KingdeeRequestResult> requestResultObj = KingdeeUtil.getRequestResultObj(requestresult, true);
            ExcelUtil<KingdeeRequestResult> util = new ExcelUtil<KingdeeRequestResult>(KingdeeRequestResult.class);
            String docfilepath = util.exportExcelToFile(requestResultObj, "BillExecuteResult_" + new SimpleDateFormat("yyyy_MMdd_HHmm_ss").format(new Date()));
            //发送到企微
           WXUtil.uploadFileToWechat(docfilepath,"shengchandingdanjiean");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void updateAllKucun() {
        Date dateNow = new Date();//更新数据的时间
        kucunService.deleteAll();
        ArrayList<JSONObject> list = KingdeeUtil.selectAllChuKuData();
        executeInsert(list,dateNow,false);
    }
    public void updatePartKucun() {
        Date dateNow = new Date();//更新数据的时间
        String date="";
        Kucun kucun = kucunService.selectLastTime();
        if(kucun!=null){
            Date syncTime = kucun.getSyncTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           date= sdf.format(syncTime);
        }
        ArrayList<JSONObject> list = KingdeeUtil.selectPartChuKuDataPart(date);
        executeInsert(list,dateNow,true);
    }
    public void executeInsert(ArrayList<JSONObject> list ,Date date,boolean ispart){
        for (JSONObject jb : list) {
            Kucun kucun = new Kucun();
            Set<String> strings = jb.keySet();
            Iterator<String> iterator = strings.iterator();
            String kuweiName="";
            while ("".equals(kuweiName)){
                String next = iterator.next();
                if(next.contains("FStockLocID.FF")){
                    String value = jb.getString(next);
                    if(value!=null&&!"".equals(value))
                    kuweiName=value;
                }
            }
            kucun.setId(jb.getString("FEntity_FEntryId"));
            kucun.setKdBillno(jb.getString("FBillNo"));
            kucun.setKdWlbm(jb.getString("FMaterialID.FNumber"));
            kucun.setKdWumc(jb.getString("FMaterialName"));
            kucun.setKdGgxh(jb.getString("FMateriaModel"));
            kucun.setKdChukuNumber(jb.getBigDecimal("FRealQty"));
            kucun.setKdChukuTime(jb.getDate("FApproveDate"));
            kucun.setKdCangkuId(jb.getString("FStockID.FNumber"));
            kucun.setKdCangkuName(jb.getString("FEntrynote"));
            kucun.setKdKuweiId(jb.getString("FStockLocID"));
            kucun.setKdKuweiName(kuweiName);
            kucun.setKdCangkuId(jb.getString("FBillNo"));
            kucun.setSyncTime(date);
            kucun.setStatus(jb.getString("FDocumentStatus"));
            if(ispart){//差异更新
                Kucun k1 = kucunService.selectKucunById(jb.getString("FEntity_FEntryId"));
                if(k1!=null){
                    kucunService.updateKucun(kucun);
                }else {
                    kucunService.insertKucun(kucun);
                }
            }else {//全量插入
                kucunService.insertKucun(kucun);
            }
        }
    };
    public void sendSuggestMsg(){
        Msgtowx msgtowx = new Msgtowx();
        msgtowx.setIsSend("0");
        List<Msgtowx> msgtowxes = iMsgtowxService.selectMsgtowxList(msgtowx);
        for (Msgtowx msg : msgtowxes) {
            if("".equals(msg.getReceiverIds()))
                continue;
            String receiverIds = msg.getReceiverIds();
            String result = WXUtil.sendMsg(receiverIds, msg.getMsg());
            if ("ok".equals(new Gson().fromJson(result, JsonObject.class).get("errmsg").getAsString())) {
                msg.setIsSend("1");
                iMsgtowxService.updateMsgtowx(msg);
            }
        }
    }
    public void saveApproveData(){
        //最近20天的审批数据
        long starttime=(new Date().getTime()-20*24*60*60*1000)/ 1000L;
        long endtime=new Date().getTime()/1000L;
        ArrayList<WxApprove> allApproveData = WXUtil.getAllApproveData(starttime, endtime, new ArrayList(), "","3WN6KB42nEBV1eJbf624qnLZyobZGb7AtRp1yEk2");
        ArrayList<WxApprove> outers = WXUtil.getAllApproveData(starttime, endtime, new ArrayList(), "","Bs7vDs7g1Cy5vxZPzB5Jth2Dqw2tYZ5UZRt9qYBrU");
        for (WxApprove outer : outers) {
            allApproveData.add(outer);
        }
        for (WxApprove wxApprove : allApproveData) {
            if(wxApprove==null)
                continue;
            WxApprove wxApprove1 = iWxApproveService.selectWxApproveBySpNo(wxApprove.getSpNo());
            if(wxApprove1==null)
                iWxApproveService.insertWxApprove(wxApprove);
            else
                iWxApproveService.updateWxApprove(wxApprove);
        }
    }
    public void saveApproveDataBySecond(){
        //最近7天内的审批数据
        long starttime=(new Date().getTime()-7*24*60*60*1000)/ 1000L;
        long endtime=new Date().getTime()/1000L;
        ArrayList<WxApprove> allApproveData = WXUtil.getAllApproveData(starttime, endtime, new ArrayList(), "","3WN6KB42nEBV1eJbf624qnLZyobZGb7AtRp1yEk2");
        ArrayList<WxApprove> outers = WXUtil.getAllApproveData(starttime, endtime, new ArrayList(), "","Bs7vDs7g1Cy5vxZPzB5Jth2Dqw2tYZ5UZRt9qYBrU");
        for (WxApprove outer : outers) {
            allApproveData.add(outer);
        }
        for (WxApprove wxApprove : allApproveData) {
            WxApprove wxApprove1 = iWxApproveService.selectWxApproveBySpNo(wxApprove.getSpNo());
            if(wxApprove1==null)
                iWxApproveService.insertWxApprove(wxApprove);
            else
                iWxApproveService.updateWxApprove(wxApprove);
        }
    }
    public void syncWXUsers(){
        ArrayList<JsonObject> userList = WXUtil.getUserObjectListFrom_TongXunLu(0);
        if(userList.size()==0)
            return;
            for (JsonElement jsonElement : userList) {
                JsonObject jb = jsonElement.getAsJsonObject();
                String userid = jb.get("userid").getAsString();
                String name = jb.get("name").getAsString();
                Wxuser wxuser = new Wxuser();
                wxuser.setName(name);
                wxuser.setUserid(userid);
                try{
                    iWxuserService.insertWxuser(wxuser);
                }catch (Exception e){}
            }
    }
    public void syncWXDept(){
        JsonArray dept = WXUtil.getDept(0);
        if(dept.size()==0)
            return;
            for (JsonElement jsonElement : dept) {
                JsonObject jb = jsonElement.getAsJsonObject();
                SysDept sysDept = new SysDept();
                sysDept.setDeptId(jb.get("id").getAsLong());
                sysDept.setDeptName(jb.get("name").getAsString());
                sysDept.setParentId(jb.get("parentid").getAsLong());
                SysDept sysDept1 = deptMapper.selectDeptById(sysDept.getDeptId());
                if(sysDept1==null)
                deptMapper.insertDept(sysDept);
            }
    }
    public static void main(String[] args) {

    }
}
