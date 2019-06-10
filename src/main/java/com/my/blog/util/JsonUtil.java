package com.my.blog.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 15:14 2019/5/15
 * @Modified By:
 */
@Component
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将实例对象转化为json格式字符串
     *
     * @param obj 实例对象
     * @param <T> 泛型
     * @return String json字符串
     */
    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 将json格式字符串转化为实例对象
     *
     * @param str   String json格式字符串
     * @param clazz Class<T>实例对象的类
     * @param <T>
     * @return 实例对象
     */
    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
