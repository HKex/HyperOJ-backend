package com.hkex.hyperoj.judge.codesandbox.impl;

import com.hkex.hyperoj.judge.codesandbox.CodeSandBox;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱（实际使用）
 */
public class RemoteCodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
