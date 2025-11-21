package com.ruoyi.tk_custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 来访登记对象 tk_visit
 * 
 * @author wfs
 * @date 2024-07-09
 */
public class TkVisit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 入厂时间 */
    @JsonFormat(pattern = "yyyy-MM-dd H:m")
    @Excel(name = "入厂时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date vDate;
    /** 出厂时间 */

    @JsonFormat(pattern = "yyyy-MM-dd H:m")
    @Excel(name = "出厂时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;
    /** 访客姓名 */
    @Excel(name = "访客姓名")
    private String vName;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String vTelephone;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String vCardid;

    /** 来访公司 */
    @Excel(name = "来访公司")
    private String company;

    /** 车牌号码 */
    @Excel(name = "车牌号码")
    private String carNumber;

    /** 来访人数 */
    @Excel(name = "来访人数")
    private String peoplecount;

    /** 来访事由 */
    @Excel(name = "来访事由")
    private String vReason;

    /** 需要鞋套 */
    @Excel(name = "需要鞋套",dictType="is_or_not")
    private String needshoes;

    /** 接洽会议室 */
    @Excel(name = "接洽会议室",dictType="tk_visit_room")
    private String room;

    /** 被访人 */
    @Excel(name = "被访人")
    private String receiver;

    /** 审核 */
    @Excel(name = "审核")
    private String approve;

    /** 审核人 */
    @Excel(name = "审核人")
    private String approver;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd  H:m")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date approvedate;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd  H:m")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdate;

    /** ID */
    private String id;

    public String getIsLook() {
        return isLook;
    }

    public void setIsLook(String isLook) {
        this.isLook = isLook;
    }
//是否为查询大屏
    private String isLook;

    public void setvDate(Date vDate) 
    {
        this.vDate = vDate;
    }

    public Date getvDate() 
    {
        return vDate;
    }
    public Date getOuttime() {
        return outTime;
    }

    public void setOuttime(Date outtime) {
        this.outTime = outtime;
    }
    public void setvName(String vName) 
    {
        this.vName = vName;
    }

    public String getvName() 
    {
        return vName;
    }
    public void setvTelephone(String vTelephone) 
    {
        this.vTelephone = vTelephone;
    }

    public String getvTelephone() 
    {
        return vTelephone;
    }
    public void setvCardid(String vCardid) 
    {
        this.vCardid = vCardid;
    }

    public String getvCardid() 
    {
        return vCardid;
    }
    public void setvReason(String vReason) 
    {
        this.vReason = vReason;
    }

    public String getvReason() 
    {
        return vReason;
    }
    public void setReceiver(String receiver) 
    {
        this.receiver = receiver;
    }

    public String getReceiver() 
    {
        return receiver;
    }
    public void setCarNumber(String carNumber) 
    {
        this.carNumber = carNumber;
    }

    public String getCarNumber() 
    {
        return carNumber;
    }
    public void setCreatedate(Date createdate) 
    {
        this.createdate = createdate;
    }

    public Date getCreatedate() 
    {
        return createdate;
    }
    public void setCompany(String company) 
    {
        this.company = company;
    }

    public String getCompany() 
    {
        return company;
    }
    public void setPeoplecount(String peoplecount) 
    {
        this.peoplecount = peoplecount;
    }

    public String getPeoplecount() 
    {
        return peoplecount;
    }
    public void setApprove(String approve) 
    {
        this.approve = approve;
    }

    public String getApprove() 
    {
        return approve;
    }
    public void setApprovedate(Date approvedate) 
    {
        this.approvedate = approvedate;
    }

    public Date getApprovedate() 
    {
        return approvedate;
    }
    public void setApprover(String approver) 
    {
        this.approver = approver;
    }

    public String getApprover() 
    {
        return approver;
    }
    public void setNeedshoes(String needshoes) 
    {
        this.needshoes = needshoes;
    }

    public String getNeedshoes() 
    {
        return needshoes;
    }
    public void setRoom(String room) 
    {
        this.room = room;
    }

    public String getRoom() 
    {
        return room;
    }
    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("vDate", getvDate())
            .append("vName", getvName())
            .append("vTelephone", getvTelephone())
            .append("vCardid", getvCardid())
            .append("vReason", getvReason())
            .append("receiver", getReceiver())
            .append("carNumber", getCarNumber())
            .append("createdate", getCreatedate())
            .append("company", getCompany())
            .append("peoplecount", getPeoplecount())
            .append("approve", getApprove())
            .append("approvedate", getApprovedate())
            .append("approver", getApprover())
            .append("needshoes", getNeedshoes())
            .append("room", getRoom())
            .append("id", getId())
            .toString();
    }
}
