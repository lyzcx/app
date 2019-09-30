package com.yf.douyintool;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.ss.android.common.applog.UserInfo;
import com.ss.sys.ces.a;
import okhttp3.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.yf.douyintool.DouyinHttpUtil.getMas;

/**
 * @Author: zhaoyoucheng
 * @Date: 2019/9/26 19:48
 * @Description:
 */
public class UserCrawler {


    public static String getUserInfoUrl(String userId, String iid) {
        int ts = (int) (System.currentTimeMillis() / 1000);
        String _ricket = System.currentTimeMillis() + "";
        String baseUrl = "https://aweme-eagle.snssdk.com/aweme/v1/user/?";
        String params = "user_id=" + userId + "&ts=" + ts + "&js_sdk_version=1.13.5&app_type=minote&os_api=22&device_type=SM-G925F&device_platform=android&ssmix=a&iid=67235425049&manifest_version_code=551&dpi=192&uuid=865166020440635&version_code=551&app_name=aweme&version_name=5.5.1&openudid=983cfdf7995b84ab&device_id=69568328186&resolution=720*1280&os_version=5.1.1&language=zh&device_brand=xiaomi&ac=wifi&update_version_code=5512&aid=1128&channel=update&_rticket=" + _ricket + "&mcc_mnc=46000";
        String url = baseUrl + params;
        String[] km = params.split("&");
        String ascp = UserInfo.getUserInfo(ts, url, km, iid);
        System.out.println("ascp: " + ascp);
        String as = ascp.substring(0, 22);
        String cp = ascp.substring(22);
        String mas = getMas(as);
        url = url + "&as=" + as + "&cp=" + cp + "&mas=" + mas;
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
        String res = null;
        Long now = System.currentTimeMillis();
        String baseUrl = "https://aweme.snssdk.com/aweme/v1/aweme/post/?";
        String params = "max_cursor=0&user_id=" + userId + "&count=20&retry_type=no_retry&mcc_mnc=46000&iid=" + iid + "&device_id=56647090334&ac=wifi&channel=wandoujia_aweme1&aid=1128&app_name=aweme&version_code=551&version_name=5.5.1&device_platform=android&ssmix=a&device_type=mi+5&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&uuid=865166026615537&openudid=ce712314b4a5205e&manifest_version_code=551&resolution=900*1600&dpi=240&update_version_code=5512&_rticket=" + now + "&ts=" + now / 1000 + "&js_sdk_version=1.13.10";
        //params = "max_cursor=0&user_id=66598046050&count=20&retry_type=no_retry&mcc_mnc=46000&iid=87369521573&device_id=56647090334&ac=wifi&channel=wandoujia_aweme1&aid=1128&app_name=aweme&version_code=551&version_name=5.5.1&device_platform=android&ssmix=a&device_type=mi+5&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&uuid=865166026615537&openudid=ce712314b4a5205e&manifest_version_code=551&resolution=1600*900&dpi=240&update_version_code=5512&_rticket=1569739033836&ts=1569739032&js_sdk_version=1.13.10";
        String[] km = params.split("&");
        String url = baseUrl + params;
        String ascp = UserInfo.getUserInfo(now.intValue(), url, km, iid);
        if (ascp.length() > 22) {
            String as = ascp.substring(0, 22);
            String cp = ascp.substring(22);
            String mas = getMas(as);
            res = url + "&as=" + as + "&cp=" + cp + "&mas=" + mas;
        }

        /*String[] arr = new String[] {"os_api","22","device_type","mi 5","device_platform","android","ssmix","a","iid","87369521573","manifest_version_code","551","dpi","240","uuid","865166026615537","version_code","551","app_name","aweme","version_name","5.5.1","openudid","ce712314b4a5205e","device_id","56647090334","resolution","900*1600","os_version","5.1.1","language","zh","device_brand","xiaomi","ac","wifi","update_version_code","5512","aid","1128","channel","wandoujia_aweme1","mcc_mnc","46000"};
        url = "https://aweme-eagle.snssdk.com/aweme/v1/user/?user_id=109780803277&retry_type=no_retry&mcc_mnc=46000&iid=87369521573&device_id=56647090334&ac=wifi&channel=wandoujia_aweme1&aid=1128&app_name=aweme&version_code=551&version_name=5.5.1&device_platform=android&ssmix=a&device_type=mi 5&device_brand=xiaomi&language=zh&os_api=22&os_version=5.1.1&uuid=865166026615537&openudid=ce712314b4a5205e&manifest_version_code=551&resolution=900*1600&dpi=240&update_version_code=5512&_rticket=1569753265461&ts=1569753265&js_sdk_version=1.13.10";
        ascp = UserInfo.getUserInfo(1569753265, url, arr, "56647090334");
        System.out.println("ascp ------> " + ascp);*/
        return res;
    }

}
