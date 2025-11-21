package com.ruoyi.tk_custom.service;

import com.ruoyi.tk_custom.domain.Talent;
import com.ruoyi.tk_custom.mapper.TalentMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ITalentService {


    /**
     * 查询人才列表
     * @param talent
     * @return List<Talent>
     */
    public List<Talent> getTalentList(Talent talent) ;

    /**
     * 依据id查询人才信息
     * @param id
     * @return
     */
    public Talent getTalentById(Long id);

    /**
     * 插入人才信息
     * @param talent
     * @return
     */
    public int insertTalent(Talent talent);

    /**
     * 更新人才信息
     * @param talent
     * @return
     */
    public int  updateTalent (Talent talent);

    /**
     * 依据id删除人才信息
     * @param id
     * @return
     */
    public int deleteTalentById(Talent talent);

    /**
     * 已经ids批量删除人才信息
     * @param ids
     * @return
     */
    public int deleteTalentByIds(Long[] ids);
}
