package com.ruoyi.tk_custom.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.TkWorkshopMapper;
import com.ruoyi.tk_custom.domain.TkWorkshop;
import com.ruoyi.tk_custom.service.ITkWorkshopService;

/**
 * 扫码登记（车间）Service业务层处理
 * 
 * @author wfs
 * @date 2025-04-19
 */
@Service
public class TkWorkshopServiceImpl implements ITkWorkshopService 
{
    @Autowired
    private TkWorkshopMapper tkWorkshopMapper;

    /**
     * 查询扫码登记（车间）
     * 
     * @param id 扫码登记（车间）主键
     * @return 扫码登记（车间）
     */
    @Override
    public TkWorkshop selectTkWorkshopById(String id)
    {
        return tkWorkshopMapper.selectTkWorkshopById(id);
    }

    /**
     * 查询扫码登记（车间）列表
     * 
     * @param tkWorkshop 扫码登记（车间）
     * @return 扫码登记（车间）
     */
    @Override
    public List<TkWorkshop> selectTkWorkshopList(TkWorkshop tkWorkshop)
    {
        return tkWorkshopMapper.selectTkWorkshopList(tkWorkshop);
    }

    /**
     * 新增扫码登记（车间）
     * 
     * @param tkWorkshop 扫码登记（车间）
     * @return 结果
     */
    @Override
    public int insertTkWorkshop(TkWorkshop tkWorkshop)
    {
        tkWorkshop.setId(IdUtils.fastUUID());
        tkWorkshop.setRequesttime(new Date());
        return tkWorkshopMapper.insertTkWorkshop(tkWorkshop);
    }

    /**
     * 修改扫码登记（车间）
     * 
     * @param tkWorkshop 扫码登记（车间）
     * @return 结果
     */
    @Override
    public int updateTkWorkshop(TkWorkshop tkWorkshop)
    {
        return tkWorkshopMapper.updateTkWorkshop(tkWorkshop);
    }

    /**
     * 批量删除扫码登记（车间）
     * 
     * @param ids 需要删除的扫码登记（车间）主键
     * @return 结果
     */
    @Override
    public int deleteTkWorkshopByIds(String[] ids)
    {
        return tkWorkshopMapper.deleteTkWorkshopByIds(ids);
    }

    /**
     * 删除扫码登记（车间）信息
     * 
     * @param id 扫码登记（车间）主键
     * @return 结果
     */
    @Override
    public int deleteTkWorkshopById(String id)
    {
        return tkWorkshopMapper.deleteTkWorkshopById(id);
    }
}
