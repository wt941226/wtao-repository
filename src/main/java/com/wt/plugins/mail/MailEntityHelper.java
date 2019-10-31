package com.wt.plugins.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * @author wtao
 * @date 2019-10-25 22:06
 */
@Slf4j
@Component
public class MailEntityHelper {

    public static MimeMessageHelper craeteMessageHelper(MimeMessage mimeMessage, MailEntity mailBean) throws MessagingException {

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        // 发件人
        // mimeMessageHelper.setFrom(emailSender);
        // 收件人
        mimeMessageHelper.setTo(mailBean.getReceiver());
        // 标题
        mimeMessageHelper.setSubject(mailBean.getSubject());
        // 是否html
        mimeMessageHelper.setText(mailBean.getText(), mailBean.isHtml());
        // 客户端邮箱发送时间
        mimeMessageHelper.setSentDate(mailBean.getSentDate() == null ? new Date() : mailBean.getSentDate());
        if (mailBean.getFiles() != null && mailBean.getFiles().size() > 0) {
            //附件添加
            mailBean.getFiles().forEach(filename -> {
                String ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
                File diskfile = new File(filename);
                try {
                    mimeMessageHelper.addAttachment(diskfile.getName(), diskfile);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    log.error("设置附件失败:{}", mailBean);
                }
            });
        }
        return mimeMessageHelper;
    }

}
