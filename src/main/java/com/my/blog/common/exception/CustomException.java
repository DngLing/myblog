package com.my.blog.common.exception;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 17:20 2019/5/10
 * @Modified By:
 */
public class CustomException extends Exception implements CommonException {

    private CommonException commonException;

    public CustomException(CommonException commonException){
        super();
        this.commonException = commonException;
    }

    public CustomException(CommonException commonException, String exceptionMsg){
        super();
        this.commonException = commonException;
        this.commonException.setExceptionMsg(exceptionMsg);
    }

    public void setCommonException(CommonException commonException){
        this.commonException = commonException;
    }


    @Override
    public int getExceptionCode() {
        return this.commonException.getExceptionCode();
    }

    @Override
    public String getExceptionMsg() {
        return this.commonException.getExceptionMsg();
    }

    @Override
    public CommonException setExceptionMsg(String exceptionMsg) {
        this.setExceptionMsg(exceptionMsg);
        return this;
    }
}
