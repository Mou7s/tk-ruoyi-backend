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
import com.ruoyi.tk_custom.domain.PdCangku;
import com.ruoyi.tk_custom.service.IPdCangkuService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 盘点_仓库Controller
 * 
 * @author ruoyi
 * @date 2025-03-21
 */
@RestController
@RequestMapping("/tk_custom/pd_ck")
public class PdCangkuController extends BaseController
{
    @Autowired
    private IPdCangkuService pdCangkuService;

    /**
     * 查询盘点_仓库列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd_ck:list')")
    @GetMapping("/list")
    public TableDataInfo list(PdCangku pdCangku)
    {
        startPage();
        List<PdCangku> list = pdCangkuService.selectPdCangkuList(pdCangku);
        return getDataTable(list);
    }

    /**
     * 导出盘点_仓库列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd_ck:export')")
    @Log(title = "盘点_仓库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PdCangku pdCangku)
    {
        List<PdCangku> list = pdCangkuService.selectPdCangkuList(pdCangku);
        ExcelUtil<PdCangku> util = new ExcelUtil<PdCangku>(PdCangku.class);
        util.exportExcel(response, list, "盘点_仓库数据");
    }

    /**
     * 获取盘点_仓库详细信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd_ck:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(pdCangkuService.selectPdCangkuById(id));
    }

    /**
     * 新增盘点_仓库
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd_ck:add')")
    @Log(title = "盘点_仓库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PdCangku pdCangku)
    {
        return toAjax(pdCangkuService.insertPdCangku(pdCangku));
    }

    /**
     * 修改盘点_仓库
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd_ck:edit')")
    @Log(title = "盘点_仓库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PdCangku pdCangku)
    {
        return toAjax(pdCangkuService.updatePdCangku(pdCangku));
    }

    /**
     * 删除盘点_仓库
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd_ck:remove')")
    @Log(title = "盘点_仓库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(pdCangkuService.deletePdCangkuByIds(ids));
    }
}
