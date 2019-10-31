package com.wt.commons.exception.result;

import com.wt.commons.exception.WtaoException;
import com.wt.commons.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回异常结果
 *
 * @author wtao
 * @date 2019-10-28 15:49
 */
@Data
@NoArgsConstructor
public class ExceptionResult {

    /**
     * 状态码
     */
    private int code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 时间戳
     */
    private String timestamp;

    public ExceptionResult(WtaoException e) {
        this.code = e.getStatus();
        this.msg = e.getMsg();
        this.timestamp = DateUtils.timeToString(
                DateUtils.getDateTimeOfTimestamp(System.currentTimeMillis()),
                "yyyy-MM-dd HH:mm:ss");
    }

    public ExceptionResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp = DateUtils.timeToString(
                DateUtils.getDateTimeOfTimestamp(System.currentTimeMillis()),
                "yyyy-MM-dd HH:mm:ss");
    }

}
