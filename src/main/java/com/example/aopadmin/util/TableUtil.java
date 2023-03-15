package com.example.aopadmin.util;

import com.example.aopadmin.model.PowerTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableUtil {

    /**
     * 将map中数据转换成二维坐标系
     * @param xName     x变量名，任意格式
     * @param yName     y变量名，数值类型
     * @param data      map
     * @return          坐标列表，按照map的顺序填充
     */
    public static List<PowerTable> changeData(String xName, String yName, List<HashMap<String,Object>> data){
        List<PowerTable> value = new ArrayList<>();
        for (HashMap<String, Object> datum : data) {

            Double s = (Double) datum.get(yName);
            datum.get(xName);
            PowerTable table = new PowerTable();
            table.setX(datum.get(xName).toString());
            table.setY(s);
            value.add(table);

        }
        return value;
    }

}
