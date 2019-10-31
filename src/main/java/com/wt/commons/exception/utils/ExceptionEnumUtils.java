package com.wt.commons.exception.utils;


import com.wt.commons.exception.enums.ExceptionEnum;

/**
 * 异常枚举 工具类
 *
 * @author wtao
 * @date 2019/10/30 10:47
 **/
public class ExceptionEnumUtils {


    public static int getStatus(ExceptionEnum em) {
        return em.getStatus();
    }

    public static String getMsg(ExceptionEnum em) {
        return em.getMsg();
    }
}
