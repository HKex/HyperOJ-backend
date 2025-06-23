package com.hkex.hyperoj.model.dto.question;

import lombok.Data;

/**
 * 题目配置
 */
@Data
public class JudgeInfo {

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
