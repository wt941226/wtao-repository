package com.wt.plugins.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wtao
 * @date 2019-10-25 22:10
 */
@Component
@Slf4j
public class MailUtils {


    @Value("${spring.mail.username}")
    private String emailSender;

    @Resource
    private JavaMailSender javaMailSender;

    private static MailUtils mailUtils;

    public MailUtils() {
        mailUtils = this;
    }

    /**
     * 构建静态获取实例
     *
     * @return
     */
    public static MailUtils buidler() {
        if (mailUtils == null) {
            return null;
        }
        return mailUtils;
    }
    // SimpleMailMessage 只能用来发送text格式的邮件
   /* public void sendSimpleMail(MailEntity mailBean) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(emailSender);
            //邮件接收人
            simpleMailMessage.setTo(mailBean.getReceiver());
            //邮件主题
            simpleMailMessage.setSubject(mailBean.getSubject());
            //邮件内容
            simpleMailMessage.setText(mailBean.getText());
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("邮件发送失败", e.getMessage());
        }
    }*/


    /**
     * 发送邮件 支持HTML
     *
     * @author wtao
     * @date 2019-10-26 23:04
     */
    public void sendMail(MailEntity mailBean) {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = MailEntityHelper.craeteMessageHelper(mimeMailMessage, mailBean);
            // 发件人
            mimeMessageHelper.setFrom(emailSender);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败", e.getMessage());
        }
    }


    /**
     * 批量发送邮件 （可变长参数）
     *
     * @author wtao
     * @date 2019-10-26 23:03
     */
    public void sendMail(MailEntity... mailBeans) {
        //批量发送集合
        List<MimeMessage> mimeMessages = new ArrayList<>(mailBeans.length);
        try {
            if (mailBeans != null && mailBeans.length > 0) {
                for (int i = 0; i < mailBeans.length; i++) {
                    MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper messageHelper = MailEntityHelper.craeteMessageHelper(mimeMailMessage, mailBeans[i]);
                    //发件人
                    messageHelper.setFrom(emailSender);
                    mimeMessages.add(mimeMailMessage);
                }
            }

            MimeMessage[] resultMessage = new MimeMessage[mimeMessages.size()];
            mimeMessages.toArray(resultMessage);
            javaMailSender.send((resultMessage));
        } catch (Exception e) {
            log.error("邮件发送失败", e.getMessage());
        }
    }

    /**
     * 批量发送邮件 （List集合）
     *
     * @author wtao
     * @date 2019-10-26 23:03
     */
    public void sendMail(List<MailEntity> mailBeans) {
        //批量发送集合
        List<MimeMessage> mimeMessages = new ArrayList<>(mailBeans.size());
        try {
            if (mailBeans != null || mailBeans.size() > 0) {
                for (MailEntity mailBean : mailBeans) {
                    MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper messageHelper = MailEntityHelper.craeteMessageHelper(mimeMailMessage, mailBean);
                    //发件人
                    messageHelper.setFrom(emailSender);
                    mimeMessages.add(mimeMailMessage);
                }
            }

            MimeMessage[] resultMessage = new MimeMessage[mimeMessages.size()];
            mimeMessages.toArray(resultMessage);
            javaMailSender.send((resultMessage));
        } catch (Exception e) {
            log.error("邮件发送失败", e.getMessage());
        }
    }
}
