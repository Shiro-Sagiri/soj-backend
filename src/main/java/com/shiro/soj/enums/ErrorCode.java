package com.shiro.soj.enums;

import lombok.Getter;

/**
 * 自定义错误码
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
@Getter
public enum ErrorCode {

    PARAMS_ERROR(40000, "请求参数错误"),
    ERROR_PASSWORD_OR_ACCOUNT(40200, "账号或密码错误"),
    ERROR_PASSWORD(40201, "原始密码错误"),
    ERROR_CHECK_PASSWORD(40300, "两次输入密码不同"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败"),
    FILE_UPLOAD_ERROR(40600, "文件上传异常");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
