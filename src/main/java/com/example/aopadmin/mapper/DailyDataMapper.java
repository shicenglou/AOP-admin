package com.example.aopadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.aopadmin.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DailyDataMapper extends BaseMapper<User> {
}
