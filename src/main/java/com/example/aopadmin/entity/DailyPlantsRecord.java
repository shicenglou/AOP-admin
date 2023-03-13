package com.example.aopadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

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
 * @since 2022-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DailyPlantsRecord对象", description="")
public class DailyPlantsRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "写入一个月内每天的长势数据")
    private Date data;

    private Double height;

    private Double width;

    private String leafArea;

      @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;


}
