package com.sweet.api.test;

import com.sweet.api.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableConfigurationProperties
@SpringBootTest(classes = Application.class)
public class AppTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis(){
        stringRedisTemplate.opsForValue().set("second","test");
        final String second = stringRedisTemplate.opsForValue().get("second");
        Assert.assertEquals(second,"test");
    }

}
