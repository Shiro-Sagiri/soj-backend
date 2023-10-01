package com.shiro.soj.judge.strategy;

import com.shiro.soj.judge.codeSandBox.model.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 判题
     * @param judgeContext 上下文
     * @return 判题结果
     */
    JudgeInfo judge(JudgeContext judgeContext);
}
