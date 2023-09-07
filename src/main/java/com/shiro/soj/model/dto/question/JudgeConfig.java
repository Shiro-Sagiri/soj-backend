package com.shiro.soj.model.dto.question;

import lombok.Data;

/**
 * 题目配置
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制 (ms)
     */
    private Long timeLimit;

    /**
     * 堆栈限制(kb)
     */
    private Long stackLimit;

    /**
     * 内存限制(kb)
     */
    private Long memoryLimit;
}
