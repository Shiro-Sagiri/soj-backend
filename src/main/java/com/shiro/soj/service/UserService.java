package com.shiro.soj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.soj.model.dto.user.UserLoginDTO;
import com.shiro.soj.model.dto.user.UserRegisterDTO;
import com.shiro.soj.model.entity.User;
import com.shiro.soj.model.vo.UserVO;

public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userRegisterDTO 用户注册信息
     * @return 用户id
     */
    Long userRegister(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     * @param userLoginDTO 用户登录信息
     * @return 用户信息
     */
    String userLogin(UserLoginDTO userLoginDTO);

    /**
     * 获取登录用户信息
     * @return 用户信息
     */
    UserVO getLoginUser();
}
