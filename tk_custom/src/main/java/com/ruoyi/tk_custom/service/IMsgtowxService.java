package com.ruoyi.tk_custom.service;

import java.util.List;
import com.ruoyi.tk_custom.domain.Msgtowx;

/**
 * 发送到企微的消息队列Service接口
 * 
 * @author wfs
 * @date 2025-08-12
 */
public interface IMsgtowxService 
{
    /**
     * 查询发送到企微的消息队列
     * 
     * @param id 发送到企微的消息队列主键
     * @return 发送到企微的消息队列
     */
    public Msgtowx selectMsgtowxById(Long id);

    /**
     * 查询发送到企微的消息队列列表
     * 
     * @param msgtowx 发送到企微的消息队列
     * @return 发送到企微的消息队列集合
     */
    public List<Msgtowx> selectMsgtowxList(Msgtowx msgtowx);

    /**
     * 新增发送到企微的消息队列
     * 
     * @param msgtowx 发送到企微的消息队列
     * @return 结果
     */
    public int insertMsgtowx(Msgtowx msgtowx);

    /**
     * 修改发送到企微的消息队列
     * 
     * @param msgtowx 发送到企微的消息队列
     * @return 结果
     */
    public int updateMsgtowx(Msgtowx msgtowx);

    /**
     * 批量删除发送到企微的消息队列
     * 
     * @param ids 需要删除的发送到企微的消息队列主键集合
     * @return 结果
     */
    public int deleteMsgtowxByIds(Long[] ids);

    /**
     * 删除发送到企微的消息队列信息
     * 
     * @param id 发送到企微的消息队列主键
     * @return 结果
     */
    public int deleteMsgtowxById(Long id);
}
