package com.ruoyi.tk_custom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;


public class Talent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;
    // 名称
    private  String name;
    // 出生日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthDate;
    // 学历
    private String eduId;
    // 是否已婚
    private String isMarriedId;
    //  岗位
    private String position;
    // 毕业学校
    private String schoolName;
    // 电话号码
    private String phoneNumber;
    // 图片地址
    private String Attachments;
    // 当前记录的状态
    private int recordStatus;
    // 是否删除
    private int isDel;
    // 性别
    private Integer gender;
    // 备注
    private String remark;
    // 籍贯
    private String nativePlace;
    // 创建人
    private String creator;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // 创建时间
    private Date createTime;
    // 修改人
    private String modifier;
    // 修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    // 面试时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date interviewTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEduId() {
        return eduId;
    }

    public void setEduId(String eduId) {
        this.eduId = eduId;
    }

    public String getIsMarriedId() {
        return isMarriedId;
    }

    public void setIsMarriedId(String isMarriedId) {
        this.isMarriedId = isMarriedId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAttachments() {
        return Attachments;
    }

    public void setAttachments(String attachments) {
        Attachments = attachments;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(Date interviewTime) {
        this.interviewTime = interviewTime;
    }
}
