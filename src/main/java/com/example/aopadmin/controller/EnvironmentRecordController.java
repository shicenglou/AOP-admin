package com.example.aopadmin.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.aopadmin.entity.EnvironmentRecord;
import com.example.aopadmin.model.PowerTable;
import com.example.aopadmin.model.Result;
import com.example.aopadmin.service.EnvironmentRecordService;
import com.example.aopadmin.util.DateEx;
import com.example.aopadmin.util.NumEx;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
 * @since 2022-06-19
 */
@RestController
@RequestMapping("/aopadmin/environment")
@Api(tags = "环境数据接口")
@Slf4j
public class EnvironmentRecordController {



    @Autowired
    private EnvironmentRecordService environmentRecordService;

    @GetMapping
    @ApiOperation(value = "获取最新数据",httpMethod = "GET")
    public Result getNew(){
        EnvironmentRecord one = environmentRecordService.getOne(new LambdaQueryWrapper<EnvironmentRecord>().orderByDesc(EnvironmentRecord::getUpdateTime).last("limit 1"));
        log.info("当时数据时间为：{}",one.getUpdateTime());
        return Result.ok(one);
    }

    @GetMapping("/historyEnv")
    @ApiOperation(value = "获取历史数据", httpMethod = "GET")
    public  Result getHistory(@RequestParam String startTime,@RequestParam String endTime,@RequestParam String target) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = dateFormat.parse(startTime);
        Date end = dateFormat.parse(endTime);
        Calendar instance = Calendar.getInstance();
        instance.setTime(start);

