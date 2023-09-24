package com.shiro.soj.judge;

import com.shiro.soj.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId 题目提交id
     * @return 判题结果
     */
    QuestionSubmit judge(Long questionSubmitId);
}
