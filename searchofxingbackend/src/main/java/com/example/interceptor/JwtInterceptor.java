package com.example.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.exception.ServiceException;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在此校验token
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //从请求头中尝试获取token
        String token = request.getHeader("token");
        //如果请求头中无token，从浏览器地址栏中尝试获取token
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        // 如果不是映射到方法直接通过
//        if (handler instanceof HandlerMethod) {
//            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
//            if (annotation != null) {
//                return true;
//            }
//        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException("401", "请登录");
        }
        // 获取 token 中的 useId
        String userId;
        try {
            //解析token，获取存储在其中的userId
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            //如果没有userId
            throw new ServiceException("401", "请登录");
        }
        // 根据token中的userid查询数据库
        User user = userMapper.getUserById(Long.valueOf(userId));
        if (user == null) {
            //如果该用户Id在数据库中不存在
            throw new ServiceException("401", "请登录");
        }
        // 用户密码加签验证 token
        //生成有效密码验证器，使用这个验证器来检验用户输入的密码是否正确
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserPassword())).build();
        try {
            //开始验证
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            //不一致
            throw new ServiceException("401", "请登录");
        }
        //token验证通过
        return true;
    }
}