package com.shiro.soj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.soj.exception.BusinessException;
import com.shiro.soj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.shiro.soj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.shiro.soj.model.entity.Question;
import com.shiro.soj.model.entity.QuestionSubmit;
import com.shiro.soj.model.entity.User;
import com.shiro.soj.model.vo.QuestionSubmitVO;
import com.shiro.soj.service.QuestionService;
import com.shiro.soj.service.QuestionSubmitService;
import com.shiro.soj.mapper.QuestionSubmitMapper;
import com.shiro.soj.service.UserService;
import com.shiro.soj.utils.ThreadLocalUtil;
import com.shiro.soj.enums.ErrorCode;
import com.shiro.soj.enums.QuestionLanguageEnum;
import com.shiro.soj.enums.QuestionSubmitStatusEnum;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Shiro
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2023-09-05 18:51:53
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;
    @Resource
    private UserService userService;

    @Override
    public Long questionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        Question question = questionService.getById(questionSubmitAddRequest.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setQuestionId(questionSubmitAddRequest.getQuestionId());
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        //判断编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionLanguageEnum languageEnum = QuestionLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言不合法");
        }
        questionSubmit.setLanguage(language);
        //设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        questionSubmit.setUserId(ThreadLocalUtil.getUserId());
        boolean res = this.save(questionSubmit);
        if (!res) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "提交失败");
        }
        return questionSubmit.getId();
    }

    /**
     * 获取查询包装类
     *
     * @param questionSubmitQueryRequest 查询条件
     * @return 查询包装类
     */
    @Override
    public LambdaQueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        LambdaQueryWrapper<QuestionSubmit> wrapper = new LambdaQueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return wrapper;
        }

        // 1. 获取查询条件
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();

        // 拼接查询条件
        wrapper.eq(StringUtils.isNotBlank(language), QuestionSubmit::getLanguage, language);
        wrapper.eq(ObjectUtils.isNotEmpty(questionId), QuestionSubmit::getUserId, questionId);
        wrapper.eq(ObjectUtils.isNotEmpty(userId), QuestionSubmit::getUserId, userId);
        wrapper.eq(QuestionSubmitStatusEnum.getByValue(status) != null, QuestionSubmit::getStatus, status);
        wrapper.eq(QuestionSubmit::getIsDelete, false);
        return wrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        //脱敏(仅管理员和本人可见)
        Long userId = ThreadLocalUtil.getUserId();
        if (!userId.equals(questionSubmit.getUserId()) && !userService.isAdmin()) {
            questionSubmitVO.setCode(null);
        }
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> submitVOList = questionSubmitList.stream().map(this::getQuestionSubmitVO).toList();
        questionSubmitVOPage.setRecords(submitVOList);
        return questionSubmitVOPage;
    }
}




