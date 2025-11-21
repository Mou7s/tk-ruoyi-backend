package com.ruoyi.tk_custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信审批对象 wx_approve
 * 
 * @author wfs
 * @date 2025-08-15
 */
public class WxApprove extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 审批编号 */
    private String spNo;

    /** 审批申请类型名称（审批模板名称） */
    @Excel(name = "审批申请类型名称", readConverterExp = "审=批模板名称")
    private String spName;

    /** 申请单状态：1-审批中；2-已通过；3-已驳回；4-已撤销；6-通过后撤销；7-已删除；10-已支付 */
    @Excel(name = "申请单状态：1-审批中；2-已通过；3-已驳回；4-已撤销；6-通过后撤销；7-已删除；10-已支付")
    private String spStatus;

    /** 审批模板id */
    @Excel(name = "审批模板id")
    private String templateId;

    /** 审批申请提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审批申请提交时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 申请人id */
    @Excel(name = "申请人id")
    private String applyerUserid;

    /** 申请人所在部门id */
    @Excel(name = "申请人所在部门id")
    private String applyerPartyid;

    /** 审批申请详情 */
    @Excel(name = "审批申请详情")
    private String applyDataContents;

    /** 出厂时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "出厂时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date outtime;

    /** 返厂时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "返厂时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date backtime;

    /** 审批人id */
    @Excel(name = "审批人id")
    private String auditerid;

    public void setSpNo(String spNo) 
    {
        this.spNo = spNo;
    }

    public String getSpNo() 
    {
        return spNo;
    }
    public void setSpName(String spName) 
    {
        this.spName = spName;
    }

    public String getSpName() 
    {
        return spName;
    }
    public void setSpStatus(String spStatus) 
    {
        this.spStatus = spStatus;
    }

    public String getSpStatus() 
    {
        return spStatus;
    }
    public void setTemplateId(String templateId) 
    {
        this.templateId = templateId;
    }

    public String getTemplateId() 
    {
        return templateId;
    }
    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
    }
    public void setApplyerUserid(String applyerUserid) 
    {
        this.applyerUserid = applyerUserid;
    }

    public String getApplyerUserid() 
    {
        return applyerUserid;
    }
    public void setApplyerPartyid(String applyerPartyid) 
    {
        this.applyerPartyid = applyerPartyid;
    }

    public String getApplyerPartyid() 
    {
        return applyerPartyid;
    }
    public void setApplyDataContents(String applyDataContents) 
    {
        this.applyDataContents = applyDataContents;
    }

    public String getApplyDataContents() 
    {
        return applyDataContents;
    }
    public void setOuttime(Date outtime) 
    {
        this.outtime = outtime;
    }

    public Date getOuttime() 
    {
        return outtime;
    }
    public void setBacktime(Date backtime) 
    {
        this.backtime = backtime;
    }

    public Date getBacktime() 
    {
        return backtime;
    }
    public void setAuditerid(String auditerid) 
    {
        this.auditerid = auditerid;
    }

    public String getAuditerid() 
    {
        return auditerid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("spNo", getSpNo())
            .append("spName", getSpName())
            .append("spStatus", getSpStatus())
            .append("templateId", getTemplateId())
            .append("applyTime", getApplyTime())
            .append("applyerUserid", getApplyerUserid())
            .append("applyerPartyid", getApplyerPartyid())
            .append("applyDataContents", getApplyDataContents())
            .append("outtime", getOuttime())
            .append("backtime", getBacktime())
            .append("auditerid", getAuditerid())
            .toString();
    }
}
