package com.my.blog.controller;

import com.auth0.jwt.JWT;
import com.my.blog.annotation.PassToken;
import com.my.blog.annotation.UserToken;
import com.my.blog.common.exception.CommonExceptionEnum;
import com.my.blog.common.exception.CustomException;
import com.my.blog.common.result.CommonReturnType;
import com.my.blog.controller.model.UserModel;
import com.my.blog.controller.view.UserView;
import com.my.blog.dao.UserDao;
import com.my.blog.entity.mybatis.User;
import com.my.blog.service.EmailService;
import com.my.blog.service.UserService;
import com.my.blog.util.RedisUtil;
import com.my.blog.util.TokenUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 14:04 2019/5/15
 * @Modified By:
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController{

    @Value("${head.path}")
    private String headImgPath;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDao userDao;

    /**
     * 用户登陆接口
     * @param user
     * @return
     * @throws CustomException
     */
    @PassToken
    @PostMapping("/login")
    public CommonReturnType login(@RequestBody User user) throws CustomException {
            Map map = null;
            if(userService.isUsernameExist(user.getUsername()) == 0){
                throw new CustomException(CommonExceptionEnum.USERNAME_NOT_EXIST);
            }
            User resultUser =  userService.login(user);
            String token = tokenUtil.getToken(resultUser);
            if(token == null || token.equals("")){
                throw new CustomException(CommonExceptionEnum.SERVICE_ERR);
            }
            //过期验证为1小时
            redisUtil.setTokenWithTimeout(resultUser.getUsername(),token,1000*60*60);
            map = new HashMap();
            map.put("token",token);
            return new CommonReturnType(map);
    }

    /**
     * 用户注册接口
     * @param user
     * @return
     * @throws Exception
     */
    @PassToken
    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class)
    public CommonReturnType register(@RequestBody UserModel user) throws Exception {
        if(user.getUsername()==null || user.getUsername().equals("")){
            throw new CustomException(CommonExceptionEnum.EMPTIY_USERNAME);
        }
        String verifyCode = redisUtil.get("email:"+user.getEmail());
        if(verifyCode == null){
            throw new CustomException(CommonExceptionEnum.VERIFY_CODE_TIMEOUT);
        }
        if(!verifyCode.equals(user.getVerifyCode())){
            throw new CustomException(CommonExceptionEnum.VERIFY_CODE_ERR);
        }
        User registerUser = convertUserModelToUser(user);
        userService.register(registerUser);
        return new CommonReturnType("注册成功");
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

    /**
     * 检测用户名可用性接口
     * @param username
     * @return
     */
    @PassToken
    @GetMapping("/isUsernameExist")
    public CommonReturnType isUsernameExist(String username){
        int code = userService.isUsernameExist(username);
        if(code == 0){
            return new CommonReturnType(0,"success","账户可用");
        }else {
            return new CommonReturnType(1,"fail","账户不可用");
        }
    }

    /**
     * 检测用户昵称可用性接口
     * @param nickname
     * @return
     */
    @PassToken
    @GetMapping("/isNicknameExist")
    public CommonReturnType isNicknameExist(String nickname){
        int code = userService.isNicknameExist(nickname);
        if(code == 0){
            return new CommonReturnType(0,"success","昵称可用");
        }else {
            return new CommonReturnType(1,"fail","昵称不可用");
        }
    }

    /**
     * 检测用户邮箱可用性接口
     * @param email
     * @return
     */
    @PassToken
    @GetMapping("/isEmailExist")
    public CommonReturnType isEmailExist(String email){
        int code = userService.isEmailExist(email);
        if(code == 0){
            return new CommonReturnType(0,"success","邮箱可用");
        }else {
            return new CommonReturnType(1,"fail","邮箱不可用");
        }
    }

    /**
     * 修改昵称接口
     * @param nickname
     * @param request
     * @return
     * @throws CustomException
     */
    @UserToken
    @GetMapping("/update/nickname")
    public CommonReturnType updateNickName(String nickname, HttpServletRequest request) throws CustomException {
        String token = request.getHeader("token");
        String username = JWT.decode(token).getAudience().get(0);
        int code = userService.updateNickNameByUsername(nickname,username);
        if(code == 0){
            return new CommonReturnType(1,"fail","修改昵称失败");
        }
        return new CommonReturnType("修改成功");
    }

    /**
     * 修改个人简介接口
     * @param newProfile
     * @param request
     * @return
     * @throws CustomException
     */
    @UserToken
    @GetMapping("/update/profile")
    public CommonReturnType updateUserProfile(String newProfile,HttpServletRequest request) throws CustomException {
        String token = request.getHeader("token");
        String username = JWT.decode(token).getAudience().get(0);
        int code =  userService.updateProfileByUsername(newProfile,username);
        if(code == 0){
            return new CommonReturnType(1,"fail","修改失败");
        }
        return new CommonReturnType("修改成功");
    }

    /**
     * 上传头像照片接口
     * @param file
     * @param request
     * @return
     * @throws CustomException
     */
    @UserToken
    @PostMapping("/upload/headImg")
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public CommonReturnType uploadNewHeadImg(MultipartFile file, HttpServletRequest request) throws CustomException {
        if(!file.getOriginalFilename().endsWith(".jpg")){
            throw new CustomException(CommonExceptionEnum.HEADIMG_NOT_JPG);
        }
        String token = request.getHeader("token");
        String username = JWT.decode(token).getAudience().get(0);
        String path = headImgPath + username +".jpg";
        if(!file.isEmpty()){
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream( new File(path)));
                bos.write(file.getBytes());
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomException(CommonExceptionEnum.UPLOAD_FILE_FAIL);
            }
            userService.updateHeadImg(path,username);
        }else {
            return new CommonReturnType(1,"fail","上传为空");
        }
        return new CommonReturnType("上传成功");
    }

    /**
     * 获得用户信息的接口
     * @param request
     * @return
     */
    @UserToken
    @GetMapping("/getUserInfo")
    public CommonReturnType getUserHeadImg(HttpServletRequest request){
        String token = request.getHeader("token");
        String username = JWT.decode(token).getAudience().get(0);
        User user = userService.queryUserByUsername(username);
        UserView userView = convertUserToUserView(user);
        return new CommonReturnType(userView);
    }



}
