package com.eebbk.bfc.sdk.behavior.control.report;

import android.text.TextUtils;

/**
 * @author hesn
 * @function 数据库保存的特殊上传条件判断
 * @date 16-8-26
 * @company 步步高教育电子有限公司
 */

public class Judgment {
    /**
     * 使用移动网络上传
     */
    public static final int TYPE_MOBILE_TRAFFIC = 1;

    //分隔符
    private static final String SYMBOL = "|";

    /**
     * 是否可以使用移动网络上传
     * @param judgment
     * @return
     */
    public static boolean isMobileTraffic(String judgment){
        return isExistType(judgment, TYPE_MOBILE_TRAFFIC);
    }

    /**
     * 设置是否允许使用移动网络
     * @param useable
     * @return
     */
    public static String useMobileTraffic(String judgment, boolean useable){
        return useable ? addJudgment(judgment, TYPE_MOBILE_TRAFFIC)
                : removeJudgment(judgment, TYPE_MOBILE_TRAFFIC);
    }

    /**
     * 是否有某类型特殊上传条件
     * @param judgment
     * @param type
     * @return
     */
    public static boolean isExistType(String judgment, int type){
        return !TextUtils.isEmpty(judgment) && judgment.contains(format(type));
    }

    /**
     * 添加特殊上传判断条件
     * @param judgment
     * @param type
     */
    public static String addJudgment(String judgment, int type){
        String typeStr = format(type);
        if(TextUtils.isEmpty(judgment)){
            return typeStr;
        }
        if(judgment.contains(typeStr)){
            return judgment;
        }
        return judgment + typeStr;
    }

    /**
     * 去掉特殊上传判断条件
     * @param judgment
     * @param type
     */
    public static String removeJudgment(String judgment, int type){
        String typeStr = format(type);
        if(TextUtils.isEmpty(judgment) || !judgment.contains(typeStr)){
            return judgment;
        }
        return judgment.replace(typeStr, "");
    }

    /**
     * 保存格式
     * @param type
     * @return
     */
    private static String format(int type){
        return SYMBOL + type + SYMBOL;
    }

    private Judgment(){
        //prevent the instance
    }
}
