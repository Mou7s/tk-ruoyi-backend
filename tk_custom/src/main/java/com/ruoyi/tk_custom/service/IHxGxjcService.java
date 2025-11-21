package com.ruoyi.tk_custom.service;

import java.util.List;
import com.ruoyi.tk_custom.domain.HxGxjc;

/**
 * 焊线工序检测记录Service接口
 * 
 * @author wfs
 * @date 2025-10-13
 */
public interface IHxGxjcService 
{
    /**
     * 查询焊线工序检测记录
     * 
     * @param id 焊线工序检测记录主键
     * @return 焊线工序检测记录
     */
    public HxGxjc selectHxGxjcById(Long id);

    /**
     * 查询焊线工序检测记录列表
     * 
     * @param hxGxjc 焊线工序检测记录
     * @return 焊线工序检测记录集合
     */
    public List<HxGxjc> selectHxGxjcList(HxGxjc hxGxjc);

    /**
     * 新增焊线工序检测记录
     * 
     * @param hxGxjc 焊线工序检测记录
     * @return 结果
     */
    public int insertHxGxjc(HxGxjc hxGxjc);

    /**
     * 修改焊线工序检测记录
     * 
     * @param hxGxjc 焊线工序检测记录
     * @return 结果
     */
    public int updateHxGxjc(HxGxjc hxGxjc);

    /**
     * 批量删除焊线工序检测记录
     * 
     * @param ids 需要删除的焊线工序检测记录主键集合
     * @return 结果
     */
    public int deleteHxGxjcByIds(Long[] ids);

    /**
     * 删除焊线工序检测记录信息
     * 
     * @param id 焊线工序检测记录主键
     * @return 结果
     */
    public int deleteHxGxjcById(Long id);

    List<String> getMechineList();

    List<String> getHandlerList();
}
