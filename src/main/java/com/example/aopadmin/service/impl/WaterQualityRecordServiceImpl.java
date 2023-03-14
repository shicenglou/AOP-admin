package com.example.aopadmin.service.impl;

import com.example.aopadmin.entity.AvgWater;
import com.example.aopadmin.entity.WaterQualityRecord;
import com.example.aopadmin.mapper.WaterQualityRecordMapper;
import com.example.aopadmin.service.WaterQualityRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aopadmin.util.NumOperaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-06-24
 */
@Service
public class WaterQualityRecordServiceImpl extends ServiceImpl<WaterQualityRecordMapper, WaterQualityRecord> implements WaterQualityRecordService {
    @Override
    public AvgWater avgWater(List<WaterQualityRecord> waterQualityRecords) {
        AvgWater value = new AvgWater();

        List<Double> temps = waterQualityRecords.stream().filter(p -> StringUtils.isNotBlank(p.getTemp())).map(o -> Double.valueOf(o.getTemp())).collect(Collectors.toList());
        if (!temps.isEmpty()) value.setTemp(NumOperaUtil.doubleArrAverage(temps));
        temps.clear();

        List<Double> do1s = waterQualityRecords.stream().filter(p -> StringUtils.isNotBlank(p.getDo1())).map(o -> Double.valueOf(o.getDo1())).collect(Collectors.toList());
        if (!do1s.isEmpty()) value.setDo1(NumOperaUtil.doubleArrAverage(do1s));
        do1s.clear();

        List<Double> phs = waterQualityRecords.stream().filter(p -> StringUtils.isNotBlank(p.getPh())).map(o -> Double.valueOf(o.getPh())).collect(Collectors.toList());
        if (!phs.isEmpty()) value.setPh(NumOperaUtil.doubleArrAverage(phs));
        phs.clear();
        return value;
    }
}
