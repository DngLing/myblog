package com.my.blog.service.impl;

import com.my.blog.common.exception.CommonException;
import com.my.blog.common.exception.CommonExceptionEnum;
import com.my.blog.common.exception.CustomException;
import com.my.blog.dao.UserDao;
import com.my.blog.entity.mybatis.User;
import com.my.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 10:37 2019/5/15
 * @Modified By:
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao = null;

    @Override
    public int register(User user) throws Exception {
        if(userDao.isUsernameExist(user.getUsername()) != 0){
            throw new CustomException(CommonExceptionEnum.USERNAME_ALREADY_EXIST);
        }
        if(userDao.isNicknameExist(user.getNickname()) !=0){
            throw new CustomException(CommonExceptionEnum.NICKNAME_ALREADY_EXIST);
        }
        if(userDao.isEmailExist(user.getEmail()) !=0){
            throw new CustomException(CommonExceptionEnum.EMAIL_ALREADY_EXIST);
        }
        int code = 0;
        code = userDao.insertUser(user);
        if(code == 0){
            throw new CustomException(CommonExceptionEnum.SERVICE_ERR);
        }
        return code;
    }

    @Override
    public User login(User user) throws CustomException {
        User resultUser = userDao.queryUserByUsername(user.getUsername());
        if(!user.getPassword().equals(resultUser.getPassword())){
            throw new CustomException(CommonExceptionEnum.PASSWORD_ERR);
        }
        return resultUser;
    }

    @Override
    public User queryUserByUsername(String username) {
        return userDao.queryUserByUsername(username);
    }

    @Override
    public int isUsernameExist(String username) {
        int resultCode = userDao.isUsernameExist(username);
        return resultCode;
    }

    @Override
    public int isNicknameExist(String nickname) {
        int resultCode = userDao.isNicknameExist(nickname);
        return resultCode;
    }

    @Override
    public int isEmailExist(String email) {
        int resultCode = userDao.isEmailExist(email);
        return resultCode;
    }

    @Override
    public int updateNickNameByUsername(String nickname, String username) throws CustomException {
        if(nickname.equals("") || nickname == null){
            throw new CustomException(CommonExceptionEnum.EMPTY_NICKNAME);
        }
        if(userDao.isNicknameExist(nickname) != 0){
            throw new CustomException(CommonExceptionEnum.NICKNAME_ALREADY_EXIST);
        }
        int code =userDao.updateNicknameByUsername(nickname,username);
        return code;
    }

    @Override
    public int updateProfileByUsername(String profile, String username) throws CustomException {
        int code = 0;
        try{
             code =  userDao.updateProfileByUsername(profile,username);
        }catch (Exception e){
            if(e instanceof  DataIntegrityViolationException){
                throw new CustomException(CommonExceptionEnum.TOO_LONG_ERR);
            }else{
                throw new CustomException(CommonExceptionEnum.SERVICE_ERR);
            }
        }
      return code;
    }

    @Override
    public int updateHeadImg(String headImg, String username) {
        int code = userDao.updateHeadImg(headImg,username);
        return code;
    }
}
