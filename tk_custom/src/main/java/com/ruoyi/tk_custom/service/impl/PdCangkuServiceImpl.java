package com.ruoyi.tk_custom.service.impl;

import java.util.List;
import java.util.UUID;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.PdCangkuMapper;
import com.ruoyi.tk_custom.domain.PdCangku;
import com.ruoyi.tk_custom.service.IPdCangkuService;

/**
 * 盘点_仓库Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-21
 */
@Service
public class PdCangkuServiceImpl implements IPdCangkuService 
{
    @Autowired
    private PdCangkuMapper pdCangkuMapper;

    /**
     * 查询盘点_仓库
     * 
     * @param id 盘点_仓库主键
     * @return 盘点_仓库
     */
    @Override
    public PdCangku selectPdCangkuById(String id)
    {
        return pdCangkuMapper.selectPdCangkuById(id);
    }

    /**
     * 查询盘点_仓库列表
     * 
     * @param pdCangku 盘点_仓库
     * @return 盘点_仓库
     */
    @Override
    public List<PdCangku> selectPdCangkuList(PdCangku pdCangku)
    {
        return pdCangkuMapper.selectPdCangkuList(pdCangku);
    }

    /**
     * 新增盘点_仓库
     * 
     * @param pdCangku 盘点_仓库
     * @return 结果
     */
    @Override
    public int insertPdCangku(PdCangku pdCangku)
    {
        pdCangku.setId(UUID.randomUUID().toString());
        pdCangku.setCreator(SecurityUtils.getUsername());
        pdCangku.setPddate(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
        return pdCangkuMapper.insertPdCangku(pdCangku);
    }

    /**
     * 修改盘点_仓库
     * 
     * @param pdCangku 盘点_仓库
     * @return 结果
     */
    @Override
    public int updatePdCangku(PdCangku pdCangku)
    {
        return pdCangkuMapper.updatePdCangku(pdCangku);
    }

    /**
     * 批量删除盘点_仓库
     * 
     * @param ids 需要删除的盘点_仓库主键
     * @return 结果
     */
    @Override
    public int deletePdCangkuByIds(String[] ids)
    {
        return pdCangkuMapper.deletePdCangkuByIds(ids);
    }

    /**
     * 删除盘点_仓库信息
     * 
     * @param id 盘点_仓库主键
     * @return 结果
     */
    @Override
    public int deletePdCangkuById(String id)
    {
        return pdCangkuMapper.deletePdCangkuById(id);
    }
}
