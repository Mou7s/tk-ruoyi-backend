package com.ruoyi.tk_custom.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.tk_custom.domain.Kucun;
import com.ruoyi.tk_custom.domain.Kucun_Sum;

/**
 * 金蝶库存查询Mapper接口
 * 
 * @author wfs
 * @date 2025-06-18
 */
public interface KucunMapper 
{
    /**
     * 查询金蝶库存查询
     * 
     * @param id 金蝶库存查询主键
     * @return 金蝶库存查询
     */
    public Kucun selectKucunById(String id);

    /**
     * 查询金蝶库存查询列表
     * 
     * @param kucun 金蝶库存查询
     * @return 金蝶库存查询集合
     */
    public List<Kucun> selectKucunList(Kucun kucun);
    public List<Kucun_Sum> selectKuCun(Kucun kucun);
    public Kucun selectLastTime();
    public int deleteAll();
    /**
     * 新增金蝶库存查询
     * 
     * @param kucun 金蝶库存查询
     * @return 结果
     */
    public int insertKucun(Kucun kucun);

    /**
     * 修改金蝶库存查询
     * 
     * @param kucun 金蝶库存查询
     * @return 结果
     */
    public int updateKucun(Kucun kucun);

    /**
     * 删除金蝶库存查询
     * 
     * @param id 金蝶库存查询主键
     * @return 结果
     */
    public int deleteKucunById(String id);

    /**
     * 批量删除金蝶库存查询
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKucunByIds(String[] ids);

    BigDecimal selectXSTHTotal(Kucun kucun);
    BigDecimal selectXSCHTotal(Kucun kucun);
}
