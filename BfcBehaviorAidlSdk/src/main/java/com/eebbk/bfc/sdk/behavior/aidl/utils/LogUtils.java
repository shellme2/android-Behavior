package com.eebbk.bfc.sdk.behavior.aidl.utils;

import android.text.TextUtils;
import android.util.Log;

import com.eebbk.bfc.sdk.behavior.aidl.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.aidl.version.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function
 * @date
 * @company 步步高教育电子有限公司
 */
public class LogUtils {

    private static final String TAG = "BfcBehaviorAidl";
    private static boolean isLog = true;
    private static final String LINE_SEPARATOR = "\n";

    public static boolean isLog() {
        return isLog;
    }

    public static void setIsLog(boolean isLog) {
        LogUtils.isLog = isLog;
    }

    public static void logStackTrace() {
        logChunkInSize(Log.DEBUG, TAG, "");
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(Exception msg) {
        e(TAG, msg.toString());
    }

    public static void v(String tag, String msg) {
        logChunkInSize(Log.VERBOSE, tag, msg);
    }

    public static void d(String tag, String msg) {
        logChunkInSize(Log.DEBUG, tag, msg);
    }

    public static void i(String tag, String msg) {
        logChunkInSize(Log.INFO, tag, msg);
    }

    public static void w(String tag, String msg) {
        logChunkInSize(Log.WARN, tag, msg);
    }

    public static void w(String tag, Exception msg) {
        logChunkInSize(Log.WARN, tag, Log.getStackTraceString(msg));
    }

    public static void e(String tag, String msg) {
        logChunkInSize(Log.ERROR, tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        logChunkInSize(Log.ERROR, tag, msg + LINE_SEPARATOR + Log.getStackTraceString(tr));
    }

    /**
     * bfc错误日志
     *
     * @param tag
     * @param eCode 错误码
     */
    public static String bfcExceptionLog(String tag, String eCode) {
        return bfcExceptionLog(tag, eCode, "");
    }

    /**
     * bfc错误日志
     *
     * @param tag
     * @param eCode 错误码
     */
    public static String bfcExceptionLog(String tag, String eCode, String detail) {
        String log = TextUtils.concat(
                "BfcBehaviorAidlException:", eCode, LINE_SEPARATOR,
                ErrorCode.getErrorCodes().get(eCode), ",", detail, LINE_SEPARATOR,
                "LibVersion:", Build.VERSION.VERSION_NAME
        ).toString();
        logChunkInSize(Log.ERROR, tag, log);
        return log;
    }

    /**
     * bfc警告日志
     * <p> 有不正常的地方，当时还是能兼容使用 <p/>
     *
     * @param tag
     * @param msg
     */
    public static void bfcWLog(String tag, String msg) {
        w(tag, msg);
    }

    public static void log(String log) {
        logChunkInSize(Log.ASSERT, TAG, log);
    }

    private static void logChunkInSize(int method, String tag, String msg) {
        if (!isLog) {
            return;
        }
        if (!ConfigAgent.getBehaviorConfig().isDebugMode && method < Log.INFO) {
            // 非调式模式下,只打印info(包括info)以上的日志
            return;
        }
        logByMode(method, tag, msg);
    }

    private static void logByMode(int method, String tag, String msg) {
        switch (method) {
            case Log.VERBOSE:
                Log.v(tag, msg);
                break;
            case Log.DEBUG:
                Log.d(tag, msg);
                break;
            case Log.INFO:
                Log.i(tag, msg);
                break;
            case Log.WARN:
                Log.w(tag, msg);
                break;
            case Log.ERROR:
                Log.e(tag, msg);
                break;
            case Log.ASSERT:
                Log.e(tag, msg);
                break;
            default:
                break;
        }
    }

    /**
     * 获取错误日志
     *
     * @return
     */
    public static String getSysErrLog() {
        StringBuilder syslog = new StringBuilder(100);
        List<String> cmdLine = new ArrayList<String>(); // 设置命令 logcat -d
        // 读取日志
        cmdLine.add("logcat");
        cmdLine.add("-d");

        List<String> clearLog = new ArrayList<String>(); // 设置命令 logcat -c
        // 清除日志
        clearLog.add("logcat");
        clearLog.add("-c");

        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            Process process = Runtime.getRuntime().exec(cmdLine.toArray(new String[cmdLine.size()]));
            // 捕获日志
            inputStreamReader = new InputStreamReader(process.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader); // 将捕获内容转换为BufferedReader
            String tempstr;
            while ((tempstr = bufferedReader.readLine()) != null) { // 开始读取日志，每次读取一行
                Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()])); // 清理日志....这里至关重要，不清理的话，任何操作都将产生新的日志，代码进入死循环，直到bufferreader满
                if (!TextUtils.isEmpty(tempstr) && syslog.length() < 1000) {
                    syslog.append(syslog).append(LINE_SEPARATOR).append(tempstr);
                } else {
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeIO(bufferedReader);
            FileUtils.closeIO(inputStreamReader);
        }
        return syslog.toString();
    }

    private LogUtils() {
        throw new AssertionError();
    }

}
