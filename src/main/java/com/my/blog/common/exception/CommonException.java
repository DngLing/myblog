package com.my.blog.common.exception;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 17:11 2019/5/10
 * @Modified By:
 */
public interface CommonException {
    /**
     * 获得移除标识码
     * @return int 异常标识码
     */
    int getExceptionCode();

    /**
     * 获得异常信息
     * @return String 异常信息
     */
    String getExceptionMsg();

    /**
     * 设置异常信息
     * @param exceptionMsg
     * @return
     */
    CommonException setExceptionMsg(String exceptionMsg);
}
