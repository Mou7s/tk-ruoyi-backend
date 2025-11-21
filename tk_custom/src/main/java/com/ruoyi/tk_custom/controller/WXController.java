package com.ruoyi.tk_custom.controller;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.tk_custom.domain.Wxuser;
import com.ruoyi.tk_custom.service.IWxuserService;
import com.ruoyi.tk_custom.utils.WXUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;


/**
 * 企业微信接口Controller
 *
 * @author wfs
 * @date 2024-07-12
 */
@RestController
@RequestMapping("/tk_custom/WX")
public class WXController extends BaseController {
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private IWxuserService wxuserService;
//    @Autowired
//    private ISysUserService userService;

    /**
     * 同步企业微信员工信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:WX:syncEmploee')")
    @GetMapping("/syncEmploee")
    public int syncEmploee() {
        ArrayList<JsonObject> userList = WXUtil.getUserObjectListFrom_TongXunLu(0);
        try {
            for (JsonElement jsonElement : userList) {
                JsonObject jb = jsonElement.getAsJsonObject();
                String userid = jb.get("userid").getAsString();
                String name = jb.get("name").getAsString();
                Wxuser wxuser = new Wxuser();
                wxuser.setName(name);
//                wxuser.setUserid();
//                wxuser.setPosition();

               // String str="UPDATE T_SFC_OPERPLANNING SET F_UNW_TEXT_JDX='"+a.ToString()+"',F_UNW_TEXT_038='"+b.ToString()+"',F_UNW_TEXT_HSJ='"+c.ToString()+"'  WHERE FMONUMBER='MO000028-001' and FMOENTRYSEQ='3' ";
                wxuser.setUserid(userid);
                try {
                    wxuserService.insertWxuser(wxuser);
                } catch (Exception e) {
//                    System.out.println(name);
                }
            }
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    /**
     * 同步企业微信部门信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:WX:syncDept')")
    @GetMapping("/syncDept")
    public int list() {
        JsonArray dept = WXUtil.getDept(0);
        try {
            for (JsonElement jsonElement : dept) {
                JsonObject jb = jsonElement.getAsJsonObject();
                SysDept sysDept = new SysDept();
                sysDept.setDeptId(jb.get("id").getAsLong());
                sysDept.setDeptName(jb.get("name").getAsString());
                sysDept.setParentId(jb.get("parentid").getAsLong());
//                sysDept.setLeader(jb.get("department_leader").getAsString());
                deptMapper.insertDept(sysDept);
            }
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }
}

