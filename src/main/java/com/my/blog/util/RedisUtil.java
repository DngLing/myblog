package com.my.blog.util;

import com.my.blog.common.exception.CommonExceptionEnum;
import com.my.blog.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 15:15 2019/5/15
 * @Modified By:
 */
@Component
public class RedisUtil {
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private StringRedisTemplate template = null;


    /**
     * set （key,value） 键值对方法
     *
     * @param key   String 键
     * @param value String 值
     * @return boolean
     * case true: 插值成功
     * case false: 插值失败
     */
    public boolean set(String key, String value) {
        try {
            template.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.info(e.toString());
            return false;
        }
    }


    /**
     * get 获得键 key的值
     *
     * @param key String 键
     * @return String key键的值
     */
    public String get(String key) throws CustomException {
        try {
            return template.opsForValue().get(key);
        }catch (Exception e){
            throw new CustomException(CommonExceptionEnum.SERVICE_ERR);
        }
    }


    /**
     * 添加key-value键值对缓存并设置超时时间(插入缓存开始后 time(ms)内有效，time(ms)后失效)
     *
     * @param key   String 键
     * @param value String 值
     * @param time  long 毫秒
     * @return
     */
    public boolean setEmailWithTimeout(String key, String value, long time) throws CustomException {
        try {
            template.opsForValue().set("email:"+key, value, time, TimeUnit.MILLISECONDS);
            return true;
        } catch (Exception e) {
            if(e instanceof RedisConnectionFailureException){
                throw new CustomException(CommonExceptionEnum.REDIS_CONNECT_ERR);
            }else {
                throw e;
            }
        }
    }

    public boolean setTokenWithTimeout(String key, String value, long time) throws CustomException {
        try {
            template.opsForValue().set("token:"+key,value,time,TimeUnit.MILLISECONDS);
            return true;
        }catch (Exception e){
            if(e instanceof RedisConnectionFailureException){
                throw new CustomException(CommonExceptionEnum.REDIS_CONNECT_ERR);
            }else {
                throw e;
            }
        }
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void deleteKey(String key) {
        try {
            template.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
