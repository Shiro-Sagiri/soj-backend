package com.shiro.soj.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiro.soj.model.dto.question.QuestionQueryRequest;
import com.shiro.soj.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.soj.model.vo.QuestionVO;

/**
* @author Shiro
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-09-05 18:50:43
*/
public interface QuestionService extends IService<Question> {

    /**
     * 验证题目
     * @param question 题目
     * @param add 是否为添加
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest  查询条件
     * @return 查询条件构造器
     */
    LambdaQueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取问题封装
     *
     * @param question 问题
     * @return 问题封装
     */
    QuestionVO getQuestionVO(Question question);

    /**
     * 分页获取问题封装
     *
     * @param questionPage 问题分页
     * @return 问题封装分页
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage);
}
