package com.example.aopadmin.model;

import lombok.Data;

@Data
public class MySmartHomePub {

    /**
     * 温度
     */
    private String temp;

    /**
     * 湿度
     */
    private String hum;

    /**
     * 光度
     */
    private String light;

    /**
     * 灯状态  0关 1开
     */
    private String led;

    /**
     * 蜂鸣器 0关 1开
     */
    private String beep;

    /**
     * co2浓度
     */
    private String co2;

    /**
     * ph值
     */
    private String ph;

    /**
     * 氧气
     */
    private String do1;
}
