package com.ruoyi.tk_custom.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.MsgtowxMapper;
import com.ruoyi.tk_custom.domain.Msgtowx;
import com.ruoyi.tk_custom.service.IMsgtowxService;

/**
 * 发送到企微的消息队列Service业务层处理
 * 
 * @author wfs
 * @date 2025-08-12
 */
@Service
public class MsgtowxServiceImpl implements IMsgtowxService 
{
    @Autowired
    private MsgtowxMapper msgtowxMapper;

    /**
     * 查询发送到企微的消息队列
     * 
     * @param id 发送到企微的消息队列主键
     * @return 发送到企微的消息队列
     */
    @Override
    public Msgtowx selectMsgtowxById(Long id)
    {
        return msgtowxMapper.selectMsgtowxById(id);
    }

    /**
     * 查询发送到企微的消息队列列表
     * 
     * @param msgtowx 发送到企微的消息队列
     * @return 发送到企微的消息队列
     */
    @Override
    public List<Msgtowx> selectMsgtowxList(Msgtowx msgtowx)
    {
        return msgtowxMapper.selectMsgtowxList(msgtowx);
    }

    /**
     * 新增发送到企微的消息队列
     * 
     * @param msgtowx 发送到企微的消息队列
     * @return 结果
     */
    @Override
    public int insertMsgtowx(Msgtowx msgtowx)
    {
        return msgtowxMapper.insertMsgtowx(msgtowx);
    }

    /**
     * 修改发送到企微的消息队列
     * 
     * @param msgtowx 发送到企微的消息队列
     * @return 结果
     */
    @Override
    public int updateMsgtowx(Msgtowx msgtowx)
    {
        return msgtowxMapper.updateMsgtowx(msgtowx);
    }

    /**
     * 批量删除发送到企微的消息队列
     * 
     * @param ids 需要删除的发送到企微的消息队列主键
     * @return 结果
     */
    @Override
    public int deleteMsgtowxByIds(Long[] ids)
    {
        return msgtowxMapper.deleteMsgtowxByIds(ids);
    }

    /**
     * 删除发送到企微的消息队列信息
     * 
     * @param id 发送到企微的消息队列主键
     * @return 结果
     */
    @Override
    public int deleteMsgtowxById(Long id)
    {
        return msgtowxMapper.deleteMsgtowxById(id);
    }
}
