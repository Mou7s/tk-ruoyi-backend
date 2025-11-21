package com.ruoyi.tk_custom.mapper;

import java.util.List;
import com.ruoyi.tk_custom.domain.TkSuggest;

/**
 * 意见/建议Mapper接口
 * 
 * @author wfs
 * @date 2025-08-11
 */
public interface TkSuggestMapper 
{
    /**
     * 查询意见/建议
     * 
     * @param id 意见/建议主键
     * @return 意见/建议
     */
    public TkSuggest selectTkSuggestById(String id);

    /**
     * 查询意见/建议列表
     * 
     * @param tkSuggest 意见/建议
     * @return 意见/建议集合
     */
    public List<TkSuggest> selectTkSuggestList(TkSuggest tkSuggest);

    /**
     * 新增意见/建议
     * 
     * @param tkSuggest 意见/建议
     * @return 结果
     */
    public int insertTkSuggest(TkSuggest tkSuggest);

    /**
     * 修改意见/建议
     * 
     * @param tkSuggest 意见/建议
     * @return 结果
     */
    public int updateTkSuggest(TkSuggest tkSuggest);

    /**
     * 删除意见/建议
     * 
     * @param id 意见/建议主键
     * @return 结果
     */
    public int deleteTkSuggestById(String id);

    /**
     * 批量删除意见/建议
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTkSuggestByIds(String[] ids);
}
