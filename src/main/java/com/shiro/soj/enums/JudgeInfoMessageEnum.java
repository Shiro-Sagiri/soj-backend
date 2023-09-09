package com.shiro.soj.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题信息结果枚举
 *
 * @author Shiro
 */
@Getter
public enum JudgeInfoMessageEnum {

    ACCEPTED("Accepted", "成功"),
    WRONG_ANSWER("Wrong Answer", "答案错误"),
    COMPILE_ERROR("Compile Error", "编译错误"),
    MEMORY_LIMIT_EXCEEDED("Memory Limit Exceeded", "内存超限"),
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded", "时间超限"),
    PRESENTATION_ERROR("Presentation Error", "格式错误"),
    OUTPUT_LIMIT_EXCEEDED("Output Limit Exceeded", "输出超限"),
    WAITING("Waiting", "等待中"),
    DANGEROUS("Dangerous", "危险代码"),
    SYSTEM_ERROR("System Error", "系统错误");

    private final String text;

    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return 值列表
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value value
     * @return UserRoleEnum
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

}
