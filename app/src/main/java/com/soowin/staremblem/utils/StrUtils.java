package com.soowin.staremblem.utils;

/**
 * Created by Administrator on 2017/6/19.
 * 类的作用： 字符串判空  获取人民币符号
 */

public class StrUtils {
    public static boolean isEmpty(String str) {
        if (str == null)
            return true;
        str = str.replace(" ", "");
        if (str.length() < 1)
            return true;
        return false;
    }

    /**
     * 获取人民币符号
     * @return
     */
    public static String getRmb(){
        char c = 165;
        return String.valueOf(c);
    }
}
