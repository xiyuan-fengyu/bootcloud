package com.xiyuan.bootcloud.bean;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;


/**
 * Created by xiyuan_fengyu on 2018/6/15 17:01.
 */
@Configuration
public class JedisPoolBean {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    JedisPool jedisPool() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
        poolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        poolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
        poolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());
        return new JedisPool(poolConfig,
                redisProperties.getHost(),
                redisProperties.getPort(),
                (int) redisProperties.getTimeout().toMillis(),
                redisProperties.getPassword(),
                redisProperties.getDatabase());
    }

}
