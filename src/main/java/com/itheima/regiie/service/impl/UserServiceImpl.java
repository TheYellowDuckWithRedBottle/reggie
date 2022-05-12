package com.itheima.regiie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.regiie.entity.User;
import com.itheima.regiie.mapper.UserMapper;
import com.itheima.regiie.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
}
