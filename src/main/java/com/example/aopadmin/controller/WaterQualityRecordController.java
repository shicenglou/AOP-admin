package com.example.aopadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

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

}

