package com.wt.commons.utils;

import com.wt.commons.vo.ResponseData;

/**
 * 响应结果工具类
 *
 * @author wtao
 * @date 2019-10-28 19:38
 */
public class ResponseUtils {

    /**
     * 返回信息 手动封装（通用）
     *
     * @author wtao
     * @date 2019-10-28 19:51
     */
    public static <T> ResponseData<T> ok(Integer code, String msg, T data) {

        return new ResponseData(code, msg, data);
    }

    public static ResponseData success() {
        return success(null);
    }

    /**
     * 查询成功返回提示信息 （不分页数据）
     *
     * @author wtao
     * @date 2019-10-28 19:47
     */
    public static <T> ResponseData<T> selectSuccess(T data) {
        return new ResponseData(200, "查询成功", data);
    }

    /**
     * 保存成功返回提示信息
     *
     * @author wtao
     * @date 2019-10-28 19:47
     */
    public static ResponseData saveSuccess() {
        return new ResponseData(200, "保存成功", null);
    }

    /**
     * 删除成功返回提示信息
     *
     * @author wtao
     * @date 2019-10-28 19:47
     */
    public static ResponseData delSuccess() {
        return new ResponseData(200, "删除成功", null);
    }

    /**
     * 自定义成功提示信息
     *
     * @author wtao
     * @date 2019-10-28 19:47
     */
    public static ResponseData success(String msg) {
        return new ResponseData(200, msg, null);
    }

    /**
     * 返回错误状态码和提示信息
     *
     * @author wtao
     * @date 2019-10-28 19:47
     */
    public static ResponseData error(Integer code, String msg) {
        return new ResponseData(code, msg, null);
    }
}
