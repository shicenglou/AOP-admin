package com.example.aopadmin.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.aopadmin.entity.AvgEnvironment;
import com.example.aopadmin.mapper.AvgEnvironmentMapper;
import com.example.aopadmin.model.PowerTable;
import com.example.aopadmin.service.AvgEnvironmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aopadmin.util.NumEx;
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
        List<PowerTable> value = changeData("time",sheetName,avgEnvironmentMapper.getTable(sheetName,time,minTime));
        value.sort((o1, o2) -> DateUtil.compare(DateUtil.parse(o1.getX(),"yyyy-MM-dd HH:mm:ss"),DateUtil.parse(o2.getX(),"yyyy-MM-dd HH:mm:ss")));
        return value;
    }

    public List<PowerTable> changeData(String xName,String yName,List<HashMap<String,String>> data){
        List<PowerTable> value = new ArrayList<>();
        for (HashMap<String, String> datum : data) {

            String s = datum.get(yName);
            if (NumEx.isNumber(s)){
                PowerTable table = new PowerTable();
                table.setX(datum.get(xName));
                table.setY(Double.valueOf(s));
                value.add(table);
            }

        }
        return value;
    }
}
