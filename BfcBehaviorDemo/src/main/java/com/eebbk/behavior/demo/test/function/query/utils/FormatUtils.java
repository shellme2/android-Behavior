package com.eebbk.behavior.demo.test.function.query.utils;

import android.content.ContentValues;

/**
 * @author hesn
 * @function
 * @date 16-8-30
 * @company 步步高教育电子有限公司
 */

public class FormatUtils {

    private static final String SYMBOL = "\n";
    private static final String VALUES_SYMBOL = " : ";

    public static String formatTitle(int cacheSize, int dbSize, int fileSize){
        return "缓存池中有: " + cacheSize + " 条" + SYMBOL
                + "数据库中有: " + dbSize + " 条" + SYMBOL
                + "文件中有: " + fileSize + " 条" + SYMBOL;
    }

    public static String formatListTitle(String title){
        return SYMBOL + SYMBOL + "****** " + title + " ******" + SYMBOL + SYMBOL;
    }

    /**
     * ContentValues 的每个values数据排版
     * @param values
     * @param valuseNames
     * @return
     */
    public static String getAsString(ContentValues values, String...valuseNames){
        StringBuilder sb = new StringBuilder(SYMBOL);
        for (String valuseName : valuseNames){
            sb.append( "[ " + valuseName + " ]" + VALUES_SYMBOL
                    + values.getAsString(valuseName) + SYMBOL);
        }
        return sb.toString();
    }
}
