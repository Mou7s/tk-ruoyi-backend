package com.ruoyi.tk_custom.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.WxApproveMapper;
import com.ruoyi.tk_custom.domain.WxApprove;
import com.ruoyi.tk_custom.service.IWxApproveService;

/**
 * 微信审批Service业务层处理
 * 
 * @author wfs
 * @date 2025-08-14
 */
@Service
public class WxApproveServiceImpl implements IWxApproveService 
{
    @Autowired
    private WxApproveMapper wxApproveMapper;

    /**
     * 查询微信审批
     * 
     * @param spNo 微信审批主键
     * @return 微信审批
     */
    @Override
    public WxApprove selectWxApproveBySpNo(String spNo)
    {
        return wxApproveMapper.selectWxApproveBySpNo(spNo);
    }

    /**
     * 查询微信审批列表
     * 
     * @param wxApprove 微信审批
     * @return 微信审批
     */
    @Override
    public List<WxApprove> selectWxApproveList(WxApprove wxApprove)
    {
        return wxApproveMapper.selectWxApproveList(wxApprove);
    }

    /**
     * 新增微信审批
     * 
     * @param wxApprove 微信审批
     * @return 结果
     */
    @Override
    public int insertWxApprove(WxApprove wxApprove)
    {
        return wxApproveMapper.insertWxApprove(wxApprove);
    }

    /**
     * 修改微信审批
     * 
     * @param wxApprove 微信审批
     * @return 结果
     */
    @Override
    public int updateWxApprove(WxApprove wxApprove)
    {
        return wxApproveMapper.updateWxApprove(wxApprove);
    }

    /**
     * 批量删除微信审批
     * 
     * @param spNos 需要删除的微信审批主键
     * @return 结果
     */
    @Override
    public int deleteWxApproveBySpNos(String[] spNos)
    {
        return wxApproveMapper.deleteWxApproveBySpNos(spNos);
    }

    /**
     * 删除微信审批信息
     * 
     * @param spNo 微信审批主键
     * @return 结果
     */
    @Override
    public int deleteWxApproveBySpNo(String spNo)
    {
        return wxApproveMapper.deleteWxApproveBySpNo(spNo);
    }

    @Override
    public List<WxApprove> selectWxApproveListForBack() {
        return wxApproveMapper.selectWxApproveListForBack();
    }

    @Override
    public List<WxApprove> selectWxApproveListForOut() {
        return wxApproveMapper.selectWxApproveListForOut();
    }

    @Override
    public List<WxApprove> selectWxApproveListForOut1() {
        return wxApproveMapper.selectWxApproveListForOut1();
    }

    @Override
    public List<WxApprove> selectWxApproveListForBack1() {
        return wxApproveMapper.selectWxApproveListForBack1();
    }
}
