package com.ruoyi.tk_custom.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.TkVisitMapper;
import com.ruoyi.tk_custom.domain.TkVisit;
import com.ruoyi.tk_custom.service.ITkVisitService;

/**
 * 来访登记Service业务层处理
 * 
 * @author wfs
 * @date 2024-07-09
 */
@Service
public class TkVisitServiceImpl implements ITkVisitService 
{
    @Autowired
    private TkVisitMapper tkVisitMapper;

    /**
     * 查询来访登记
     * 
     * @param id 来访登记主键
     * @return 来访登记
     */
    @Override
    public TkVisit selectTkVisitById(String id)
    {
        return tkVisitMapper.selectTkVisitById(id);
    }

    /**
     * 查询来访登记列表
     * 
     * @param tkVisit 来访登记
     * @return 来访登记
     */
    @Override
    public List<TkVisit> selectTkVisitList(TkVisit tkVisit)
    {
        return tkVisitMapper.selectTkVisitList(tkVisit);
    }

    /**
     * 新增来访登记
     * 
     * @param tkVisit 来访登记
     * @return 结果
     */
    @Override
    public int insertTkVisit(TkVisit tkVisit)
    {
        tkVisit.setId(IdUtils.fastUUID());
        tkVisit.setCreatedate(new Date());
        return tkVisitMapper.insertTkVisit(tkVisit);
    }

    /**
     * 修改来访登记
     * 
     * @param tkVisit 来访登记
     * @return 结果
     */
    @Override
    public int updateTkVisit(TkVisit tkVisit)
    {
        return tkVisitMapper.updateTkVisit(tkVisit);
    }

    /**
     * 批量删除来访登记
     * 
     * @param ids 需要删除的来访登记主键
     * @return 结果
     */
    @Override
    public int deleteTkVisitByIds(String[] ids)
    {
        return tkVisitMapper.deleteTkVisitByIds(ids);
    }

    /**
     * 删除来访登记信息
     * 
     * @param id 来访登记主键
     * @return 结果
     */
    @Override
    public int deleteTkVisitById(String id)
    {
        return tkVisitMapper.deleteTkVisitById(id);
    }

    @Override
    public TkVisit selectTkvisitByPhoneNumber(String vTelephone) {
        return tkVisitMapper.selectTkVisitByPhoneNumber(vTelephone);
    }

}
