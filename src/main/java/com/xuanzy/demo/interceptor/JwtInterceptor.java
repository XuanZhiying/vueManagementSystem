package com.xuanzy.demo.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xuanzy.demo.common.Constants;
import com.xuanzy.demo.interceptor.annotation.LoginToken;
import com.xuanzy.demo.interceptor.annotation.PassToken;
import com.xuanzy.demo.entity.User;
import com.xuanzy.demo.exception.ServiceException;
import com.xuanzy.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Author: 轩志颍
 * Create: 2022-04-21 09:21
 **/
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        log.info("------------------>>>>>>>>>>请求路径为:{}",requestURI);

        String token = request.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new ServiceException(Constants.CODE_401,"token验证失败，无法获取用户ID，请重新登录");
                }
                User user = userService.getById(userId);
                if (user == null) {
                    throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
                }
                //用户密码加签验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token); //验证 token
                } catch (JWTVerificationException e) {
                        throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
                }
                return true;
            }
        }
        return true;

    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
