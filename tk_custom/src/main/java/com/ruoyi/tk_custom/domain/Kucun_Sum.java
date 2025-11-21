package com.ruoyi.tk_custom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 金蝶库存查询对象 kucun
 * 
 * @author wfs
 * @date 2025-06-18
 */
public class Kucun_Sum extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String kdWlbm;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String kdWumc;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String kdGgxh;
    /** 仓库 */
    @Excel(name = "仓库")

    private String kdCangkuName;


    /** 库位 */
    @Excel(name = "库位")
    private String kdKuweiName;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private BigDecimal kdChukuNumber;
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getKdWlbm() {
        return kdWlbm;
    }

    public void setKdWlbm(String kdWlbm) {
        this.kdWlbm = kdWlbm;
    }

    public String getKdWumc() {
        return kdWumc;
    }

    public void setKdWumc(String kdWumc) {
        this.kdWumc = kdWumc;
    }

    public String getKdGgxh() {
        return kdGgxh;
    }

    public void setKdGgxh(String kdGgxh) {
        this.kdGgxh = kdGgxh;
    }

    public String getKdCangkuName() {
        return kdCangkuName;
    }

    public void setKdCangkuName(String kdCangkuName) {
        this.kdCangkuName = kdCangkuName;
    }

    public String getKdKuweiName() {
        return kdKuweiName;
    }

    public void setKdKuweiName(String kdKuweiName) {
        this.kdKuweiName = kdKuweiName;
    }

    public BigDecimal getKdChukuNumber() {
        return kdChukuNumber;
    }

    public void setKdChukuNumber(BigDecimal kdChukuNumber) {
        this.kdChukuNumber = kdChukuNumber;
    }






    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("kdWlbm", getKdWlbm())
            .append("kdWumc", getKdWumc())
            .append("kdGgxh", getKdGgxh())
            .append("kdCangkuName", getKdCangkuName())
            .append("kdKuweiName", getKdKuweiName())
            .append("kdChukuNumber", getKdChukuNumber())
            .toString();
    }
}
