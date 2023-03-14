package com.example.aopadmin.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.aopadmin.entity.PlantsRecord;
import com.example.aopadmin.entity.WaterQualityRecord;
import com.example.aopadmin.model.Result;
import com.example.aopadmin.service.PlantsRecordService;
import com.example.aopadmin.service.WaterQualityRecordService;
import com.example.aopadmin.util.DateEx;
import com.example.aopadmin.util.NumEx;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-06-24
 */
@Slf4j
@RestController
@RequestMapping("/plants")
public class PlantsRecordController {

    @Autowired
    private PlantsRecordService plantsRecordService;


    @GetMapping("/getPlantsMonthHistory")
    @ApiOperation(value = "获取长势前30日平均数据", httpMethod = "GET")
    public Result getPlantsMonthHistory() {
        DateTime endOfDate = DateUtil.date();
        DateTime beginOfDay = DateUtil.offsetDay(endOfDate, -30);
        List<PlantsRecord> plantsList = plantsRecordService.list(new LambdaQueryWrapper<PlantsRecord>()
                .ge(PlantsRecord::getTime, beginOfDay)
                .le(PlantsRecord::getTime, endOfDate));

        List<Double> heights = new ArrayList<>();
        List<Double> widths = new ArrayList<>();
        List<String> time = new ArrayList<>();
        for (DateTime dateTime = beginOfDay; dateTime.compareTo(endOfDate) <= 0 ; dateTime = DateUtil.offsetDay(dateTime, 1)) {
            DateTime finalDateTime = dateTime;
            time.add(DateUtil.format(finalDateTime, "MM/dd"));
            double height = plantsList.stream().filter(env -> DateUtil.isSameDay(env.getTime(), finalDateTime) && NumEx.isNumber(env.getHeight()))
                    .mapToDouble(item -> Double.valueOf(item.getHeight()))
                    .average().orElse(0.0);
            heights.add(height);
            double width = plantsList.stream().filter(env -> DateUtil.isSameDay(env.getTime(), finalDateTime) && NumEx.isNumber(env.getWidth()))
                    .mapToDouble(item -> Double.valueOf(item.getWidth()))
                    .average().orElse(0.0);
            widths.add(width);
        }
        HashMap<String, List> result = new HashMap<>(4);
        result.put("height",heights);
        result.put("width",widths);
        result.put("time",time);
        return Result.ok(result);
    }


    @GetMapping("/getPlantsDayHistory")
    @ApiOperation(value = "获取长势前24时日平均数据", httpMethod = "GET")
    public Result getPlantsDayHistory() {
        DateTime endOfDate = DateUtil.date();
        DateTime beginOfDay = DateUtil.offsetHour(endOfDate, -24);
        List<PlantsRecord> plantsList = plantsRecordService.list(new LambdaQueryWrapper<PlantsRecord>()
                .ge(PlantsRecord::getTime, beginOfDay)
                .le(PlantsRecord::getTime, endOfDate));

        List<Double> heights = new ArrayList<>();
        List<Double> widths = new ArrayList<>();
        List<String> time = new ArrayList<>();
        for (DateTime dateTime = beginOfDay; dateTime.compareTo(endOfDate) <= 0 ; dateTime = DateUtil.offsetHour(dateTime, 1)) {
            DateTime finalDateTime = dateTime;
            time.add(DateUtil.format(finalDateTime, "HH:00"));
            double height = plantsList.stream().filter(env -> DateUtil.isSameDay(env.getTime(), finalDateTime) && NumEx.isNumber(env.getHeight()))
                    .mapToDouble(item -> Double.valueOf(item.getHeight()))
                    .average().orElse(0.0);
            heights.add(height);
            double width = plantsList.stream().filter(env -> DateUtil.isSameDay(env.getTime(), finalDateTime) && NumEx.isNumber(env.getWidth()))
                    .mapToDouble(item -> Double.valueOf(item.getWidth()))
                    .average().orElse(0.0);
            widths.add(width);
        }
        HashMap<String, List> result = new HashMap<>(4);
        result.put("height",heights);
        result.put("width",widths);
        result.put("time",time);
        return Result.ok(result);
    }

}

