package com.eebbk.bfc.sdk.behavior.utils;


import org.apache.commons.codec.binary.Base64;

/**
 * @author hesn
 * @function
 * @date 16-11-7
 * @company 步步高教育电子有限公司
 */

public class BASE64Coder {

    private BASE64Coder() {
    }

    public static String encode(byte[] data) throws Exception {
        return new String(Base64.encodeBase64(data), "utf-8");
    }

    public static String decode(byte[] data) throws Exception {
        return new String(Base64.decodeBase64(data), "utf-8");
    }
}
