package com.hkex.hyperoj.judge;

import com.hkex.hyperoj.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    QuestionSubmit doJudge(Long questionSubmitId);
}
