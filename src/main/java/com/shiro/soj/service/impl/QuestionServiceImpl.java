package com.shiro.soj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.soj.enums.ErrorCode;
import com.shiro.soj.exception.BusinessException;
import com.shiro.soj.exception.ThrowUtils;
import com.shiro.soj.model.dto.question.QuestionQueryRequest;
import com.shiro.soj.model.entity.Question;
import com.shiro.soj.model.entity.User;
import com.shiro.soj.model.vo.QuestionVO;
import com.shiro.soj.model.vo.UserVO;
import com.shiro.soj.service.QuestionService;
import com.shiro.soj.mapper.QuestionMapper;
import com.shiro.soj.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Shiro
 * @description 针对表【question(题目)】的数据库操作Service实现
 * @createDate 2023-09-05 18:50:43
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    @Resource
    private UserService userService;

    /**
     * 校验题目是否合法
     *
     * @param question 题目
     * @param add      是否为添加
     */
    @Override
    public void validQuestion(Question question, boolean add) {
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String title = question.getTitle();
        String content = question.getContent();
        String tags = question.getTags();
        String answer = question.getAnswer();
        String judgeConfig = question.getJudgeConfig();
        String judgeCase = question.getJudgeCase();


        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, content, tags, judgeCase, judgeConfig, answer), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
        if (StringUtils.isNotBlank(answer) && answer.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "答案过长");
        }
        if (StringUtils.isNotBlank(judgeConfig) && judgeConfig.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "判题配置过长");
        }
        if (StringUtils.isNotBlank(judgeCase) && judgeCase.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "判题用例过长");
        }
    }

    /**
     * 获取查询包装类
     *
     * @param questionQueryRequest 查询条件
     * @return 查询包装类
     */
    @Override
    public LambdaQueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        if (questionQueryRequest == null) {
            return wrapper;
        }


        Long id = questionQueryRequest.getId();
        String title = questionQueryRequest.getTitle();
        String content = questionQueryRequest.getContent();
        List<String> tags = questionQueryRequest.getTags();
        String answer = questionQueryRequest.getAnswer();
        Long userId = questionQueryRequest.getUserId();

        // 拼接查询条件
        wrapper.like(StringUtils.isNotBlank(title), Question::getTitle, title);
        wrapper.like(StringUtils.isNotBlank(answer), Question::getAnswer, answer);
        wrapper.like(StringUtils.isNotBlank(content), Question::getContent, content);
        if (CollectionUtils.isNotEmpty(tags)) {
            for (String tag : tags) {
                wrapper.like(Question::getTags, "\"" + tag + "\"");
            }
        }
        wrapper.eq(ObjectUtils.isNotEmpty(id), Question::getId, id);
        wrapper.eq(ObjectUtils.isNotEmpty(userId), Question::getUserId, userId);
        wrapper.eq(Question::getIsDelete, false);
        return wrapper;
    }

    @Override
    public QuestionVO getQuestionVO(Question question) {
        QuestionVO questionVO = QuestionVO.objToVo(question);
        // 1. 关联查询用户信息
        Long userId = question.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        questionVO.setUserVO(userVO);
        return questionVO;
    }

    @Override
    public Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage) {
        List<Question> questionList = questionPage.getRecords();
        Page<QuestionVO> questionVOPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if (CollectionUtils.isEmpty(questionList)) {
            return questionVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionList.stream().map(Question::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        List<QuestionVO> questionVOList = questionList.stream().map(question -> {
            QuestionVO questionVO = QuestionVO.objToVo(question);
            Long userId = question.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionVO.setUserVO(userService.getUserVO(user));
            return questionVO;
        }).collect(Collectors.toList());
        questionVOPage.setRecords(questionVOList);
        return questionVOPage;
    }


}




