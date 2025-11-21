package com.ruoyi.tk_custom.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.tk_custom.domain.Wxuser;
import com.ruoyi.tk_custom.mapper.WxuserMapper;
import com.ruoyi.tk_custom.service.IWxuserService;
import com.ruoyi.tk_custom.utils.WXUtil;
import org.apache.ibatis.jdbc.Null;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import com.ruoyi.tk_custom.domain.TkVisit;
import com.ruoyi.tk_custom.service.ITkVisitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 来访登记Controller
 *
 * @author wfs
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/tk_custom/visit")
public class TkVisitController extends BaseController {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    @Autowired
    private ITkVisitService tkVisitService;

    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private IWxuserService wxuserService;
    /**
     * 查询来访登记列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:visit:list')")
    @GetMapping("/list")
    public TableDataInfo list(TkVisit tkVisit) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        List<SysRole> roles = user.getRoles();
        boolean isAdmin=false;
        for (SysRole role : roles) {
            if(role.getRoleId()==1||role.getRoleId()==100){
                isAdmin=true;
                break;
            }
        }
        if(!isAdmin)
            tkVisit.setvDate(DateUtils.parseDate(DateUtils.getDate()));
        startPage();
        List<TkVisit> list = tkVisitService.selectTkVisitList(tkVisit);
        return getDataTable(list);
    }
//    /**
//     * 查询来访登记列表
//     */
//    @Anonymous
//    @GetMapping("/lookScreen")
//    public TableDataInfo lookScreen(TkVisit tkVisit) {
//        tkVisit.setIsLook("1");
//        List<TkVisit> list = tkVisitService.selectTkVisitList(tkVisit);
//        for (TkVisit visit : list) {
//            String phone = visit.getvTelephone();
//            if(!"".equals(phone)&&phone!=null)
//            visit.setvTelephone(phone.substring(0, 3) + "****" + phone.substring(7, 11));
//        }
//        return getDataTable(list);
//    }
    /**
     * 导出来访登记列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:visit:export')")
    @Log(title = "来访登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TkVisit tkVisit) {
        List<TkVisit> list = tkVisitService.selectTkVisitList(tkVisit);
        ExcelUtil<TkVisit> util = new ExcelUtil<TkVisit>(TkVisit.class);
        util.exportExcel(response, list, "来访登记数据");
    }

    /**
     * 获取来访登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:visit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(tkVisitService.selectTkVisitById(id));
    }
    public void sendMessageToPerson(TkVisit entity,String userid){
//        WXUtil.sendMsg(list.get(0).getId(),
//                entity.getCompany()+"的"+entity.getVname()+"提交了来访登记单，预约的来访时间为"+entity.getVdate().substring(0,16)
//                        +",立即去<a href='http://wx.ctk-elec.com:1688/views/approve.html?id="+id+"'>审核</a>");
    }
    /**
     * 扫码登记
     */
//    @Anonymous
////    @Log(title = "扫码登记", businessType = BusinessType.INSERT)
//    @PostMapping(value = "/scanAdd")
//    public AjaxResult scanAdd(@RequestBody TkVisit tkVisit) {
//        Wxuser wxuser = wxuserService.selectWxuserByUserName(tkVisit.getReceiver());
//        if(wxuser==null)
//            return new AjaxResult(500,"被访人不存在！");
//        int i = tkVisitService.insertTkVisit(tkVisit);
//        //发送消息提醒到企业微信
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                sendTest(tkVisit,0);
//            }
//        });
//        return toAjax(i);
//    }
//    /**
//     * 快速扫码登记
//     */
//    @Anonymous
////    @Log(title = "快速扫码登记", businessType = BusinessType.INSERT)
//    @PostMapping(value = "/simpleAdd")
//    public AjaxResult simpleAdd(@RequestBody TkVisit tkVisit) {
//        TkVisit tv= tkVisitService.selectTkvisitByPhoneNumber(tkVisit.getvTelephone());
//        if(tv==null)
//            return toAjax(0);
//        tkVisit.setvCardid(tv.getvCardid());
//        Wxuser wxuser = wxuserService.selectWxuserByUserName(tkVisit.getReceiver());
//        if(wxuser==null)
//            return new AjaxResult(500,"被访人不存在！");
//        int i = tkVisitService.insertTkVisit(tkVisit);
//        //发送消息提醒到企业微信
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                sendTest(tkVisit,0);
//            }
//        });
//        return toAjax(i);
//    }
//    /**
//     * 出厂确定接口
//     */
//    @Anonymous
//    @PostMapping(value = "/updateOutTime")
//    public AjaxResult updateOutTime( TkVisit tkVisit) {
//        TkVisit visit = tkVisitService.selectTkVisitById(tkVisit.getId());
//        if (visit==null||visit.getOuttime()!=null)
//            return toAjax(0);
//        visit.setOuttime(new Date());
//        int i = tkVisitService.updateTkVisit(visit);
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                sendTest(visit,0);
//            }
//        });
//        return toAjax(i);
//    }
    /**
     * 新增来访登记
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:visit:add')")
    @Log(title = "来访登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TkVisit tkVisit) {
        return toAjax(tkVisitService.insertTkVisit(tkVisit));
    }

    /**
     * 修改来访登记
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:visit:edit')")
    @Log(title = "来访登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TkVisit tkVisit) {
        return toAjax(tkVisitService.updateTkVisit(tkVisit));
    }

//    /**
//     * 依手机号查询来访登记
//     */
//    @Anonymous
////    @Log(title = "依手机号查询来访登记")
//    @GetMapping(value = "/selectByPhone/{vTelephone}")
//    public AjaxResult selectVisitByPhone(@PathVariable String vTelephone) {
//        return success(tkVisitService.selectTkvisitByPhoneNumber(vTelephone));
//    }
    /**
     * 删除来访登记
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:visit:remove')")
    @Log(title = "来访登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(tkVisitService.deleteTkVisitByIds(ids));
    }
    /**
     * 审核
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:visit:approve')")
    @Log(title = "来访登记", businessType = BusinessType.UPDATE)
    @GetMapping(value = "/approve/{id}")
    public AjaxResult approve(@PathVariable String id) {
        TkVisit tkVisit = new TkVisit();
        tkVisit.setId(id);
        TkVisit tv = tkVisitService.selectTkVisitById(id);
        if ("Y".equals(tv.getApprove())){
            tkVisit.setApprove("N");
            tkVisit.setApprovedate(new Date());
            tkVisit.setApprover("");
        }
        else {
            tkVisit.setApprove("Y");
            tkVisit.setApprovedate(new Date());
            String username = SecurityUtils.getUsername();
            tkVisit.setApprover(username);
        }

        return toAjax(tkVisitService.updateTkVisit(tkVisit));
    }

    public void sendTest(TkVisit entity, int times) {
        if (times > 10){
            System.out.println("发送消息到企业微信失败，请检查！！");
            return;
        }
        HashMap<String, Object> hp = new HashMap<>();
        hp.put("chatid", "123456");//88888888    666666
        hp.put("msgtype", "text");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("content",getTemplate(entity,true));
        hp.put("text",hashMap);
        String s = WXUtil.sendMsgToChatRoom(hp);
        times++;
        if (!"ok".equals(new Gson().fromJson(s, JsonObject.class).get("errmsg").getAsString())) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendTest(entity, times);
        }
    }
//    public void sendCustomMsg(TkVisit entity) {
//        HashMap<String, Object> hp = new HashMap<>();
//        hp.put("chatid", "666666");//88888888    666666
//        hp.put("msgtype", "text");
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("content",getTemplate(entity,true));
//        hp.put("text",hashMap);
//        WXUtil.sendMsgToChatRoom(hp);
//    }
//    public void sendMsg(TkVisit entity) {
//        HashMap<String, Object> hp = new HashMap<>();
//        hp.put("chatid", "88888888");//88888888    666666
//        hp.put("msgtype", "text");
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("content",getTemplate(entity,true));
//        hp.put("text",hashMap);
//        WXUtil.sendMsgToChatRoom(hp);
//    }
    public String getTemplate(TkVisit entity,boolean hidePhone){
        String telephone = entity.getvTelephone();
        if (hidePhone&&telephone!=null && telephone.length() == 11)
            telephone = telephone.substring(0, 3) + "****" + telephone.substring(7, 11);
        String carNumber=entity.getCarNumber();
        if(carNumber==null)
            carNumber="";
        StringBuilder sb = new StringBuilder();
        Date outtime = entity.getOuttime();
        if(outtime!=null)
        sb.append("【 已 出 厂 区 】\n");
        sb.append("入厂时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entity.getvDate()) + "\n" );
        if(outtime!=null)
        sb.append("出厂时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(outtime)  + "\n");
        sb.append("访客姓名：" + entity.getvName()+ "\n" );
        sb.append("洽谈地点：" + dictDataService.selectDictLabel("tk_visit_room",entity.getRoom()) + "\n" );
        sb.append("手机号码：" + telephone + "\n" );
        sb.append("车牌号码：" + carNumber + "\n");
        sb.append("来访公司：" + entity.getCompany() + "\n" );
        sb.append("来访事由：" + entity.getvReason() + "\n");
        sb.append("来访人数：" + entity.getPeoplecount() + "\n");
        sb.append("需要鞋套(布)：" + dictDataService.selectDictLabel("is_or_not",entity.getNeedshoes()) + "\n");
        sb.append("被 访 人 ：" + entity.getReceiver());
        return  sb.toString();
    }
}
