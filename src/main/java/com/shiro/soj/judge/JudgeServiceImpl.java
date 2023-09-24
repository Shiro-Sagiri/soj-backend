package com.shiro.soj.judge;

import cn.hutool.json.JSONUtil;
import com.shiro.soj.enums.ErrorCode;
import com.shiro.soj.enums.QuestionSubmitStatusEnum;
import com.shiro.soj.exception.BusinessException;
import com.shiro.soj.judge.codeSandBox.CodeSendBox;
import com.shiro.soj.judge.codeSandBox.CodeSendBoxFactory;
import com.shiro.soj.judge.codeSandBox.CodeSendBoxProxy;
import com.shiro.soj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.shiro.soj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.shiro.soj.judge.strategy.JudgeContext;
import com.shiro.soj.model.dto.question.JudgeCase;
import com.shiro.soj.model.dto.questionSubmit.JudgeInfo;
import com.shiro.soj.model.entity.Question;
import com.shiro.soj.model.entity.QuestionSubmit;
import com.shiro.soj.service.QuestionService;
import com.shiro.soj.service.QuestionSubmitService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Value("${codeSendBox.type}")
    private String type;

    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private JudgeManager judgeManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestionSubmit judge(Long questionSubmitId) {
        //非空校验
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //更改题目提交状态为判题中
        if (!Objects.equals(questionSubmit.getStatus(), QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中,或已判题完成");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmit.getId());
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean success = questionSubmitService.updateById(questionSubmitUpdate);
        if (!success) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        //调用代码沙箱
        CodeSendBox codeSendBoxInstance = CodeSendBoxFactory.getCodeSendBoxInstance(type);
        codeSendBoxInstance = new CodeSendBoxProxy(codeSendBoxInstance);
        List<JudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).toList();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(questionSubmit.getCode())
                .language(questionSubmit.getLanguage())
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSendBoxInstance.executeCode(executeCodeRequest);

        if (executeCodeResponse == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<String> outputList = executeCodeResponse.getOutputList();

        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeManager.judge(judgeContext);

        //修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmit.getId());
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        success = questionSubmitService.updateById(questionSubmitUpdate);
        if (!success) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return questionSubmitService.getById(questionSubmitId);
    }

}
