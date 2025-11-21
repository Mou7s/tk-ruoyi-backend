package com.ruoyi.tk_custom.service;

import java.util.List;
import com.ruoyi.tk_custom.domain.TkVisit;

/**
 * 来访登记Service接口
 * 
 * @author wfs
 * @date 2024-07-09
 */
public interface ITkVisitService 
{
    /**
     * 查询来访登记
     * 
     * @param id 来访登记主键
     * @return 来访登记
     */
    public TkVisit selectTkVisitById(String id);

    /**
     * 查询来访登记列表
     * 
     * @param tkVisit 来访登记
     * @return 来访登记集合
     */
    public List<TkVisit> selectTkVisitList(TkVisit tkVisit);

    /**
     * 新增来访登记
     * 
     * @param tkVisit 来访登记
     * @return 结果
     */
    public int insertTkVisit(TkVisit tkVisit);

    /**
     * 修改来访登记
     * 
     * @param tkVisit 来访登记
     * @return 结果
     */
    public int updateTkVisit(TkVisit tkVisit);

    /**
     * 批量删除来访登记
     * 
     * @param ids 需要删除的来访登记主键集合
     * @return 结果
     */
    public int deleteTkVisitByIds(String[] ids);

    /**
     * 删除来访登记信息
     * 
     * @param id 来访登记主键
     * @return 结果
     */
    public int deleteTkVisitById(String id);

    public TkVisit selectTkvisitByPhoneNumber(String vTelephone);

}
