package com.shiro.soj.interceptors;

import com.shiro.soj.enums.ErrorCode;
import com.shiro.soj.exception.BusinessException;
import com.shiro.soj.utils.JwtUtil;
import com.shiro.soj.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    /*
     * 在请求处理之前进行调用(Controller方法调用之前)
     * 若返回true请求将会继续执行后面的操作
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BusinessException {
        String token = request.getHeader("token");
        log.info("token校验:{}", token);
        // 如果不是映射到方法不拦截 直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //验证token
        if (StringUtils.isAnyBlank(token) || !JwtUtil.validateToken(token)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        //若token验证成功，把用户id存储在ThreadLocal
        Long userId = JwtUtil.extractClaim(token, claims -> claims.get("userId", Long.class));
        ThreadLocalUtil.setUserId(userId);
        log.info("用户id:{}", userId);
        return true;
    }

    /***
     * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //清除线程变量
        ThreadLocalUtil.clear();
    }
}
