package com.neu.common.utils;

public class CommonUtils {

    /**
     * 生成随机数
     * @param num 位数
     * @return
     */
    public static String createRandomNum(int num){
        String randomNumStr = "";
        for(int i = 0; i < num;i ++){
            int randomNum = (int)(Math.random() * 10);
            randomNumStr += randomNum;
        }
        return randomNumStr;
    }
    /**
     * 判断是否有空字段
     */

    public static boolean stringIsEmpty(String str) {
        return str==null||str.equals("");
    }
}
