package com.ruoyi.tk_custom.utils;

import com.ruoyi.common.config.RuoYiConfig;

import java.io.File;

public class test {
    public static void main(String[] args) {
String str="";
    }
//获取指定文件的可执行文件 todo 设置端口加密规则使用
    public static void getFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if(file1.isDirectory())
            getFiles(file1.getAbsolutePath());
            else {
                String absolutePath = file1.getName();
                if (absolutePath.endsWith(".exe")){
                    System.out.println(absolutePath+",允许,Tcp,全天候,1,65535,0.0.0.0,255.255.255.255,不生效");
                    System.out.println(absolutePath+",允许,Udp,全天候,1,65535,0.0.0.0,255.255.255.255,不生效");
                }
            }
        }



    }
}
