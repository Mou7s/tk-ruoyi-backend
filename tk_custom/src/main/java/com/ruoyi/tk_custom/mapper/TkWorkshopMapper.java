package com.ruoyi.tk_custom.mapper;

import java.util.List;
import com.ruoyi.tk_custom.domain.TkWorkshop;

/**
 * 扫码登记（车间）Mapper接口
 * 
 * @author wfs
 * @date 2025-04-19
 */
public interface TkWorkshopMapper 
{
    /**
     * 查询扫码登记（车间）
     * 
     * @param id 扫码登记（车间）主键
     * @return 扫码登记（车间）
     */
    public TkWorkshop selectTkWorkshopById(String id);

    /**
     * 查询扫码登记（车间）列表
     * 
     * @param tkWorkshop 扫码登记（车间）
     * @return 扫码登记（车间）集合
     */
    public List<TkWorkshop> selectTkWorkshopList(TkWorkshop tkWorkshop);

    /**
     * 新增扫码登记（车间）
     * 
     * @param tkWorkshop 扫码登记（车间）
     * @return 结果
     */
    public int insertTkWorkshop(TkWorkshop tkWorkshop);

    /**
     * 修改扫码登记（车间）
     * 
     * @param tkWorkshop 扫码登记（车间）
     * @return 结果
     */
    public int updateTkWorkshop(TkWorkshop tkWorkshop);

    /**
     * 删除扫码登记（车间）
     * 
     * @param id 扫码登记（车间）主键
     * @return 结果
     */
    public int deleteTkWorkshopById(String id);

    /**
     * 批量删除扫码登记（车间）
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTkWorkshopByIds(String[] ids);
}
