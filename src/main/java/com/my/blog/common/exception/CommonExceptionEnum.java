package com.my.blog.common.exception;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 17:15 2019/5/10
 * @Modified By:
 */
public enum CommonExceptionEnum implements CommonException{

    SERVICE_ERR(1,"服务繁忙"),
    USERNAME_ALREADY_EXIST(2,"用户名已存在"),
    USERNAME_NOT_EXIST(3,"用户不存在"),
    NICKNAME_ALREADY_EXIST(4,"昵称已存在"),
    NICKNAME_NOT_EXIST(5,"该昵称不存在"),
    EMAIL_ALREADY_EXIST(6,"邮箱已存在"),
    PASSWORD_ERR(7,"密码错误"),
    EMPTIY_USERNAME(8,"用户名为空"),
    EMPTY_PASSWORD(9,"密码为空"),
    EMPTY_NICKNAME(10,"昵称为空"),
    TOO_LONG_ERR(11,"超过字数限制"),
    HEADIMG_NOT_JPG(12,"头像图片格式不正确"),
    UPLOAD_FILE_FAIL(13,"上传文件失败"),

    //30
    SEND_EMAIL_FAIL(30,"发送邮件失败"),
    VERIFY_CODE_TIMEOUT(31,"验证码失效"),
    VERIFY_CODE_ERR(32,"验证码输入错误"),
    REDIS_CONNECT_ERR(33,"未连接到Redis服务器"),

    //40token 验证
    NO_PERMISSION(40,"无权限登陆"),
    LOGIN_EXP(41,"登陆失效"),
    LOGIN_TIMEOUT(42,"验证失效请重新登陆");


    private int exceptionCode;
    private String exceptionMsg;

    CommonExceptionEnum(int exceptionCode, String exceptionMsg) {
        this.exceptionCode = exceptionCode;
        this.exceptionMsg = exceptionMsg;
    }

    @Override
    public int getExceptionCode() {
        return this.exceptionCode;
    }

    @Override
    public String getExceptionMsg() {
        return this.exceptionMsg;
    }

    @Override
    public CommonException setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
        return this;
    }}
