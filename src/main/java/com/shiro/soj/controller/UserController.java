package com.shiro.soj.controller;

import com.shiro.soj.enums.ErrorCode;
import com.shiro.soj.common.Result;
import com.shiro.soj.exception.BusinessException;
import com.shiro.soj.model.dto.user.UserLoginDTO;
import com.shiro.soj.model.dto.user.UserRegisterDTO;
import com.shiro.soj.model.dto.user.UserUpdateDTO;
import com.shiro.soj.model.vo.UserVO;
import com.shiro.soj.service.UserService;
import com.shiro.soj.utils.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/user")
@Api("user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<Long> userRegister(@RequestBody UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO == null || StringUtils.isAnyBlank(userRegisterDTO.getUserAccount(), userRegisterDTO.getUserPassword(), userRegisterDTO.getCheckPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Long userId = userService.userRegister(userRegisterDTO);
        return Result.success(userId).setMessage("注册成功");
    }

    @PostMapping("/login")
    @ApiOperation("用户登入")
    public Result<String> userLogin(@RequestBody UserLoginDTO userLoginDTO) {
        if (userLoginDTO == null || StringUtils.isAnyBlank(userLoginDTO.getUserAccount(), userLoginDTO.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        String token = userService.userLogin(userLoginDTO);
        return Result.success(token).setMessage("登录成功");
    }

    @GetMapping("/getLoginUser")
    @ApiOperation("获取登录用户信息")
    public Result<UserVO> getLoginUser() {
        UserVO userVO = userService.getLoginUser();
        return Result.success(userVO).setMessage("获取成功");
    }

    /**
     * 用户信息更新
     *
     * @param userUpdateDTO 用户信息更新DTO
     * @return 用户视图对象
     */
    @PutMapping
    @ApiOperation("修改用户个人信息")
    public Result<Long> updateUserInfo(@RequestBody UserUpdateDTO userUpdateDTO) {
        log.info("用户信息更新");
        if (userUpdateDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (userUpdateDTO.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!userUpdateDTO.getId().equals(ThreadLocalUtil.getUserId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Long userId = userService.updateUser(userUpdateDTO);
        return Result.success(userId).setMessage("账号信息更新成功");
    }

    @PostMapping("/updateAvatar")
    @ApiOperation("修改头像")
    public Result<String> updateUserAvatar(@RequestBody MultipartFile file) {
        log.info("文件上传TODO");
        //TODO 更换版本后，文件上传功能暂时不可用
//        String url;
//        try {
//            url = aliOSSUtils.upload(file);
//        } catch (IOException e) {
//            throw new BusinessException(ErrorCode.FILE_UPLOAD_ERROR);
//        }
//        User user = userService.getById(ThreadLocalUtil.getUserId());
//        if (user == null) {
//            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
//        }
//        user.setUserAvatar(url);
//        userService.updateById(user);
//        return Result.success(url);
        return Result.success("接口暂时不可用");
    }
}