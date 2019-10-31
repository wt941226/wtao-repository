package com.wt.security.jwt.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wt.commons.exception.WtaoException;
import com.wt.commons.exception.enums.AuthExceptionEnum;
import com.wt.commons.exception.enums.ExceptionEnum;
import com.wt.commons.exception.result.ExceptionResult;
import com.wt.commons.exception.utils.ExceptionEnumUtils;
import com.wt.security.jwt.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wtao
 * @date 2019-10-27 17:54
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * 判断用户是否需要登入。
     * 检测header里面是否包含Authorization字段即可，有就进行Token登录认证授权
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {

        String authorization = this.getAuthzHeader(request);
        log.info("JWTFilter: isAccessAllowed() 【判断用户是否需要登录】，token = {}", authorization);
        return authorization != null;
    }


    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        // 调用 isLoginAttempt() 方法，判断是否有token，
        // 返回true说明 有token，调用executeLogin()进入shiro的realm中的认证方法,进行token校验认证
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                this.response401(request, response, e);
            }
        } else {
            // 没有携带Token
            this.response401(request, response, new WtaoException(AuthExceptionEnum.TOKEN_IS_EMPTY));
            return false;
        }
        return true;
    }


    /**
     * 通过token 进行登陆认证
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {

        JWTToken token = new JWTToken(this.getAuthzHeader(request));
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }


    /**
     * 无需转发，直接返回Response信息
     * 这里统一返回状态码都是401，便于APP，前端权限控制
     */
    private void response401(ServletRequest request, ServletResponse response, Exception exception) {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ExceptionResult result;
        if (exception instanceof WtaoException) {
            result = new ExceptionResult((WtaoException) exception);
        } else {
            result = new ExceptionResult(401, exception.getMessage());
        }
        try {
            resp.getWriter().append(JSONObject.toJSON(result).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 对跨域提供支持，跨域时会首先发送一个option请求，这里给option请求直接返回正常状态
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
