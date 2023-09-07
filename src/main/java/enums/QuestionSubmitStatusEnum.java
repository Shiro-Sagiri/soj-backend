package enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum QuestionSubmitStatusEnum {

    /**
     * 待判题
     */
    WAITING(0, "待判题"),

    /**
     * 判题中
     */
    RUNNING(1, "判题中"),

    /**
     * 成功
     */
    SUCCEED(2, "成功"),

    /**
     * 失败
     */
    FAILED(3, "失败");

    /**
     * 值
     */
    private final Integer value;

    /**
     * 信息
     */
    private final String message;

    QuestionSubmitStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public static QuestionSubmitStatusEnum getByValue(Integer value) {
        if (ObjectUtil.isEmpty(value)) {
            return null;
        }
        return Arrays.stream(values()).filter(item -> item.value.equals(value)).findFirst().orElse(null);
    }
}
