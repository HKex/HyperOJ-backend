package com.hkex.hyperoj.judge.codesandbox.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.hkex.hyperoj.common.ErrorCode;
import com.hkex.hyperoj.exception.BusinessException;
import com.hkex.hyperoj.judge.codesandbox.CodeSandBox;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;

/**
 * 远程代码沙箱（实际使用）
 */
public class RemoteCodeSandBoxImpl implements CodeSandBox {
    //鉴权请求头&密钥
    private static final String AUTH_REQUEST_HEADER = "Auth";

    private static final String AUTH_REQUEST_SECRET = "itsmygo";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8090/executeCode";
        String executeCodeRequestSTR = JSONUtil.toJsonStr(executeCodeRequest);
        String responseBody = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(executeCodeRequestSTR)
                .execute()
                .body();
        if(StringUtils.isBlank(responseBody)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"execute error ,message :"+ responseBody);
        }
        return JSONUtil.toBean(responseBody, ExecuteCodeResponse.class);
    }
}
