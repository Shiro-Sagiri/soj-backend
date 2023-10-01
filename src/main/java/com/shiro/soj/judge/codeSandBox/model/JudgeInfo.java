package com.shiro.soj.judge.codeSandBox.model;

import lombok.Data;

@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗内存(kb)
     */
    private Long memory;

    /**
     * 消耗时间(ms)
     */
    private Long time;
}
