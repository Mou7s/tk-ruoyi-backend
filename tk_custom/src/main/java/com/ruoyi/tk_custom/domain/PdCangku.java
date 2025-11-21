package com.ruoyi.tk_custom.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 盘点_仓库对象 pd_cangku
 *
 * @author ruoyi
 * @date 2025-03-21
 */
public class PdCangku extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String mertiralid;

    /**
     * 物料名称
     */
    @Excel(name = "物料名称")
    private String mertiralname;

    /**
     * 封装
     */
    @Excel(name = "封装")
    private String fengzhuang;

    /**
     * 印字
     */
    @Excel(name = "印字")
    private String yinzi;

    /**
     * 库位
     */
    @Excel(name = "库位")
    private String kuwei;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private BigDecimal number;

    /**
     * 盘点日期
     */
    @Excel(name = "盘点日期")
    private String pddate;
    /**
     * 盘点日期
     */
    @Excel(name = "盘点人")
    private String creator;

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setMertiralid(String mertiralid) {
        this.mertiralid = mertiralid;
    }

    public String getMertiralid() {
        return mertiralid;
    }

    public void setMertiralname(String mertiralname) {
        this.mertiralname = mertiralname;
    }

    public String getMertiralname() {
        return mertiralname;
    }

    public void setFengzhuang(String fengzhuang) {
        this.fengzhuang = fengzhuang;
    }

    public String getFengzhuang() {
        return fengzhuang;
    }

    public void setYinzi(String yinzi) {
        this.yinzi = yinzi;
    }

    public String getYinzi() {
        return yinzi;
    }

    public void setKuwei(String kuwei) {
        this.kuwei = kuwei;
    }

    public String getKuwei() {
        return kuwei;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setPddate(String pddate) {
        this.pddate = pddate;
    }

    public String getPddate() {
        return pddate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("mertiralid", getMertiralid())
                .append("mertiralname", getMertiralname())
                .append("fengzhuang", getFengzhuang())
                .append("yinzi", getYinzi())
                .append("kuwei", getKuwei())
                .append("number", getNumber())
                .append("pddate", getPddate())
                .append("remark", getRemark())
                .append("creator", getCreator())
                .toString();
    }
}
