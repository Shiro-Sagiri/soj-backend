package com.shiro.soj.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.shiro.soj.enums.ErrorCode;
import com.shiro.soj.common.Result;
import com.shiro.soj.exception.BusinessException;
import com.shiro.soj.model.dto.user.UserLoginDTO;
import com.shiro.soj.model.dto.user.UserRegisterDTO;
import com.shiro.soj.model.dto.user.UserUpdateDTO;
import com.shiro.soj.model.entity.User;
import com.shiro.soj.model.vo.UserVO;
import com.shiro.soj.service.UserService;
import com.shiro.soj.utils.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/user")
@Api("user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private OSSClient ossClient;
    @Value("${oss.bucket-name}")
    private String bucketName;

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
        log.info("文件上传");

        //将MultipartFile转换成File
        File uploadFile;
        try {
            String originalFilename = file.getOriginalFilename();
            String[] filename = new String[0];
            if (originalFilename != null) {
                filename = originalFilename.split("\\.");
            }
            uploadFile = File.createTempFile(filename[0], filename[1]);
            file.transferTo(uploadFile);
            uploadFile.deleteOnExit();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_ERROR);
        }

        // 创建PutObjectRequest对象:参数 bucket 和 文件名 和 文件对象
        String filename = UUID.randomUUID() + file.getOriginalFilename();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, uploadFile);

        // 上传文件。
        ossClient.putObject(putObjectRequest);


        //获取url
        Date expiration = new Date(new Date().getTime() + 3600L * 1000 * 24 * 365 * 10); // 10年
        String url = ossClient.generatePresignedUrl(bucketName, filename, expiration).toString();

        //修改用户头像url
        User user = userService.getById(ThreadLocalUtil.getUserId());
        user.setUserAvatar(url);
        userService.updateById(user);

        // 关闭OSSClient
        ossClient.shutdown();
        return Result.success(url).setMessage("修改头像成功");
    }
}