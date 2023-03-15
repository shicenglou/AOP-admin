package com.example.aopadmin.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.aopadmin.entity.AvgWater;
import com.example.aopadmin.mapper.AvgWaterMapper;
import com.example.aopadmin.model.PowerTable;
import com.example.aopadmin.service.AvgWaterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aopadmin.util.TableUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DPH
 * @since 2023-03-14
 */
@Service
@RequiredArgsConstructor
public class AvgWaterServiceImpl extends ServiceImpl<AvgWaterMapper, AvgWater> implements AvgWaterService {

    private final AvgWaterMapper waterMapper;

    @Override
    public List<PowerTable> getTable(String sheetName, DateTime time, DateTime minTime) {
        List<HashMap<String, Object>> table = waterMapper.getTable(sheetName, time, minTime);
        List<PowerTable> value = TableUtil.changeData("time",sheetName,table);
        value.sort((o1, o2) -> DateUtil.compare(DateUtil.parse(o1.getX(),"yyyy-MM-dd HH:mm:ss"),DateUtil.parse(o2.getX(),"yyyy-MM-dd HH:mm:ss")));
        return value;
    }
}
