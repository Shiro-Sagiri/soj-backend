package com.shiro.soj.judge.codeSandBox.impl;

import com.shiro.soj.judge.codeSandBox.CodeSendBox;
import com.shiro.soj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.shiro.soj.judge.codeSandBox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 远程代码沙箱(实际调用接口的沙箱)
 */
@Slf4j
public class RemoteCodeSendBox implements CodeSendBox {


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("远程代码沙箱执行代码");
        return null;
    }
}
