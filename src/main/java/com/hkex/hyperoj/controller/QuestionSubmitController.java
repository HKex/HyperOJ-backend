package com.hkex.hyperoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkex.hyperoj.annotation.AuthCheck;
import com.hkex.hyperoj.common.BaseResponse;
import com.hkex.hyperoj.common.ErrorCode;
import com.hkex.hyperoj.common.ResultUtils;
import com.hkex.hyperoj.constant.UserConstant;
import com.hkex.hyperoj.exception.BusinessException;
import com.hkex.hyperoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.hkex.hyperoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.hkex.hyperoj.model.entity.QuestionSubmit;
import com.hkex.hyperoj.model.entity.User;
import com.hkex.hyperoj.model.vo.QuestionSubmitVO;
import com.hkex.hyperoj.service.QuestionSubmitService;
import com.hkex.hyperoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author HKex
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return bool
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        Long questionSubmitID = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitID);
    }

    /**
     * 分页获取列表（非管理员仅能看见公开信息）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                   HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        //从数据库中查询原始分页信息
        Page<QuestionSubmit> questionPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = userService.getLoginUser(request);
        //返回脱敏信息
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionPage, loginUser));
    }

}
