package com.hkex.hyperoj.judge;

import com.hkex.hyperoj.model.entity.QuestionSubmit;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 判题服务
 */
public interface JudgeService {

    QuestionSubmit doJudge(Long questionSubmitId);

    /**
     * 处理沙箱回调判题结果
     */
    void handleExecuteResponse(Long questionSubmitId, ExecuteCodeResponse response);
}
