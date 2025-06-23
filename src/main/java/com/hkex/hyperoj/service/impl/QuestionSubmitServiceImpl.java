package com.hkex.hyperoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkex.hyperoj.common.ErrorCode;
import com.hkex.hyperoj.exception.BusinessException;
import com.hkex.hyperoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.hkex.hyperoj.model.entity.Post;
import com.hkex.hyperoj.model.entity.QuestionSubmit;
import com.hkex.hyperoj.model.entity.User;
import com.hkex.hyperoj.model.enums.QuestionSubmitLanguageEnum;
import com.hkex.hyperoj.model.enums.QuestionSubmitStatusEnum;
import com.hkex.hyperoj.service.PostService;
import com.hkex.hyperoj.service.QuestionSubmitService;
import com.hkex.hyperoj.mapper.QuestionSubmitMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author HKex
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2025-06-23 20:18:27
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{

    @Resource
    private PostService questionService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {

        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Post question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //校验编程语言是否合理
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum enumByValue = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (enumByValue == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        // 是否有题目
        long userId = loginUser.getId();

        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean result = this.save(questionSubmit);
        if(result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据输入失败");
        }
        return questionSubmit.getId();
    }
}




