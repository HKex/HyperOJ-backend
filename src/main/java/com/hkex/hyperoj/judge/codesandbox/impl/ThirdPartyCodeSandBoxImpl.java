package com.hkex.hyperoj.judge.codesandbox.impl;

import com.hkex.hyperoj.judge.codesandbox.CodeSandBox;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱(调用他人API)
 */
public class ThirdPartyCodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
