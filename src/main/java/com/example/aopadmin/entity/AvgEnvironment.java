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
 * @author DPH
 * @since 2023-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AvgEnvironment对象", description="")
public class AvgEnvironment implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "数据对应日期")
    private Date time;

    @ApiModelProperty(value = "温度")
    private Double temp;

    @ApiModelProperty(value = "湿度")
    private Double hum;

    @ApiModelProperty(value = "光照强度")
    private Double light;

    @ApiModelProperty(value = "灯状态  0关 1开")
    private Integer led;

    @ApiModelProperty(value = "蜂鸣器 0关 1开")
    private Integer beep;

    @ApiModelProperty(value = "co2浓度")
    private Double co2;

    public boolean isFail(){
        return temp == null && hum == null && light == null && co2 == null;
    }


}
