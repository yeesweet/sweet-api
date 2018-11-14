package com.sweet.api.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhang.hp
 * @date 2018/10/17.
 */
public class Md5Encrypt {
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public Md5Encrypt() {
    }

    public static String md5(String text) {
        MessageDigest msgDigest = null;

        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var5) {
            throw new IllegalStateException("System doesn\'t support MD5 algorithm.");
        }

        try {
            msgDigest.update(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var4) {
            throw new IllegalStateException("System doesn\'t support your  EncodingException.");
        }

        byte[] bytes = msgDigest.digest();
        String md5Str = new String(encodeHex(bytes));
        return md5Str;
    }

    public static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for(int j = 0; i < l; ++i) {
            out[j++] = DIGITS[(240 & data[i]) >>> 4];
            out[j++] = DIGITS[15 & data[i]];
        }

        return out;
    }
}
