package com.hkex.hyperoj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题信息枚举
 *
 * @author HKex
 */
public enum QuestionSubmitJudgeInfoEnum {

    ACCEPTED("成功", "ACCEPTED"),

    WRONG_ANSWER("答案错误", "WRONG_ANSWER"),

    COMPLIED_ERROR("编译错误", "COMPLIED_ERROR"),

    MEMORY_LIMIT_EXCEEDED("内存溢出", "MEMORY_LIMIT_EXCEEDED"),

    TIME_LIMIT_EXCEEDED("超时", "TIME_LIMIT_EXCEEDED"),

    PRESENTATION_ERROR("展示错误", "PRESENTATION_ERROR"),

    OUTPUT_LIMIT_EXCEEDED("输出溢出", "OUTPUT_LIMIT_EXCEEDED"),

    WAITING("等待", "WAITING"),

    DANGEROUS_OPERATION("危险操作", "DANGEROUS_OPERATION"),

    RUNTIME_ERROR("运行时错误", "RUNTIME_ERROR"),

    SYSTEM_ERROR("系统错误", "SYSTEM_ERROR");

    private final String text;

    private final String value;

    QuestionSubmitJudgeInfoEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static QuestionSubmitJudgeInfoEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestionSubmitJudgeInfoEnum anEnum : QuestionSubmitJudgeInfoEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
