package com.ruoyi.tk_custom.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 弹坑图片对象 dk_pic
 * 
 * @author wfs
 * @date 2025-09-03
 */
public class DkPic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 流程单号 */
    @Excel(name = "流程单号")
    private String flowno;
    /** 客户代码 */
    @Excel(name = "客户代码")
    private String khdm;
    /** 物料编码 */
    private String materialno;

    /** 物料名称 */
    @Excel(name = "成品型号")
    private String materialname;

    /** 规格型号 */
    @Excel(name = "封装")
    private String specification;

    private String content;

    private String imgSrcPaths;
    /** 机台号 */
    @Excel(name = "WB机台号")
    private String mechineName;
    /** 芯片代码 */
    @Excel(name = "芯片代码")
    private String xpdm;

    /** 芯片批号 */
    @Excel(name = "芯片批号")
    private String xpph;

    /** 操作/技术员 */
    @Excel(name = "操作/技术员")
    private String handler;
    /** 判定结果 */
    @Excel(name = "判定结果")
    private String result;
    /** 试验数量 */
    @Excel(name = "试验数量")
    private String testNumber;

    /** 试验时机 */
    @Excel(name = "试验时机")
    private String testCharge;


    /** 创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createdate;
    private String createdatestr;
    /** 创建人 */

    @Excel(name = "创建人")
    private String creator;
    /** 最后修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date modifydate;
    private String modifydatestr;
    /** 最后修改人 */
    @Excel(name = "最后修改人")
    private String updater;

    @Excel(name="弹坑图",cellType = Excel.ColumnType.IMAGE)
    private String imgPaths;
    public String getModifydatestr() {
        return modifydatestr;
    }

    public void setModifydatestr(String modifydatestr) {
        this.modifydatestr = modifydatestr;
    }

    public String getCreatedatestr() {
        return createdatestr;
    }

    public void setCreatedatestr(String createdatestr) {
        this.createdatestr = createdatestr;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
    public String getImgSrcPaths() {
        return imgSrcPaths;
    }
    public void setImgSrcPaths(String imgSrcPaths) {
        this.imgSrcPaths = imgSrcPaths;
    }
    public Long getId() 
    {
        return id;
    }
    public void setFlowno(String flowno) 
    {
        this.flowno = flowno;
    }

    public String getFlowno() 
    {
        return flowno;
    }
    public void setMaterialno(String materialno) 
    {
        this.materialno = materialno;
    }

    public String getMaterialno() 
    {
        return materialno;
    }
    public void setMaterialname(String materialname) 
    {
        this.materialname = materialname;
    }

    public String getMaterialname() 
    {
        return materialname;
    }
    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setImgPaths(String imgPaths) 
    {
        this.imgPaths = imgPaths;
    }

    public String getImgPaths() 
    {
        return imgPaths;
    }
    public void setMechineName(String mechineName) 
    {
        this.mechineName = mechineName;
    }

    public String getMechineName() 
    {
        return mechineName;
    }
    public void setModifydate(Date modifydate) 
    {
        this.modifydate = modifydate;
    }

    public Date getModifydate() 
    {
        return modifydate;
    }

    public Date getcreatedate() {
        return createdate;
    }

    public void setcreatedate(Date createdate) {
        this.createdate = createdate;

    }
    public String getXpdm() {
        return xpdm;
    }

    public void setXpdm(String xpdm) {
        this.xpdm = xpdm;
    }

    public String getXpph() {
        return xpph;
    }

    public void setXpph(String xpph) {
        this.xpph = xpph;
    }

    public String getKhdm() {
        return khdm;
    }

    public void setKhdm(String khdm) {
        this.khdm = khdm;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public String getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(String testNumber) {
        this.testNumber = testNumber;
    }

    public String getTestCharge() {
        return testCharge;
    }

    public void setTestCharge(String testCharge) {
        this.testCharge = testCharge;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("flowno", getFlowno())
            .append("materialno", getMaterialno())
            .append("materialname", getMaterialname())
            .append("specification", getSpecification())
            .append("content", getContent())
            .append("imgPaths", getImgPaths())
            .append("mechineName", getMechineName())
            .append("createdate", getcreatedate())
            .append("modifydate", getModifydate())
            .toString();
    }
}
