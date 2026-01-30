package com.ruoyi.tk_custom.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 盘点对象 pd
 * 
 * @author wfs
 * @date 2024-11-27
 */
public class Pd extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private String id;

    /** 盘点时间 */
    @Excel(name = "盘点时间")
    private String pdDate;

    /** 盘点工序 */
    @Excel(name = "盘点工序")
    private String process;

    /** 流程单号 */
    @Excel(name = "流程单号")
    private String flowNo;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 批号 */
    @Excel(name = "批号")
    private String lot;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private BigDecimal number;

    /** 盘点数量 */
    @Excel(name = "盘点数量")
    private BigDecimal pdNumber;

    /** 工序状态 */
    @Excel(name = "工序状态")
    private String processStatus;

    public String getIsUpdatePdData() {
        return isUpdatePdData;
    }

    public void setIsUpdatePdData(String isUpdatePdData) {
        this.isUpdatePdData = isUpdatePdData;
    }

    private String isUpdatePdData;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPdDate(String pdDate) {
        this.pdDate = pdDate;
    }

    public String getPdDate() {
        return pdDate;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getProcess() {
        return process;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSpecification() {
        return specification;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getLot() {
        return lot;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setPdNumber(BigDecimal pdNumber) {
        this.pdNumber = pdNumber;
    }

    public BigDecimal getPdNumber() {
        return pdNumber;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("pdDate", getPdDate())
                .append("process", getProcess())
                .append("flowNo", getFlowNo())
                .append("productId", getProductId())
                .append("productName", getProductName())
                .append("specification", getSpecification())
                .append("lot", getLot())
                .append("number", getNumber())
                .append("pdNumber", getPdNumber())
                .append("remark", getRemark())
                .toString();
    }
}
