package com.shiro.soj.judge.codeSandBox;

import com.shiro.soj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.shiro.soj.judge.codeSandBox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSendBox {


    /**
     * 执行代码
     * @param executeCodeRequest 请求参数
     * @return 执行结果
     */
     ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
