package com.my.blog.service;

import com.my.blog.common.exception.CustomException;
import com.my.blog.entity.mybatis.User;

/**
 * @Author： Dong
 * @Description: 实现User的登陆注册，修改个人资料等业务
 * @Date: Created in 10:35 2019/5/15
 * @Modified By:
 */
public interface UserService {

    int register(User user) throws Exception;

    User login(User user) throws CustomException;

    User queryUserByUsername(String username);

    int isUsernameExist(String username);

    int isNicknameExist(String nickname);

    int isEmailExist(String email);

    int updateNickNameByUsername(String nickname, String username) throws CustomException;

    int updateProfileByUsername(String profile, String username) throws CustomException;

    int updateHeadImg(String headImg, String username);
}
