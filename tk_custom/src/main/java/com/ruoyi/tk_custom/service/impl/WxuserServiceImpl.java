package com.ruoyi.tk_custom.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tk_custom.mapper.WxuserMapper;
import com.ruoyi.tk_custom.domain.Wxuser;
import com.ruoyi.tk_custom.service.IWxuserService;

/**
 * 企业微信成员表Service业务层处理
 * 
 * @author wfs
 * @date 2025-04-19
 */
@Service
public class WxuserServiceImpl implements IWxuserService 
{
    @Autowired
    private WxuserMapper wxuserMapper;

    /**
     * 查询企业微信成员表
     * 
     * @param userid 企业微信成员表主键
     * @return 企业微信成员表
     */
    @Override
    public Wxuser selectWxuserByUserid(String userid)
    {
        return wxuserMapper.selectWxuserByUserid(userid);
    }

    /**
     * 查询企业微信成员表列表
     * 
     * @param wxuser 企业微信成员表
     * @return 企业微信成员表
     */
    @Override
    public List<Wxuser> selectWxuserList(Wxuser wxuser)
    {
        return wxuserMapper.selectWxuserList(wxuser);
    }

    /**
     * 新增企业微信成员表
     * 
     * @param wxuser 企业微信成员表
     * @return 结果
     */
    @Override
    public int insertWxuser(Wxuser wxuser)
    {
        return wxuserMapper.insertWxuser(wxuser);
    }

    /**
     * 修改企业微信成员表
     * 
     * @param wxuser 企业微信成员表
     * @return 结果
     */
    @Override
    public int updateWxuser(Wxuser wxuser)
    {
        return wxuserMapper.updateWxuser(wxuser);
    }

    /**
     * 批量删除企业微信成员表
     * 
     * @param userids 需要删除的企业微信成员表主键
     * @return 结果
     */
    @Override
    public int deleteWxuserByUserids(String[] userids)
    {
        return wxuserMapper.deleteWxuserByUserids(userids);
    }

    /**
     * 删除企业微信成员表信息
     * 
     * @param userid 企业微信成员表主键
     * @return 结果
     */
    @Override
    public int deleteWxuserByUserid(String userid)
    {
        return wxuserMapper.deleteWxuserByUserid(userid);
    }

    @Override
    public Wxuser selectWxuserByUserName(String receiver) {
        return null;
    }
}
