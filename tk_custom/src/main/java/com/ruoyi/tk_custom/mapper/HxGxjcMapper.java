package com.ruoyi.tk_custom.mapper;

import java.util.List;
import com.ruoyi.tk_custom.domain.HxGxjc;

/**
 * 焊线工序检测记录Mapper接口
 * 
 * @author wfs
 * @date 2025-10-13
 */
public interface HxGxjcMapper 
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
     * 删除焊线工序检测记录
     * 
     * @param id 焊线工序检测记录主键
     * @return 结果
     */
    public int deleteHxGxjcById(Long id);

    /**
     * 批量删除焊线工序检测记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHxGxjcByIds(Long[] ids);

    List<String> getMechineList();

    List<String> getHandlerList();
}
