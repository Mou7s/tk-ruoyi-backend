package com.ruoyi.tk_custom.controller;

import java.math.BigDecimal;
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
import com.ruoyi.tk_custom.domain.Pd;
import com.ruoyi.tk_custom.service.IPdService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 盘点Controller
 * 
 * @author wfs
 * @date 2024-11-27
 */
@RestController
@RequestMapping("/tk_custom/pd")
public class PdController extends BaseController
{
    @Autowired
    private IPdService pdService;

    /**
     * 查询盘点列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd:list')")
    @GetMapping("/list")
    public TableDataInfo list(Pd pd)
    {
        startPage();
        List<Pd> list = pdService.selectPdList(pd);
        return getDataTable(list);
    }

    /**
     * 导出盘点列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd:export')")
    @Log(title = "盘点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Pd pd)
    {
        List<Pd> list = pdService.selectPdList(pd);
        ExcelUtil<Pd> util = new ExcelUtil<Pd>(Pd.class);
        util.exportExcel(response, list, "盘点数据");
    }

    /**
     * 获取盘点详细信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(pdService.selectPdById(id));
    }
    /**
     * 获取盘点详细信息--从金蝶系统获取数据
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd:query')")
    @GetMapping(value = "/kingdee/{id}")
    public AjaxResult getInfoFromKingdee(@PathVariable("id") String id)
    {
        //todo   处理分单盘点问题
        Pd pdDataFromKingdee=null;
        Pd queryPd = new Pd();
        queryPd.setFlowNo(id);
        List<Pd> pds = pdService.selectPdList(queryPd);
        if(pds.size()>0){
            Pd pd = pds.get(0);
            String pdDate =pd.getPdDate().replace("-","/");
            if(new Date().getTime()-new Date(pdDate).getTime()<7*24*60*60*1000) {//超过7天，视为上次盘点
                pdDataFromKingdee=pd;
                pdDataFromKingdee.setIsUpdatePdData("1");
            }else {
                pdDataFromKingdee= getPdDataFromKingdee(id);
            }
        }else{
            pdDataFromKingdee= getPdDataFromKingdee(id);
        }

        return success(pdDataFromKingdee);
    }

    public static void main(String[] args) {
        String billno="TW020378";

    }
    //工序计划的半成品数据
    public static Pd getPdDataFromKingdee(String billno){
        List<List<Object>> list = KingdeeUtil.getData("SFC_OperationPlanning",
                "FSubEntity_FSeq," +//序号
                        "FBillNo," +//流程单号
                        "FMONumber," +//生产订单编号
                        "FProductId.FNumber," +//产品编码
                        "FProductName," +//产品名称
                        "FProSpecification,"+//封装
                        "FQualifiedQty," +//合格数量（工序汇报数量）
                        "FTransOutQty," +//转出数量
                        "FTransInQty," +//转入数量,
                        "FOperQty", //工序数量
                "[" +
                        "{\"Left\":\"(\",\"FieldName\":\"FBillNo\",\"Compare\":\"67\",\"Value\":\"" + billno + "\",\"Right\":\")\",\"Logic\":\"0\"}" +
                        "]", "", "0", "50");
        if(list.size()==0){
            Pd pd_one = getPdDatafromKingdee_one(billno);
            if(pd_one!=null){
                return pd_one;
            }
            return null;
        }
        Pd pd = new Pd();
        List<Object> objects = list.get(0);
        pd.setFlowNo((String)objects.get(1));
        pd.setProductId((String)objects.get(3));
        pd.setProductName((String)objects.get(4));
        pd.setSpecification((String)objects.get(5));
        Double db=0.0;
        Double rukuNumber=(Double)list.get(list.size()-1).get(6);//入库数量
        if(rukuNumber==0.0) {
            for (List<Object> obj : list) {
                Double db1 = (Double) obj.get(6);//汇报合格数量
                if (db1 > 0.0)
                    db = db1;
            }
            if (db == 0.0)
                db = (Double) objects.get(9);
        }else{
            Double gujingNumber=(Double)list.get(0).get(6);//第一个工序的合格数量
            db=(gujingNumber*1000-rukuNumber*1000)/1000;
        }

        pd.setNumber(new BigDecimal(db.doubleValue()));
        return pd;
    }
    //即时库存中的数据
    public static Pd getPdDatafromKingdee_one(String billno){
        JSONObject jb = KingdeeUtil.queryOne("STK_Inventory",
                "FMaterialId.FNumber,FMaterialId.FName,FMaterialId.FSpecification,FLot.FNumber,FAVBQty,FBaseQty,FMaterialid.FSTOREURNOM,FMaterialid.FSTOREURNUM",
                "[" +
                        "{\"Left\":\"(\",\"FieldName\":\"FLot.FNumber\",\"Compare\":\"67\",\"Value\":\"" + billno + "\",\"Right\":\")\",\"Logic\":\"0\"}" +
                        "]");
        if(jb.isEmpty())
            return null;
        Pd pd = new Pd();
        pd.setFlowNo(jb.getString("FLot.FNumber"));
        pd.setProductId(jb.getString("FMaterialId.FNumber"));
        pd.setProductName(jb.getString("FMaterialId.FName"));
        pd.setSpecification(jb.getString("FMaterialId.FSpecification"));
        pd.setNumber(jb.getBigDecimal("FBaseQty"));
        pd.setLot(jb.getString("FLot.FNumber"));
        return pd;
    }

    /**
     * 新增盘点
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd:add')")
    @Log(title = "盘点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Pd pd)
    {
        return toAjax(pdService.insertPd(pd));
    }

    /**
     * 修改盘点
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd:edit')")
    @Log(title = "盘点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Pd pd)
    {
        return toAjax(pdService.updatePd(pd));
    }

    /**
     * 删除盘点
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pd:remove')")
    @Log(title = "盘点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(pdService.deletePdByIds(ids));
    }
}
