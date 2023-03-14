package com.example.aopadmin.service.impl;

import cn.hutool.core.date.DateTime;
import com.example.aopadmin.entity.AvgEnvironment;
import com.example.aopadmin.entity.EnvironmentRecord;
import com.example.aopadmin.mapper.EnvironmentRecordMapper;
import com.example.aopadmin.model.PowerTable;
import com.example.aopadmin.service.EnvironmentRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aopadmin.util.NumOperaUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-06-19
 */
@Service
@RequiredArgsConstructor
public class EnvironmentRecordServiceImpl extends ServiceImpl<EnvironmentRecordMapper, EnvironmentRecord> implements EnvironmentRecordService {

    private final EnvironmentRecordMapper environmentRecordMapper;
    @Override
    public AvgEnvironment avgEnv(List<EnvironmentRecord> environmentRecords) {
        AvgEnvironment value = new AvgEnvironment();

        List<Double> hums = environmentRecords.stream().filter(p -> StringUtils.isNotBlank(p.getHum())).map(o -> Double.valueOf(o.getHum())).collect(Collectors.toList());
        if (!hums.isEmpty()) value.setHum(NumOperaUtil.doubleArrAverage(hums));
        hums.clear();

        List<Double> temps = environmentRecords.stream().filter(p -> StringUtils.isNotBlank(p.getTemp())).map(o -> Double.valueOf(o.getTemp())).collect(Collectors.toList());
        if (!temps.isEmpty()) value.setTemp(NumOperaUtil.doubleArrAverage(temps));
        temps.clear();

        List<Double> lights = environmentRecords.stream().filter(p -> StringUtils.isNotBlank(p.getLight())).map(o -> Double.valueOf(o.getLight())).collect(Collectors.toList());
        if (!lights.isEmpty()) value.setLight(NumOperaUtil.doubleArrAverage(lights));
        lights.clear();

        List<Double> co2s = environmentRecords.stream().filter(p -> StringUtils.isNotBlank(p.getCo2())).map(o -> Double.valueOf(o.getCo2())).collect(Collectors.toList());
        if (!co2s.isEmpty()) value.setCo2(NumOperaUtil.doubleArrAverage(co2s));
        co2s.clear();

        return value;
    }
}
