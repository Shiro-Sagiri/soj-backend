package com.shiro.soj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiro.soj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.shiro.soj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.shiro.soj.model.entity.QuestionSubmit;
import com.shiro.soj.model.vo.QuestionSubmitVO;
import com.shiro.soj.model.vo.UserVO;
import com.shiro.soj.enums.ErrorCode;
import com.shiro.soj.common.Result;
import com.shiro.soj.exception.BusinessException;
import com.shiro.soj.model.entity.User;
import com.shiro.soj.service.QuestionSubmitService;
import com.shiro.soj.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "提交题目", description = "提交题目")
    public Result<Long> questionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = UserVO.toUser(userService.getLoginUser());
        Long questionSubmitId = questionSubmitService.questionSubmit(questionSubmitAddRequest, loginUser);
        return Result.success(questionSubmitId).setMessage("提交成功");
    }


    /**
     * 分页获取提交记录
     * 普通用户只能查到非代码的公开信息
     *
     * @param questionSubmitQueryRequest 查询条件
     * @return 分页的提交记录
     */
    @Operation(summary = "分页获取提交记录", description = "分页获取提交记录")
    @PostMapping("/page")
    public Result<Page<QuestionSubmitVO>> getQuestionSubmitPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        if (questionSubmitQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (questionSubmitQueryRequest.getTags() != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //从数据库中查询原始信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.pageQuery(questionSubmitQueryRequest);
        return Result.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage));
    }
}
