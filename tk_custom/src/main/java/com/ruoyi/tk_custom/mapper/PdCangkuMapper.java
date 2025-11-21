package com.ruoyi.tk_custom.mapper;

import java.util.List;
import com.ruoyi.tk_custom.domain.PdCangku;

/**
 * 盘点_仓库Mapper接口
 * 
 * @author ruoyi
 * @date 2025-03-21
 */
public interface PdCangkuMapper 
{
    /**
     * 查询盘点_仓库
     * 
     * @param id 盘点_仓库主键
     * @return 盘点_仓库
     */
    public PdCangku selectPdCangkuById(String id);

    /**
     * 查询盘点_仓库列表
     * 
     * @param pdCangku 盘点_仓库
     * @return 盘点_仓库集合
     */
    public List<PdCangku> selectPdCangkuList(PdCangku pdCangku);

    /**
     * 新增盘点_仓库
     * 
     * @param pdCangku 盘点_仓库
     * @return 结果
     */
    public int insertPdCangku(PdCangku pdCangku);

    /**
     * 修改盘点_仓库
     * 
     * @param pdCangku 盘点_仓库
     * @return 结果
     */
    public int updatePdCangku(PdCangku pdCangku);

    /**
     * 删除盘点_仓库
     * 
     * @param id 盘点_仓库主键
     * @return 结果
     */
    public int deletePdCangkuById(String id);

    /**
     * 批量删除盘点_仓库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePdCangkuByIds(String[] ids);
}
