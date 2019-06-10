package com.my.blog.controller;

import com.my.blog.annotation.PassToken;
import com.my.blog.common.exception.CustomException;
import com.my.blog.common.result.CommonReturnType;
import com.my.blog.controller.model.UserModel;
import com.my.blog.service.EmailService;
import com.my.blog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 19:16 2019/5/20
 * @Modified By:
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailService emailService;


    @PostMapping("/updatePwd")
    public CommonReturnType updatePassword(UserModel userModel){
        return null;
    }

    /**
     * 发送验证邮箱接口
     * @param email
     * @return
     * @throws CustomException
     */
    @PassToken
    @GetMapping("/sendVerify")
    public CommonReturnType sendVerifyEmail(String email) throws CustomException {
        String verifyCode = emailService.getVerifyCode();
        redisUtil.setEmailWithTimeout(email,verifyCode,1000*30*60);
        emailService.sendMail(email,verifyCode);
        return new CommonReturnType("发送成功");
    }
}
