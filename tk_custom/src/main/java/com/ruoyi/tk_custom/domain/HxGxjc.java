package com.ruoyi.tk_custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 焊线工序检测记录对象 hx_gxjc
 * 
 * @author wfs
 * @date 2025-10-13
 */
public class HxGxjc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 流程单号 */
    @Excel(name = "流程单号")
    private String flowno;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialno;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialname;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 机台号 */
    @Excel(name = "机台号")
    private String mechineName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdate;

    /** 最后修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date modifydate;

    /** 客户代码 */
    @Excel(name = "客户代码")
    private String khdm;

    /** 操作/技术员 */
    @Excel(name = "操作/技术员")
    private String handler;

    /** 创建人 */
    @Excel(name = "创建人")
    private String creator;

    /** 最后修改人 */
    @Excel(name = "最后修改人")
    private String updater;

    /** 判定结果 */
    @Excel(name = "判定结果")
    private String result;

    /** 试验时机 */
    @Excel(name = "试验时机")
    private String testCharge;

    /** 线材 */
    @Excel(name = "线材")
    private String xiancai;

    /** 线径 */
    @Excel(name = "线径")
    private String xianjing;

    /** 弧高测试(um) */
    @Excel(name = "弧高测试(um)")
    private String hgcs;

    /** 最低弧高 */
    @Excel(name = "最低弧高")
    private String zdhg;

    /** 球厚度(um) */
    @Excel(name = "球厚度(um)")
    private String qhd;

    /** 球直径（um) */
    @Excel(name = "球直径")
    private String qzj;

    /** 线拉力(g) */
    @Excel(name = "线拉力(g)")
    private String xll;

    /** 线推力（g） */
    @Excel(name = "线推力")
    private String xtl;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFlowno(String flowno) 
    {
        this.flowno = flowno;
    }

    public String getFlowno() 
    {
        return flowno;
    }
    public void setMaterialno(String materialno) 
    {
        this.materialno = materialno;
    }

    public String getMaterialno() 
    {
        return materialno;
    }
    public void setMaterialname(String materialname) 
    {
        this.materialname = materialname;
    }

    public String getMaterialname() 
    {
        return materialname;
    }
    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }
    public void setMechineName(String mechineName) 
    {
        this.mechineName = mechineName;
    }

    public String getMechineName() 
    {
        return mechineName;
    }
    public void setCreatedate(Date createdate) 
    {
        this.createdate = createdate;
    }

    public Date getCreatedate() 
    {
        return createdate;
    }
    public void setModifydate(Date modifydate) 
    {
        this.modifydate = modifydate;
    }

    public Date getModifydate() 
    {
        return modifydate;
    }
    public void setKhdm(String khdm) 
    {
        this.khdm = khdm;
    }

    public String getKhdm() 
    {
        return khdm;
    }
    public void setHandler(String handler) 
    {
        this.handler = handler;
    }

    public String getHandler() 
    {
        return handler;
    }
    public void setCreator(String creator) 
    {
        this.creator = creator;
    }

    public String getCreator() 
    {
        return creator;
    }
    public void setUpdater(String updater) 
    {
        this.updater = updater;
    }

    public String getUpdater() 
    {
        return updater;
    }
    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
    }
    public void setTestCharge(String testCharge) 
    {
        this.testCharge = testCharge;
    }

    public String getTestCharge() 
    {
        return testCharge;
    }
    public void setXiancai(String xiancai) 
    {
        this.xiancai = xiancai;
    }

    public String getXiancai() 
    {
        return xiancai;
    }
    public void setXianjing(String xianjing) 
    {
        this.xianjing = xianjing;
    }

    public String getXianjing() 
    {
        return xianjing;
    }
    public void setHgcs(String hgcs) 
    {
        this.hgcs = hgcs;
    }

    public String getHgcs() 
    {
        return hgcs;
    }
    public void setZdhg(String zdhg) 
    {
        this.zdhg = zdhg;
    }

    public String getZdhg() 
    {
        return zdhg;
    }
    public void setQhd(String qhd) 
    {
        this.qhd = qhd;
    }

    public String getQhd() 
    {
        return qhd;
    }
    public void setQzj(String qzj) 
    {
        this.qzj = qzj;
    }

    public String getQzj() 
    {
        return qzj;
    }
    public void setXll(String xll) 
    {
        this.xll = xll;
    }

    public String getXll() 
    {
        return xll;
    }
    public void setXtl(String xtl) 
    {
        this.xtl = xtl;
    }

    public String getXtl() 
    {
        return xtl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("flowno", getFlowno())
            .append("materialno", getMaterialno())
            .append("materialname", getMaterialname())
            .append("specification", getSpecification())
            .append("remark", getRemark())
            .append("mechineName", getMechineName())
            .append("createdate", getCreatedate())
            .append("modifydate", getModifydate())
            .append("khdm", getKhdm())
            .append("handler", getHandler())
            .append("creator", getCreator())
            .append("updater", getUpdater())
            .append("result", getResult())
            .append("testCharge", getTestCharge())
            .append("xiancai", getXiancai())
            .append("xianjing", getXianjing())
            .append("hgcs", getHgcs())
            .append("zdhg", getZdhg())
            .append("qhd", getQhd())
            .append("qzj", getQzj())
            .append("xll", getXll())
            .append("xtl", getXtl())
            .toString();
    }
}
