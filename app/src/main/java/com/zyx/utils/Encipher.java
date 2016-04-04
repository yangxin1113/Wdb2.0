package com.zyx.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by zyx on 2016/2/11.
 */
public class Encipher {

    private static final String key = "hhjd1357";
    // 密钥
    private static final byte[] KeyValue = "kedllked".getBytes();

    static AlgorithmParameterSpec iv = null;// 加密算法的参数接口，IvParameterSpec是它的一个实现
    /**
     * 解密数据
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    private static String decrypt(String message, String key) throws Exception {

        byte[] bytesrc = convertHexString(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    /**
     * 加密数据
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    private static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    /**
     * 加密
     *
     * @param args
     * @return
     * @throws Exception
     */
    public static String jiaMi(String args) throws Exception {
        // jiami = java.net.URLEncoder.encode(args, "utf-8").toLowerCase();
        String jiami = URLEncoder.encode(args, "utf-8");
        String a = toHexString(encrypt(jiami, key)).toUpperCase();
        return a;
    }

    /**
     * 解密
     *
     * @param args
     * @return
     * @throws Exception
     * @throws UnsupportedEncodingException
     */
    public static String jieMi(String args)
            throws UnsupportedEncodingException, Exception {
        String b = java.net.URLDecoder.decode(decrypt(args, key), "utf-8");
        return b;

    }

    private static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
        return hexString.toString();
    }
}
