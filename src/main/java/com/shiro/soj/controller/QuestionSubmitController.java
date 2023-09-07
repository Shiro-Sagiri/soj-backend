package com.shiro.soj.controller;

import com.shiro.soj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.shiro.soj.model.vo.UserVO;
import enums.ErrorCode;
import com.shiro.soj.common.Result;
import com.shiro.soj.exception.BusinessException;
import com.shiro.soj.model.entity.User;
import com.shiro.soj.service.QuestionSubmitService;
import com.shiro.soj.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 问题提交接口
 *
 * @author Shiro
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest 添加问题DTO
     * @return 提交记录的id
     */
    @PostMapping
    public Result<Long> questionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = UserVO.toUser(userService.getLoginUser());
        Long questionSubmitId = questionSubmitService.questionSubmit(questionSubmitAddRequest, loginUser);
        return Result.success(questionSubmitId);
    }

}
