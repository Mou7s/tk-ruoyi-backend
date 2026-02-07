package com.ruoyi.tk_custom.utils;

import com.alibaba.fastjson2.JSONObject;
import com.github.houbb.sensitive.word.core.SensitiveWordHelper;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.tk_custom.domain.WxApprove;
import com.ruoyi.tk_custom.service.IWxApproveService;
import com.ruoyi.tk_custom.service.impl.WxApproveServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WXUtil {

    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static String accessToken = "_IGaQp19yNcHiVrNTWkpb-armRjngrpgTjD9XNXYrGduepxHbo9ufHATS9E_c9io7sLIR5eos4s3LeG-aMc2TlIC0K2tVjUM4IwFrXKGnARfCqeqSvqkcaDq1CZF79wyqefibRBDCN1Y8V4tYfFlVbDucVgUq2Dq-DYug3bg7WkLHqOKHkdDAMm_QjNGASiAQRw9SxE2fvngRepvrGRvVQ";
    private static String accessToken_TongXunLu = "t9fDrMFJLnUNUmyV05AUmnJ44imGJZZAyR2H9DDT-7eh_dirhEMBDFLOza3Y_vIGThav3nEHwGwoVl2kFxqQpmBRH9obdVem74BrWFVAw-LSUS1Tl1D7acT9sPgaA1knlBw3pi08lD0jWSnbEVBvyK9kAQynHy_YvLMKdrsoOlMiomJNiR18DAEd5cOcpPi7rlcvHVrkoPZ6S2IWNOJHrg";
    private static Date getTokenTime;
    private static Date getTokenTime_TongXunLu;
    private static String userid = "itGongChengShi-WenFengSheng";
    private static String corpid = "ww24102549409368d2";
    private static String corpsecret = "ccPmRhJ_lcDaeUNL9eYOEbCpn20wRYj-K9dDI3UY2QA";
    private static String corpsecret_TongXunLu = "VumpV7xxgq_t5O2TesIkggSOVb9zWyO6DODuKg1PoI0";

    private static String agentid = "1000025";

    public static String getToken() {
        if (getTokenTime != null && (new Date().getTime() - getTokenTime.getTime()) / 1000 < 7000) {
            System.out.println("老token");
            return accessToken;
        }
        String body = HttpRequest
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret)
                .body();
        HashMap hashMap = new Gson().fromJson(body, HashMap.class);
        accessToken = hashMap.get("access_token").toString();
        getTokenTime = new Date();
        System.out.println("重新获取token");
        System.out.println(accessToken);
        return accessToken;
    }

    public static String getToken_TongXunLu() {
        if (getTokenTime_TongXunLu != null && (new Date().getTime() - getTokenTime_TongXunLu.getTime()) / 1000 < 7000) {
            // System.out.println("老token");
            return accessToken_TongXunLu;
        }
        String body = HttpRequest.get(
                "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret_TongXunLu)
                .body();
        HashMap hashMap = new Gson().fromJson(body, HashMap.class);
        accessToken_TongXunLu = hashMap.get("access_token").toString();
        getTokenTime_TongXunLu = new Date();
        // System.out.println("重新获取token");
        return accessToken_TongXunLu;
    }   

    public static ArrayList<JsonObject> getUserObjectListFrom_TongXunLu(int times) {
        HttpRequest httpRequest = HttpRequest
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/list_id?access_token=" + getToken_TongXunLu())
                .acceptJson();
        HashMap<String, String> hp = new HashMap<>();
        if (times > 99)
            return null;
        times++;
        String body = httpRequest.send(new Gson().toJson(hp)).body();
        if (!"ok".equals(new Gson().fromJson(body, JsonObject.class).get("errmsg").getAsString())) {// 请求失败
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getUserObjectListFrom_TongXunLu(times);
        }
        JsonElement dept_user = new Gson().fromJson(body, JsonObject.class).get("dept_user");
        JsonArray asJsonArray = dept_user.getAsJsonArray();
        ArrayList<JsonObject> arr = new ArrayList<>();
        for (JsonElement obj : asJsonArray) {
            String userid = obj.getAsJsonObject().get("userid").getAsString();
            JsonObject jb = getUserbyId(userid);
            arr.add(jb);
        }
        return arr;
    }

    public static JsonArray getDept(int times) {
        HttpRequest httpRequest = HttpRequest
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=" + getToken()).acceptJson();
        HashMap<String, String> hp = new HashMap<>();
        if (times > 99)
            return null;
        times++;
        String body = httpRequest.send(new Gson().toJson(hp)).body();
        if (!"ok".equals(new Gson().fromJson(body, JsonObject.class).get("errmsg").getAsString())) {// 请求失败
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getDept(times);
        }
        JsonElement dept_user = new Gson().fromJson(body, JsonObject.class).get("department");
        JsonArray arr = dept_user.getAsJsonArray();
        return arr;
    }

    public static String createChatRoom() {
        HttpRequest httpRequest = HttpRequest
                .post("https://qyapi.weixin.qq.com/cgi-bin/appchat/create?access_token=" + getToken()).acceptJson();
        HashMap<String, String> hp = new HashMap<>();
        return httpRequest.send("{\n" +
                "    \"name\" : \"物品放行记录\",\n" +
                "    \"owner\" : \"itGongChengShi-WenFengSheng\",\n" +
                "    \"userlist\" : [\"itGongChengShi-WenFengSheng\", \"BingTangHuLu\"],\n" +
                "    \"chatid\" : \"wupingfangxingtiao\"\n" +
                "}").body();
    }

    public static String getUserNamebyId(String id) {
        String body = HttpRequest
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=" + getToken() + "&userid=" + id)
                .body();
        String name = new Gson().fromJson(body, JsonObject.class).get("name").getAsString();
        return name;
    }

    public static JsonObject getUserInfoById(String id) {
        String body = HttpRequest
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=" + getToken() + "&userid=" + id)
                .body();
        JsonObject jb = new Gson().fromJson(body, JsonObject.class);
        return jb;
    }

    public static JsonObject getUserbyId(String id) {
        String body = HttpRequest
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=" + getToken() + "&userid=" + id)
                .body();
        JsonObject jb = new Gson().fromJson(body, JsonObject.class);
        return jb;
    }

    public static void uploadFileToWechat(String path, String roomid) throws FileNotFoundException {
        if ("".equals(path))
            return;
        File file = new File(path);
        if (!file.exists()) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    sendMsg("itGongChengShi-WenFengSheng", "结案文件生成失败！，请重新手动执行", 0);
                }
            });
            return;
        }
        // .url("https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token="+getToken()+"&type=file")
        HttpPost httpPost = new HttpPost(
                "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=" + getToken() + "&type=file&debug=1");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                + "AppleWebKit/537.36 (KHTML, like Gecko) "
                + "Chrome/62.0.3202.89 Safari/537.36");
        httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
        httpPost.setHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.7");
        httpPost.setHeader("Connection", "keep-alive");
        MultipartEntityBuilder mutiEntity = MultipartEntityBuilder.create();
        FileBody fileBody = new FileBody(file);
        mutiEntity.addPart("media", fileBody);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        httpPost.setEntity(mutiEntity.build());
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        org.apache.http.HttpEntity entity = httpResponse.getEntity();
        String content = null;
        try {
            content = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject respObj = new Gson().fromJson(content, JsonObject.class);
        if ("ok".equals(respObj.get("errmsg").getAsString())) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    sendFileMsg(respObj.get("media_id").getAsString(), roomid, 0);
                }
            });
        } else {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    sendMsg("itGongChengShi-WenFengSheng", "结案文件生成失败！，请重新手动执行", 0);
                }
            });
        }
    }

    public static void sendMsg(String userid, String content, int times) {
        if (times > 10) {
            System.out.println("发送消息到企业微信失败，请检查！！");
            return;
        }
        String requestResult = sendMsg(userid, content);
        times++;
        if (!"ok".equals(new Gson().fromJson(requestResult, JsonObject.class).get("errmsg").getAsString())) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(requestResult);
            sendMsg(userid, content, times);
        }
    }

    ;

    public static void sendFileMsg(String media_id, String roomId, int times) {
        if (times > 10) {
            System.out.println("发送消息到企业微信失败，请检查！！");
            return;
        }
        HashMap<String, Object> hp = new HashMap<>();
        hp.put("chatid", "shengchandingdanjiean");
        hp.put("msgtype", "file");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("media_id", media_id);
        hp.put("file", hashMap);
        String requestResult = WXUtil.sendMsgToChatRoom(hp);
        times++;
        if (!"ok".equals(new Gson().fromJson(requestResult, JsonObject.class).get("errmsg").getAsString())) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendFileMsg(media_id, roomId, times);
        }
    }

    public static String sendMsg(String id, String content) {
        HttpRequest httpRequest = HttpRequest
                .post("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + getToken()).acceptJson();
        return httpRequest.send("{\n" +
                "   \"touser\" : \"" + id + "\",\n" +
                "   \"toparty\" : \"\",\n" +
                "   \"totag\" : \"\",\n" +
                "   \"msgtype\" : \"text\",\n" +
                "   \"agentid\" : " + agentid + ",\n" +
                "   \"text\" : {\n" +
                "       \"content\" : \"" + content + "\"\n" +
                "   },\n" +
                "   \"safe\":0,\n" +
                "   \"enable_id_trans\": 0,\n" +
                "   \"enable_duplicate_check\": 0,\n" +
                "   \"duplicate_check_interval\": 1800\n" +
                "}").body();
    }

    public static String sendMsgToChatRoom(HashMap<String, Object> hp) {// 88888888
        HttpRequest httpRequest = HttpRequest
                .post("https://qyapi.weixin.qq.com/cgi-bin/appchat/send?access_token=" + getToken()).acceptJson();
        return httpRequest.send(new Gson().toJson(hp)).body();
    }

    public static boolean veryWords(String str) {
        // 定义一个正则表达式，匹配所有非字母数字字符
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\u4e00-\\u9fa5]*");
        String words = "";
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            words += matcher.group(0);
        }
        return SensitiveWordHelper.contains(words);
    }

    public static ArrayList<WxApprove> getAllApproveData(long starttime, long endtime, ArrayList arrs,
            String new_cursor, String tempId) {
        HttpRequest httpRequest = HttpRequest
                .post("https://qyapi.weixin.qq.com/cgi-bin/oa/getapprovalinfo?access_token=" + getToken()).acceptJson();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String body = httpRequest.send("{\n" +
                "    \"starttime\" : \"" + starttime + "\",\n" +
                "    \"endtime\" : \"" + endtime + "\",\n" +
                "    \"new_cursor\" : \"" + new_cursor + "\" ,\n" +
                "    \"size\" : 100 ,\n" +
                "    \"filters\" : [\n" +
                "        {\n" +
                "            \"key\": \"template_id\",\n" +
                "            \"value\": \"" + tempId + "\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"key\" : \"sp_status\",\n" +
                "            \"value\" : \"2\"\n" +
                "        } " +
                "    ]\n" +
                "}").body();
        JsonObject jsonObject = new Gson().fromJson(body, JsonObject.class);
        if (!"ok".equals(jsonObject.get("errmsg").getAsString())) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getAllApproveData(starttime, endtime, arrs, new_cursor, tempId);
        }
        if (jsonObject.has("new_next_cursor")) {
            getAllApproveData(starttime, endtime, arrs, jsonObject.get("new_next_cursor").getAsString(), tempId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JsonArray checkindata = jsonObject.getAsJsonArray("sp_no_list");
        for (JsonElement spNo : checkindata) {
            // todo
            String spnoStr = spNo.getAsString();
            IWxApproveService iWxApproveService = SpringContextUtils.getBean(IWxApproveService.class);
            WxApprove wxApproveDB = iWxApproveService.selectWxApproveBySpNo(spnoStr);
            if (wxApproveDB == null) {
                WxApprove wxApprove = getAndSaveApproveDetail(spnoStr);
                arrs.add(wxApprove);
            }
        }
        return arrs;
    }

    public static WxApprove getAndSaveApproveDetail(String detailID) {
        HttpRequest httpRequest = HttpRequest
                .post("https://qyapi.weixin.qq.com/cgi-bin/oa/getapprovaldetail?access_token=" + getToken())
                .acceptJson();
        String body = httpRequest.send("{\n" +
                "   \"sp_no\" : \"" + detailID + "\"\n" +
                "}").body();
        JsonObject jsonObject = new Gson().fromJson(body, JsonObject.class);
        if (!"ok".equals(jsonObject.get("errmsg").getAsString())) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getAndSaveApproveDetail(detailID);
        }
        JsonObject info = jsonObject.getAsJsonObject("info");
        WxApprove wxApprove = new WxApprove();
        wxApprove.setSpNo(info.get("sp_no").getAsString());
        wxApprove.setSpName(info.get("sp_name").getAsString());
        wxApprove.setSpStatus(info.get("sp_status").getAsString());
        wxApprove.setTemplateId(info.get("template_id").getAsString());
        wxApprove.setApplyTime(new Date(info.get("apply_time").getAsLong() * 1000));
        wxApprove.setApplyerUserid(info.getAsJsonObject("applyer").get("userid").getAsString());
        wxApprove.setApplyerPartyid(info.getAsJsonObject("applyer").get("partyid").getAsString());
        wxApprove.setApplyDataContents(
                getApplyDataItemValue(info.getAsJsonObject("apply_data").get("contents").getAsJsonArray()));
        JsonArray records = info.get("sp_record").getAsJsonArray();
        StringBuilder sb = new StringBuilder();
        for (JsonElement record : records) {
            JsonObject asJsonObject = record.getAsJsonObject();
            JsonArray details = asJsonObject.get("details").getAsJsonArray();
            for (JsonElement detail : details) {
                JsonObject jxb = detail.getAsJsonObject();
                String str = jxb.get("approver").getAsJsonObject().get("userid").getAsString();
                if (!"".equals(sb.toString()))
                    sb.append(",");
                sb.append(str);
            }
        }
        wxApprove.setAuditerid(sb.toString());
        return wxApprove;
    }

    public static String getApplyDataItemValue(JsonArray arr) {
        JSONObject returnJb = new JSONObject();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (JsonElement je : arr) {
            JsonObject jb = je.getAsJsonObject();
            String control = jb.get("control").getAsString();
            if ("Date".equals(control)) {
                long asLong = jb.get("value").getAsJsonObject().get("date").getAsJsonObject().get("s_timestamp")
                        .getAsLong();
                returnJb.put(jb.get("title").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString(),
                        simpleDateFormat.format(new Date(asLong * 1000)));
            }
            if ("Selector".equals(control)) {
                returnJb.put("needBack",
                        jb.get("value").getAsJsonObject().get("selector").getAsJsonObject().get("options")
                                .getAsJsonArray().get(0).getAsJsonObject().get("value").getAsJsonArray().get(0)
                                .getAsJsonObject().get("text").getAsString());
            }
            if ("Contact".equals(control)) {
                JsonArray arr1 = jb.get("value").getAsJsonObject().get("members").getAsJsonArray();
                if (arr1.size() == 0)
                    arr1 = jb.get("value").getAsJsonObject().get("departments").getAsJsonArray();
                StringBuilder sb = new StringBuilder();
                for (JsonElement jsonElement : arr1) {
                    JsonObject jb1 = jsonElement.getAsJsonObject();
                    String name = jb1.get("name").getAsString();
                    if (!"".equals(sb.toString()))
                        sb.append(",");
                    sb.append(name);
                }
                returnJb.put(jb.get("title").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString(),
                        sb.toString());
            }
            if ("Text".equals(control)) {
                returnJb.put(jb.get("title").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString(),
                        jb.get("value").getAsJsonObject().get("text").getAsString());
            }
            if ("Textarea".equals(control) || "Text".equals(control)) {
                returnJb.put(jb.get("title").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString(),
                        jb.get("value").getAsJsonObject().get("text").getAsString());
            }
            if ("Attendance".equals(control)) {
                JsonObject asJsonObject = jb.get("value").getAsJsonObject().get("attendance").getAsJsonObject()
                        .get("date_range").getAsJsonObject();
                JsonObject slice_info = jb.get("value").getAsJsonObject().get("attendance").getAsJsonObject()
                        .get("slice_info").getAsJsonObject();

                String starttime = simpleDateFormat.format(new Date(asJsonObject.get("new_begin").getAsLong() * 1000));
                String endtime = simpleDateFormat.format(new Date(asJsonObject.get("new_end").getAsLong() * 1000));
                String time = "" + slice_info.get("duration").getAsLong() / 60 / 60;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("starttime", starttime);
                jsonObject.put("endtime", endtime);
                jsonObject.put("time", time);
                returnJb.put(jb.get("title").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString(),
                        jsonObject.toString());
            }
            if ("File".equals(control)) {
                JsonArray asJsonArray = jb.get("value").getAsJsonObject().get("files").getAsJsonArray();
                StringBuilder sb = new StringBuilder();
                StringBuilder sb1 = new StringBuilder();
                for (JsonElement jsonElement : asJsonArray) {
                    JsonObject jb2 = jsonElement.getAsJsonObject();
                    String file_id = jb2.get("file_id").getAsString();
                    try {
                        String filename = downloadNet(file_id);
                        if ("".equals(filename) || filename == null)
                            continue;
                        if (!"".equals(sb.toString())) {
                            sb.append(",");
                            sb1.append(",");
                        }
                        sb.append(filename);
                        sb1.append(file_id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                returnJb.put("filepaths", sb.toString());
                returnJb.put("media_ids", sb1.toString());
            }
        }
        return returnJb.toString();
    }

    /**
     * 下载网络文件
     *
     * @throws IOException
     */
    public static String downloadNet(String media_id) throws IOException {
        int bytesum = 0;
        int byteread;
        URL url = new URL("https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=" + getToken() + "&media_id="
                + media_id + "");
        // String path = RuoYiConfig.getProfile() + "//wx_pic";
        String path = RuoYiConfig.getProfile() + "\\wx_pic";
        File file = new File(path);
        boolean canSave = true;
        if (!file.exists()) {
            boolean mkdir = file.mkdirs();
            canSave = mkdir;
        }
        if (!canSave)
            return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = format.format(new Date());
        URLConnection conn = url.openConnection();
        try (
                InputStream inStream = conn.getInputStream();
                FileOutputStream fs = new FileOutputStream(path + "//" + fileName + ".png")) {
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }

        } catch (Exception e) {
            return null;
        }
        zipFile(path, fileName);
        return fileName;
    }

    public static void main(String[] args) {
        System.out.println(new Date().getTime());
    }

    public static void zipFile(String path, String fileName) {
        File file = new File(path, fileName + ".png");
        long size = file.length() / 1024;
        float scale = 0.3f;
        float quality = 1f;
        String zipDir = path;
        if (!path.contains("pic_zip"))
            ;
        zipDir = path.replace("wx_pic", "pic_zip");
        File file1 = new File(zipDir);
        if (!file1.exists())
            file1.mkdirs();
        String srcPath = path + "\\" + fileName + ".png";
        String zipPath = zipDir + "\\" + fileName + ".png";
        try {
            Thumbnails.of(srcPath)
                    .scale(scale)
                    .outputQuality(quality)
                    .toFile(zipPath);
        } catch (IOException e) {
            return;
        }
        System.out.println("压缩1次");
        File zipFile = new File(zipPath);
        if ((zipFile.length() / 1024) > 1000)
            zipFile(zipDir, fileName);
    }

    public static void tarFile(String srcPath, String destPath) {
        float scale = 1f;
        float quality = 1f;
        File file = new File(destPath);
        if (file.length() / 1024 > 100)
            scale = 0.3f;
        if (!file.exists()) {
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Thumbnails.of(srcPath)
                    .scale(scale)
                    .outputQuality(quality)
                    .toFile(destPath);
        } catch (IOException e) {
            return;
        }
        File destFile = new File(destPath);
        if ((destFile.length() / 1024) > 100)
            tarFile(destPath, destPath);
    }
}
