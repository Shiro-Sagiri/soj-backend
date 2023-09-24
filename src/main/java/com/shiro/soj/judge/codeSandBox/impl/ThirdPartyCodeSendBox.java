package com.shiro.soj.judge.codeSandBox.impl;

import com.shiro.soj.judge.codeSandBox.CodeSendBox;
import com.shiro.soj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.shiro.soj.judge.codeSandBox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 第三方代码沙箱(调用现成的代码沙箱)
 */
@Slf4j
public class ThirdPartyCodeSendBox implements CodeSendBox {


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("第三方代码沙箱执行代码");
        return null;
    }
}
