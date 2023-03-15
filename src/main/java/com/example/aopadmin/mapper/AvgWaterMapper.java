package com.example.aopadmin.mapper;

import cn.hutool.core.date.DateTime;
import com.example.aopadmin.entity.AvgWater;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DPH
 * @since 2023-03-14
 */
@Mapper
public interface AvgWaterMapper extends BaseMapper<AvgWater> {

    List<HashMap<String, Object>> getTable(String sheetName, DateTime time, DateTime minTime);
}
