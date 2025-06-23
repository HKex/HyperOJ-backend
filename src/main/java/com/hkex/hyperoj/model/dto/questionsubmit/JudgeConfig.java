package com.hkex.hyperoj.model.dto.questionsubmit;

import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeConfig {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗时间(ms)
     */
    private Long time;

    /**
     * 消耗空间(kb)
     */
    private Long memory;
}
