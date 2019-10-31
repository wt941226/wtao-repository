package com.wt.commons.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类 异常 枚举
 *
 * @author ccod
 * @date 2019/10/30 10:53
 **/
@Getter
@AllArgsConstructor
public enum OperationExceptionEnum implements ExceptionEnum {


    PARAM_EXCEPTION(400, "参数异常"),
    SAVE_FAILURE(500, "保存失败"),
    SELECT_FAILURE(500, "查询失败"),
    DEL_FAILURE(500, "删除失败"),

    ;

    private int status;
    private String msg;
}
