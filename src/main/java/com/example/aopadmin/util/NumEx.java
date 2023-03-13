package com.example.aopadmin.util;

import java.util.List;
import java.util.regex.Pattern;

public class NumEx {

    /**
     * 求平均数 空返回-1
     * @param list      传入数组
     * @return          均值
     */
    public static Double avgDouble(List<Double> list){
        if (list.isEmpty()){
            return -1.0;
        }else {
            Double len = Double.valueOf(list.size());
            Double num = 0.0;
            for (Double item:list){
                num = num + item;
            }
            return num/len;
        }
    }


    /**
     * 判断字符串可准换为double
     * @param string
     * @return
     */
    public static boolean isNumber(String string) {
        if (string == null)
            return false;
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        return pattern.matcher(string).matches();
    }
}
