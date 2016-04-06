package com.brilliantreform.sc.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Lm on 2015/12/21.
 */
public class CheckUtil {
    private static final String token = "lmweixin";
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        //����
        Arrays.sort(arr);

        //����ַ�
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            buffer.append(arr[i]);
        }
        //sha1����
        String temp = sha1(buffer.toString());
        return temp.equals(signature);
    }

    public static String sha1(String text) {
        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(text.getBytes());
            outStr = byteToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return outStr;
    }

    private static String byteToString(byte[] digest) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            String tempStr = Integer.toHexString(digest[i] & 0xff);
            if (tempStr.length() == 1) {
                buf.append("0").append(tempStr);
            } else {
                buf.append(tempStr);
            }
        }
        return buf.toString().toLowerCase();
    }

}
