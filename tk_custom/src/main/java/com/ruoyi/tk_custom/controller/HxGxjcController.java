package com.ruoyi.tk_custom.controller;

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
import com.ruoyi.tk_custom.domain.HxGxjc;
import com.ruoyi.tk_custom.service.IHxGxjcService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 焊线工序检测记录Controller
 * 
 * @author wfs
 * @date 2025-10-13
 */
@RestController
@RequestMapping("/tk_custom/gxjc")
public class HxGxjcController extends BaseController
{
    @Autowired
    private IHxGxjcService hxGxjcService;
    /**
     * 查询操作员列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gxjc:list')")
    @GetMapping("/getHandlerList")
    public List<String> getHandlerList() {
        List<String> list=hxGxjcService.getHandlerList();
        return list;
    }
    /**
     * 查询机台号列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gxjc:list')")
    @GetMapping("/getMechineList")
    public List<String> getMechineList() {
        List<String> list=hxGxjcService.getMechineList();
        return list;
    }
    @Log(title = "流程单信息获取")
    @GetMapping("/getFlowBillInfo/{flowno}")
    public JSONObject getFlowBillInfo(@PathVariable("flowno") String flowno) {
        List<List<Object>> list = KingdeeUtil.getData("SFC_OperationPlanning",
                "FSubEntity_FSeq," +//序号
                        "FBillNo," +//流程单号
                        "FProductId.FNumber," +//产品编码
                        "FProductName," +//产品名称
                        "FProSpecification," +//封装
                        "F_UNW_Text_w5c," +//芯片批号
                        "F_UNW_BaseProperty_re51," +//芯片尺寸
                        "FCustId.FShortName," +//客户名
                        "F_UNW_BaseProperty_re5,F_UNW_BaseProperty_w5c"//芯片型号
                ,
                "[" +
                        "{\"Left\":\"(\",\"FieldName\":\"FBillNo\",\"Compare\":\"67\",\"Value\":\"" + flowno + "\",\"Right\":\")\",\"Logic\":\"0\"}" +
                        "]", "", "0", "50");
        JSONObject jb = new JSONObject();
        if (list.size() > 0) {
            List<Object> objects = list.get(0);
            jb.put("FBillNo", objects.get(1));
            jb.put("productId", objects.get(2));
            jb.put("productName", objects.get(3));
            jb.put("specification", objects.get(4));
            jb.put("xpph", objects.get(5));
            jb.put("xpcc", objects.get(6));
            jb.put("khdm", objects.get(7));
            jb.put("xpxh", objects.get(8));
            jb.put("xiancai", objects.get(9));

        }
        return jb;
    }
    /**
     * 查询焊线工序检测记录列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gxjc:list')")
    @GetMapping("/list")
    public TableDataInfo list(HxGxjc hxGxjc)
    {
        startPage();
        List<HxGxjc> list = hxGxjcService.selectHxGxjcList(hxGxjc);
        return getDataTable(list);
    }

    /**
     * 导出焊线工序检测记录列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gxjc:export')")
    @Log(title = "焊线工序检测记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HxGxjc hxGxjc)
    {
        List<HxGxjc> list = hxGxjcService.selectHxGxjcList(hxGxjc);
        ExcelUtil<HxGxjc> util = new ExcelUtil<HxGxjc>(HxGxjc.class);
        for (HxGxjc gxjc : list) {
            String hgcs = gxjc.getHgcs();
            if(!"".equals(hgcs)&&hgcs!=null){
                StringBuilder sb = new StringBuilder();
                JSONObject jsonObject = JSONObject.parseObject(hgcs);
                sb.append(jsonObject.getString("no1"));
                sb.append(" ");
                sb.append(jsonObject.getString("no2"));
                sb.append(" ");
                sb.append(jsonObject.getString("no3"));
                sb.append(" ");
                sb.append(jsonObject.getString("no4"));
                gxjc.setHgcs(sb.toString());
            }
            String zdhg = gxjc.getZdhg();
            if(!"".equals(zdhg)&&zdhg!=null){
                StringBuilder sb = new StringBuilder();
                JSONObject jsonObject = JSONObject.parseObject(zdhg);
                sb.append(jsonObject.getString("no1"));
                sb.append(" ");
                sb.append(jsonObject.getString("no2"));
                sb.append(" ");
                sb.append(jsonObject.getString("no3"));
                sb.append(" ");
                sb.append(jsonObject.getString("no4"));
                gxjc.setZdhg(sb.toString());
            }
            String qhd = gxjc.getQhd();
            if(!"".equals(qhd)&&qhd!=null){
                StringBuilder sb = new StringBuilder();
                JSONObject jsonObject = JSONObject.parseObject(qhd);
                sb.append(jsonObject.getString("no1"));
                sb.append(" ");
                sb.append(jsonObject.getString("no2"));
                sb.append(" ");
                sb.append(jsonObject.getString("no3"));
                sb.append(" ");
                sb.append(jsonObject.getString("no4"));
                gxjc.setQhd(sb.toString());
            }
            String qzj = gxjc.getQzj();
            if(!"".equals(qzj)&&qzj!=null){
                StringBuilder sb = new StringBuilder();
                JSONObject jsonObject = JSONObject.parseObject(qzj);
                sb.append(jsonObject.getString("no1"));
                sb.append(" ");
                sb.append(jsonObject.getString("no2"));
                sb.append(" ");
                sb.append(jsonObject.getString("no3"));
                sb.append(" ");
                sb.append(jsonObject.getString("no4"));
                gxjc.setQzj(sb.toString());
            }
            String xll = gxjc.getXll();
            if(!"".equals(xll)&&xll!=null){
                StringBuilder sb = new StringBuilder();
                JSONObject jsonObject = JSONObject.parseObject(xll);
                sb.append(jsonObject.getString("no1"));
                sb.append(" ");
                sb.append(jsonObject.getString("no2"));
                sb.append(" ");
                sb.append(jsonObject.getString("no3"));
                sb.append(" ");
                sb.append(jsonObject.getString("no4"));
                sb.append(" ");
                sb.append(jsonObject.getString("no5"));
                sb.append(" ");
                sb.append(jsonObject.getString("no6"));
                sb.append(" ");
                sb.append(jsonObject.getString("no7"));
                sb.append(" ");
                sb.append(jsonObject.getString("no8"));
                gxjc.setXll(sb.toString());
            }
            String xtl = gxjc.getXtl();
            if(!"".equals(xtl)&&xtl!=null){
                StringBuilder sb = new StringBuilder();
                JSONObject jsonObject = JSONObject.parseObject(xtl);
                sb.append(jsonObject.getString("no1"));
                sb.append(" ");
                sb.append(jsonObject.getString("no2"));
                sb.append(" ");
                sb.append(jsonObject.getString("no3"));
                sb.append(" ");
                sb.append(jsonObject.getString("no4"));
                sb.append(" ");
                sb.append(jsonObject.getString("no5"));
                sb.append(" ");
                sb.append(jsonObject.getString("no6"));
                sb.append(" ");
                sb.append(jsonObject.getString("no7"));
                sb.append(" ");
                sb.append(jsonObject.getString("no8"));
                gxjc.setXtl(sb.toString());
            }
        }
        util.exportExcel(response, list, "焊线工序检测记录数据");
    }

    /**
     * 获取焊线工序检测记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gxjc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(hxGxjcService.selectHxGxjcById(id));
    }

    /**
     * 新增焊线工序检测记录
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gxjc:add')")
    @Log(title = "焊线工序检测记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HxGxjc hxGxjc)
    {
        return toAjax(hxGxjcService.insertHxGxjc(hxGxjc));
    }

    /**
     * 修改焊线工序检测记录
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gxjc:edit')")
    @Log(title = "焊线工序检测记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HxGxjc hxGxjc)
    {
        return toAjax(hxGxjcService.updateHxGxjc(hxGxjc));
    }

    /**
     * 删除焊线工序检测记录
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:gxjc:remove')")
    @Log(title = "焊线工序检测记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hxGxjcService.deleteHxGxjcByIds(ids));
    }
}
