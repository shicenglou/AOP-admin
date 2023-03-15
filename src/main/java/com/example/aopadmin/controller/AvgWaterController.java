package com.example.aopadmin.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.aopadmin.model.PowerTable;
import com.example.aopadmin.model.Result;
import com.example.aopadmin.service.AvgWaterService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
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
@RequestMapping("/avgWater")
@RequiredArgsConstructor
public class AvgWaterController {
    private final AvgWaterService waterService;

    private final List<String> cols = Arrays.asList("ph","do","temp");

    @GetMapping("/table/{sheetName}")
    public Result getTable(@PathVariable(name = "sheetName") String sheetName, @RequestParam(required = false) String dateTime){
        if (cols.contains(sheetName)){
            DateTime parse = StringUtils.isNotBlank(dateTime) ? DateUtil.parse(dateTime, "") : new DateTime();
            DateTime minTime = DateUtil.offsetDay(parse, -1);
            List<PowerTable> powers = waterService.getTable(sheetName,parse,minTime);
            if (powers.isEmpty()) return Result.error("该时间无数据");
            return Result.ok(powers);
        }
        return Result.error("y 值错误,或时间格式非法");
    }
}

