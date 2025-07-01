package com.hkex.hyperoj.judge.strategy;

import com.hkex.hyperoj.judge.codesandbox.model.JudgeInfo;

/**
 * 评测策略
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