        if (StringUtils.isBlank(target)){return Result.error("请选择时间频率");}
        //分钟
        HashMap<String, List> result = new HashMap<>();
        List<Double> hum = new ArrayList<>();
        List<Double> light = new ArrayList<>();
        List<Double> temp = new ArrayList<>();
        List<Double> co2 = new ArrayList<>();
        List<Double> beep = new ArrayList<>();
        List<Double> led = new ArrayList<>();
        List<Date> time = new ArrayList<>();
        if (target.equals("1")){
            //分钟计算

            while (instance.getTime().before(end)){
                time.add(instance.getTime());
                Date nextTime = DateEx.addMinute(instance.getTime(), 1);
                List<EnvironmentRecord> list = environmentRecordService.list(new LambdaQueryWrapper<EnvironmentRecord>()
                        .ge(EnvironmentRecord::getUpdateTime, instance.getTime())
                        .lt(EnvironmentRecord::getUpdateTime, nextTime)
                        .orderByAsc(EnvironmentRecord::getUpdateTime));
                //均值，设置环境信息，没有取-1
                addList(list,hum,temp,light,co2,led,beep);
                instance.setTime(nextTime);
            }
        }
        //小时
        if (target.equals("2")){
            while (instance.getTime().before(end)){
                Date time1 = instance.getTime();
                time.add(time1);
                instance.add(Calendar.HOUR,1);
                List<EnvironmentRecord> list = environmentRecordService.list(new LambdaQueryWrapper<EnvironmentRecord>()
                        .ge(EnvironmentRecord::getUpdateTime, time1)
                        .lt(EnvironmentRecord::getUpdateTime, instance.getTime())
                        .orderByAsc(EnvironmentRecord::getUpdateTime));
                addList(list,hum,temp,light,co2,led,beep);
            }
        }
        //天
        if (target.equals("3")){
            while (instance.getTime().before(end)){
                Date time1 = instance.getTime();
                time.add(time1);
                instance.add(Calendar.DAY_OF_YEAR,1);
                List<EnvironmentRecord> list = environmentRecordService.list(new LambdaQueryWrapper<EnvironmentRecord>()
                        .ge(EnvironmentRecord::getUpdateTime, time1)
                        .lt(EnvironmentRecord::getUpdateTime, instance.getTime())
                        .orderByAsc(EnvironmentRecord::getUpdateTime));
                addList(list,hum,temp,light,co2,led,beep);
            }
        }
        //周
        if (target.equals("4")){
            while (instance.getTime().before(end)){
                Date time1 = instance.getTime();
                time.add(time1);
                instance.add(Calendar.WEEK_OF_YEAR,1);
                List<EnvironmentRecord> list = environmentRecordService.list(new LambdaQueryWrapper<EnvironmentRecord>()
                        .ge(EnvironmentRecord::getUpdateTime, time1)
                        .lt(EnvironmentRecord::getUpdateTime, instance.getTime())
                        .orderByAsc(EnvironmentRecord::getUpdateTime));
                addList(list,hum,temp,light,co2,led,beep);
            }
        }
        result.put("hum",hum);
        result.put("light",light);
        result.put("temp",temp);
        result.put("co2",co2);
        result.put("beep",beep);
        result.put("led",led);
        result.put("time",time);
        return Result.ok(result);
    }
    public void addList(List<EnvironmentRecord> list,List<Double> hum,List<Double> temp,List<Double> light,List<Double> co2,List<Double> led,List<Double> beep){
        if (!list.isEmpty()){
            List<Double> humList = list.stream().filter(item -> StringUtils.isNotBlank(item.getHum()) && NumEx.isNumber(item.getHum())).map(item->Double.parseDouble(item.getHum())).collect(Collectors.toList());
            List<Double> lightList = list.stream().filter(item -> StringUtils.isNotBlank(item.getLight()) && NumEx.isNumber(item.getLight())).map(item->Double.parseDouble(item.getLight())).collect(Collectors.toList());
            List<Double> tempList = list.stream().filter(item -> StringUtils.isNotBlank(item.getTemp()) && NumEx.isNumber(item.getTemp())).map(item->Double.parseDouble(item.getTemp())).collect(Collectors.toList());
            List<Double> co2List = list.stream().filter(item -> StringUtils.isNotBlank(item.getCo2()) && NumEx.isNumber(item.getCo2())).map(item->Double.parseDouble(item.getCo2())).collect(Collectors.toList());
            beep.add(StringUtils.isNotBlank(list.get(0).getBeep()) && NumEx.isNumber(list.get(0).getBeep()) ? Double.parseDouble(list.get(0).getBeep()) : 0.0);
            hum.add(Double.parseDouble(String.format("%.2f", NumEx.avgDouble(humList))));
            co2.add(Double.parseDouble(String.format("%.2f",NumEx.avgDouble(co2List))));
            light.add(Double.parseDouble(String.format("%.2f",NumEx.avgDouble(lightList))));
            temp.add(Double.parseDouble(String.format("%.2f",NumEx.avgDouble(tempList))));
            led.add(StringUtils.isNotBlank(list.get(0).getLed()) && NumEx.isNumber(list.get(0).getLed()) ? Double.parseDouble(list.get(0).getLed()) : 0.0);
        }else {
            beep.add(0.0);
            co2.add(0.0);
            light.add(0.0);
            temp.add(0.0);
            hum.add(0.0);
            led.add(0.0);
        }
    }

    @GetMapping("/getEnvMonthHistory")
    @ApiOperation(value = "获取环境前30日平均数据", httpMethod = "GET")
    public Result getEnvMonthHistory() {
        DateTime endOfDate = DateUtil.date();
        DateTime beginOfDay = DateUtil.offsetDay(endOfDate, -30);
        List<EnvironmentRecord> envList = environmentRecordService.list(new LambdaQueryWrapper<EnvironmentRecord>()
                .ge(EnvironmentRecord::getUpdateTime, beginOfDay)
                .le(EnvironmentRecord::getUpdateTime, endOfDate));

        List<Double> hums = new ArrayList<>();
        List<Double> lights = new ArrayList<>();
        List<Double> temps = new ArrayList<>();
        List<Double> co2s = new ArrayList<>();
        List<String> time = new ArrayList<>();
        for (DateTime dateTime = beginOfDay; dateTime.compareTo(endOfDate) <= 0 ; dateTime = DateUtil.offsetDay(dateTime, 1)) {
            DateTime finalDateTime = dateTime;
            time.add(DateUtil.format(finalDateTime, "MM/dd"));
            double hum = envList.stream().filter(env -> DateUtil.isSameDay(env.getUpdateTime(), finalDateTime) && NumEx.isNumber(env.getHum()))
                    .mapToDouble(item -> Double.valueOf(item.getHum()))
                    .average().orElse(0.0);
            hums.add(hum);
            double temp = envList.stream().filter(env -> DateUtil.isSameDay(env.getUpdateTime(), finalDateTime) && NumEx.isNumber(env.getTemp()))
                    .mapToDouble(item -> Double.valueOf(item.getTemp()))
                    .average().orElse(0.0);
            temps.add(temp);
            double light = envList.stream().filter(env -> DateUtil.isSameDay(env.getUpdateTime(), finalDateTime) && NumEx.isNumber(env.getLight()))
                    .mapToDouble(item -> Double.valueOf(item.getLight()))
                    .average().orElse(0.0);
            lights.add(light);
            double co2 = envList.stream().filter(env -> DateUtil.isSameDay(env.getUpdateTime(), finalDateTime) && NumEx.isNumber(env.getCo2()))
                    .mapToDouble(item -> Double.valueOf(item.getCo2()))
                    .average().orElse(0.0);
            co2s.add(co2);
        }
        HashMap<String, List> result = new HashMap<>(5);
        result.put("hum",hums);
        result.put("light",lights);
        result.put("temp",temps);
        result.put("co2",co2s);
        result.put("time",time);
        return Result.ok(result);
    }

    @GetMapping("/getEnvDayHistory")
    @ApiOperation(value = "获取环境前24小时平均数据", httpMethod = "GET")
    public Result getEnvDayHistory() {
        DateTime endOfDate = DateUtil.date();
        DateTime beginOfDay = DateUtil.offsetHour(endOfDate, -24);
        List<EnvironmentRecord> envList = environmentRecordService.list(new LambdaQueryWrapper<EnvironmentRecord>()
                .ge(EnvironmentRecord::getUpdateTime, beginOfDay)
                .le(EnvironmentRecord::getUpdateTime, endOfDate));

        List<Double> hums = new ArrayList<>();
        List<Double> lights = new ArrayList<>();
        List<Double> temps = new ArrayList<>();
        List<Double> co2s = new ArrayList<>();
        List<String> time = new ArrayList<>();
        for (DateTime dateTime = beginOfDay; dateTime.compareTo(endOfDate) <= 0 ; dateTime = DateUtil.offsetHour(dateTime, 1)) {
            DateTime finalDateTime = dateTime;
            time.add(DateUtil.format(finalDateTime, "HH:00"));
            double hum = envList.stream().filter(env -> DateUtil.isSameDay(env.getUpdateTime(), finalDateTime) && NumEx.isNumber(env.getHum()))
                    .mapToDouble(item -> Double.valueOf(item.getHum()))
                    .average().orElse(0.0);
            hums.add(hum);
            double temp = envList.stream().filter(env -> DateUtil.isSameDay(env.getUpdateTime(), finalDateTime) && NumEx.isNumber(env.getTemp()))
                    .mapToDouble(item -> Double.valueOf(item.getTemp()))
                    .average().orElse(0.0);
            temps.add(temp);
            double light = envList.stream().filter(env -> DateUtil.isSameDay(env.getUpdateTime(), finalDateTime) && NumEx.isNumber(env.getLight()))
                    .mapToDouble(item -> Double.valueOf(item.getLight()))
                    .average().orElse(0.0);
            lights.add(light);
            double co2 = envList.stream().filter(env -> DateUtil.isSameDay(env.getUpdateTime(), finalDateTime) && NumEx.isNumber(env.getCo2()))
                    .mapToDouble(item -> Double.valueOf(item.getCo2()))
                    .average().orElse(0.0);
            co2s.add(co2);
        }
        HashMap<String, List> result = new HashMap<>(5);
        result.put("hum",hums);
        result.put("light",lights);
        result.put("temp",temps);
        result.put("co2",co2s);
        result.put("time",time);
        return Result.ok(result);
    }

}

