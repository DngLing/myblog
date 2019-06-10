package com.my.blog.intercepter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.my.blog.annotation.PassToken;
import com.my.blog.annotation.UserToken;
import com.my.blog.common.exception.CommonExceptionEnum;
import com.my.blog.common.exception.CustomException;
import com.my.blog.dao.UserDao;
import com.my.blog.entity.mybatis.User;
import com.my.blog.util.RedisUtil;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 17:35 2019/5/16
 * @Modified By:
 */
public class AuthenticationIntercepter implements HandlerInterceptor {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中获得token
        String token = request.getHeader("token");
        //如果不是映射方法就直接跳过
        if(! (handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //检测到@PassToken就直接跳过
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }

        //检测到@UserToken时进行验证
        if(method.isAnnotationPresent(UserToken.class)){
            UserToken userToken = method.getAnnotation(UserToken.class);
            if(userToken.required()){
                if(token == null || token.equals("")){
                    throw new CustomException(CommonExceptionEnum.NO_PERMISSION);
                }

                //验证用户有效性
                String username = "";
                username = JWT.decode(token).getAudience().get(0);
                if(userDao.isUsernameExist(username) == 0){
                    throw  new CustomException(CommonExceptionEnum.USERNAME_NOT_EXIST);
                }

                User user = userDao.queryUserByUsername(username);
                JWTVerifier  verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();

                //验证token有效性
                try {
                    verifier.verify(token);
                }catch (JWTVerificationException e){
                    throw new CustomException(CommonExceptionEnum.LOGIN_EXP);
                }

                String vToken = redisUtil.get("token:"+username);
                if(vToken == null){
                    throw new CustomException(CommonExceptionEnum.LOGIN_TIMEOUT);
                }
                if(!vToken.equals(token)){
                    throw new CustomException(CommonExceptionEnum.NO_PERMISSION);
                }
                redisUtil.setTokenWithTimeout(username,token,1000*60*60);
                return true;
            }
        }


        return false;
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

