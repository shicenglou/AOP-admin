package com.example.aopadmin.service;

import cn.hutool.core.date.DateTime;
import com.example.aopadmin.entity.AvgWater;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aopadmin.model.PowerTable;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DPH
 * @since 2023-03-14
 */
public interface AvgWaterService extends IService<AvgWater> {

    List<PowerTable> getTable(String sheetName, DateTime time, DateTime minTime);
}
