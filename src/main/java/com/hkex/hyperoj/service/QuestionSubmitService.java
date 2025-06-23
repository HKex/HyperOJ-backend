package com.hkex.hyperoj.service;

import com.hkex.hyperoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.hkex.hyperoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hkex.hyperoj.model.entity.User;

/**
* @author HKex
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-06-23 20:18:27
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交信息
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

}
