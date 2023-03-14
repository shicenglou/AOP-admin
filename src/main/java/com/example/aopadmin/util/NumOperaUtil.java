package com.example.aopadmin.util;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NumOperaUtil {


    public static double doubleArrAverage(List<Double> arr) {
        double sum = 0;
        for (double v : arr) {
            sum += v;
        }
        return sum / arr.size();
    }

    /**
     * 获取最多数量的数字
     * @param arr
     * @return
     */
    public static int getMaxNum(List<Integer> arr){
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer i : arr){
            if (map.containsKey(i)){
                map.replace(i,map.get(i) + 1);
            }else {
                map.put(i,1);
            }
        }
        int a = 0,b = 0;
        for (Integer key : map.keySet()){
            if (map.get(key) > b){
                a = key;
                b = map.get(key);
            }
        }
        return a;
    }
}
