package com.ruoyi.tk_custom.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.tk_custom.domain.Kucun_Sum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.KucunMapper;
import com.ruoyi.tk_custom.domain.Kucun;
import com.ruoyi.tk_custom.service.IKucunService;

/**
 * 金蝶库存查询Service业务层处理
 * 
 * @author wfs
 * @date 2025-06-18
 */
@Service
public class KucunServiceImpl implements IKucunService 
{
    @Autowired
    private KucunMapper kucunMapper;

    /**
     * 查询金蝶库存查询
     * 
     * @param id 金蝶库存查询主键
     * @return 金蝶库存查询
     */
    @Override
    public Kucun selectKucunById(String id)
    {
        return kucunMapper.selectKucunById(id);
    }

    /**
     * 查询金蝶库存查询列表
     * 
     * @param kucun 金蝶库存查询
     * @return 金蝶库存查询
     */
    @Override
    public List<Kucun> selectKucunList(Kucun kucun)
    {
        return kucunMapper.selectKucunList(kucun);
    }
    @Override
    public List<Kucun_Sum> selectKucunSumList(Kucun kucun)
    {
        return kucunMapper.selectKuCun(kucun);
    }
    /**
     * 新增金蝶库存查询
     * 
     * @param kucun 金蝶库存查询
     * @return 结果
     */
    @Override
    public int insertKucun(Kucun kucun)
    {
        return kucunMapper.insertKucun(kucun);
    }
    @Override
    public Kucun selectLastTime()
    {
        return kucunMapper.selectLastTime();
    }
    @Override
    public int deleteAll()
    {
        return kucunMapper.deleteAll();
    }
    /**
     * 修改金蝶库存查询
     * 
     * @param kucun 金蝶库存查询
     * @return 结果
     */
    @Override
    public int updateKucun(Kucun kucun)
    {
        return kucunMapper.updateKucun(kucun);
    }

    /**
     * 批量删除金蝶库存查询
     * 
     * @param ids 需要删除的金蝶库存查询主键
     * @return 结果
     */
    @Override
    public int deleteKucunByIds(String[] ids)
    {
        return kucunMapper.deleteKucunByIds(ids);
    }

    /**
     * 删除金蝶库存查询信息
     * 
     * @param id 金蝶库存查询主键
     * @return 结果
     */
    @Override
    public int deleteKucunById(String id)
    {
        return kucunMapper.deleteKucunById(id);
    }

    @Override
    public BigDecimal selectXSTHTotal(Kucun kucun) {
        return kucunMapper.selectXSTHTotal(kucun);
    }
    @Override
    public BigDecimal selectXSCHTotal(Kucun kucun) {
        return kucunMapper.selectXSCHTotal(kucun);
    }
}
