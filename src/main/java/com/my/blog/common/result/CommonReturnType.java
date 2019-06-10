package com.my.blog.common.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 11:23 2019/5/15
 * @Modified By:
 */
@Getter
@Setter
@ToString
public class CommonReturnType {
    private int code;
    private String msg;
    private Object data;

    public CommonReturnType() {

    }

    public CommonReturnType(Object data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public CommonReturnType(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
