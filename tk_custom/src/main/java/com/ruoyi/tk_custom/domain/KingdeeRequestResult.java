package com.ruoyi.tk_custom.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 金蝶webapi请求响应结果
 * 
 * @author wfs
 * @date 2025-04-24
 */
public class KingdeeRequestResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Excel(name = "执行结果")
    private String message;

}
