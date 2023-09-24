package com.shiro.soj;

import com.shiro.soj.judge.codeSandBox.CodeSendBox;
import com.shiro.soj.judge.codeSandBox.CodeSendBoxFactory;
import com.shiro.soj.judge.codeSandBox.CodeSendBoxProxy;
import com.shiro.soj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.shiro.soj.judge.codeSandBox.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;

//@SpringBootTest
public class SendCodeBoxTest {

    @Value("${codeSandBox.type:remote}")
    private String type;

    @Test
    void executeCodeTest() {
        CodeSendBox codeSendBoxInstance = CodeSendBoxFactory.getCodeSendBoxInstance(type);
        codeSendBoxInstance = new CodeSendBoxProxy(codeSendBoxInstance);
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code("""
                        public class Main {
                            public static void main(String[] args) {
                                System.out.println("Hello World!");
                            }
                        }""")
                .language("java")
                .inputList(Arrays.asList("1 2", "3 4"))
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSendBoxInstance.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }
}
