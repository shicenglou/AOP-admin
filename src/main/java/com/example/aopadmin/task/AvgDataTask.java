package com.example.aopadmin.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.aopadmin.entity.AvgEnvironment;
import com.example.aopadmin.entity.AvgWater;
import com.example.aopadmin.entity.EnvironmentRecord;
import com.example.aopadmin.entity.WaterQualityRecord;
import com.example.aopadmin.service.AvgEnvironmentService;
import com.example.aopadmin.service.AvgWaterService;
import com.example.aopadmin.service.EnvironmentRecordService;
import com.example.aopadmin.service.WaterQualityRecordService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Time;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AvgDataTask {

    private final EnvironmentRecordService environmentRecordService;

    private final WaterQualityRecordService waterQualityRecordService;

    private final AvgEnvironmentService avgEnvironmentService;

    private final AvgWaterService avgWaterService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void avgDailyData() throws InterruptedException {

//        Thread.sleep(10);
        //整点执行数据录入，录入上小时均值
        dataToAvg(new DateTime());
    }

    public void dataToAvg(DateTime dateTime){
        DateTime start = DateUtil.offsetHour(dateTime, -1);

        List<EnvironmentRecord> environmentRecords = environmentRecordService.list(new LambdaQueryWrapper<EnvironmentRecord>()
                .ge(EnvironmentRecord::getTime, start)
                .le(EnvironmentRecord::getTime, dateTime));
        if (!environmentRecords.isEmpty()){
            AvgEnvironment avgEnvironment = environmentRecordService.avgEnv(environmentRecords);
            avgEnvironment.setTime(start);
            if (!avgEnvironment.isFail()) avgEnvironmentService.save(avgEnvironment);
        }

        List<WaterQualityRecord> waterQualityRecords = waterQualityRecordService.list(new LambdaQueryWrapper<WaterQualityRecord>()
                .ge(WaterQualityRecord::getTime, start)
                .le(WaterQualityRecord::getTime, dateTime));
        if (!waterQualityRecords.isEmpty()){
            AvgWater avgWater = waterQualityRecordService.avgWater(waterQualityRecords);
            avgWater.setTime(start);
            if (!avgWater.isFail()) avgWaterService.save(avgWater);
        }
    }
}
