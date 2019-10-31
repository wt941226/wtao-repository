package com.wt.commons.exception.enums;


/**
 * 异常 枚举 接口
 *
 * @author wtao
 * @date 2019-10-30 10:38
 */

public interface ExceptionEnum {


    /**
     * 获取 异常枚举中的 状态码
     *
     * @return int 状态码
     * @author wtao
     * @date 2019/10/30 11:01
     */
    int getStatus();

    /**
     * 获取 异常枚举中的 异常信息
     *
     * @return int 状态码
     * @author wtao
     * @date 2019/10/30 11:01
     */
    String getMsg();
}
