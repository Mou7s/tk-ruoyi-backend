package com.ruoyi.tk_custom.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.tk_custom.domain.Wxuser;
import com.ruoyi.tk_custom.service.IWxuserService;
import com.ruoyi.tk_custom.utils.DBUtil;
import com.ruoyi.tk_custom.utils.WXUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.tk_custom.domain.WxApprove;
import com.ruoyi.tk_custom.service.IWxApproveService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信审批Controller
 *
 * @author wfs
 * @date 2025-08-14
 */
@RestController
@RequestMapping("/tk_custom/WXApprove")
public class WxApproveController extends BaseController {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    @Autowired
    private IWxuserService wxuserService;
    @Autowired
    private IWxApproveService wxApproveService;

    @Anonymous
    @GetMapping("/getImage/{id}")
    public void getImage(HttpServletResponse response, @PathVariable String id) throws IOException {
        ServletOutputStream os = response.getOutputStream();
        File file = new File(RuoYiConfig.getProfile()+"\\wx_pic", id + ".png");
        BufferedImage read = ImageIO.read(file);
        response.setContentType("image/png");
        ImageIO.write(read, "png", os);
    }
    @Anonymous
    @GetMapping("/getImageZip/{id}")
    public void getImageZip(HttpServletResponse response, @PathVariable String id) throws IOException {
        ServletOutputStream os = response.getOutputStream();
        File file = new File(RuoYiConfig.getProfile()+"\\pic_zip", id + ".png");
        BufferedImage read = ImageIO.read(file);
        response.setContentType("image/png");
        ImageIO.write(read, "png", os);
    }

    /**
     * 查询微信审批列表
     */
    @Anonymous
    @GetMapping("/list")
    public TableDataInfo list(WxApprove wxApprove) {
        startPage();
        List<WxApprove> list = wxApproveService.selectWxApproveList(wxApprove);
        return getDataTable(list);
    }
    /**
     * 查询今天入场的车牌
     */
    @Anonymous
    @GetMapping("/getCardNumber")
    public TableDataInfo getCardNumber() {
        ArrayList<String> carIds = DBUtil.getCarIds();
        return getDataTable(carIds);
    }
    /**
     * 查询保安名字
     */
    @Anonymous
    @GetMapping("/queryBA")
    public TableDataInfo queryBA() {
        Wxuser wxuser = new Wxuser();
        wxuser.setDept("1");
        List<Wxuser> wxusers = wxuserService.selectWxuserList(wxuser);
        ArrayList<String> arr = new ArrayList<>();
        for (Wxuser wxuser1 : wxusers) {
            arr.add(wxuser1.getName());
        }
        return getDataTable(arr);
    }
    @Anonymous
    @GetMapping("/listForOut")
    public TableDataInfo listForOut() {
        List<WxApprove> list = wxApproveService.selectWxApproveListForOut();
        for (WxApprove wxApprove : list) {
            wxApprove.setApplyerUserid(wxuserService.selectWxuserByUserid(wxApprove.getApplyerUserid()).getName());
            String auditerids = wxApprove.getAuditerid();
            wxApprove.setAuditerid(getAuditer(auditerids));
        }
        return getDataTable(list);
    }

    @Anonymous
    @GetMapping("/listForBack")
    public TableDataInfo list() {
        List<WxApprove> list = wxApproveService.selectWxApproveListForBack();
        for (WxApprove wxApprove : list) {
            wxApprove.setApplyerUserid(wxuserService.selectWxuserByUserid(wxApprove.getApplyerUserid()).getName());
            String auditerids = wxApprove.getAuditerid();
            wxApprove.setAuditerid(getAuditer(auditerids));
        }
        return getDataTable(list);
    }
    @Anonymous
    @GetMapping("/listForOut1")
    public TableDataInfo listForOut1() {
        List<WxApprove> list = wxApproveService.selectWxApproveListForOut1();
        for (WxApprove wxApprove : list) {
            wxApprove.setApplyerUserid(wxuserService.selectWxuserByUserid(wxApprove.getApplyerUserid()).getName());
            String auditerids = wxApprove.getAuditerid();
            wxApprove.setAuditerid(getAuditer(auditerids));
        }
        return getDataTable(list);
    }

