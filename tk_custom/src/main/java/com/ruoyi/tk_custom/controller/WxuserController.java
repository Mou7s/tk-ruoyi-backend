package com.ruoyi.tk_custom.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.tk_custom.domain.Wxuser;
import com.ruoyi.tk_custom.service.IWxuserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业微信成员表Controller
 * 
 * @author wfs
 * @date 2025-04-19
 */
@RestController
@RequestMapping("/tk_custom/wxuser")
public class WxuserController extends BaseController
{
    @Autowired
    private IWxuserService wxuserService;

    /**
     * 查询企业微信成员表列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:wxuser:list')")
    @GetMapping("/list")
    public TableDataInfo list(Wxuser wxuser)
    {
        startPage();
        List<Wxuser> list = wxuserService.selectWxuserList(wxuser);
        return getDataTable(list);
    }

    /**
     * 导出企业微信成员表列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:wxuser:export')")
    @Log(title = "企业微信成员表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Wxuser wxuser)
    {
        List<Wxuser> list = wxuserService.selectWxuserList(wxuser);
        ExcelUtil<Wxuser> util = new ExcelUtil<Wxuser>(Wxuser.class);
        util.exportExcel(response, list, "企业微信成员表数据");
    }

    /**
     * 获取企业微信成员表详细信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:wxuser:query')")
    @GetMapping(value = "/{userid}")
    public AjaxResult getInfo(@PathVariable("userid") String userid)
    {
        return success(wxuserService.selectWxuserByUserid(userid));
    }

    /**
     * 新增企业微信成员表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:wxuser:add')")
    @Log(title = "企业微信成员表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Wxuser wxuser)
    {
        return toAjax(wxuserService.insertWxuser(wxuser));
    }

    /**
     * 修改企业微信成员表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:wxuser:edit')")
    @Log(title = "企业微信成员表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Wxuser wxuser)
    {
        return toAjax(wxuserService.updateWxuser(wxuser));
    }

    /**
     * 删除企业微信成员表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:wxuser:remove')")
    @Log(title = "企业微信成员表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userids}")
    public AjaxResult remove(@PathVariable String[] userids)
    {
        return toAjax(wxuserService.deleteWxuserByUserids(userids));
    }
}
