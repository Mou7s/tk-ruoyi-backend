package com.ruoyi.tk_custom.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.TkSuggestMapper;
import com.ruoyi.tk_custom.domain.TkSuggest;
import com.ruoyi.tk_custom.service.ITkSuggestService;

/**
 * 意见/建议Service业务层处理
 * 
 * @author wfs
 * @date 2025-08-11
 */
@Service
public class TkSuggestServiceImpl implements ITkSuggestService 
{
    @Autowired
    private TkSuggestMapper tkSuggestMapper;

    /**
     * 查询意见/建议
     * 
     * @param id 意见/建议主键
     * @return 意见/建议
     */
    @Override
    public TkSuggest selectTkSuggestById(String id)
    {
        return tkSuggestMapper.selectTkSuggestById(id);
    }

    /**
     * 查询意见/建议列表
     * 
     * @param tkSuggest 意见/建议
     * @return 意见/建议
     */
    @Override
    public List<TkSuggest> selectTkSuggestList(TkSuggest tkSuggest)
    {
        return tkSuggestMapper.selectTkSuggestList(tkSuggest);
    }

    /**
     * 新增意见/建议
     * 
     * @param tkSuggest 意见/建议
     * @return 结果
     */
    @Override
    public int insertTkSuggest(TkSuggest tkSuggest)
    {
        return tkSuggestMapper.insertTkSuggest(tkSuggest);
    }

    /**
     * 修改意见/建议
     * 
     * @param tkSuggest 意见/建议
     * @return 结果
     */
    @Override
    public int updateTkSuggest(TkSuggest tkSuggest)
    {
        return tkSuggestMapper.updateTkSuggest(tkSuggest);
    }

    /**
     * 批量删除意见/建议
     * 
     * @param ids 需要删除的意见/建议主键
     * @return 结果
     */
    @Override
    public int deleteTkSuggestByIds(String[] ids)
    {
        return tkSuggestMapper.deleteTkSuggestByIds(ids);
    }

    /**
     * 删除意见/建议信息
     * 
     * @param id 意见/建议主键
     * @return 结果
     */
    @Override
    public int deleteTkSuggestById(String id)
    {
        return tkSuggestMapper.deleteTkSuggestById(id);
    }
}
