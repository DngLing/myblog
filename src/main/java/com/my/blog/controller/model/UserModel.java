package com.my.blog.controller.model;

import lombok.Data;

/**
 * @Author： Dong
 * @Description: 用户模型类，用于s
 * @Date: Created in 18:13 2019/5/15
 * @Modified By:
 */
@Data
public class UserModel {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String myInfo;
    private String verifyCode;
}
