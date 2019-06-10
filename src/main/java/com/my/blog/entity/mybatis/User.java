package com.my.blog.entity.mybatis;

import com.my.blog.enumeration.RoleEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * @Author： Dong
 * @Description: the sql entity map table 'user'
 * @Date: Created in 15:22 2019/5/10
 * @Modified By:
 */

@Alias("user")
@Getter
@Setter
@ToString
public class User {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String headImg;
    private String myInfo;
    private String email;
    private RoleEnum role;
    private int state;
}
