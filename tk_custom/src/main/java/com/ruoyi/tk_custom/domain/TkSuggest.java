package com.ruoyi.tk_custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 意见/建议对象 tk_suggest
 * 
 * @author wfs
 * @date 2025-08-12
 */
public class TkSuggest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 意见/建议内容 */
    @Excel(name = "意见/建议内容")
    private String suggestText;

    /** 建议提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "建议提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date suggestTime;

    /** IP地址 */
    @Excel(name = "IP地址")
    private String ipAdress;

    /** 不发送到企微（包含敏感词汇的默认为1） */
    private String noSend;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setSuggestText(String suggestText) 
    {
        this.suggestText = suggestText;
    }

    public String getSuggestText() 
    {
        return suggestText;
    }
    public void setSuggestTime(Date suggestTime) 
    {
        this.suggestTime = suggestTime;
    }

    public Date getSuggestTime() 
    {
        return suggestTime;
    }
    public void setIpAdress(String ipAdress) 
    {
        this.ipAdress = ipAdress;
    }

    public String getIpAdress() 
    {
        return ipAdress;
    }
    public void setNoSend(String noSend) 
    {
        this.noSend = noSend;
    }

    public String getNoSend() 
    {
        return noSend;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("suggestText", getSuggestText())
            .append("suggestTime", getSuggestTime())
            .append("ipAdress", getIpAdress())
            .append("noSend", getNoSend())
            .toString();
    }
}
