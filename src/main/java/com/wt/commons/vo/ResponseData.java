package com.wt.commons.vo;

import lombok.Data;

/**
 * 通用 响应结果
 *
 * @author wtao
 * @date 2019-10-28 19:36
 */
@Data
public class ResponseData<T> {


    private Integer code;
    private String msg;
    private T data;

    public ResponseData(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}