package com.ruoyi.tk_custom.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.tk_custom.domain.Msgtowx;
import com.ruoyi.tk_custom.service.IMsgtowxService;
import com.ruoyi.tk_custom.service.impl.MsgtowxServiceImpl;
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
import com.ruoyi.tk_custom.domain.TkSuggest;
import com.ruoyi.tk_custom.service.ITkSuggestService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 意见/建议Controller
 * 
 * @author wfs
 * @date 2025-08-11
 */
@RestController
@RequestMapping("/tk_custom/suggest")
public class TkSuggestController extends BaseController
{
    @Autowired
    private ITkSuggestService tkSuggestService;
    @Autowired
    private IMsgtowxService msgtowxService;
    /**
     * 查询意见/建议列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:suggest:list')")
    @GetMapping("/list")
    public TableDataInfo list(TkSuggest tkSuggest)
    {
        startPage();
        List<TkSuggest> list = tkSuggestService.selectTkSuggestList(tkSuggest);
        return getDataTable(list);
    }

    /**
     * 导出意见/建议列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:suggest:export')")
    @Log(title = "意见/建议", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TkSuggest tkSuggest)
    {
        List<TkSuggest> list = tkSuggestService.selectTkSuggestList(tkSuggest);
        ExcelUtil<TkSuggest> util = new ExcelUtil<TkSuggest>(TkSuggest.class);
        util.exportExcel(response, list, "意见/建议数据");
    }

    /**
     * 获取意见/建议详细信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:suggest:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(tkSuggestService.selectTkSuggestById(id));
    }

    /**
     * 新增意见/建议
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:suggest:add')")
    @Log(title = "意见/建议", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TkSuggest tkSuggest)
    {
        return toAjax(tkSuggestService.insertTkSuggest(tkSuggest));
    }
    /**
     * 新增意见/建议
     */
    @Anonymous
    @PostMapping("/scanAdd")
    public AjaxResult scanAdd(@RequestBody TkSuggest tkSuggest)
    {
        String Id= UUID.randomUUID().toString();
        Date currentDate=new Date();
        tkSuggest.setSuggestTime(currentDate);
        tkSuggest.setId(Id);
        String ipAddr = IpUtils.getIpAddr();
        TkSuggest tkSuggest1 = new TkSuggest();
        tkSuggest1.setIpAdress(ipAddr);
        tkSuggest1.setNoSend("0");
        List<TkSuggest> tkSuggests = tkSuggestService.selectTkSuggestList(tkSuggest1);
        if(tkSuggests.size()>0&&(new Date().getTime()-tkSuggests.get(0).getSuggestTime().getTime())<3*60*1000)
            return AjaxResult.error("3分钟内只允许提交一次！");
        tkSuggest.setIpAdress(ipAddr);
        if(WXUtil.veryWords(tkSuggest.getSuggestText())){
            tkSuggest.setNoSend("1");
            tkSuggestService.insertTkSuggest(tkSuggest);
            return AjaxResult.error("内容包含敏感词汇，请修正后提交！");
        }
//加入定时消息队列
            Msgtowx msgtowx = new Msgtowx();
            msgtowx.setType("匿名意见");
            msgtowx.setMsg(tkSuggest.getSuggestText());
            msgtowx.setCreatetime(currentDate);
            msgtowx.setParentid(Id);
        int i = msgtowxService.insertMsgtowx(msgtowx);
        if(i<1)
            return AjaxResult.error("失败！请重新提交！");
        return toAjax(tkSuggestService.insertTkSuggest(tkSuggest));
    }

    /**
     * 修改意见/建议
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:suggest:edit')")
    @Log(title = "意见/建议", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TkSuggest tkSuggest)
    {
        return toAjax(tkSuggestService.updateTkSuggest(tkSuggest));
    }

    /**
     * 删除意见/建议
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:suggest:remove')")
    @Log(title = "意见/建议", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tkSuggestService.deleteTkSuggestByIds(ids));
    }
}
