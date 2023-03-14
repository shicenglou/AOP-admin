package com.example.aopadmin.service;

import com.example.aopadmin.entity.AvgWater;
import com.example.aopadmin.entity.WaterQualityRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-06-24
 */
public interface WaterQualityRecordService extends IService<WaterQualityRecord> {

    AvgWater avgWater(List<WaterQualityRecord> waterQualityRecords);
}
