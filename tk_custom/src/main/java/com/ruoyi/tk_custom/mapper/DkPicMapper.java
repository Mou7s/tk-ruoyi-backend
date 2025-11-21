package com.ruoyi.tk_custom.mapper;

import java.util.List;
import com.ruoyi.tk_custom.domain.DkPic;

/**
 * 弹坑图片Mapper接口
 * 
 * @author wfs
 * @date 2025-09-03
 */
public interface DkPicMapper 
{
    /**
     * 查询弹坑图片
     * 
     * @param id 弹坑图片主键
     * @return 弹坑图片
     */
    public DkPic selectDkPicById(Long id);

    /**
     * 查询弹坑图片列表
     * 
     * @param dkPic 弹坑图片
     * @return 弹坑图片集合
     */
    public List<DkPic> selectDkPicList(DkPic dkPic);

    /**
     * 新增弹坑图片
     * 
     * @param dkPic 弹坑图片
     * @return 结果
     */
    public int insertDkPic(DkPic dkPic);

    /**
     * 修改弹坑图片
     * 
     * @param dkPic 弹坑图片
     * @return 结果
     */
    public int updateDkPic(DkPic dkPic);

    /**
     * 删除弹坑图片
     * 
     * @param id 弹坑图片主键
     * @return 结果
     */
    public int deleteDkPicById(Long id);

    /**
     * 批量删除弹坑图片
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDkPicByIds(Long[] ids);

    List<DkPic> selectDkPicByIds(String[] split);

    List<String> getHandlerList();

    List<String> getMechineList();
}
