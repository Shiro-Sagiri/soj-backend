package com.shiro.soj.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.shiro.soj.enums.ErrorCode;
import com.shiro.soj.exception.BusinessException;
import lombok.Data;

/**
 * 题目提交
 * @TableName question_submit
 */
@TableName(value ="question_submit")
@Data
public class QuestionSubmit implements Serializable {


    /**
     * id
     */
    private Long id;

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

    /**
     * 判题信息(JSON对象)
     */
    private String judgeInfo;

    /**
     * 判题状态(0-待判题,1-判题中,2-成功,3-失败)
     */
    private Integer status;

    /**
     * 提交用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 获取排序字段
     * @param sortField 排序字段
     * @return 排序字段对应的值
     */
    public Object getSortField(String sortField) {
        return switch (sortField) {
            case "id" -> this.getId();
            case "questionId" -> this.getQuestionId();
            case "language" -> this.getLanguage();
            case "status" -> this.getStatus();
            case "userId" -> this.getUserId();
            case "createTime" -> this.getCreateTime();
            case "updateTime" -> this.getUpdateTime();
            default -> throw new BusinessException(ErrorCode.PARAMS_ERROR, "QuestionSubmit类不存在字段名：" + sortField);
        };
    }

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}