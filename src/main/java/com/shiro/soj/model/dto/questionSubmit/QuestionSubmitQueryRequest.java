package com.shiro.soj.model.dto.questionSubmit;

import com.shiro.soj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 问题提交查询DTO
 *
 * @author Shiro
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8573793501776416699L;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目标签列表(JSON字符串)
     */
    private List<String> tags;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 判题状态
     */
    private Integer status;

    /**
     * 题目 ID
     */
    private Long questionId;

    /**
     * 用户 ID
     */
    private Long userId;

}
