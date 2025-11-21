package com.ruoyi.tk_custom.mapper;

import com.ruoyi.tk_custom.domain.Talent;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TalentMapper {


    /**
     * 查询列表数据
     * @param talent
     * @return  List<Talent>
     */
    public List<Talent> getTalentList(Talent talent);

    /**
     * 依据id查询数据
     * @param id
     * @return Talent
     */
    public Talent  getTalentById(Long id);

    /**
     *  插入数据
     * @param Talent
     * @return int
     */
    public int insertTalent(Talent Talent);

    /**
     *  更新数据
     * @param talent
     * @return int
     */
    public int updateTalent(Talent talent);

    /**
     *  依据id删除数据
     * @param id
     * @return int
     */
    public int deleteTalentById(Talent talent);

    /**
     * 依据ids批量删除数据
     * @param ids
     * @return int
     */
    public int deleteTalentByIds(@Param("ids") Long[] ids);
}
