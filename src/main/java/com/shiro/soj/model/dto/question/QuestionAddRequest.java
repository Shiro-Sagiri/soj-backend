package com.shiro.soj.model.dto.question;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 创建请求
 *
 * @author Shiro
 */
@Data
public class QuestionAddRequest implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表(JSON字符串)
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 判题配置(JSON对象) ---便于扩展,只需要改变内部的字段,而不是修改数据库

     {
     timeLimit:string
     stackLimit:string
     }
     */
    private JudgeConfig judgeConfig;

    /**
     * 判题用例(JSON对象)
     {
     input: string
     output:string
     }
     */
    private List<JudgeCase> judgeCase;

    @Serial
    private static final long serialVersionUID = 1L;
}