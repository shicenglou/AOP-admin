package com.example.aopadmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "User对象", description = "")
@TableName(value = "user")
public class User {
    @ApiModelProperty(value = "主键id")
    @TableField(value = "user_id")
    @TableId
    private String userId;

    @ApiModelProperty(value = "用户姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "密码")
    @TableField("pwd")
    private String password;

    @ApiModelProperty(value = "mobile")
    @TableField("mobile")
    private String mobile;
}
