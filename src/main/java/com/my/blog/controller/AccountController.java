package com.my.blog.controller;

import com.auth0.jwt.JWT;
import com.my.blog.annotation.PassToken;
import com.my.blog.annotation.UserToken;
import com.my.blog.common.exception.CommonExceptionEnum;
import com.my.blog.common.exception.CustomException;
import com.my.blog.common.result.CommonReturnType;
import com.my.blog.controller.model.UserModel;
import com.my.blog.service.EmailService;
import com.my.blog.service.UserService;
import com.my.blog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 19:16 2019/5/20
 * @Modified By:
 */
@RestController
@RequestMapping("/api/account")
public class AccountController extends BaseController{

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailService emailService;

    @Value("${head.path}")
    private String headImgPath;

    @Autowired
    private UserService userService;

    /**
     * 上传头像照片接口
     * @param file
     * @param request
     * @return
     * @throws CustomException
     */
    @UserToken
    @PostMapping("/uploadHead")
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public CommonReturnType uploadNewHeadImg(MultipartFile file, HttpServletRequest request) throws CustomException {
        if(file == null){
            throw new CustomException(CommonExceptionEnum.FILE_EMPTY);
        }

        //文件大小最大2mb
        if(file.getSize()>1024*1024*2){
            throw new CustomException(CommonExceptionEnum.TOO_BIG_FILE);
        }

        if(!file.getOriginalFilename().endsWith(".jpg")||!file.getOriginalFilename().endsWith(".png")){
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
}
