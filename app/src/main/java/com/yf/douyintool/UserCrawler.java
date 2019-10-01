package com.yf.douyintool;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.ss.android.common.applog.UserInfo;
import com.ss.sys.ces.a;
import okhttp3.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.yf.douyintool.DouyinHttpUtil.getMas;

/**
 * @Author: zhaoyoucheng
 * @Date: 2019/9/26 19:48
 * @Description:
 */
public class UserCrawler {

    private static final String USER_INFO_BASE_URL = "https://aweme-eagle.snssdk.com/aweme/v1/user/?";
    private static final String USER_PUBLISH_BASE_URL = "https://api.amemv.com/aweme/v1/aweme/post/?";


    public static String getUserInfoUrl(String userId) {
        int ts = (int) (System.currentTimeMillis() / 1000);
        String _ricket = System.currentTimeMillis() + "";
        String params = "user_id=" + userId + "&ts=" + ts + "&js_sdk_version=1.13.5&app_type=minote&os_api=22&device_type=SM-G925F&device_platform=android&ssmix=a&iid=67235425049&manifest_version_code=551&dpi=192&uuid=865166020440635&version_code=551&app_name=aweme&version_name=5.5.1&openudid=983cfdf7995b84ab&device_id=69568328186&resolution=720*1280&os_version=5.1.1&language=zh&device_brand=xiaomi&ac=wifi&update_version_code=5512&aid=1128&channel=update&_rticket=" + _ricket + "&mcc_mnc=46000";
        String url = USER_INFO_BASE_URL + params;
        /*String[] km = params.split("&");
        String ascp = UserInfo.getUserInfo(ts, url, km, "69568328186");
        String as = ascp.substring(0, 22);
        String cp = ascp.substring(22);
        String mas = getMas(as);
        url = url + "&as=" + as + "&cp=" + cp + "&mas=" + mas;*/
        return url;
    }

    public static void getUserInfo(String url) {
        DouyinHttpUtil.getUserInfo(url, jsonStr -> {
            JSONObject userInfo = JSONObject.parseObject(jsonStr).getJSONObject("user");
            String username = userInfo.getString("nickname");
            String avatar = userInfo.getJSONObject("avatar_medium").getJSONArray("url_list").getString(0);
            String uid = userInfo.getString("uid");
            System.out.println(username);
            System.out.println(avatar);
            System.out.println(uid);
        });
    }

    public static String getUserPublishUrl(String userId, String iid) {
        long now = System.currentTimeMillis();
        int ts = (int) (now / 1000);
        String res = null;
        /*String params = "max_cursor=0&user_id=" + userId + "&count=20&retry_type=no_retry&mcc_mnc=46000&iid=" + iid + "&device_id=56647090334&ac=wifi&channel=wandoujia_aweme1&aid=1128&app_name=aweme&version_code=551&version_name=5.5.1&device_platform=android&ssmix=a&device_type=mi+5&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&uuid=865166026615537&openudid=ce712314b4a5205e&manifest_version_code=551&resolution=900*1600&dpi=240&update_version_code=5512&_rticket=" + now + "&ts=" + ts + "&js_sdk_version=1.13.10";
        for (String p : params.split("&")) {
            String[] split = p.split("=", 2);
            if (PARAM_MAP.containsKey(split[0])) {
                PARAM_MAP.put(split[0], split[1]);
            }
        }
        List<String> list = new LinkedList<>();
        for (Map.Entry<String, String> entry : PARAM_MAP.entrySet()) {
            list.add(entry.getKey());
            list.add(entry.getValue());
        }
        String[] paramArr = list.toArray(new String[0]);
        String url = USER_PUBLISH_BASE_URL + params;
        String[] arr = new String[] {"os_api","22","device_type","mi 4lte","device_platform","android","ssmix","a","iid","87942222336","manifest_version_code","551","dpi","240","uuid","865166025288963","version_code","551","app_name","aweme","version_name","5.5.1","openudid","c8b07f2b89f01ec3","device_id","50367159526","resolution","900*1600","os_version","5.1.1","language","zh","device_brand","xiaomi","ac","wifi","update_version_code","5512","aid","1128","channel","wandoujia_aweme1","mcc_mnc","46000"};
        String ascp = UserInfo.getUserInfo(ts, url, paramArr, PARAM_MAP.get("device_id"));
        if (ascp.length() > 22) {
            String as = ascp.substring(0, 22);
            String cp = ascp.substring(22);
            String mas = getMas(as);
            res = url + "&as=" + as + "&cp=" + cp + "&mas=" + mas;
        }*/

        String[] arr = new String[] {"os_api","22","device_type","mi 4lte","device_platform","android","ssmix","a","iid","87942222336","manifest_version_code","551","dpi","240","uuid","865166025288963","version_code","551","app_name","aweme","version_name","5.5.1","openudid","c8b07f2b89f01ec3","device_id","50367159526","resolution","900*1600","os_version","5.1.1","language","zh","device_brand","xiaomi","ac","wifi","update_version_code","5512","aid","1128","channel","wandoujia_aweme1","mcc_mnc","46000"};
        String url = "https://aweme.snssdk.com/aweme/v1/aweme/post/?max_cursor=0&user_id=102217477530&count=20&retry_type=no_retry&mcc_mnc=46000&iid=87942222336&device_id=50367159526&ac=wifi&channel=wandoujia_aweme1&aid=1128&app_name=aweme&version_code=551&version_name=5.5.1&device_platform=android&ssmix=a&device_type=mi 4lte&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&uuid=865166025288963&openudid=c8b07f2b89f01ec3&manifest_version_code=551&resolution=900*1600&dpi=240&update_version_code=5512&_rticket=" + now + "&ts=" + now/1000;
        String ascp = UserInfo.getUserInfo(1569848130, url, arr, "50367159526");
        System.out.println("ascp ------> " + ascp);
        return res;
    }

    public static void getUserPublishInfo(String url) {
        DouyinHttpUtil.getUserInfo(url, jsonStr -> {
            System.out.println(jsonStr);
        });
    }


    private static Map<String, String> PARAM_MAP = new LinkedHashMap<String, String>() {{
        put("os_api", "");
        put("device_type", "");
        put("device_platform", "");
        put("ssmix", "");
        put("iid", "");
        put("manifest_version_code", "");
        put("dpi", "");
        put("uuid", "");
        put("version_code", "");
        put("app_name", "");
        put("version_name", "");
        put("openudid", "");
        put("device_id", "");
        put("resolution", "");
        put("os_version", "");
        put("language", "");
        put("device_brand", "");
        put("ac", "");
        put("update_version_code", "");
        put("aid", "");
        put("channel", "");
        put("mcc_mnc", "");
    }};

}
