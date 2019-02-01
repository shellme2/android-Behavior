package com.eebbk.bfc.sdk.behavior.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;

public class DesUtils {
    private static final String strDefaultKey = "123456";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    public DesUtils() {
        this(strDefaultKey);
    }

    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes("UTF-8")));
    }

    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)), "UTF-8");
    }

    private static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (byte anArrB : arrB) {
            int intTmp = anArrB;
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    private static byte[] hexStr2ByteArr(String strIn) {
        if(TextUtils.isEmpty(strIn)){
            return new byte[0];
        }
        byte[] arrB = null;
        try {
            arrB = strIn.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(arrB == null){
            return new byte[0];
        }

        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            try {
                String strTmp = new String(arrB, i, 2, "UTF-8");
                arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return arrOut;
    }

    private DesUtils(String strKey) {
        Key key;
        try {
            key = getKey(strKey.getBytes("UTF-8"));
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    private byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    private Key getKey(byte[] arrBTmp) {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥
        return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }

}
