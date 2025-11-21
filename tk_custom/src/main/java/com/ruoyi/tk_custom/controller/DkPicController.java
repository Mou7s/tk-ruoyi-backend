package com.ruoyi.tk_custom.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.tk_custom.domain.Pd;
import com.ruoyi.tk_custom.utils.KingdeeUtil;
import com.ruoyi.tk_custom.utils.WXUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.tk_custom.domain.DkPic;
import com.ruoyi.tk_custom.service.IDkPicService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 弹坑图片Controller
 *
 * @author wfs
 * @date 2025-09-02
 */
@RestController
@RequestMapping("/tk_custom/pic")
public class DkPicController extends BaseController {
    @Autowired
    private IDkPicService dkPicService;

    /**
     * 弹坑图片上传
     */
    @Log(title = "弹坑图片上传")
    @PostMapping("/dkPic")
    public AjaxResult dkPic(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String fileDir = RuoYiConfig.getProfile() + "/dkPic";
            String tarDir = RuoYiConfig.getProfile() + "/dkPic_zip";
            File file3 = new File(fileDir);
            if (!file3.exists())
                file3.mkdirs();
            String img = FileUploadUtils.upload(fileDir, file, MimeTypeUtils.IMAGE_EXTENSION);
            String[] split = img.split("/dkPic");
            String srcPath = fileDir + split[split.length - 1];
            String imgTar = tarDir + split[split.length - 1];
            File file2 = new File(tarDir);
            if (!file2.exists())
                file2.mkdirs();
            if (new File(srcPath).exists())
                WXUtil.tarFile(srcPath, imgTar);
            return new AjaxResult(200, img.replace("/dkPic", "/dkPic_zip"));
        }
        return error("上传图片异常，请联系管理员");
    }

    @Log(title = "流程单信息获取")
    @GetMapping("/getFlowBillInfo/{flowno}")
    public JSONObject getFlowBillInfo(@PathVariable("flowno") String flowno) {
        List<List<Object>> list = KingdeeUtil.getData("SFC_OperationPlanning",
                "FSubEntity_FSeq," +//序号
                        "FBillNo," +//流程单号
                        "FProductId.FNumber," +//产品编码
                        "FProductName," +//产品名称
                        "FProSpecification," +//封装
                        "F_UNW_Text_w5c," +//芯片批号
                        "F_UNW_BaseProperty_re51," +//芯片尺寸
                        "FCustId.FShortName," +//客户名
                        "F_UNW_BaseProperty_re5"//芯片型号
                ,
                "[" +
                        "{\"Left\":\"(\",\"FieldName\":\"FBillNo\",\"Compare\":\"67\",\"Value\":\"" + flowno + "\",\"Right\":\")\",\"Logic\":\"0\"}" +
                        "]", "", "0", "50");
        JSONObject jb = new JSONObject();
        if (list.size() > 0) {
            List<Object> objects = list.get(0);
            jb.put("FBillNo", objects.get(1));
            jb.put("productId", objects.get(2));
            jb.put("productName", objects.get(3));
            jb.put("specification", objects.get(4));
            jb.put("xpph", objects.get(5));
            jb.put("xpcc", objects.get(6));
            jb.put("khdm", objects.get(7));
            jb.put("xpxh", objects.get(8));

        }
        return jb;
    }

    /**
     * 弹坑图片上传
     */
    @Log(title = "弹坑图片删除")
    @PostMapping("/deldkPic")
    public void deldkPic(@RequestBody JSONObject jb) throws Exception {
        String[] split = jb.get("filename").toString().split("/dkPic/");
        deleteFile(RuoYiConfig.getProfile() + "/dkPic/" + split[split.length - 1]);
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists())
            file.delete();
        File file1 = null;
        if (path.contains("/dkPic/"))
            file1 = new File(path.replace("/dkPic/", "/dkPic_zip/"));
        else
            file1 = new File(path.replace("/dkPic_zip/", "/dkPic/"));
        if (file1.exists())
            file1.delete();
    }

    /**
     * 查询弹坑图片列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pic:list')")
    @GetMapping("/list")
    public TableDataInfo list(DkPic dkPic) {
        startPage();
        List<DkPic> list = dkPicService.selectDkPicList(dkPic);
        for (DkPic pic : list) {
            String imgPaths = pic.getImgPaths();
            pic.setImgSrcPaths(imgPaths.replaceAll("/dkPic_zip/", "/dkPic/"));
        }
        return getDataTable(list);
    }
    /**
     * 查询操作员列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pic:list')")
    @GetMapping("/getHandlerList")
    public List<String> getHandlerList() {
        List<String> list=dkPicService.getHandlerList();
        return list;
    }
    /**
     * 查询机台号列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pic:list')")
    @GetMapping("/getMechineList")
    public List<String> getMechineList() {
        List<String> list=dkPicService.getMechineList();
        return list;
    }
    /**
     * 导出弹坑图片列表
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pic:export')")
    @Log(title = "弹坑图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DkPic dkPic) {
        List<DkPic> list = dkPicService.selectDkPicList(dkPic);
        ExcelUtil<DkPic> util = new ExcelUtil<DkPic>(DkPic.class);
        ArrayList<DkPic> list_new=new ArrayList<DkPic>();
        for (int i = 0; i < list.size(); i++) {
            DkPic pic=list.get(i);
            pic.setImgPaths(pic.getImgPaths().replace("/dkPic_zip/", "/dkPic/"));
            if(i<200)
                list_new.add(pic);
        }
        util.exportExcel(response, list_new, "弹坑图片数据");
    }

    /**
     * 导出弹坑图片列表_选中
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pic:export')")
    @Log(title = "弹坑图片", businessType = BusinessType.EXPORT)
    @PostMapping("/exportSelect")
    public void exportSelect(HttpServletResponse response, DkPic dkPic) {
        String remark = dkPic.getRemark();
        if ("".equals(remark) || remark == null)
            return;
        List<DkPic> list = dkPicService.selectDkPicByIds(remark.split(","));
        ExcelUtil<DkPic> util = new ExcelUtil<DkPic>(DkPic.class);
        for (DkPic pic : list) {
            pic.setImgPaths(pic.getImgPaths().replace("/dkPic_zip/", "/dkPic/"));
        }
        util.exportExcel(response, list, "弹坑图片数据");
    }

    /**
     * 获取弹坑图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pic:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(dkPicService.selectDkPicById(id));
    }

    /**
     * 新增弹坑图片
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pic:add')")
    @Log(title = "弹坑图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DkPic dkPic) {
        return toAjax(dkPicService.insertDkPic(dkPic));
    }

    /**
     * 修改弹坑图片
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pic:edit')")
    @Log(title = "弹坑图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DkPic dkPic) {
        DkPic dkPicOld = dkPicService.selectDkPicById(dkPic.getId());
        String remarkOld = dkPicOld.getImgPaths();
        String[] pathsOld = remarkOld.split(",");
        String remark = dkPic.getImgPaths();
        String[] paths = remark.split(",");
        ArrayList<String> arr = new ArrayList<>();
        for (String pathOld : pathsOld) {
            boolean isexist = false;
            for (String path : paths) {
                if (pathOld.equals(path)) {
                    isexist = true;
                    break;
                }
            }
            if (!isexist)
                arr.add(RuoYiConfig.getProfile() + pathOld.replace("/dev-api/profile", ""));
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (String s : arr) {
                    deleteFile(s);
                }
            }
        });
        //todo 删除图片
        return toAjax(dkPicService.updateDkPic(dkPic));
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 删除弹坑图片
     */
    @PreAuthorize("@ss.hasPermi('tk_custom:pic:remove')")
    @Log(title = "弹坑图片", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        ArrayList<String> arr = new ArrayList<>();
        for (Long id : ids) {
            DkPic dkPic = dkPicService.selectDkPicById(id);
            String imgPaths = dkPic.getImgPaths();
            if (!"".equals(imgPaths)) {
                String[] split = imgPaths.split(",");
                for (String s : split) {
                    arr.add(RuoYiConfig.getProfile() + s.replace("/dev-api/profile", ""));
                }
            }
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (String s : arr) {
                    deleteFile(s);
                }
            }
        });
        return toAjax(dkPicService.deleteDkPicByIds(ids));
    }
}
