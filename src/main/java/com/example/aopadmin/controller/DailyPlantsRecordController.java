package com.example.aopadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.aopadmin.entity.DailyPlantsRecord;
import com.example.aopadmin.service.DailyPlantsRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-06-24
 */
@Slf4j
@Api(tags = "每日长势数据接口")
@RestController
@RequestMapping("/dailyPlants")
public class DailyPlantsRecordController {

    @Autowired
    private DailyPlantsRecordService plantsRecordService;

    @GetMapping
    @ApiOperation(value = "获取某月每日数据", httpMethod = "GET")
    @ApiImplicitParam(name = "month",value = "月份")
    public R getDailyMonth(@RequestParam String month) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //起始时间
        Date monthStart = dateFormat.parse(month);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(monthStart);
        rightNow.add(Calendar.MONTH,1);
        //结束时间
        Date monthEnd = rightNow.getTime();
        List<Double> heightList = new ArrayList<>();
        List<Double> widthList = new ArrayList<>();
        List<String> leafAreaList = new ArrayList<>();
        List<DailyPlantsRecord> list = plantsRecordService.list(new LambdaQueryWrapper<DailyPlantsRecord>().ge(DailyPlantsRecord::getData, monthStart).lt(DailyPlantsRecord::getData, monthEnd).orderByAsc(DailyPlantsRecord::getData));
        list.forEach(item->{
            heightList.add(item.getHeight());
            widthList.add(item.getWidth());
            leafAreaList.add(item.getLeafArea());
        });
        HashMap<String, List> result = new HashMap<>();
        result.put("height",heightList);
        result.put("width",widthList);
        result.put("leafArea",leafAreaList);
        return R.ok(result);
    }

}