    @Anonymous
    @GetMapping("/listForBack1")
    public TableDataInfo list1() {
        List<WxApprove> list = wxApproveService.selectWxApproveListForBack1();
        for (WxApprove wxApprove : list) {
            wxApprove.setApplyerUserid(wxuserService.selectWxuserByUserid(wxApprove.getApplyerUserid()).getName());
            String auditerids = wxApprove.getAuditerid();
            wxApprove.setAuditerid(getAuditer(auditerids));
        }
        return getDataTable(list);
    }
    /**
     * 更新出厂时间
     */
    @Anonymous
    @GetMapping("/updateOutTime/{spno}")
    public AjaxResult updateOutTime(HttpServletRequest request,@PathVariable String spno) {
        WxApprove wxApprove = wxApproveService.selectWxApproveBySpNo(spno);
        String contents = wxApprove.getApplyDataContents();
        JSONObject jb = new Gson().fromJson(contents, JSONObject.class);
        Date date = new Date();
        wxApprove.setOuttime(date);
        if(jb.containsKey("needBack")&&!"是".equals((String) jb.get("needBack"))){
            wxApprove.setBacktime(date);
        }
        String mainUrl=request.getRequestURI();
        int i = wxApproveService.updateWxApprove(wxApprove);
        if (i > 0) {
            //发送消息提醒到企业微信
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    sendMsgToChatRoom(spno,0);
                }
            });
        }
        return toAjax(i);
    }
    /**
     * 更新出厂时间
     */
    @Anonymous
    @PostMapping("/updateOutTime1")
    public AjaxResult updateOutTime1(@RequestBody JSONObject jsonObject) {
        String spno = jsonObject.getString("spno");
        WxApprove wxApprove = wxApproveService.selectWxApproveBySpNo(spno);
        String contents = wxApprove.getApplyDataContents();
        JSONObject jb = new Gson().fromJson(contents, JSONObject.class);
        if(jsonObject.containsKey("carNumber")&&!"".equals(jsonObject.getString("carNumber")))
            jb.put("carNumber",jsonObject.getString("carNumber"));
        if(jsonObject.containsKey("baoan")&&!"".equals(jsonObject.getString("baoan")))
            jb.put("baoan",jsonObject.getString("baoan"));
        wxApprove.setApplyDataContents(jb.toString());
        Date date = new Date();
        wxApprove.setOuttime(date);
        if(jb.containsKey("needBack")&&!"是".equals((String) jb.get("needBack"))){
            wxApprove.setBacktime(date);
        }
        int i = wxApproveService.updateWxApprove(wxApprove);
        if (i > 0) {
            //发送消息提醒到企业微信
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    sendMsgToChatRoom(spno,0);
                }
            });
        }
        return toAjax(i);
    }
    public static void main(String[] args) {
    }
    public String getParseDate(Date date){
        String time="";
        try {
            time = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", date);
        }catch (Exception e){

        }
        return time;
    }
    public String getAuditer(String auditIds){
        String[] split = auditIds.split(",");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            String name = wxuserService.selectWxuserByUserid(s).getName();
            if(!"".equals(sb.toString()))
                sb.append(" → ");
            sb.append(name);
        }
        return sb.toString();
    }
    public void sendText(WxApprove wxApprove, int times) {
        if (times > 10){
            System.out.println("发送消息到企业微信失败，请检查！！");
            return;
        }
        HashMap<String, Object> hp = new HashMap<>();
        hp.put("chatid", "fangxingtiao");
        hp.put("msgtype", "text");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("content","外出人:"+wxuserService.selectWxuserByUserid(wxApprove.getApplyerUserid()).getName()+"\n" +
                "外出事由:"+new Gson().fromJson(wxApprove.getApplyDataContents(),JSONObject.class).get("外出事由").toString()+"\n" +
                "外出地点:"+new Gson().fromJson(wxApprove.getApplyDataContents(),JSONObject.class).get("外出地点").toString()+"\n" +
                "开始时间:"+new Gson().fromJson(new Gson().fromJson(wxApprove.getApplyDataContents(),JSONObject.class).get("外出").toString(),JSONObject.class).get("starttime").toString().substring(0,16)+"\n" +
                "结束时间:"+new Gson().fromJson(new Gson().fromJson(wxApprove.getApplyDataContents(),JSONObject.class).get("外出").toString(),JSONObject.class).get("endtime").toString().substring(0,16)+"\n" +
                "时长:"+new Gson().fromJson(new Gson().fromJson(wxApprove.getApplyDataContents(),JSONObject.class).get("外出").toString(),JSONObject.class).get("time")+"\n" +
                "审批人:"+getAuditer(wxApprove.getAuditerid())+"\n" +
                "实际出厂时间:"+getParseDate(wxApprove.getOuttime())+"\n" +
                "实际返厂时间:"+getParseDate(wxApprove.getBacktime()));
        hp.put("text",hashMap);
        String s = WXUtil.sendMsgToChatRoom(hp);
        times++;
        if (!"ok".equals(new Gson().fromJson(s, JsonObject.class).get("errmsg").getAsString())) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendText(wxApprove, times);
        }
    }
    public  void sendMsgToChatRoom(String spno,int times) {
        if (times > 10){
            System.out.println("发送消息到企业微信失败，请检查！！");
            return;
        }
        WxApprove wxApprove = wxApproveService.selectWxApproveBySpNo(spno);
        if("外出".equals(wxApprove.getSpName())){
            sendText(wxApprove,0);
            return;
        }
        String contents = wxApprove.getApplyDataContents();
        JSONObject jb = new Gson().fromJson(contents, JSONObject.class);
        String outtime =(String) jb.get("放行时间");
        String ouerMan =(String) jb.get("携带人");
        String dept =(String) jb.get("所属部门");
        String reason =(String) jb.get("补充说明");
        String bringThing =(String) jb.get("携带物品");
        HashMap<String, Object> hp = new HashMap<>();
        hp.put("chatid", "wupingfangxingtiao");
//        hp.put("chatid", "shengchandingdanjiean");

        hp.put("msgtype", "textcard");
//        hp.put("agentid", "1000025");
        HashMap<String, Object> hashMap1 = new HashMap<>();
        ArrayList<Object> arr = new ArrayList<>();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("title","携带人:"+ouerMan+"\n"+
                "携带物品:"+bringThing+"\n" +
                "所属部门:"+dept+"\n" +
                "审批人:"+getAuditer(wxApprove.getAuditerid()));
        Date outtime1 = wxApprove.getOuttime();
        Date backtime = wxApprove.getBacktime();
        String str="";
        str+="申请放行时间:"+outtime.substring(0,16)+"\n";
        if(outtime1!=null)
            str+="出厂时间:"+ getParseDate(outtime1)+"\n";
        if (backtime!=null)
            str+="返厂时间:"+ getParseDate(backtime)+"\n";
        hashMap.put("description",str+"补充说明:"+reason);
        hashMap.put("url","http://wx.ctk-elec.com:999/WXApproveDetail?spno="+spno);
//        hashMap.put("url","http://localhost/WXApproveDetail?spno="+spno);
        hp.put("textcard",hashMap);
        String s = WXUtil.sendMsgToChatRoom(hp);
        times++;
        if (!"ok".equals(new Gson().fromJson(s, JsonObject.class).get("errmsg").getAsString())) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendMsgToChatRoom( spno,times);
        }
    }
    /**
     * 更新返厂时间
     */
    @Anonymous
    @GetMapping("/updateBackTime/{spno}")
    public AjaxResult updateBackTime(HttpServletRequest request,@PathVariable String spno) {
        WxApprove wxApprove = new WxApprove();
        wxApprove.setSpNo(spno);
        wxApprove.setBacktime(new Date());
        String mainUrl=request.getRequestURI();
        int i = wxApproveService.updateWxApprove(wxApprove);
        if (i > 0) {
            //发送消息提醒到企业微信
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    sendMsgToChatRoom(spno,0);
                }
            });
        }
        return toAjax(i);
    }

    /**
     * 导出微信审批列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:WXApprove:export')")
    @Log(title = "微信审批", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxApprove wxApprove) {
        List<WxApprove> list = wxApproveService.selectWxApproveList(wxApprove);
        ExcelUtil<WxApprove> util = new ExcelUtil<WxApprove>(WxApprove.class);
        util.exportExcel(response, list, "微信审批数据");
    }

    /**
     * 获取微信审批详细信息
     */
    @Anonymous
    @GetMapping(value = "/{spNo}")
    public AjaxResult getInfo(@PathVariable("spNo") String spNo) {
        WxApprove wxApprove = wxApproveService.selectWxApproveBySpNo(spNo);
        if(wxApprove!=null){
            wxApprove.setAuditerid(getAuditer(wxApprove.getAuditerid()));
            wxApprove.setApplyerUserid(wxuserService.selectWxuserByUserid(wxApprove.getApplyerUserid()).getName());
        }
        return success(wxApprove);
    }

    /**
     * 新增微信审批
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:WXApprove:add')")
    @Log(title = "微信审批", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxApprove wxApprove) {
        return toAjax(wxApproveService.insertWxApprove(wxApprove));
    }

    /**
     * 修改微信审批
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:WXApprove:edit')")
    @Log(title = "微信审批", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxApprove wxApprove) {
        return toAjax(wxApproveService.updateWxApprove(wxApprove));
    }

    /**
     * 删除微信审批
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:WXApprove:remove')")
    @Log(title = "微信审批", businessType = BusinessType.DELETE)
    @DeleteMapping("/{spNos}")
    public AjaxResult remove(@PathVariable String[] spNos) {
        return toAjax(wxApproveService.deleteWxApproveBySpNos(spNos));
    }
}
