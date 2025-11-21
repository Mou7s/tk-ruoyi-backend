package com.ruoyi.tk_custom.mapper;

import java.util.List;
import com.ruoyi.tk_custom.domain.PdGdzc;

/**
 * 固定资产盘点Mapper接口
 * 
 * @author wfs
 * @date 2025-05-09
 */
public interface PdGdzcMapper 
{
    /**
     * 查询固定资产盘点
     * 
     * @param id 固定资产盘点主键
     * @return 固定资产盘点
     */
    public PdGdzc selectPdGdzcById(String id);

    /**
     * 查询固定资产盘点列表
     * 
     * @param pdGdzc 固定资产盘点
     * @return 固定资产盘点集合
     */
    public List<PdGdzc> selectPdGdzcList(PdGdzc pdGdzc);

    /**
     * 新增固定资产盘点
     * 
     * @param pdGdzc 固定资产盘点
     * @return 结果
     */
    public int insertPdGdzc(PdGdzc pdGdzc);

    /**
     * 修改固定资产盘点
     * 
     * @param pdGdzc 固定资产盘点
     * @return 结果
     */
    public int updatePdGdzc(PdGdzc pdGdzc);

    /**
     * 删除固定资产盘点
     * 
     * @param id 固定资产盘点主键
     * @return 结果
     */
    public int deletePdGdzcById(String id);

    /**
     * 批量删除固定资产盘点
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePdGdzcByIds(String[] ids);
}
