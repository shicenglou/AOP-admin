package com.example.aopadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aopadmin.entity.User;
import com.example.aopadmin.mapper.DailyDataMapper;
import com.example.aopadmin.service.DailyDataService;
import org.springframework.stereotype.Service;

@Service
public class DailyDataServiceImpl extends ServiceImpl<DailyDataMapper, User> implements DailyDataService {
}
