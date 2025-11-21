package com.ruoyi.tk_custom.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.PdGdzcMapper;
import com.ruoyi.tk_custom.domain.PdGdzc;
import com.ruoyi.tk_custom.service.IPdGdzcService;

/**
 * 固定资产盘点Service业务层处理
 * 
 * @author wfs
 * @date 2025-05-09
 */
@Service
public class PdGdzcServiceImpl implements IPdGdzcService 
{
    @Autowired
    private PdGdzcMapper pdGdzcMapper;

    /**
     * 查询固定资产盘点
     * 
     * @param id 固定资产盘点主键
     * @return 固定资产盘点
     */
    @Override
    public PdGdzc selectPdGdzcById(String id)
    {
        return pdGdzcMapper.selectPdGdzcById(id);
    }

    /**
     * 查询固定资产盘点列表
     * 
     * @param pdGdzc 固定资产盘点
     * @return 固定资产盘点
     */
    @Override
    public List<PdGdzc> selectPdGdzcList(PdGdzc pdGdzc)
    {
        return pdGdzcMapper.selectPdGdzcList(pdGdzc);
    }

    /**
     * 新增固定资产盘点
     * 
     * @param pdGdzc 固定资产盘点
     * @return 结果
     */
    @Override
    public int insertPdGdzc(PdGdzc pdGdzc)
    {
        PdGdzc queryParams = new PdGdzc();
        queryParams.setKpBm(pdGdzc.getKpBm());
        queryParams.setPddate(new SimpleDateFormat("YYYY-MM").format(new Date()));
        List<PdGdzc> list = pdGdzcMapper.selectPdGdzcList(queryParams);
        if(list.size()>0){
            pdGdzc.setId(list.get(0).getId());
           return updatePdGdzc(pdGdzc);
        }else{
            pdGdzc.setId(UUID.randomUUID().toString());
            pdGdzc.setCreator(SecurityUtils.getUsername());
            pdGdzc.setPddate(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
            return pdGdzcMapper.insertPdGdzc(pdGdzc);
        }
    }

    /**
     * 修改固定资产盘点
     * 
     * @param pdGdzc 固定资产盘点
     * @return 结果
     */
    @Override
    public int updatePdGdzc(PdGdzc pdGdzc)
    {
        return pdGdzcMapper.updatePdGdzc(pdGdzc);
    }

    /**
     * 批量删除固定资产盘点
     * 
     * @param ids 需要删除的固定资产盘点主键
     * @return 结果
     */
    @Override
    public int deletePdGdzcByIds(String[] ids)
    {
        return pdGdzcMapper.deletePdGdzcByIds(ids);
    }

    /**
     * 删除固定资产盘点信息
     * 
     * @param id 固定资产盘点主键
     * @return 结果
     */
    @Override
    public int deletePdGdzcById(String id)
    {
        return pdGdzcMapper.deletePdGdzcById(id);
    }
}
