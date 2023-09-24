package com.shiro.soj.judge;

import com.shiro.soj.judge.strategy.DefaultJudgeStrategy;
import com.shiro.soj.judge.strategy.JavaLanguageJudgeStrategy;
import com.shiro.soj.judge.strategy.JudgeContext;
import com.shiro.soj.judge.strategy.JudgeStrategy;
import com.shiro.soj.model.dto.questionSubmit.JudgeInfo;
import com.shiro.soj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理(简化判题服务)
 */
@Service
public class JudgeManager {

    /**
     * 判题
     * @param judgeContext 上下文
     * @return 判题结果
     */
    JudgeInfo judge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.judge(judgeContext);
    }
}
