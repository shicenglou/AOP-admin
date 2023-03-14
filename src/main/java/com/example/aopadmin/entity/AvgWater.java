package com.example.aopadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="AvgWater对象", description="")
public class AvgWater implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "温度")
    private Double temp;

    @ApiModelProperty(value = "ph值")
    private Double ph;

    @ApiModelProperty(value = "含氧量")
    @TableField("do")
    private Double do1;

    @ApiModelProperty(value = "对应时间")
    private Date time;

    public boolean isFail(){
        return temp == null && ph == null && do1 == null;
    }

}
