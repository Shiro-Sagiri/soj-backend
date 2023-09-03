package com.shiro.soj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.soj.mapper.UserMapper;
import com.shiro.soj.model.entity.User;
import com.shiro.soj.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
