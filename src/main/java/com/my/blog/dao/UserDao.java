package com.my.blog.dao;

import com.my.blog.entity.mybatis.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 15:35 2019/5/10
 * @Modified By:
 */

@Repository
public interface UserDao {

    /**
     * 查询所有用户
     * @return
     */
    List<User> queryAllUser();

    /**
     * 通过用户名查询用户
     * @param username String
     * @return User entity.User
     */
    User queryUserByUsername(String username);

    /**
     * 判断Username是否存在
     * @param username STring
     * @return  int
     * case 0: 未被注册
     * case 1: 已被注册
     */
    int isUsernameExist(String username);

    /**
     * 判断邮箱email是否被注
     * @param email String
     * @return int
     * case 0: 未被注册
     * case 1: 已被注册
     */
    int isEmailExist(String email);

    /**
     * 判断昵称nickname 是否被注册
     *
     * @param nickname String
     * @return int
     * case 0: 未被注册
     * case 1: 已被注册
     */
    int isNicknameExist(String nickname);

    /**
     * 向数据库插入一个User
     *
     * @param user entity.User
     * @return int
     * case 0: 插入失败
     * case 1: 插入成功
     */
    int insertUser(User user);

    /**
     * 修改昵称
     * @param nickname 新的昵称
     * @return
     * case 0: 修改失败
     * case 1: 修改成功
     *
     */
    int updateNicknameByUsername(@Param("nickname") String nickname,
                                 @Param("username") String username);


    /**
     * 修改用户得个人简介
     * @param profile
     * @param username
     * @return
     */
    int updateProfileByUsername(@Param("profile") String profile,
                                @Param("username") String username);


    /**
     * 修改用户的头像地址
     * @param headImg
     * @param username
     * @return
     */
    int updateHeadImg(@Param("headImg") String headImg,
                      @Param("username") String username);

}
