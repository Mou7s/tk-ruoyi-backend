package com.ruoyi.tk_custom.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业微信成员表对象 wxuser
 * 
 * @author wfs
 * @date 2025-04-19
 */
public class Wxuser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String userid;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 部门 */
    @Excel(name = "部门")
    private String dept;

    /** 职位 */
    @Excel(name = "职位")
    private String position;

    public void setUserid(String userid) 
    {
        this.userid = userid;
    }

    public String getUserid() 
    {
        return userid;
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
    public void setPosition(String position) 
    {
        this.position = position;
    }

    public String getPosition() 
    {
        return position;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userid", getUserid())
            .append("name", getName())
            .append("dept", getDept())
            .append("position", getPosition())
            .toString();
    }
}
