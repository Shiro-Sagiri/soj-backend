package com.shiro.soj.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑请求
 *
 * @author Shiro
 */
@Data
public class QuestionEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 题目答案
     */
    private String answer;

    private static final long serialVersionUID = 1L;
}