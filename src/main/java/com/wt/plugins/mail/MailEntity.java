package com.wt.plugins.mail;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 邮件 封装 实体类
 *
 * @author wtao
 * @date 2019-10-25 22:05
 */
@Data
public class MailEntity {
    /**
     * 收件人
     */
    private String receiver;
    /**
     * 标题
     */
    private String subject;
    /**
     * 正文
     */
    private String text;
    /**
     * 是否 HTML邮件
     */
    private boolean isHtml = false;
    /**
     * 附件地址
     */
    private List<String> files;
    /**
     * 发送时间
     */
    private Date sentDate;
}
