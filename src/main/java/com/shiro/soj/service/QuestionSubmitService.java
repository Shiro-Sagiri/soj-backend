package com.shiro.soj.service;

import com.shiro.soj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.shiro.soj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.soj.model.entity.User;

/**
 * @author Shiro
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2023-09-05 18:51:53
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {


    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest 添加问题DTO
     * @param loginUser               登录用户
     * @return 提交记录的id
     */
    Long questionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);
}
