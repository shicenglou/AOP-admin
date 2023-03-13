package com.example.aopadmin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEx {

    /**
     * 将日期加指定分钟
     * @param date          传入日期
     * @param min           增加多少分钟
     * @return              返回一个指定日期
     * @throws ParseException
     */
    public static Date addMinute(Date date, int min) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(date);
        String substring = format.substring(14, 16);
        int i = Integer.parseInt(substring);
        String newMin = Integer.toString(i + min);
        if (newMin.length()<2){
            newMin = "0"+newMin;
        }
        StringBuilder stringBuilder = new StringBuilder(format);
        stringBuilder.replace(14,16,newMin);
        String s = stringBuilder.toString();
        return dateFormat.parse(s);
    }
}
