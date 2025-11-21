package com.ruoyi.tk_custom.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.HxGxjcMapper;
import com.ruoyi.tk_custom.domain.HxGxjc;
import com.ruoyi.tk_custom.service.IHxGxjcService;

/**
 * 焊线工序检测记录Service业务层处理
 * 
 * @author wfs
 * @date 2025-10-13
 */
@Service
public class HxGxjcServiceImpl implements IHxGxjcService 
{
    @Autowired
    private HxGxjcMapper hxGxjcMapper;

    /**
     * 查询焊线工序检测记录
     * 
     * @param id 焊线工序检测记录主键
     * @return 焊线工序检测记录
     */
    @Override
    public HxGxjc selectHxGxjcById(Long id)
    {
        return hxGxjcMapper.selectHxGxjcById(id);
    }

    /**
     * 查询焊线工序检测记录列表
     * 
     * @param hxGxjc 焊线工序检测记录
     * @return 焊线工序检测记录
     */
    @Override
    public List<HxGxjc> selectHxGxjcList(HxGxjc hxGxjc)
    {
        return hxGxjcMapper.selectHxGxjcList(hxGxjc);
    }

    /**
     * 新增焊线工序检测记录
     * 
     * @param hxGxjc 焊线工序检测记录
     * @return 结果
     */
    @Override
    public int insertHxGxjc(HxGxjc hxGxjc)
    {
        hxGxjc.setCreatedate(new Date());
        hxGxjc.setModifydate(new Date());
        hxGxjc.setCreator(SecurityUtils.getUsername());
        hxGxjc.setUpdater(SecurityUtils.getUsername());
        return hxGxjcMapper.insertHxGxjc(hxGxjc);
    }

    /**
     * 修改焊线工序检测记录
     * 
     * @param hxGxjc 焊线工序检测记录
     * @return 结果
     */
    @Override
    public int updateHxGxjc(HxGxjc hxGxjc)
    {
        hxGxjc.setModifydate(new Date());
        hxGxjc.setUpdater(SecurityUtils.getUsername());
        return hxGxjcMapper.updateHxGxjc(hxGxjc);
    }

    /**
     * 批量删除焊线工序检测记录
     * 
     * @param ids 需要删除的焊线工序检测记录主键
     * @return 结果
     */
    @Override
    public int deleteHxGxjcByIds(Long[] ids)
    {
        return hxGxjcMapper.deleteHxGxjcByIds(ids);
    }

    /**
     * 删除焊线工序检测记录信息
     * 
     * @param id 焊线工序检测记录主键
     * @return 结果
     */
    @Override
    public int deleteHxGxjcById(Long id)
    {
        return hxGxjcMapper.deleteHxGxjcById(id);
    }

    @Override
    public List<String> getMechineList() {
        return hxGxjcMapper.getMechineList();
    }

    @Override
    public List<String> getHandlerList() {
        return hxGxjcMapper.getHandlerList();
    }
}
