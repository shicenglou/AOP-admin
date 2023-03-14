package com.example.aopadmin.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.aopadmin.entity.EnvironmentRecord;
import com.example.aopadmin.entity.WaterQualityRecord;
import com.example.aopadmin.model.MySmartHomePub;
import com.example.aopadmin.model.Result;
import com.example.aopadmin.service.EnvironmentRecordService;
import com.example.aopadmin.service.WaterQualityRecordService;
import com.example.aopadmin.task.AvgDataTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.Date;


@RestController
@RequestMapping("/dailyData")
@Api(tags = "日常数据管理接口")
@Slf4j
@RequiredArgsConstructor
public class DaliyDataController {

    private final EnvironmentRecordService environmentRecordService;

    private final WaterQualityRecordService waterQualityRecordService;

    private final AvgDataTask avgDataTask;

    @PostMapping
    @ApiOperation(value = "新增环境数据", httpMethod = "POST")
    @ResponseBody
    public Result addUser(@RequestBody MySmartHomePub pub){
        EnvironmentRecord environmentRecord = new EnvironmentRecord();
        WaterQualityRecord waterQualityRecord = new WaterQualityRecord();

        //环境数据
        environmentRecord.setUpdateTime(new Date());
        environmentRecord.setTime(new Date());
        environmentRecord.setLed(pub.getLed());
        environmentRecord.setTemp(pub.getTemp());
        environmentRecord.setLight(pub.getLight());
        environmentRecord.setHum(pub.getHum());
        environmentRecord.setBeep(pub.getBeep());
        environmentRecord.setCo2(pub.getCo2());
        //水质
        waterQualityRecord.setTime(new Date());
        waterQualityRecord.setTemp(pub.getTemp());
        waterQualityRecord.setPh(pub.getPh());
        waterQualityRecord.setDo1(pub.getDo1());

        if (!environmentRecord.isFail()) environmentRecordService.save(environmentRecord);
        if (!waterQualityRecord.isFail()) waterQualityRecordService.saveOrUpdate(waterQualityRecord);
        return Result.ok("添加数据成功");
    }

    @GetMapping("/avgEnv")
    public Result avgEnv(@RequestParam String dateTime){
        DateTime parse = DateUtil.parse(dateTime, "yyyy-MM-dd HH:mm:ss");
        avgDataTask.dataToAvg(parse);
        return Result.ok();
    }
}
