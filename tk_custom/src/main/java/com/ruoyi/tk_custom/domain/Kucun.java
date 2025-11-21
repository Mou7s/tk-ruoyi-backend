package com.ruoyi.tk_custom.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 金蝶库存查询对象 kucun
 * 
 * @author wfs
 * @date 2025-06-18
 */
public class Kucun extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 销售出/退库单号 */
    @Excel(name = "销售出/退库单号")
    private String kdBillno;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String kdWlbm;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String kdWumc;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String kdGgxh;

    /** 仓库id */
    private String kdCangkuId;

    /** 仓库 */
    @Excel(name = "仓库")
    private String kdCangkuName;

    /** 库位id */
    private String kdKuweiId;
    /** 审核状态 */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /** 库位 */
    @Excel(name = "库位")
    private String kdKuweiName;

    /** 出库数量 */
    @Excel(name = "出库数量")
    private BigDecimal kdChukuNumber;

    /** 出库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "出库时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date kdChukuTime;

    /** 同步时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "同步时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date syncTime;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setKdBillno(String kdBillno) 
    {
        this.kdBillno = kdBillno;
    }

    public String getKdBillno() 
    {
        return kdBillno;
    }
    public void setKdWlbm(String kdWlbm) 
    {
        this.kdWlbm = kdWlbm;
    }

    public String getKdWlbm() 
    {
        return kdWlbm;
    }
    public void setKdWumc(String kdWumc) 
    {
        this.kdWumc = kdWumc;
    }

    public String getKdWumc() 
    {
        return kdWumc;
    }
    public void setKdGgxh(String kdGgxh) 
    {
        this.kdGgxh = kdGgxh;
    }

    public String getKdGgxh() 
    {
        return kdGgxh;
    }
    public void setKdCangkuId(String kdCangkuId) 
    {
        this.kdCangkuId = kdCangkuId;
    }

    public String getKdCangkuId() 
    {
        return kdCangkuId;
    }
    public void setKdCangkuName(String kdCangkuName) 
    {
        this.kdCangkuName = kdCangkuName;
    }

    public String getKdCangkuName() 
    {
        return kdCangkuName;
    }
    public void setKdKuweiId(String kdKuweiId) 
    {
        this.kdKuweiId = kdKuweiId;
    }

    public String getKdKuweiId() 
    {
        return kdKuweiId;
    }
    public void setKdKuweiName(String kdKuweiName) 
    {
        this.kdKuweiName = kdKuweiName;
    }

    public String getKdKuweiName() 
    {
        return kdKuweiName;
    }
    public void setKdChukuNumber(BigDecimal kdChukuNumber) 
    {
        this.kdChukuNumber = kdChukuNumber;
    }

    public BigDecimal getKdChukuNumber() 
    {
        return kdChukuNumber;
    }
    public void setKdChukuTime(Date kdChukuTime) 
    {
        this.kdChukuTime = kdChukuTime;
    }

    public Date getKdChukuTime() 
    {
        return kdChukuTime;
    }
    public void setSyncTime(Date syncTime) 
    {
        this.syncTime = syncTime;
    }

    public Date getSyncTime() 
    {
        return syncTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("kdBillno", getKdBillno())
            .append("kdWlbm", getKdWlbm())
            .append("kdWumc", getKdWumc())
            .append("kdGgxh", getKdGgxh())
            .append("kdCangkuId", getKdCangkuId())
            .append("kdCangkuName", getKdCangkuName())
            .append("kdKuweiId", getKdKuweiId())
            .append("kdKuweiName", getKdKuweiName())
            .append("kdChukuNumber", getKdChukuNumber())
            .append("kdChukuTime", getKdChukuTime())
            .append("syncTime", getSyncTime())
            .toString();
    }
}
