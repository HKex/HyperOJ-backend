package com.hkex.hyperoj.model.dto.questionsubmit;

import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制(ms)
     */
    private Long timeLimit;

    /**
     * 空间限制(kb)
     */
    private Long memoryLimit;

    /**
     * 栈空间限制(kb)
     */
    private Long stackLimit;
}
