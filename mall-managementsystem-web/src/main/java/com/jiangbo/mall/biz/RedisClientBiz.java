package com.jiangbo.mall.biz;

import com.jiangbo.mall.util.ProtobuffSerializationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Jiangbo Cheng on 2017/11/4.
 */
@Component
public class RedisClientBiz {

    private static final Logger logger = LoggerFactory.getLogger(RedisClientBiz.class);
    @Autowired
    ShardedJedisPool shardedJedisPool;

    /**
     * 设置单个值
     * @param key
     * @param value
     * @return
     */
    public String set (String key, Object value){
        String result = null;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return null;
        }

        try {
            result = shardedJedis.set(key, new String(ProtobuffSerializationUtil.serialize(value), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }finally {
            shardedJedis.close();
        }

        return result;
    }

    /**
     * @param key
     * @param value
     * @return
     * 设置List
     */
    public String setList(String key,List value){
        String result = null;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return result;
        }
        try {
            result = shardedJedis.set(key,new String(ProtobuffSerializationUtil.serializeList(value),"ISO-8859-1"));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally{
            shardedJedis.close();
        }
        return result;
    }

    /**
     * @param key
     * @param value
     * @param seconds
     * @return
     * 设置单个值和有效时间
     */
    public String set(String key,Object value,int seconds){
        String result = null;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return result;
        }
        try {
            result = shardedJedis.set(key,new String(ProtobuffSerializationUtil.serialize(value),"ISO-8859-1"));
            expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally{
            shardedJedis.close();
        }
        return result;
    }

    /**
     * @param key
     * @param value
     * @param seconds
     * @return
     * 设置List和有效时间
     */
    public String setList(String key,List value,int seconds){
        String result = null;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return result;
        }
        try {
            result = shardedJedis.set(key,new String(ProtobuffSerializationUtil.serializeList(value),"ISO-8859-1"));
            expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        } finally{
            shardedJedis.close();
        }
        return result;
    }

    /**
     * @param key
     * @param clazz
     * @return
     * 获取单个值
     */
    public <T> T get(String key,Class<T> clazz){
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return null;
        }
        try {
            String resultStr = shardedJedis.get(key);
            if(StringUtils.isEmpty(resultStr)) {
                return null;
            }
            return ProtobuffSerializationUtil.deserialize(resultStr.getBytes("ISO-8859-1"), clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        } finally{
            shardedJedis.close();
        }
        return null;
    }

    /**
     * @param key
     * @param clazz
     * @return
     * 获取List
     */
    public <T> List<T> getList(String key,Class<T> clazz){
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return null;
        }
        try {
            String resultStr = shardedJedis.get(key);
            if(StringUtils.isEmpty(resultStr)) {
                return null;
            }
            return ProtobuffSerializationUtil.deserializeList(resultStr.getBytes("ISO-8859-1"), clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        } finally{
            shardedJedis.close();
        }
        return null;
    }

    /**
     * @param key
     * @return
     * 判断key是否存在
     */
    public Boolean exists(String key){
        Boolean result = false;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return result;
        }
        try {
            result = shardedJedis.exists(key);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally{
            shardedJedis.close();
        }
        return result;
    }

    /**
     * @param key
     * @return
     * 删除key
     */
    public Boolean del(String key){
        Boolean result = false;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return result;
        }
        try {
            return shardedJedis.del(key) > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally{
            shardedJedis.close();
        }
        return result;
    }

    /**
     * @param key
     * @param seconds
     * @return
     * 设置key的过期时间段（单位秒）
     */
    public Long expire(String key,int seconds){
        Long result = null;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return result;
        }
        try {
            result = shardedJedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally{
            shardedJedis.close();
        }
        return result;
    }

    /**
     * 设置key的有效时间
     * @param key
     * @param unixTime
     * @return
     */
    public Long expire(String key, long unixTime){
        Long result = null;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return null;
        }
        try{
            result = shardedJedis.expireAt(key, unixTime);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }finally{
            shardedJedis.close();
        }

        return result;
    }
}
