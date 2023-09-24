package com.shiro.soj.judge.codeSandBox;

import com.shiro.soj.judge.codeSandBox.impl.ExampleCodeSendBox;
import com.shiro.soj.judge.codeSandBox.impl.RemoteCodeSendBox;
import com.shiro.soj.judge.codeSandBox.impl.ThirdPartyCodeSendBox;

/**
 * 代码沙箱工厂(根据字符串创建对应的代码沙箱)
 */
public class CodeSendBoxFactory {

    public static CodeSendBox getCodeSendBoxInstance(String codeSendBoxType) {
        return switch (codeSendBoxType) {
            case "example" -> new ExampleCodeSendBox();
            case "remote" -> new RemoteCodeSendBox();
            case "thirdParty" -> new ThirdPartyCodeSendBox();
            default -> new ExampleCodeSendBox(); //默认返回示例代码沙箱
        };
    }
}
