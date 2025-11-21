package com.ruoyi.tk_custom.service;

import java.util.List;
import com.ruoyi.tk_custom.domain.Pd;

/**
 * 盘点Service接口
 * 
 * @author wfs
 * @date 2024-11-27
 */
public interface IPdService 
{
    /**
     * 查询盘点
     * 
     * @param id 盘点主键
     * @return 盘点
     */
    public Pd selectPdById(String id);

    /**
     * 查询盘点列表
     * 
     * @param pd 盘点
     * @return 盘点集合
     */
    public List<Pd> selectPdList(Pd pd);

    /**
     * 新增盘点
     * 
     * @param pd 盘点
     * @return 结果
     */
    public int insertPd(Pd pd);

    /**
     * 修改盘点
     * 
     * @param pd 盘点
     * @return 结果
     */
    public int updatePd(Pd pd);

    /**
     * 批量删除盘点
     * 
     * @param ids 需要删除的盘点主键集合
     * @return 结果
     */
    public int deletePdByIds(String[] ids);

    /**
     * 删除盘点信息
     * 
     * @param id 盘点主键
     * @return 结果
     */
    public int deletePdById(String id);
}
