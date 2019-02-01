package com.eebbk.behavior.demo.utils;

/**
 * @author hesn
 * @function
 * @date 17-8-9
 * @company 步步高教育电子有限公司
 */

public class JNIUtils {

    static {
        System.loadLibrary("crashtest");
    }

    //java调C中的方法都需要用native声明且方法名必须和c的方法名一样
    public native String getString();
}
