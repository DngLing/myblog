package com.my.blog.controller;

import com.my.blog.common.exception.CustomException;
import com.my.blog.common.result.CommonReturnType;
import com.my.blog.controller.model.UserModel;
import com.my.blog.controller.view.UserView;
import com.my.blog.entity.mybatis.User;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 11:26 2019/5/15
 * @Modified By:
 */
public class BaseController {


    //定义ExceptionHandler解决Controller层未被处理掉的异常
    @ExceptionHandler(Exception.class)
    //Controller 层返回的异常应该属于后端的异常
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception e) {
        Map<String, Object> responseData = new HashMap<>();
        CommonReturnType commonReturnType = new CommonReturnType();
        if (e instanceof CustomException) {
            //强制转化捕获的错误为UserException
            CustomException exception = (CustomException) e;
            //将错误信息转化为通用的上传格式
            commonReturnType.setMsg("fail");
            commonReturnType.setCode(exception.getExceptionCode());
            commonReturnType.setData(exception.getExceptionMsg());

        } else {
            //将非自定义异常的关键信息上传并打印
            commonReturnType.setCode(e.hashCode());
            commonReturnType.setMsg(e.getMessage());
            commonReturnType.setData(e.getLocalizedMessage());
        }
        return commonReturnType;
    }

    public User convertUserModelToUser(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userModel, user);
        return user;
    }

    public UserView convertUserToUserView(User user){
        if(user == null){
            return null;
        }
        UserView userView = new UserView();
        BeanUtils.copyProperties(user,userView);
        return userView;
    }
}
