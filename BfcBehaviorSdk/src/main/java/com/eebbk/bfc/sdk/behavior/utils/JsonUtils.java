package com.eebbk.bfc.sdk.behavior.utils;

import com.eebbk.bfc.sequence.SequenceTools;

import java.lang.reflect.Type;

/**
 * @author hesn
 * @function
 * @date 16-11-7
 * @company 步步高教育电子有限公司
 */

public class JsonUtils {

    public static String toJson(Object srcObj) {
        return SequenceTools.serialize(srcObj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return SequenceTools.deserialize(json, classOfT);
    }
}
