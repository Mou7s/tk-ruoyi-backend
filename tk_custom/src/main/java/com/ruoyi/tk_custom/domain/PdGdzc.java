package com.ruoyi.tk_custom.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 固定资产盘点对象 pd_gdzc
 * 
 * @author wfs
 * @date 2025-05-14
 */
public class PdGdzc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private String id;

    /** 卡片编码 */
    @Excel(name = "卡片编码")
    private String kpBm;

    /** 资产编码 */
    @Excel(name = "资产编码")
    private String zcBm;

    /** 资产名称 */
    @Excel(name = "资产名称")
    private String name;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String ggxh;

    /** 资产位置 */
    @Excel(name = "资产位置")
    private String zcLocation;

    /** 使用部门 */
    @Excel(name = "使用部门")
    private String useDept;

    /** 盘点日期 */
    @Excel(name = "盘点日期")
    private String pddate;

    /** 创建人 */
    @Excel(name = "创建人")
    private String creator;

    /** 盘点数量 */
    @Excel(name = "盘点数量")
    private BigDecimal number;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setKpBm(String kpBm) 
    {
        this.kpBm = kpBm;
    }

    public String getKpBm() 
    {
        return kpBm;
    }
    public void setZcBm(String zcBm) 
    {
        this.zcBm = zcBm;
    }

    public String getZcBm() 
    {
        return zcBm;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setGgxh(String ggxh) 
    {
        this.ggxh = ggxh;
    }

    public String getGgxh() 
    {
        return ggxh;
    }
    public void setZcLocation(String zcLocation) 
    {
        this.zcLocation = zcLocation;
    }

    public String getZcLocation() 
    {
        return zcLocation;
    }
    public void setUseDept(String useDept) 
    {
        this.useDept = useDept;
    }

    public String getUseDept() 
    {
        return useDept;
    }
    public void setPddate(String pddate) 
    {
        this.pddate = pddate;
    }

    public String getPddate() 
    {
        return pddate;
    }
    public void setCreator(String creator) 
    {
        this.creator = creator;
    }

    public String getCreator() 
    {
        return creator;
    }
    public void setNumber(BigDecimal number) 
    {
        this.number = number;
    }

    public BigDecimal getNumber() 
    {
        return number;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("kpBm", getKpBm())
            .append("zcBm", getZcBm())
            .append("name", getName())
            .append("ggxh", getGgxh())
            .append("zcLocation", getZcLocation())
            .append("useDept", getUseDept())
            .append("pddate", getPddate())
            .append("creator", getCreator())
            .append("remark", getRemark())
            .append("number", getNumber())
            .toString();
    }
}
