package com.hkex.hyperoj.judge.codesandbox;

import com.hkex.hyperoj.judge.codesandbox.impl.ExampleCodeSandBoxImpl;
import com.hkex.hyperoj.judge.codesandbox.impl.RemoteCodeSandBoxImpl;
import com.hkex.hyperoj.judge.codesandbox.impl.ThirdPartyCodeSandBoxImpl;

/**
 * 代码沙箱工厂
 */
public class CodeSandBoxFactory {

    /**
     * 根据类型获取对应的代码沙箱
     *
     * @param type
     * @return
     */
    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandBoxImpl();
            case "remote":
                return new RemoteCodeSandBoxImpl();
            case "thirdParty":
                return new ThirdPartyCodeSandBoxImpl();
            default:
                return new ExampleCodeSandBoxImpl();
        }
    }
}
