package com.example.aopadmin.controller;


import com.example.aopadmin.entity.EnviromentRecord;

import com.example.aopadmin.model.Result;

import com.example.aopadmin.service.EnviromentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/dailyData")
@Api(tags = "日常数据管理接口")
@Slf4j
public class DaliyDataController {


    @Autowired
    private EnviromentService enviromentService;

    @PostMapping
    @ApiOperation(value = "新增环境数据", httpMethod = "POST")
    @ResponseBody
    public Result addUser(@RequestBody EnviromentRecord enviromentRecord){
        enviromentRecord.setId(UUID.randomUUID().toString().replace("-",""));
        enviromentRecord.setTime(new Date());
        enviromentRecord.setUpdateTime(new Date());
        if (StringUtils.isNotBlank(enviromentRecord.getHum()) ||
        StringUtils.isNotBlank(enviromentRecord.getBeep()) ||
        StringUtils.isNotBlank(enviromentRecord.getCo2()) ||
        StringUtils.isNotBlank(enviromentRecord.getLed()) ||
        StringUtils.isNotBlank(enviromentRecord.getLight()) ||
        StringUtils.isNotBlank(enviromentRecord.getTemp())){
            enviromentService.saveOrUpdate(enviromentRecord);
        }

        log.info("保存成功！{}",enviromentRecord);
        return Result.ok("添加用户成功");
    }
}
