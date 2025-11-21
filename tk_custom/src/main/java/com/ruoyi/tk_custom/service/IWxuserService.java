package com.ruoyi.tk_custom.service;

import java.util.List;
import com.ruoyi.tk_custom.domain.Wxuser;

/**
 * 企业微信成员表Service接口
 * 
 * @author wfs
 * @date 2025-04-19
 */
public interface IWxuserService 
{
    /**
     * 查询企业微信成员表
     * 
     * @param userid 企业微信成员表主键
     * @return 企业微信成员表
     */
    public Wxuser selectWxuserByUserid(String userid);

    /**
     * 查询企业微信成员表列表
     * 
     * @param wxuser 企业微信成员表
     * @return 企业微信成员表集合
     */
    public List<Wxuser> selectWxuserList(Wxuser wxuser);

    /**
     * 新增企业微信成员表
     * 
     * @param wxuser 企业微信成员表
     * @return 结果
     */
    public int insertWxuser(Wxuser wxuser);

    /**
     * 修改企业微信成员表
     * 
     * @param wxuser 企业微信成员表
     * @return 结果
     */
    public int updateWxuser(Wxuser wxuser);

    /**
     * 批量删除企业微信成员表
     * 
     * @param userids 需要删除的企业微信成员表主键集合
     * @return 结果
     */
    public int deleteWxuserByUserids(String[] userids);

    /**
     * 删除企业微信成员表信息
     * 
     * @param userid 企业微信成员表主键
     * @return 结果
     */
    public int deleteWxuserByUserid(String userid);

    Wxuser selectWxuserByUserName(String receiver);
}
