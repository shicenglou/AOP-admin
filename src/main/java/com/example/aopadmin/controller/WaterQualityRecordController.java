package com.example.aopadmin.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.example.aopadmin.entity.EnvironmentRecord;
import com.example.aopadmin.entity.WaterQualityRecord;
import com.example.aopadmin.model.Result;
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
@RequestMapping("/water")
public class WaterQualityRecordController {

    @Autowired
    private WaterQualityRecordService waterQualityRecordService;

    @PostMapping("/add")
    @ApiOperation(value = "EMQX规则入库水质", httpMethod = "POST")
    public Result addSql(@RequestBody WaterQualityRecord waterQualityRecord){
        waterQualityRecord.setTime(new Date());
        waterQualityRecord.setId(UUID.randomUUID().toString().replace("-",""));
        if (StringUtils.isNotBlank(waterQualityRecord.getTemp()) ||
        StringUtils.isNotBlank(waterQualityRecord.getPh()) ||
        StringUtils.isNotBlank(waterQualityRecord.getDo1())){
            waterQualityRecordService.saveOrUpdate(waterQualityRecord);
        }
        log.info("保存成功！{}",waterQualityRecord);
        return Result.ok("数据入库");
    }

    @GetMapping("/history")
    @ApiOperation(value = "获取历史数据", httpMethod = "GET")
    public Result getHistory(@RequestParam String startTime, @RequestParam String endTime, @RequestParam String target) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = dateFormat.parse(startTime);
        Date end = dateFormat.parse(endTime);
        Calendar instance = Calendar.getInstance();
        instance.setTime(start);

