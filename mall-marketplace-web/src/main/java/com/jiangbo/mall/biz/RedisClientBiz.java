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
import java.util.UUID;

/**
 * @author Jiangbo Cheng on 2017/11/4.
 */
@Component
public class RedisClientBiz {

    private static final Logger logger = LoggerFactory.getLogger(RedisClientBiz.class);
    @Autowired
    ShardedJedisPool shardedJedisPool;

    private static final String RESUBMIT_TOKEN_PREFIX = "placeOrder:token:";

    /**
     * 设置单个值
     * @param key
     * @param value
     * @return
     */
    public String set(String key, Object value){
        String result = null;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return null;
        }

        try {
            result = shardedJedis.set(key, value.toString());
        } finally {
            shardedJedis.close();
        }

        return result;
    }

    /**
     * 设置单个对象值
     * @param key
     * @param value
     * @return
     */
    public String setObject(String key, Object value){
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
            result = shardedJedis.set(key, value.toString());
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
     * 设置单个对象和有效时间
     */
    public String setObject(String key,Object value,int seconds){
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
    public <T> T getObject(String key,Class<T> clazz){
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
     * @return
     * 获取单个值
     */
    public Object get(String key){
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return null;
        }
        try {
            String resultStr = shardedJedis.get(key);
            if(StringUtils.isEmpty(resultStr)) {
                return null;
            }
            return resultStr;
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

    /**
     * 根据key，使其value自加1
     * @param key
     * @return
     */
    public Long incr(final String key) {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return null;
        }

        //进行加1操作
        return shardedJedis.incr(key);
    }

    /**
     * 根据key，使其value自加指定的值
     * @param key
     * @return
     */
    public Long incr(final String key,int value) {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        if(shardedJedis == null){
            return null;
        }

        //将value进行指定值的添加操作
        return shardedJedis.incrBy(key, value);
    }

    /**
     * 创建token，放到redis中
     * token作为key, 并设置过期时间
     * value为数字 初始值设为0
     * @param userId
     * @return
     */
    public String createToken(Integer userId){
        String key = RESUBMIT_TOKEN_PREFIX + userId;
        String token = UUID.randomUUID().toString();
        //保存到redis
        this.set(key+token, 0, 1000);
        return token;
    }

    /**
     * 检查token是否有效
     * @param userId
     * @param token
     * @return
     */
    public boolean checkToken(Integer userId, String token){
        String key = RESUBMIT_TOKEN_PREFIX + userId;
        if(this.get(key + token) != null){
            long time = this.incr(key + token);
            if(time == 1){
                //利用increment原子性，判断该token是否使用过
                return true;
            }
            this.del(key + token);
        }

        return false;
    }
}
