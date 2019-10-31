package com.wt.blog.controller;

import com.wt.plugins.mail.MailEntity;
import com.wt.plugins.mail.MailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wtao
 * @date 2019-10-25 22:16
 */
@Api(tags = "邮件接口")
@RestController
@RequestMapping("/email")
public class EmailController {


    @ApiOperation(value = "发送邮件测试接口")
    @GetMapping("test")
    public String test() {
        MailEntity emailContextPojo = new MailEntity();
        emailContextPojo.setSubject("Ycloud 邮箱标题1");
        emailContextPojo.setText("Ycloud 邮箱正文1");
        emailContextPojo.setReceiver("2475459925@qq.com");

        MailEntity emailContextPojo1 = new MailEntity();
        emailContextPojo1.setSubject("Ycloud 邮箱标题2");
        emailContextPojo1.setText("Ycloud 邮箱正文2");
        emailContextPojo1.setReceiver("1083877365@qq.com");

        MailEntity emailContextPojo3 = new MailEntity();
        emailContextPojo3.setSubject("妞妞你好");
        emailContextPojo3.setText("妞妞你好");
        emailContextPojo3.setReceiver("2405492760@qq.com");

        new Thread(new Runnable() {
            @Override
            public void run() {
                MailUtils.buidler().
                        sendMail(emailContextPojo, emailContextPojo1, emailContextPojo3);
            }
        }).start();

        return "发送成功";
    }
}
