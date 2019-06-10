package com.my.blog.service;

import com.my.blog.common.exception.CommonExceptionEnum;
import com.my.blog.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 14:50 2019/5/15
 * @Modified By:
 */
@Service
public class EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    public void sendMail(String to,String verifyCode) throws CustomException {
        logger.info("from: "+from);
        logger.info("to: "+to);
        SimpleMailMessage message = new SimpleMailMessage();
        String title = "注册验证邮件";
        String content = "尊敬的用户:\n" + "   你正在注册Books书城账号，验证码是:" + verifyCode + "\n  若非本人操作，请忽略。\n";
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);

        try {
            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(CommonExceptionEnum.SEND_EMAIL_FAIL);
        }
    }


    public String getVerifyCode(){
        return "" + (int) ((Math.random() * 9 + 1) * 100000) + "";
    }


}
