package com.eebbk.bfc.sdk.behavior.utils;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 16-8-17
 * @company 步步高教育电子有限公司
 */

public class MapUtils {

    /**
     * hashmap转json
     * @param map
     * @return
     */
    public static String map2Json(Map map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        return new JSONObject(map).toString();
    }

    private MapUtils(){
        //prevent the instance
    }
}
