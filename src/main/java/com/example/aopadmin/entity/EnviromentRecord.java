package com.example.aopadmin.entity;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "EnviromentRecord对象", description = "")
@TableName(value = "environment_record")
public class EnviromentRecord {

    @ApiModelProperty(value = "环境ID")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT,value = "time")
    private Date time;

    @ApiModelProperty(value = "co2")
    @TableField(value = "co2")
    private String co2;

    @ApiModelProperty(value = "光度")
    @TableField(value = "light")
    private String light;

    @ApiModelProperty(value = "温度")
    @TableField(value = "temp")
    private String temp;

    @ApiModelProperty(value = "湿度")
    @TableField(value = "hum")
    private String hum;

    @ApiModelProperty(value = "蜂鸣器 0关 1开")
    @TableField(value = "beep")
    private String beep;

    @ApiModelProperty(value = "灯状态  0关 1开")
    @TableField(value = "led")
    private String led;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
