package com.shiro.soj.utils;

public class ThreadLocalUtil {
    // 使用 ThreadLocal 存储用户id
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    // 存储信息到当前线程
    public static void setUserId(Long userId) {
        threadLocal.set(userId);
    }

    // 获取当前线程存储的信息
    public static Long getUserId() {
        return threadLocal.get();
    }

    // 清除当前线程存储的信息
    public static void clear() {
        threadLocal.remove();
    }
}
