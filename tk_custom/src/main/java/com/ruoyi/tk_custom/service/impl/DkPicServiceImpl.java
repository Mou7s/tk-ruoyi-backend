package com.ruoyi.tk_custom.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.DkPicMapper;
import com.ruoyi.tk_custom.domain.DkPic;
import com.ruoyi.tk_custom.service.IDkPicService;

/**
 * 弹坑图片Service业务层处理
 * 
 * @author wfs
 * @date 2025-09-03
 */
@Service
public class DkPicServiceImpl implements IDkPicService 
{
    @Autowired
    private DkPicMapper dkPicMapper;

    /**
     * 查询弹坑图片
     * 
     * @param id 弹坑图片主键
     * @return 弹坑图片
     */
    @Override
    public DkPic selectDkPicById(Long id)
    {
        return dkPicMapper.selectDkPicById(id);
    }

    /**
     * 查询弹坑图片列表
     * 
     * @param dkPic 弹坑图片
     * @return 弹坑图片
     */
    @Override
    public List<DkPic> selectDkPicList(DkPic dkPic)
    {
        return dkPicMapper.selectDkPicList(dkPic);
    }

    /**
     * 新增弹坑图片
     * 
     * @param dkPic 弹坑图片
     * @return 结果
     */
    @Override
    public int insertDkPic(DkPic dkPic)
    {
        dkPic.setcreatedate(new Date());
        dkPic.setModifydate(new Date());
        dkPic.setCreator(SecurityUtils.getUsername());
        dkPic.setUpdater(SecurityUtils.getUsername());
        return dkPicMapper.insertDkPic(dkPic);
    }

    /**
     * 修改弹坑图片
     * 
     * @param dkPic 弹坑图片
     * @return 结果
     */
    @Override
    public int updateDkPic(DkPic dkPic)
    {
        dkPic.setModifydate(new Date());
        dkPic.setUpdater(SecurityUtils.getUsername());
        return dkPicMapper.updateDkPic(dkPic);
    }

    /**
     * 批量删除弹坑图片
     * 
     * @param ids 需要删除的弹坑图片主键
     * @return 结果
     */
    @Override
    public int deleteDkPicByIds(Long[] ids)
    {
        return dkPicMapper.deleteDkPicByIds(ids);
    }

    /**
     * 删除弹坑图片信息
     * 
     * @param id 弹坑图片主键
     * @return 结果
     */
    @Override
    public int deleteDkPicById(Long id)
    {
        return dkPicMapper.deleteDkPicById(id);
    }

    @Override
    public List<DkPic> selectDkPicByIds(String[] split) {
       return dkPicMapper.selectDkPicByIds(split);
    }

    @Override
    public List<String> getHandlerList() {
        return dkPicMapper.getHandlerList();
    }

    @Override
    public List<String> getMechineList() {
        return dkPicMapper.getMechineList();
    }
}
