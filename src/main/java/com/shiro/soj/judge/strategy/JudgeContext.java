package com.shiro.soj.judge.strategy;

import com.shiro.soj.model.dto.question.JudgeCase;
import com.shiro.soj.judge.codeSandBox.model.JudgeInfo;
import com.shiro.soj.model.entity.Question;
import com.shiro.soj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private Question question;

    private List<JudgeCase> judgeCaseList;

    private QuestionSubmit questionSubmit;
}
