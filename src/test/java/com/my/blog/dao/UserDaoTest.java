package com.my.blog.dao;

import com.my.blog.common.exception.CommonExceptionEnum;
import com.my.blog.common.exception.CustomException;
import com.my.blog.entity.mybatis.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 15:38 2019/5/10
 * @Modified By:
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

    @Test
    public void testQueryUserByUsername(){
       User user = userDao.queryUserByUsername("dongyifan");
       System.out.println(user);
    }

    @Test
    public void testIsUsernameExist(){
        int result1 = userDao.isUsernameExist("dongyifan");
        int result2 = userDao.isUsernameExist("wangguanghui");
        System.out.println(result1 + "  " + result2);
    }

    @Test
    public void testIsEmailExist(){
        int result1 = userDao.isEmailExist("example@email.com");
        int result2 = userDao.isEmailExist("329640258@qq.com");
        System.out.println(result1 + "" + result2);
    }

    @Test
    public void testIsNicknameExist(){
        int result1 = userDao.isNicknameExist("王子护卫队");
        int result2 = userDao.isNicknameExist("古欧哦");
        System.out.println(result1 + " " + result2);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("qiqi123");
        user.setPassword("王中王");
        user.setNickname("cool");
        user.setHeadImg("C:\\ccc\\ccc.jpg");
        user.setEmail("example@163.com");
        user.setMyInfo("我是这条街最靓的仔");
        int result = userDao.insertUser(user);
        System.out.println(result);
    }

    @Test
    public void testUpdateNickname(){
       int code =  userDao.updateNicknameByUsername("我的新昵称","dongyifan");
       logger.info("code: "+ code);
    }

    @Test
    public void testUpdateProfile(){
        int code = userDao.updateProfileByUsername("nihao ","ling");
        logger.info("code: "+ code);
    }
}