        if (StringUtils.isBlank(target)){return Result.error("请选择时间频率");}
        //分钟
        HashMap<String, List> result = new HashMap<>();
        List<Double> temp = new ArrayList<>();
        List<Double> o2 = new ArrayList<>();
        List<Double> ph = new ArrayList<>();
        List<Date> time = new ArrayList<>();
        if (target.equals("1")){
            //分钟计算

            while (instance.getTime().before(end)){
                time.add(instance.getTime());
                Date nextTime = DateEx.addMinute(instance.getTime(), 1);
                List<WaterQualityRecord> list = waterQualityRecordService.list(new LambdaQueryWrapper<WaterQualityRecord>()
                        .ge(WaterQualityRecord::getTime, instance.getTime())
                        .lt(WaterQualityRecord::getTime, nextTime)
                        .orderByAsc(WaterQualityRecord::getTime));
                //均值，设置环境信息，没有取-1
                addList(list,temp,o2,ph);
                instance.setTime(nextTime);
            }
        }
        //小时
        if (target.equals("2")){
            while (instance.getTime().before(end)){
                Date time1 = instance.getTime();
                time.add(time1);
                instance.add(Calendar.HOUR,1);
                List<WaterQualityRecord> list = waterQualityRecordService.list(new LambdaQueryWrapper<WaterQualityRecord>()
                        .ge(WaterQualityRecord::getTime, time1)
                        .lt(WaterQualityRecord::getTime, instance.getTime())
                        .orderByAsc(WaterQualityRecord::getTime));
                addList(list,temp,o2,ph);
            }
        }
        //天
        if (target.equals("3")){
            while (instance.getTime().before(end)){
                Date time1 = instance.getTime();
                time.add(time1);
                instance.add(Calendar.DAY_OF_YEAR,1);
                List<WaterQualityRecord> list = waterQualityRecordService.list(new LambdaQueryWrapper<WaterQualityRecord>()
                        .ge(WaterQualityRecord::getTime, time1)
                        .lt(WaterQualityRecord::getTime, instance.getTime())
                        .orderByAsc(WaterQualityRecord::getTime));
                addList(list,temp,o2,ph);
            }
        }
        //周
        if (target.equals("4")){
            while (instance.getTime().before(end)){
                Date time1 = instance.getTime();
                time.add(time1);
                instance.add(Calendar.WEEK_OF_YEAR,1);
                List<WaterQualityRecord> list = waterQualityRecordService.list(new LambdaQueryWrapper<WaterQualityRecord>()
                        .ge(WaterQualityRecord::getTime, time1)
                        .lt(WaterQualityRecord::getTime, instance.getTime())
                        .orderByAsc(WaterQualityRecord::getTime));
                addList(list,temp,o2,ph);
            }
        }
        result.put("temp",temp);
        result.put("o2",o2);
        result.put("ph",ph);
        result.put("time",time);
        return Result.ok(result);
    }


    public void addList(List<WaterQualityRecord> list,List<Double> temp,List<Double> o2,List<Double> ph){
        if (!list.isEmpty()){
            List<Double> tempList = list.stream().filter(item -> StringUtils.isNotBlank(item.getTemp()) && NumEx.isNumber(item.getTemp())).map(item->Double.parseDouble(item.getTemp())).collect(Collectors.toList());
            List<Double> o2List = list.stream().filter(item -> StringUtils.isNotBlank(item.getDo1()) && NumEx.isNumber(item.getDo1())).map(item->Double.parseDouble(item.getDo1())).collect(Collectors.toList());
            List<Double> phList = list.stream().filter(item -> StringUtils.isNotBlank(item.getPh()) && NumEx.isNumber(item.getPh())).map(item->Double.parseDouble(item.getPh())).collect(Collectors.toList());


            o2.add(Double.parseDouble(String.format("%.2f",NumEx.avgDouble(o2List))));
            temp.add(Double.parseDouble(String.format("%.2f",NumEx.avgDouble(tempList))));
            ph.add(Double.parseDouble(String.format("%.2f",NumEx.avgDouble(phList))));
        }else {
            o2.add(0.0);
            temp.add(0.0);
            ph.add(0.0);
        }
    }

    @GetMapping("/getWaterMonthHistory")
    @ApiOperation(value = "获取水质前30日平均数据", httpMethod = "GET")
    public Result getWaterMonthHistory() {
        DateTime endOfDate = DateUtil.date();
        DateTime beginOfDay = DateUtil.offsetDay(endOfDate, -30);
        List<WaterQualityRecord> waterList = waterQualityRecordService.list(new LambdaQueryWrapper<WaterQualityRecord>()
                .ge(WaterQualityRecord::getTime, beginOfDay)
                .le(WaterQualityRecord::getTime, endOfDate));

        List<Double> phs = new ArrayList<>();
        List<Double> temps = new ArrayList<>();
        List<Double> dos = new ArrayList<>();
        List<String> time = new ArrayList<>();
        for (DateTime dateTime = beginOfDay; dateTime.compareTo(endOfDate) <= 0 ; dateTime = DateUtil.offsetDay(dateTime, 1)) {
            DateTime finalDateTime = dateTime;
            time.add(DateUtil.format(finalDateTime, "MM/dd"));
            double ph = waterList.stream().filter(env -> DateUtil.isIn(env.getTime(), finalDateTime, DateUtil.offsetDay(finalDateTime, 1)) && NumEx.isNumber(env.getPh()))
                    .mapToDouble(item -> Double.valueOf(item.getPh()))
                    .average().orElse(0.0);
            phs.add(ph);
            double temp = waterList.stream().filter(env -> DateUtil.isIn(env.getTime(), finalDateTime, DateUtil.offsetDay(finalDateTime, 1)) && NumEx.isNumber(env.getTemp()))
                    .mapToDouble(item -> Double.valueOf(item.getTemp()))
                    .average().orElse(0.0);
            temps.add(temp);
            double do1 = waterList.stream().filter(env -> DateUtil.isIn(env.getTime(), finalDateTime, DateUtil.offsetDay(finalDateTime, 1)) && NumEx.isNumber(env.getDo1()))
                    .mapToDouble(item -> Double.valueOf(item.getDo1()))
                    .average().orElse(0.0);
            dos.add(do1);
        }
        HashMap<String, List> result = new HashMap<>(4);
        result.put("ph",phs);
        result.put("do",dos);
        result.put("temp",temps);
        result.put("time",time);
        return Result.ok(result);
    }

    @GetMapping("/getWaterDayHistory")
    @ApiOperation(value = "获取水质前24小时平均数据", httpMethod = "GET")
    public Result getWaterDayHistory() {
        DateTime endOfDate = DateUtil.date();
        DateTime beginOfDay = DateUtil.offsetHour(endOfDate, -24);
        List<WaterQualityRecord> waterList = waterQualityRecordService.list(new LambdaQueryWrapper<WaterQualityRecord>()
                .ge(WaterQualityRecord::getTime, beginOfDay)
                .le(WaterQualityRecord::getTime, endOfDate));

        List<Double> phs = new ArrayList<>();
        List<Double> temps = new ArrayList<>();
        List<Double> dos = new ArrayList<>();
        List<String> time = new ArrayList<>();
        for (DateTime dateTime = beginOfDay; dateTime.compareTo(endOfDate) <= 0 ; dateTime = DateUtil.offsetHour(dateTime, 1)) {
            DateTime finalDateTime = dateTime;
            time.add(DateUtil.format(finalDateTime, "HH:00"));
            double ph = waterList.stream().filter(env -> DateUtil.isSameDay(env.getTime(), finalDateTime) && NumEx.isNumber(env.getPh()))
                    .mapToDouble(item -> Double.valueOf(item.getPh()))
                    .average().orElse(0.0);
            phs.add(ph);
            double temp = waterList.stream().filter(env -> DateUtil.isSameDay(env.getTime(), finalDateTime) && NumEx.isNumber(env.getTemp()))
                    .mapToDouble(item -> Double.valueOf(item.getTemp()))
                    .average().orElse(0.0);
            temps.add(temp);
            double do1 = waterList.stream().filter(env -> DateUtil.isSameDay(env.getTime(), finalDateTime) && NumEx.isNumber(env.getDo1()))
                    .mapToDouble(item -> Double.valueOf(item.getDo1()))
                    .average().orElse(0.0);
            dos.add(do1);
        }
        HashMap<String, List> result = new HashMap<>(4);
        result.put("ph",phs);
        result.put("do",dos);
        result.put("temp",temps);
        result.put("time",time);
        return Result.ok(result);
    }

}

