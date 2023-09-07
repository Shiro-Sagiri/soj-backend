package com.shiro.soj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.soj.exception.BusinessException;
import com.shiro.soj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.shiro.soj.model.entity.Question;
import com.shiro.soj.model.entity.QuestionSubmit;
import com.shiro.soj.model.entity.User;
import com.shiro.soj.service.QuestionService;
import com.shiro.soj.service.QuestionSubmitService;
import com.shiro.soj.mapper.QuestionSubmitMapper;
import enums.ErrorCode;
import enums.QuestionLanguageEnum;
import enums.QuestionSubmitStatusEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Shiro
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2023-09-05 18:51:53
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Override
    public Long questionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        Question question = questionService.getById(questionSubmitAddRequest.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setQuestionId(questionSubmitAddRequest.getQuestionId());
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        //判断编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionLanguageEnum languageEnum = QuestionLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言不合法");
        }
        questionSubmit.setLanguage(language);
        //设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean res = this.save(questionSubmit);
        if (!res) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "提交失败");
        }
        return questionSubmit.getId();
    }
}




