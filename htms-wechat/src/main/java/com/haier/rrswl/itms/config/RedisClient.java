package com.haier.rrswl.itms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * redis 客户端
 *
 * @Author: Lin
 * @CreateDate: 2019/3/4 17:45
 */
@Configuration
public class RedisClient {

    @Resource
    private JedisCluster jedisCluster;

    @Resource
    private DefaultRedisScript<Number> redisScript;

    /**
     * @param key  key
     * @param size 限流人数
     * @param time 限流时间
     * @return Boolean 是否满员
     */
    public boolean rateLimit(String key, String size, String time) {
        List<String> keys = Arrays.asList(key);
        List<String> args = Arrays.asList(size, time);//最大限制10人 2秒钟
        Long eval = (Long) jedisCluster.eval(redisScript.getScriptAsString(), keys, args);
        if (0L == eval) return true;
        return false;
    }


}
