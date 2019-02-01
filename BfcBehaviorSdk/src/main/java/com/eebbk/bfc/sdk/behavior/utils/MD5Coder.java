package com.eebbk.bfc.sdk.behavior.utils;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author hesn
 * @function
 * @date 16-11-7
 * @company 步步高教育电子有限公司
 */

public class MD5Coder {

    private static final String TAG = "MD5Coder";
    private static final String KEY_MD5 = "MD5";
    private int RADIX = 36;

    public MD5Coder(int radix) {
        this.RADIX = radix;
    }

    public MD5Coder() {
    }

    public String encode(byte[] data) {
        byte[] md5 = this.getMD5(data);
        BigInteger bi = (new BigInteger(md5)).abs();
        return bi.toString(this.RADIX);
    }

    private byte[] getMD5(byte[] data) {
        byte[] hash = null;

        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(data);
            hash = e.digest();
        } catch (NoSuchAlgorithmException var4) {
            Log.e("MD5Coder", var4 + "");
        }

        return hash;
    }

}
