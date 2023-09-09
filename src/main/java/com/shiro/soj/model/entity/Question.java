package com.shiro.soj.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.shiro.soj.enums.ErrorCode;
import com.shiro.soj.exception.BusinessException;
import lombok.Data;

/**
 * 题目
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * id
     */
    private Long id;

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
    private String tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置(JSON对象) ---便于扩展,只需要改变内部的字段,而不是修改数据库

{
  timeLimit:string
  stackLimit:string
}
     */
    private String judgeConfig;

    /**
     * 判题用例(JSON对象)
{
   input: string
   output:string
}
     */
    private String judgeCase;

    /**
     * 创建题目的用户id
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
     * 逻辑删除
     */
    private Integer isDelete;

    /**
     * 排序字段
     *
     * @param fieldName 字段名
     * @return 排序字段
     */
    public Object getSortField(String fieldName) {
        return switch (fieldName) {
            case "id" -> this.id;
            // 添加其他字段的处理逻辑
            case "submitNum" -> this.submitNum;
            case "acceptedNum" -> this.acceptedNum;
            case "createTime" -> this.createTime;
            case "updateTime" -> this.updateTime;
            default -> throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的排序字段：" + fieldName);
        };
    }

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}