package com.example.aopadmin.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.aopadmin.entity.AvgEnvironment;
import com.example.aopadmin.mapper.AvgEnvironmentMapper;
import com.example.aopadmin.model.PowerTable;
import com.example.aopadmin.service.AvgEnvironmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aopadmin.util.NumEx;
import com.example.aopadmin.util.TableUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

import java.util.ArrayList;
import java.util.Comparator;
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
public class AvgEnvironmentServiceImpl extends ServiceImpl<AvgEnvironmentMapper, AvgEnvironment> implements AvgEnvironmentService {

    private final AvgEnvironmentMapper avgEnvironmentMapper;
    @Override
    public List<PowerTable> getTable(String sheetName, DateTime time, DateTime minTime) {
        List<HashMap<String, Object>> table = avgEnvironmentMapper.getTable(sheetName, time, minTime);
        List<PowerTable> value = TableUtil.changeData("time",sheetName,table);
        value.sort((o1, o2) -> DateUtil.compare(DateUtil.parse(o1.getX(),"yyyy-MM-dd HH:mm:ss"),DateUtil.parse(o2.getX(),"yyyy-MM-dd HH:mm:ss")));
        return value;
    }

}
