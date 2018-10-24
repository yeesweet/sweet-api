package com.sweet.api.test.redis;

import com.sweet.api.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * @author zhang.hp
 * @date 2018/9/18.
 */
@RunWith(SpringRunner.class)
@EnableConfigurationProperties
@SpringBootTest(classes = Application.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void setValueTest() {
        redisTemplate.opsForValue().set("111", "222");
        String value = redisTemplate.opsForValue().get("111");
        Assert.isTrue(value == "222", "");
    }


}
