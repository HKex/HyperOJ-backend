package com.hkex.hyperoj.judge.codesandbox;

import com.hkex.hyperoj.judge.codesandbox.impl.ExampleCodeSandBoxImpl;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.hkex.hyperoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.hkex.hyperoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeSandBoxTest {

    @Value("${codesandbox.type:example}")
    private String type;

    @Test
    void executeCode() {
        System.out.println(type);
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        String Language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputs = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                .code("public class Main {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        int a = Integer.parseInt(args[0]);\n" +
                        "        int b = Integer.parseInt(args[1]);\n" +
                        "        System.out.println(\"结果:\" + (a + b));\n" +
                        "    }\n" +
                        "}")
                .language(Language)
                .inputList(inputs)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(request);
        System.out.println(executeCodeResponse);
    }

    @Test
    void executeCodeByProxy() {
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String Language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputs = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                .code("int main(){}")
                .language(Language)
                .inputList(inputs)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(request);
    }

    public static void main(String[] args) {
    }
}