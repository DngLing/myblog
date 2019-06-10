package com.my.blog.service.impl;

import com.my.blog.common.exception.CustomException;
import com.my.blog.entity.mybatis.User;
import com.my.blog.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 11:09 2019/5/15
 * @Modified By:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void register() {
        User user = new User();
        user.setUsername("didi456");
        user.setPassword("123891231");
        user.setNickname("ccsa");
        user.setEmail("example@tx.com");
        user.setHeadImg("C:\\asd.jpg");

        user.setMyInfo("你说是那就是不狡辩");
        try {
            int result = userService.register(user);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void login() {
        User user = new User();
        user.setUsername("dongyifan");
        user.setPassword("dong1998");
        User resultUser = new User();
        try {
            resultUser = userService.login(user);
        } catch (CustomException e) {
            e.printStackTrace();
        }
        System.out.println(resultUser);
    }
}