package com.wt.commons.exception.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.wt.commons.exception.WtaoException;
import com.wt.commons.exception.result.ExceptionResult;
import com.wt.commons.utils.ResponseUtils;
import com.wt.commons.vo.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Result;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截
 *
 * @author wtao
 * @date 2019-10-28 14:22
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义异常
     *
     * @param e exception
     * @return ExceptionResult
     * @author wtao
     * @date 2019/10/30 16:50
     */
    @ExceptionHandler(WtaoException.class)
    public ExceptionResult error(WtaoException e) {
        log.info("【统一异常处理】拦截到异常 {}: {}", e.getClass(), e.getMsg());

        return new ExceptionResult(e);
    }


    /**
     * 参数校验异常
     *
     * @param e 异常
     * @return ExceptionResult
     * @author wtao
     * @date 2019/10/30 16:35
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResult error(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.info("【统一异常处理】拦截到异常 {}: {}", e.getClass(), e.getMessage());

        return new ExceptionResult(400, msg);
    }


    /**
     * 数据库插入异常
     *
     * @param e 异常
     * @return Result Result
     * @author wtao
     * @date 2019/10/14 16:36
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ExceptionResult error(DuplicateKeyException e) {
        log.info("【统一异常处理】拦截到异常 {}: {}", e.getClass(), e.getMessage());
        return new ExceptionResult(400, "保存失败，可能已经存在重复数据");
    }


    /**
     * 参数绑定异常
     *
     * @param e exception
     * @return ExceptionResult
     * @author wtao
     * @date 2019/10/30 16:51
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionResult error(HttpMessageNotReadableException e) {
        log.info("【统一异常处理】拦截到异常 {}: {}", e.getClass(), e.getMessage());
        return new ExceptionResult(400, "参数异常，请检查参数类型是否匹配");
    }


    /**
     * 参数绑定异常
     *
     * @param e exception
     * @return ExceptionResult
     * @author wtao
     * @date 2019/10/30 16:51
     */
    @ExceptionHandler(Exception.class)
    public ExceptionResult error(Exception e) {
        log.info("【统一异常处理】拦截到异常 {}: {}", e.getClass(), e.getMessage());
        return new ExceptionResult(500, "服务器内部错误");
    }
}
