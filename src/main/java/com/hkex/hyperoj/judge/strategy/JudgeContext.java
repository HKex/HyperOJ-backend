package com.hkex.hyperoj.judge.strategy;

import com.hkex.hyperoj.model.dto.question.JudgeCase;
import com.hkex.hyperoj.judge.codesandbox.model.JudgeInfo;
import com.hkex.hyperoj.model.entity.Question;
import com.hkex.hyperoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 策略上下文
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> InputList;

    private List<String> OutputList;

    private List<JudgeCase> JudgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
