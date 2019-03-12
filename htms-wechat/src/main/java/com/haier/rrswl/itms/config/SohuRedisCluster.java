package com.haier.rrswl.itms.config;

import com.sohu.tv.builder.ClientBuilder;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import redis.clients.jedis.JedisCluster;

@Configuration
public class SohuRedisCluster {
    public static final Logger LOG = LoggerFactory.getLogger(SohuRedisCluster.class);

    @Value("${sohuredis.appId}")
    private long appId;
    @Value("${sohuredis.connectionTimeout}")
    private int connectionTimeout;
    @Value("${sohuredis.soTimeout}")
    private int soTimeout;
    @Value("${sohuredis.password}")
    private String password;

    final int maxRedirections = 5;
    private int maxTotal = 40;
    private int maxIdle = 40;

    @Bean
    public JedisCluster init() {
        LOG.info("jedisCluster 初始化！");
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        return ClientBuilder.redisCluster(appId).setJedisPoolConfig(poolConfig).setConnectionTimeout(connectionTimeout).setSoTimeout(soTimeout)
                .setMaxRedirections(maxRedirections).build(password);
    }

    /**
     * 读取限流脚本
     *
     * @return
     */
    @Bean
    public DefaultRedisScript<Number> redisluaScript() {
        DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimit.lua")));
        redisScript.setResultType(Number.class);
        return redisScript;
    }


}
