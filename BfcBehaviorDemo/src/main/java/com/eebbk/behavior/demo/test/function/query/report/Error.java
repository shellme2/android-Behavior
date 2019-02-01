package com.eebbk.behavior.demo.test.function.query.report;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hesn
 * 2018/7/13
 */
public class Error {

    private static final Map<Integer, String> MSG = new HashMap<>();

    static {
        MSG.put(Code.SUCCESS, "成功");
        MSG.put(Code.ERROR_VERSION_LOW, "失败 【版本过低】");
//        MSG.put(Code.ERROR_BIND_FAIL, "获取失败\n1.没有集成行为采集库\n2.集成aidl版本\n3.非“com.eebbk.”和“com.bbk.”前缀包名无法唤起检查");
        MSG.put(Code.ERROR_BIND_FAIL, "失败 【读取配置失败】");
        MSG.put(Code.ERROR_INIT, "失败 【app没有在application中初始化,获取的配置信息不准确】");
    }

    public interface Code{
        int SUCCESS = 0;
        int ERROR_VERSION_LOW = 1;
        int ERROR_BIND_FAIL = 2;
        int ERROR_INIT = 3;
    }

    public static String getErrorMsg(int code){
        return MSG.get(code);
    }
}
