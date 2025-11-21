package com.ruoyi.tk_custom.service;

import java.util.List;
import com.ruoyi.tk_custom.domain.WxApprove;

/**
 * 微信审批Service接口
 * 
 * @author wfs
 * @date 2025-08-14
 */
public interface IWxApproveService 
{
    /**
     * 查询微信审批
     * 
     * @param spNo 微信审批主键
     * @return 微信审批
     */
    public WxApprove selectWxApproveBySpNo(String spNo);

    /**
     * 查询微信审批列表
     * 
     * @param wxApprove 微信审批
     * @return 微信审批集合
     */
    public List<WxApprove> selectWxApproveList(WxApprove wxApprove);

    /**
     * 新增微信审批
     * 
     * @param wxApprove 微信审批
     * @return 结果
     */
    public int insertWxApprove(WxApprove wxApprove);

    /**
     * 修改微信审批
     * 
     * @param wxApprove 微信审批
     * @return 结果
     */
    public int updateWxApprove(WxApprove wxApprove);

    /**
     * 批量删除微信审批
     * 
     * @param spNos 需要删除的微信审批主键集合
     * @return 结果
     */
    public int deleteWxApproveBySpNos(String[] spNos);

    /**
     * 删除微信审批信息
     * 
     * @param spNo 微信审批主键
     * @return 结果
     */
    public int deleteWxApproveBySpNo(String spNo);

    List<WxApprove> selectWxApproveListForBack();

    List<WxApprove> selectWxApproveListForOut();

    List<WxApprove> selectWxApproveListForOut1();

    List<WxApprove> selectWxApproveListForBack1();
}
