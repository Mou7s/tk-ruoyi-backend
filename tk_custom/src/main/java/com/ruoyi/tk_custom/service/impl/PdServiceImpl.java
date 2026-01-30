package com.ruoyi.tk_custom.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.PdMapper;
import com.ruoyi.tk_custom.domain.Pd;
import com.ruoyi.tk_custom.service.IPdService;

/**
 * 盘点Service业务层处理
 * 
 * @author wfs
 * @date 2024-11-27
 */
@Service
public class PdServiceImpl implements IPdService 
{
    @Autowired
    private PdMapper pdMapper;

    /**
     * 查询盘点
     * 
     * @param id 盘点主键
     * @return 盘点
     */
    @Override
    public Pd selectPdById(String id)
    {
        return pdMapper.selectPdById(id);
    }

    /**
     * 查询盘点列表
     * 
     * @param pd 盘点GY0202296
     *           GY0202804
     *
     * @return 盘点
     */
    @Override
    public List<Pd> selectPdList(Pd pd)
    {
        return pdMapper.selectPdList(pd);
    }

    /**
     * 新增盘点
     * 
     * @param pd 盘点
     * @return 结果
     */
    @Override
    public int insertPd(Pd pd)
    {
        pd.setId(UUID.randomUUID().toString());
        pd.setPdDate(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD));
        return pdMapper.insertPd(pd);
    }

    /**
     * 修改盘点
     * 
     * @param pd 盘点
     * @return 结果
     */
    @Override
    public int updatePd(Pd pd)
    {
        pd.setPdDate(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD));
        return pdMapper.updatePd(pd);
    }

    /**
     * 批量删除盘点
     * 
     * @param ids 需要删除的盘点主键
     * @return 结果
     */
    @Override
    public int deletePdByIds(String[] ids)
    {
        return pdMapper.deletePdByIds(ids);
    }

    /**
     * 删除盘点信息
     * 
     * @param id 盘点主键
     * @return 结果
     */
    @Override
    public int deletePdById(String id)
    {
        return pdMapper.deletePdById(id);
    }
}
