package com.hkex.hyperoj.judge;

import cn.hutool.json.JSONUtil;
import com.hkex.hyperoj.common.ErrorCode;
import com.hkex.hyperoj.exception.BusinessException;
import com.hkex.hyperoj.judge.codesandbox.CodeSandBox;
import com.hkex.hyperoj.judge.codesandbox.CodeSandBoxFactory;
import com.hkex.hyperoj.judge.codesandbox.CodeSandBoxProxy;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.hkex.hyperoj.judge.strategy.JudgeContext;
import com.hkex.hyperoj.model.dto.question.JudgeCase;
import com.hkex.hyperoj.model.dto.question.JudgeInfo;
import com.hkex.hyperoj.model.entity.Question;
import com.hkex.hyperoj.model.entity.QuestionSubmit;
import com.hkex.hyperoj.model.enums.QuestionSubmitStatusEnum;
import com.hkex.hyperoj.service.QuestionService;
import com.hkex.hyperoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Value("${codesandbox.type:example}")
    private String type;

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(Long questionSubmitId) {
        // 传入id,获取题目
        // 调用沙箱，获取结果
        // 根据执行结果，返回题目判题状态&信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        //若不为等待中 防止重复执行
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "已在判题");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmit.getId());
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新数据失败");
        }

        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCasestr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCasestr, JudgeCase.class);
        //获取输入用例
        List<String> inputs = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        //执行代码沙箱
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputs)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(request);

        List<String> outputList = executeCodeResponse.getOutputList();
        //根据输出判断
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setInputList(inputs);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        //基于context使用策略
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        //更新数据库判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmit.getId());
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新数据失败");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}
