package com.ruoyi.tk_custom.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.ruoyi.common.config.RuoYiConfig;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ExcelUtil {
    private static HashMap objs_KJ=new HashMap();//框架Object
    //填充至外发上锡模板并返回文件名
    public static String fillToTemplete(List<List<Object>> list,String billnos){
        if(list.size()==0)
            return "";
        List<HashMap> list1=ListUtils.newArrayList();
        List<HashMap> list2=ListUtils.newArrayList();
        Map<String, Object> map = MapUtils.newHashMap();
        List<Object> firstRow = list.get(0);
        map.put("head_1", firstRow.get(0));
        map.put("head_2", billnos);
        map.put("head_3", firstRow.get(2));
        map.put("head_4", firstRow.get(3).toString().substring(0,10));
        int index=1;
        int list1_total=0;
        for (List<Object> objects : list) {
            HashMap<String, Object> itemObj = new HashMap<>();
            String kuangjia=""+(int)objects .get(12);
            //避免资源浪费，重复查询
            if(!objs_KJ.containsKey(kuangjia)){
                HashMap kuangJiaInfo = DBUtil.getKuangJiaInfo(kuangjia);
                String st="";
                if(kuangJiaInfo.size()>0)
                    st= ((String) kuangJiaInfo.get("name"));
                objs_KJ.put(kuangjia,st);
            }
            itemObj.put("col9",objs_KJ.get(kuangjia));
            itemObj.put("col1",index);
            index++;
            itemObj.put("col2",objects.get(4));
            itemObj.put("col3",objects.get(5));
            int tiao = getTiao(objects);
            list1_total+=tiao;
            itemObj.put("col7",tiao);//条
            String fengzhuang = (String) objects.get(6);
            String[] split = fengzhuang.split("/");
            String fengzhuangStr=split[0];//封装
            String key=fengzhuangStr+objs_KJ.get(kuangjia);
            boolean isExist=false;
            for (HashMap hashMap : list2) {
                if(hashMap.get("col2").equals(fengzhuangStr)&&hashMap.get("col5").equals(objs_KJ.get(kuangjia))){//已经存在
                    hashMap.put("col3",tiao+(int)hashMap.get("col3"));
                    hashMap.put("col4",(int)hashMap.get("col4")+1);//批数
                    hashMap.put("col6",(int)hashMap.get("col6")+(((String) objects.get(8)).split("/").length));//盒数
                    isExist=true;
                    break;
                }
            }
            if(!isExist){
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("col1",list2.size()+1);
                map1.put("col2",fengzhuangStr);
                map1.put("col3",tiao);
                map1.put("col4",1);
                map1.put("col5",objs_KJ.get(kuangjia));
                map1.put("col6", (((String) objects.get(8)).split("/").length));
                list2.add(map1);
            }
            itemObj.put("col4",fengzhuangStr);
            itemObj.put("col5",objects.get(7));
            itemObj.put("col6",objects.get(9));
            itemObj.put("col8",objects.get(8));
            list1.add(itemObj);
            // TODO
        }
        int list2_total=0;
        for (HashMap map2 : list2) {
            list2_total+=(int)map2.get("col4");
        }
        map.put("list1_total", list1_total);
        map.put("list2_total", list2_total);
        //todo
        String exceltemplete = RuoYiConfig.getProfile()+"\\wfsx_excel\\WFDD.xlsx";
        String fileName=exceltemplete.replace("WFDD","LS");
        ZipSecureFile.setMinInflateRatio(-1D);
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(exceltemplete).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(new FillWrapper("list1", list1),fillConfig, writeSheet);
            excelWriter.fill(new FillWrapper("list2", list2),fillConfig, writeSheet);
            excelWriter.fill(map, writeSheet);
            return fileName;
        }catch (Exception e){
            System.out.println(e);
            return "";
        }
    }
    private static int getTiao(List<Object> obj){
        if(((String) obj.get(4)).contains("TW054270")){
            System.out.println(111);
        }
        // int ( round (  (((FOutSendQtyFake *1000)* (FRateValQty *1000))/ (FRateOperQty*1000 ))/1000+ 0.4999999999 ) )
        Double FOutSendQtyFake = ((Double) obj.get(9));
        Double FRateValQty = ((Double) obj.get(10));
        Double FRateOperQty = ((Double) obj.get(11));
        return (int)Math.round(((FOutSendQtyFake*1000*FRateValQty*1000)/(FRateOperQty*1000))/1000+0.4999999999);
    }
    private static List<HashMap> data() {
        List<HashMap> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            HashMap hashMap = new HashMap();
            list.add(hashMap);
            hashMap.put("col1","张三");
            hashMap.put("col2","张三");
            hashMap.put("col3","张三");
            hashMap.put("col4","张三");
            hashMap.put("col5","张三");
            hashMap.put("col6","张三");
            hashMap.put("col7","张三");
            hashMap.put("col8","张三");
            hashMap.put("col9","张三");
        }
        return list;
    }
    //获取资源文件
    public static String getResourceFilePath(String dir,String filename){
        Resource resource = new ClassPathResource(dir+"/"+filename);
        String path="";
        try {
            File file = resource.getFile();
            path=file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
public static void main(String[] args) throws IOException {
//    fillToTemplete();
}
}
