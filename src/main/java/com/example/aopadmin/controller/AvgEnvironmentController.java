package com.example.aopadmin.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.aopadmin.mapper.AvgEnvironmentMapper;
import com.example.aopadmin.model.PowerTable;
import com.example.aopadmin.model.Result;
import com.example.aopadmin.service.AvgEnvironmentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DPH
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/avgEnv")
@RequiredArgsConstructor
public class AvgEnvironmentController {

    private final AvgEnvironmentService avgEnvironmentService;

    private final AvgEnvironmentMapper mapper;

    private final List<String> cols = Arrays.asList("temp","hum","co2","light");

    @GetMapping("/table/{sheetName}")
    public Result getTable(@PathVariable(value = "sheetName") String sheetName , @RequestParam(required = false) String dataTime){
        DateTime time = null;
        if (StringUtils.isNotBlank(dataTime)){
            time = DateUtil.parse(dataTime,"yyyy-MM-dd HH:mm:ss");
        }else {
            time = new DateTime();
        }
        DateTime minTime = DateUtil.offsetDay(time, -1);
        if (cols.contains(sheetName)){
            List<PowerTable> powers = avgEnvironmentService.getTable(sheetName,time,minTime);
            if (powers.isEmpty()) return Result.error("该时间无数据");
            return Result.ok(powers);
        }
        return Result.error("y 值错误,或时间格式非法");
    }

    @GetMapping("test")
    public Result getCount(){
        return Result.ok(mapper.getAllCount());
    }

}

