package com.example.aopadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2022-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EnvironmentRecord对象", description="")
public class EnvironmentRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键id")
      @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "时间")
    private Date time;

    @ApiModelProperty(value = "二氧化碳")
    private String co2;

    @ApiModelProperty(value = "光度")
    private String light;

    @ApiModelProperty(value = "温度")
    private String temp;

    @ApiModelProperty(value = "湿度")
    private String hum;

    @ApiModelProperty(value = "蜂鸣器 0关 1开")
    private String beep;

    @ApiModelProperty(value = "灯状态  0关 1开")
    private String led;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
