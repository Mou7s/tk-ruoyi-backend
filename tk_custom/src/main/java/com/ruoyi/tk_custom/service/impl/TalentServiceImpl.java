package com.ruoyi.tk_custom.service.impl;

import com.ruoyi.tk_custom.domain.Talent;
import com.ruoyi.tk_custom.mapper.TalentMapper;
import com.ruoyi.tk_custom.service.ITalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TalentServiceImpl implements ITalentService {

    @Autowired
    private TalentMapper talentMapper;
    /**
     * 查询人才列表
     *
     * @param talent
     * @return List<Talent>
     */
    @Override
    public List<Talent> getTalentList(Talent talent) {
       return talentMapper.getTalentList(talent);
    }

    /**
     * 依据id查询人才信息
     *
     * @param id
     * @return
     */
    @Override
    public Talent getTalentById(Long id) {
        return talentMapper.getTalentById(id);
    }

    /**
     * 插入人才信息
     *
     * @param talent
     * @return
     */
    @Override
    public int insertTalent(Talent talent) {
        return talentMapper.insertTalent(talent);
    }

    /**
     * 更新人才信息
     *
     * @param talent
     * @return
     */
    @Override
    public int updateTalent(Talent talent) {
        return talentMapper.updateTalent(talent);
    }

    /**
     * 依据id删除人才信息
     *
     * @param id
     * @return
     */
    @Override
    public int deleteTalentById(Talent talent) {
        return talentMapper.deleteTalentById(talent);
    }

    /**
     * 已经ids批量删除人才信息
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteTalentByIds(Long[] ids) {
        return talentMapper.deleteTalentByIds(ids);
    }
}
