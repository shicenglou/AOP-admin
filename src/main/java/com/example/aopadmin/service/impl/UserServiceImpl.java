package com.example.aopadmin.service.impl;

import com.example.aopadmin.entity.User;
import com.example.aopadmin.mapper.UserMapper;
import com.example.aopadmin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-06-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
