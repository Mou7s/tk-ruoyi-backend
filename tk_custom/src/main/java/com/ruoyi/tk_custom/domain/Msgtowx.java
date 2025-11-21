package com.ruoyi.tk_custom.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 发送到企微的消息队列对象 msgtowx
 * 
 * @author wfs
 * @date 2025-08-12
 */
public class Msgtowx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 类型（来访通知、车间通知、建议通知） */
    @Excel(name = "类型", readConverterExp = "来=访通知、车间通知、建议通知")
    private String type;

    /** 是否已发送 */
    @Excel(name = "是否已发送")
    private String isSend;

    /** 接收人ID集合 */
    @Excel(name = "接收人ID集合")
    private String receiverIds;

    /** 源单id */
    @Excel(name = "源单id")
    private String parentid;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String msg;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Date createtime;
    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setIsSend(String isSend) 
    {
        this.isSend = isSend;
    }

    public String getIsSend() 
    {
        return isSend;
    }
    public void setReceiverIds(String receiverIds) 
    {
        this.receiverIds = receiverIds;
    }

    public String getReceiverIds() 
    {
        return receiverIds;
    }
    public void setParentid(String parentid) 
    {
        this.parentid = parentid;
    }

    public String getParentid() 
    {
        return parentid;
    }
    public void setMsg(String msg) 
    {
        this.msg = msg;
    }

    public String getMsg() 
    {
        return msg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("isSend", getIsSend())
            .append("receiverIds", getReceiverIds())
            .append("createtime", getCreatetime())
            .append("parentid", getParentid())
            .append("msg", getMsg())
            .toString();
    }
}
