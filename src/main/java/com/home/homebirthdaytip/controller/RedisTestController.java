package com.home.homebirthdaytip.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @author: hemb
 * @date: 2020年10月20日 11:25
 */
@RestController
public class RedisTestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostMapping("/set")
    public void set(String key, String value) {
        logger.info("正在往redis存入key为"+key+"值为"+value);
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @GetMapping("/get")
    public String show(String key) {
        logger.info("正在往redis取出key为"+key+"value为"+stringRedisTemplate.opsForValue().get(key));
        return stringRedisTemplate.opsForValue().get(key);
    }
}
