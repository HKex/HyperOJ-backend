package com.hkex.hyperoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.hkex.hyperoj.model.dto.question.JudgeCase;
import com.hkex.hyperoj.judge.codesandbox.model.JudgeInfo;
import com.hkex.hyperoj.model.dto.questionsubmit.JudgeConfig;
import com.hkex.hyperoj.model.entity.Question;
import com.hkex.hyperoj.model.enums.QuestionSubmitJudgeInfoEnum;

import java.util.List;

public class JavaJudgeStrategy implements JudgeStrategy {
    /**
     * 默认判题策略
     *
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();

        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(judgeInfo.getMemory());
        judgeInfoResponse.setTime(judgeInfo.getTime());

        QuestionSubmitJudgeInfoEnum judgeInfoEnum = QuestionSubmitJudgeInfoEnum.ACCEPTED;

        //判断运行结果是否正确
        if (outputList.size() != inputList.size()) {
            judgeInfoEnum = QuestionSubmitJudgeInfoEnum.WRONG_ANSWER;
            return null;
        }
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoEnum = QuestionSubmitJudgeInfoEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
                return judgeInfoResponse;
            }
        }

        //判断题目限制
        //java额外执行 10s
        Long JAVA_EXTRA_TIME_COST = 10000L;
        Long time = judgeInfo.getTime();
        Long memory = judgeInfo.getMemory();
        String judgeConfigstr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigstr, JudgeConfig.class);
        if ( memory > judgeConfig.getMemoryLimit()) {
            judgeInfoEnum = QuestionSubmitJudgeInfoEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
            return judgeInfoResponse;
        }
        if (time - JAVA_EXTRA_TIME_COST > judgeConfig.getTimeLimit()) {
            judgeInfoEnum = QuestionSubmitJudgeInfoEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
            return judgeInfoResponse;
        }


        judgeInfoResponse.setMessage(judgeInfoEnum.getValue());

        return judgeInfoResponse;
    }
}
