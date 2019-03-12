package com.haier.rrswl.itms.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 微信认证校验工具类
 */
public class WxUtil {

    public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
        // 1、排序
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        // 2、生成新的字符串
        StringBuilder content = new StringBuilder();
        for (String anArr : arr) {
            content.append(anArr);
        }
        // 3、shal加密
        String temp = getSha1(content.toString());
        return temp.equals(signature);
    }

    public static String getSha1(String str) {
        if (null == str || 0 == str.length())
            return null;
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
