package com.my.blog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.my.blog.entity.mybatis.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 18:58 2019/5/16
 * @Modified By:
 */
@Component
public class TokenUtil {
    /**
     * 根据用户名username生产token, 密码password 作为密钥
     * @param user entity.User
     * @return String token
     */
    public String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(user.getUsername()). // 将UserName 保存在token中
                withClaim("role",user.getRole().getRoleName()). //将用户角色放入Token
                withClaim("now",System.currentTimeMillis()).   //放入一个时间戳来生成一个不同的token
                sign(Algorithm.HMAC256(user.getPassword())); // 将密码作为密钥
        return token;
    }
}
