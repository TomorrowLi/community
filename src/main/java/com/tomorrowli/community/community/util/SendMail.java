package com.tomorrowli.community.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * @Author: LiMing
 * @Date: 2020/3/3 11:19
 * @Description:
 **/

@Component
public class SendMail {


    private static final Logger logger= LoggerFactory.getLogger(SendMail.class);

    @Value("${spring.mail.username}")
    private String form;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to,String subject,String content){

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(form);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content,true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("发送失败",e.getMessage());
        }


    }
}
