package com.example.aopadmin.mapper;

import cn.hutool.core.date.DateTime;
import com.example.aopadmin.entity.AvgEnvironment;
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
public interface AvgEnvironmentMapper extends BaseMapper<AvgEnvironment> {

    List<HashMap<String, String>> getTable(String sheetName, DateTime time, DateTime minTime);
}
