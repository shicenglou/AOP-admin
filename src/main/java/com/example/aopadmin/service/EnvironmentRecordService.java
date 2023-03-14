package com.example.aopadmin.service;

import com.example.aopadmin.entity.AvgEnvironment;
import com.example.aopadmin.entity.EnvironmentRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-06-19
 */
public interface EnvironmentRecordService extends IService<EnvironmentRecord> {

    AvgEnvironment avgEnv(List<EnvironmentRecord> environmentRecords);
}
