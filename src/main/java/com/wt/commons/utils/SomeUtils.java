package com.wt.commons.utils;

/**
 * 常用工具类
 *
 * @author wtao
 * @date 2019-10-27 21:56
 */
public class SomeUtils {

    /**
     * 判断对象是否为null或者为空字符串，当对象不为Null或者不是空字符串时返回true
     *
     * @author wtao
     * @date 2019-10-06 15:06:07
     */
    public static boolean isNoEmpty(Object... value) {
        if (value == null) {
            return false;
        }
        for (Object val : value) {
            if (val == null || "".equals(val)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 为空时返回空字符串
     *
     * @param object 任意数据
     * @return String
     */
    public static String EmptyReturnString(Object object) {

        return isNoEmpty(object) ? String.valueOf(object) : "";
    }

    /**
     * 不够位数的在前面补0，保留num的长度位数字
     *
     * @param number 编号
     * @param length 长度
     * @return String
     * @author wtao
     */
    public static String autoGenericCode(int number, int length) {
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型

        return String.format("%0" + length + "d", number + 1);
    }

    /**
     * double保存后两位
     *
     * @param money 金额
     * @return String
     */
    public static String getDoubleKeep2(Double money) {
        if (money == null) {
            return "0.00";
        }
        return String.format("%.2f", ((int) (money * 100)) / 100.0);
    }

}
