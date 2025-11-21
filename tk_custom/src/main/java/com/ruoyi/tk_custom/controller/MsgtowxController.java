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
import com.ruoyi.tk_custom.domain.Msgtowx;
import com.ruoyi.tk_custom.service.IMsgtowxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 发送到企微的消息队列Controller
 * 
 * @author wfs
 * @date 2025-08-12
 */
@RestController
@RequestMapping("/tk_custom/msgtowx")
public class MsgtowxController extends BaseController
{
    @Autowired
    private IMsgtowxService msgtowxService;

    /**
     * 查询发送到企微的消息队列列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:msgtowx:list')")
    @GetMapping("/list")
    public TableDataInfo list(Msgtowx msgtowx)
    {
        startPage();
        List<Msgtowx> list = msgtowxService.selectMsgtowxList(msgtowx);
        return getDataTable(list);
    }

    /**
     * 导出发送到企微的消息队列列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:msgtowx:export')")
    @Log(title = "发送到企微的消息队列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Msgtowx msgtowx)
    {
        List<Msgtowx> list = msgtowxService.selectMsgtowxList(msgtowx);
        ExcelUtil<Msgtowx> util = new ExcelUtil<Msgtowx>(Msgtowx.class);
        util.exportExcel(response, list, "发送到企微的消息队列数据");
    }

    /**
     * 获取发送到企微的消息队列详细信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:msgtowx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(msgtowxService.selectMsgtowxById(id));
    }

    /**
     * 新增发送到企微的消息队列
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:msgtowx:add')")
    @Log(title = "发送到企微的消息队列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Msgtowx msgtowx)
    {
        return toAjax(msgtowxService.insertMsgtowx(msgtowx));
    }

    /**
     * 修改发送到企微的消息队列
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:msgtowx:edit')")
    @Log(title = "发送到企微的消息队列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Msgtowx msgtowx)
    {
        return toAjax(msgtowxService.updateMsgtowx(msgtowx));
    }

    /**
     * 删除发送到企微的消息队列
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:msgtowx:remove')")
    @Log(title = "发送到企微的消息队列", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(msgtowxService.deleteMsgtowxByIds(ids));
    }
}
