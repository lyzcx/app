package com.yf.douyintool;

import android.text.TextUtils;
import android.util.Log;
import com.ss.sys.ces.a;
import okhttp3.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: zhaoyoucheng
 * @Date: 2019/9/27 16:57
 * @Description:
 */
public class DouyinHttpUtil {

    private static String xtttoken = "004cd1df7bd4fddd6674a80e6da09c27304b0a39de5343f08f1adb0f196433c3cc48286ee0aa915761b71e0201000f8ccf37";
    private static String ck = "install_id=67982304031; ttreq=1$9cf124c9495dc5a1da9e9a611eee577738dc8fc1; odin_tt=9ac4660ee23266248ee5edb5afe9a293118bbaa210b20316f36845e27e6bd44517b8a69241c8a4e09d91f53b0642fe61e70cf88eadfa6d9d59bc401369d06c40; qh[360]=1";
    private static final String NULL_MD5_STRING = "00000000000000000000000000000000";

    public static void getUserInfo(String url, final ResponseHandler<String> handler) {
        Request request = new Request.Builder()
                .url(url).get()
                .addHeader("X-SS-REQ-TICKET",System.currentTimeMillis()+"")
                .addHeader("X-Khronos", String.valueOf(System.currentTimeMillis()/1000))
                .addHeader("X-Gorgon", getXGon(url))
                .addHeader("sdk-version","1")
                .addHeader("Cookie", ck)
                .addHeader("X-Pods","")
                .addHeader("Connection","Keep-Alive")
                .addHeader("User-Agent","okhttp/3.10.0.1")
                .addHeader("x-tt-token", xtttoken)
                .addHeader("Host","aweme.snssdk.com")
                .build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("http request error: ", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.handle(response.body().string());
            }
        });

    }

    public static String getMas(String as) {
        return ByteToStr(a.e(as.getBytes()));
    }


    public static String getXGon(String url) {
        long time = System.currentTimeMillis() / 1000;
        String p = url.substring(url.indexOf("?") + 1);
        String s = getXGon(p, "", ck, "");
        String xGon = ByteToStr(a.leviathan((int) time, StrToByte(s)));
        return xGon;
    }

    public static String getXGon(String url, String stub, String ck, String sessionid) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(url)) {
            sb.append(NULL_MD5_STRING);
        } else {
            sb.append(encryption(url).toLowerCase());
        }

        if (TextUtils.isEmpty(stub)) {
            sb.append(NULL_MD5_STRING);
        } else {
            sb.append(stub);
        }

        if (TextUtils.isEmpty(ck)) {
            sb.append(NULL_MD5_STRING);
        } else {
            sb.append(encryption(ck).toLowerCase());
        }

        if (TextUtils.isEmpty(sessionid)) {
            sb.append(NULL_MD5_STRING);
        } else {
            sb.append(encryption(sessionid).toLowerCase());
        }
        return sb.toString();
    }

    public static String ByteToStr(byte[] bArr) {

        int i = 0;

        char[] toCharArray = "0123456789abcdef".toCharArray();
        char[] cArr = new char[(bArr.length * 2)];
        while (i < bArr.length) {
            int i2 = bArr[i] & 255;
            int i3 = i * 2;
            cArr[i3] = toCharArray[i2 >>> 4];
            cArr[i3 + 1] = toCharArray[i2 & 15];
            i++;
        }
        return new String(cArr);
    }

    public static byte[] StrToByte(String str) {
        String str2 = str;
        Object[] objArr = new Object[1];
        int i = 0;
        objArr[0] = str2;

        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        while (i < length) {
            bArr[i / 2] = (byte) ((Character.digit(str2.charAt(i), 16) << 4) + Character.digit(str2.charAt(i + 1), 16));
            i += 2;
        }
        return bArr;
    }

    public static String encryption(String str) {
        String re_md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5.toUpperCase();
    }

    interface ResponseHandler<T> {
        void handle(T response);
    }

}
