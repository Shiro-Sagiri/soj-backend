package com.shiro.soj.model.dto.questionSubmit;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 提交问题DTO
 *
 * @author Shiro
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}