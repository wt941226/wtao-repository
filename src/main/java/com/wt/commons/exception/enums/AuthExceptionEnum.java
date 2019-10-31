package com.wt.commons.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证相关异常 枚举
 *
 * @author wtao
 * @date 2019-10-28 14:16
 */
@Getter
@AllArgsConstructor
public enum AuthExceptionEnum implements ExceptionEnum {


    TOKEN_IS_EMPTY(401, "TOKEN为空"),
    TOKEN_INVALID(401, "TOKEN已失效,请重新登录"),
    ACCOUNT_NOT_FOUND(401, "账号不存在"),
    ACCOUNT_NOT_AVAILABLE(401, "账号已被冻结"),
    PASSWORD_ERROR(401, "密码错误"),
    NO_PERMISSION(401, "权限不足"),

    ;
    private int status;
    private String msg;

}
