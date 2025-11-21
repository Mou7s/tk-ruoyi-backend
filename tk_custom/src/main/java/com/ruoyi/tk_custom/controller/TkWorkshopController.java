package com.ruoyi.tk_custom.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
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
import com.ruoyi.tk_custom.domain.TkWorkshop;
import com.ruoyi.tk_custom.service.ITkWorkshopService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 扫码登记（车间）Controller
 * 
 * @author wfs
 * @date 2025-04-19
 */
@RestController
@RequestMapping("/tk_custom/workshop")
public class TkWorkshopController extends BaseController
{
    @Autowired
    private ITkWorkshopService tkWorkshopService;

    /**
     * 查询扫码登记（车间）列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:workshop:list')")
    @GetMapping("/list")
    public TableDataInfo list(TkWorkshop tkWorkshop)
    {
        startPage();
        List<TkWorkshop> list = tkWorkshopService.selectTkWorkshopList(tkWorkshop);
        return getDataTable(list);
    }

    /**
     * 导出扫码登记（车间）列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:workshop:export')")
    @Log(title = "扫码登记（车间）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TkWorkshop tkWorkshop)
    {
        List<TkWorkshop> list = tkWorkshopService.selectTkWorkshopList(tkWorkshop);
        ExcelUtil<TkWorkshop> util = new ExcelUtil<TkWorkshop>(TkWorkshop.class);
        util.exportExcel(response, list, "扫码登记（车间）数据");
    }

    /**
     * 获取扫码登记（车间）详细信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:workshop:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(tkWorkshopService.selectTkWorkshopById(id));
    }
    /**
     * 新增扫码登记（车间）
     */
    @Anonymous
    @PostMapping(value = "/scanAdd")
    public AjaxResult scan(@RequestBody TkWorkshop tkWorkshop)
    {
        return toAjax(tkWorkshopService.insertTkWorkshop(tkWorkshop));
    }
    /**
     * 新增扫码登记（车间）
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:workshop:add')")
    @Log(title = "扫码登记（车间）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TkWorkshop tkWorkshop)
    {
        return toAjax(tkWorkshopService.insertTkWorkshop(tkWorkshop));
    }

    /**
     * 修改扫码登记（车间）
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:workshop:edit')")
    @Log(title = "扫码登记（车间）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TkWorkshop tkWorkshop)
    {
        return toAjax(tkWorkshopService.updateTkWorkshop(tkWorkshop));
    }

    /**
     * 删除扫码登记（车间）
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:workshop:remove')")
    @Log(title = "扫码登记（车间）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tkWorkshopService.deleteTkWorkshopByIds(ids));
    }
}
