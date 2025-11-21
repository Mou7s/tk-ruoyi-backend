package com.ruoyi.tk_custom.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.tk_custom.domain.Talent;
import com.ruoyi.tk_custom.service.ITalentService;
import com.ruoyi.tk_custom.utils.WXUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/tk_custom/talent")
public class TalentController extends BaseController {

    @Autowired
    private ITalentService talentService;


    /**
     * 删除文件
     * @param path
     */
    public static void deleteFile(String path) {
        File file =new File(path);
        if(file.exists())
            file.delete();
        File fileTwo =null;
        if(path.contains("/talentPics/"))
            fileTwo =new File (path.replace("/talentPics/","talentPics_zip"));
        else
            fileTwo  =new File (path.replace("/talentPics_zip/","/talentPics/"));
        if(file.exists())
            fileTwo.delete();
    }

    /**
     * 获取人才列表信息
     * @param talent
     * @return
     */
    @Log(title="人才库列表")
    @GetMapping("/getTalentList")
    public TableDataInfo getTalentList(Talent talent) {
        startPage();
        List<Talent> list = talentService.getTalentList(talent);
        return getDataTable(list);
    }

    /**
     * 添加人才信息
     * @param talent
     * @return
     */
    @Log(title="添加人才数据", businessType = BusinessType.INSERT)
    @PostMapping("/addTalentInfo")
    public AjaxResult addTalentInfo(@RequestBody Talent talent){

        if (talent.getName() == null || talent.getName().trim().isEmpty()) {
            return AjaxResult.error("名称不能为空");
        }
        if (talent.getNativePlace() == null || talent.getNativePlace().trim().isEmpty()) {
            return AjaxResult.error("籍贯不能为空");
        }
        if (talent.getPosition() == null || talent.getPosition().trim().isEmpty()) {
            return AjaxResult.error("岗位不能为空");
        }
        if (talent.getPhoneNumber() == null || talent.getPhoneNumber().trim().isEmpty()) {
            return AjaxResult.error("电话不能为空");
        }
        if (talent.getEduId() == null || talent.getEduId().trim().isEmpty()) {
            return AjaxResult.error("学历不能为空");
        }
        if (talent.getGender() == null ) {
            return AjaxResult.error("性别不能为空");
        }
        if (talent.getBirthDate() == null) {
            return AjaxResult.error("出生日期不能为空");
        }
        talent.setCreateTime(new Date());
        talent.setCreator(SecurityUtils.getNickname());
        talentService.insertTalent(talent);
       return AjaxResult.success(talent);
    }

    /**
     * 修改人才信息
     * @param talent
     * @return
     */
    @Log(title ="修改人才数据")
    @PostMapping("/editTalentInfo")
    public  AjaxResult editTalentInfo(@RequestBody Talent talent){
        if (talent.getName() == null || talent.getName().trim().isEmpty()) {
            return AjaxResult.error("名称不能为空");
        }
        if (talent.getNativePlace() == null || talent.getNativePlace().trim().isEmpty()) {
            return AjaxResult.error("籍贯不能为空");
        }
        if (talent.getPosition() == null || talent.getPosition().trim().isEmpty()) {
            return AjaxResult.error("岗位不能为空");
        }
        if (talent.getPhoneNumber() == null || talent.getPhoneNumber().trim().isEmpty()) {
            return AjaxResult.error("电话不能为空");
        }
        if (talent.getEduId() == null || talent.getEduId().trim().isEmpty()) {
            return AjaxResult.error("学历不能为空");
        }
        if (talent.getGender() == null ) {
            return AjaxResult.error("性别不能为空");
        }
        if (talent.getBirthDate() == null) {
            return AjaxResult.error("出生日期不能为空");
        }
        Talent oldTalent = talentService.getTalentById(talent.getId());
        String oldImagePathsStr = oldTalent.getAttachments();
        String[] oldImagePaths = new String[0];
        if (oldImagePathsStr != null) {
            oldImagePaths =  oldImagePathsStr.split(",");
        }
        String imagePathsStr = talent.getAttachments();
        String[] currentImagePaths =  new String[0];
        if (imagePathsStr != null) {
            currentImagePaths =  imagePathsStr.split(",");
        }
        ArrayList<String> arr = new ArrayList<>();
        for(String oldImagePath :oldImagePaths) {
            boolean isExist = false;
            for(String imagePath:  currentImagePaths) {
                if(oldImagePath.equals(imagePath)) {
                    isExist = true;
                    break;
                }
            }
            if(!isExist) {
                arr.add(RuoYiConfig.getProfile() + oldImagePath.replace("/dev-api/profile", ""));
            }
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (String str : arr) {
                    deleteFile(str);
                }
            }
        });
        talent.setModifier(SecurityUtils.getNickname());
        talent.setUpdateTime(new Date());
        return toAjax(talentService.updateTalent(talent));
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();


    /**
     * 人才资料图片上传
     * @param imageFiles
     * @return
     * @throws Exception
     */
    @Log(title = "人才资料图片上传")
    @PostMapping("/talentPic")
    public AjaxResult savePic(@RequestParam("imageFiles") MultipartFile[] imageFiles) throws Exception{
        List<String> successPaths = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            if(!file.isEmpty()) {
                String fileDir = RuoYiConfig.getProfile() + "/talentPics";
                String tarDir = RuoYiConfig.getProfile() + "/talentPic_zip";
                File file3 = new File(fileDir);
                if(!file3.exists())
                    file3.mkdirs();
                String img = FileUploadUtils.upload(fileDir,file, MimeTypeUtils.IMAGE_EXTENSION);
                String[] split = img.split("/talentPics");
                String srcPath = fileDir +  split[split.length -1];
                String imgTar = tarDir + split[split.length - 1];
                File file2  = new File(tarDir);
                if(!file2.exists())
                    file2.mkdirs();
                if (new File(srcPath).exists())
                    WXUtil.tarFile(srcPath,imgTar);

                successPaths.add(img);
            }
        }
        return success(successPaths);
    }

    /**
     * 获取人才信息依据主键信息
     * @param id
     * @return
     */
    @Log(title="获取人才信息")
    @GetMapping("/getTalentInfo")
    public AjaxResult getTalentInfo(@RequestParam Long id) {
        return  success(talentService.getTalentById(id));
    }


    /**
     *  依据ids批量删除人才信息
     * @param ids
     * @return
     */
    @Log(title = "删除人才", businessType = BusinessType.DELETE)
    @DeleteMapping("/delTalentByIds")
    public AjaxResult delTalent(@RequestBody Long[] ids) {
        return AjaxResult.success(talentService.deleteTalentByIds(ids));
    }

    /**
     * 依据id删除人才信息
     * @param id
     * @return
     */
    @Log(title="删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delTalentById/{id}")
    public AjaxResult delTalentById(@PathVariable Long id){
        Talent talent =  new Talent();
        talent.setId(id);
        return  AjaxResult.success(talentService.deleteTalentById(talent));
    }





}
