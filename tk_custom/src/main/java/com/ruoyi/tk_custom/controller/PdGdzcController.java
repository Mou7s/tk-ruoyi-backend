package com.ruoyi.tk_custom.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.tk_custom.utils.KingdeeUtil;
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
import com.ruoyi.tk_custom.domain.PdGdzc;
import com.ruoyi.tk_custom.service.IPdGdzcService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 固定资产盘点Controller
 * 
 * @author wfs
 * @date 2025-05-09
 */
@RestController
@RequestMapping("/tk_custom/gdzc")
public class PdGdzcController extends BaseController
{
    @Autowired
    private IPdGdzcService pdGdzcService;
    /**
     * 查询固定资产盘点列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gdzc:list')")
    @GetMapping("/selectFromKingdee/{billno}")
    public  PdGdzc selectFromKingdee(@PathVariable String billno){
        JSONObject card_jb = KingdeeUtil.queryOne("FA_CARD",
                "FNumber,FVCHASSETNO,FName,FSpecification,FPositionID.FName,FAllocUseDeptID.FName,FQuantity",
                "[{\"Left\": \"\",\"FieldName\": \"FNumber\",\"Compare\": \"67\",\"Value\": \""+billno+"\",\"Right\": \"\",\"Logic\": 0}]","FISNEWREC desc");
        PdGdzc pdGdzc = new PdGdzc();
        pdGdzc.setKpBm(card_jb.getString("FNumber"));
        pdGdzc.setZcBm(card_jb.getString("FVCHASSETNO"));
        pdGdzc.setName(card_jb.getString("FName"));
        pdGdzc.setGgxh(card_jb.getString("FSpecification"));
        pdGdzc.setZcLocation(card_jb.getString("FPositionID.FName"));
        pdGdzc.setUseDept(card_jb.getString("FAllocUseDeptID.FName"));
        pdGdzc.setNumber(card_jb.getBigDecimal("FQuantity"));
        return pdGdzc;
    }

    public static void main(String[] args) {
        System.out.println();
    }
    /**
     * 查询固定资产盘点列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gdzc:list')")
    @GetMapping("/list")
    public TableDataInfo list(PdGdzc pdGdzc) {
        startPage();
        List<PdGdzc> list = pdGdzcService.selectPdGdzcList(pdGdzc);
        return getDataTable(list);
    }

    /**
     * 导出固定资产盘点列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gdzc:export')")
    @Log(title = "固定资产盘点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PdGdzc pdGdzc)
    {
        List<PdGdzc> list = pdGdzcService.selectPdGdzcList(pdGdzc);
        ExcelUtil<PdGdzc> util = new ExcelUtil<PdGdzc>(PdGdzc.class);
        util.exportExcel(response, list, "固定资产盘点数据");
    }

    /**
     * 获取固定资产盘点详细信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gdzc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(pdGdzcService.selectPdGdzcById(id));
    }

    /**
     * 新增固定资产盘点
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gdzc:add')")
    @Log(title = "固定资产盘点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PdGdzc pdGdzc)
    {
        return toAjax(pdGdzcService.insertPdGdzc(pdGdzc));
    }

    /**
     * 修改固定资产盘点
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gdzc:edit')")
    @Log(title = "固定资产盘点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PdGdzc pdGdzc)
    {
        return toAjax(pdGdzcService.updatePdGdzc(pdGdzc));
    }

    /**
     * 删除固定资产盘点
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gdzc:remove')")
    @Log(title = "固定资产盘点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(pdGdzcService.deletePdGdzcByIds(ids));
    }
}
