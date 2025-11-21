package com.ruoyi.tk_custom.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.tk_custom.domain.Kucun_Sum;
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
import com.ruoyi.tk_custom.domain.Kucun;
import com.ruoyi.tk_custom.service.IKucunService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 金蝶库存查询Controller
 * 
 * @author wfs
 * @date 2025-06-18
 */
@RestController
@RequestMapping("/tk_custom/kucun")
public class KucunController extends BaseController
{
    @Autowired
    private IKucunService kucunService;

    /**
     * 查询金蝶库存查询列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:kucun:list')")
    @GetMapping("/list")
    public TableDataInfo list(Kucun kucun)
    {
        startPage();
        List<Kucun> list = kucunService.selectKucunList(kucun);
        return getDataTable(list);
    }
    /**
     * 查询金蝶库存查询列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:kucun:list')")
    @GetMapping("/list_sum")
    public TableDataInfo list_sum(Kucun kucun)
    {
        startPage();
        List<Kucun_Sum> list = kucunService.selectKucunSumList(kucun);
        return getDataTable(list);
    }
    /**
     * 查询金蝶库存总计
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:kucun:list')")
    @GetMapping("/list_sum_total")
    public JSONObject list_sum_total(Kucun kucun)
    {
        JSONObject jsonObject = new JSONObject();
        BigDecimal xsth = kucunService.selectXSTHTotal(kucun);
        BigDecimal xsch = kucunService.selectXSCHTotal(kucun);
        if(xsth==null)
            xsth=BigDecimal.ZERO;
        if(xsch==null)
            xsch=BigDecimal.ZERO;
        BigDecimal total = xsch.add(xsth);
        jsonObject.put("xsth",xsth);
        jsonObject.put("xsch",xsch);
        jsonObject.put("total",total);
        return jsonObject;
    }
    /**
     * 导出金蝶库存查询列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:kucun:export')")
    @Log(title = "金蝶库存查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Kucun kucun)
    {
        List<Kucun> list = kucunService.selectKucunList(kucun);
        ExcelUtil<Kucun> util = new ExcelUtil<Kucun>(Kucun.class);
        util.exportExcel(response, list, "金蝶库存查询数据");
    }
    /**
     * 导出金蝶库存查询列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:kucun:export')")
    @Log(title = "金蝶库存查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export1")
    public void export1(HttpServletResponse response, Kucun kucun)
    {
        List<Kucun_Sum> kucun_sums = kucunService.selectKucunSumList(kucun);
        ExcelUtil<Kucun_Sum> util = new ExcelUtil<Kucun_Sum>(Kucun_Sum.class);
        util.exportExcel(response, kucun_sums, "金蝶库存查询数据");
    }
    /**
     * 获取金蝶库存查询详细信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:kucun:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(kucunService.selectKucunById(id));
    }

    /**
     * 新增金蝶库存查询
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:kucun:add')")
    @Log(title = "金蝶库存查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Kucun kucun)
    {
        return toAjax(kucunService.insertKucun(kucun));
    }

    /**
     * 修改金蝶库存查询
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:kucun:edit')")
    @Log(title = "金蝶库存查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Kucun kucun)
    {
        return toAjax(kucunService.updateKucun(kucun));
    }

    /**
     * 删除金蝶库存查询
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:kucun:remove')")
    @Log(title = "金蝶库存查询", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(kucunService.deleteKucunByIds(ids));
    }
}
