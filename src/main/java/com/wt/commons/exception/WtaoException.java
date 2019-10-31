package com.wt.commons.exception;

import com.wt.commons.exception.enums.ExceptionEnum;
import com.wt.commons.exception.utils.ExceptionEnumUtils;
import lombok.Data;

/**
 * 自定义异常
 *
 * @author wtao
 * @date 2019-10-28 14:35
 */
@Data
public class WtaoException extends RuntimeException {


    private int status;

    private String msg;

    private ExceptionEnum em;

    public WtaoException(ExceptionEnum em) {
        this.status = ExceptionEnumUtils.getStatus(em);
        this.msg = ExceptionEnumUtils.getMsg(em);
        this.em = em;
    }

    public WtaoException(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
