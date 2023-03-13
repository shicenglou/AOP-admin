package com.example.aopadmin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aopadmin.entity.EnviromentRecord;

import com.example.aopadmin.mapper.EnviromentMapper;
import com.example.aopadmin.service.EnviromentService;
import org.springframework.stereotype.Service;

@Service
public class EnviromentServiceImpl extends ServiceImpl<EnviromentMapper, EnviromentRecord> implements EnviromentService {
}
