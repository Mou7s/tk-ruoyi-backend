package com.ruoyi.tk_custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 扫码登记（车间）对象 tk_workshop
 * 
 * @author wfs
 * @date 2025-04-19
 */
public class TkWorkshop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 部门 */
    @Excel(name = "部门")
    private String dept;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date requesttime;

    /** 登记理由 */
    @Excel(name = "登记理由")
    private String reason;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outtime;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setDept(String dept) 
    {
        this.dept = dept;
    }

    public String getDept() 
    {
        return dept;
    }
    public void setRequesttime(Date requesttime) 
    {
        this.requesttime = requesttime;
    }

    public Date getRequesttime() 
    {
        return requesttime;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }
    public void setOuttime(Date outtime) 
    {
        this.outtime = outtime;
    }

    public Date getOuttime() 
    {
        return outtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("dept", getDept())
            .append("requesttime", getRequesttime())
            .append("reason", getReason())
            .append("outtime", getOuttime())
            .toString();
    }
}
