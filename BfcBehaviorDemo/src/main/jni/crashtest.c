//
// Created by hesn on 17-8-9.
//
#include "com_eebbk_behavior_demo_utils_JNIUtils.h"
/**
 * 上边的引用标签一定是.h的文件名家后缀，方法名一定要和.h文件中的方法名称一样
 */

JNIEXPORT jstring JNICALL Java_com_eebbk_behavior_demo_utils_JNIUtils_getString(JNIEnv *env, jobject obj) {
    int i = 10;
    int y = i / 5;
    int* p = 0; //空指针
    *p = 1; //写空指针指向的内存，产生SIGSEGV信号，造成Crash
    return (*env)->NewStringUTF(env, "这是我测试的jni");
}
