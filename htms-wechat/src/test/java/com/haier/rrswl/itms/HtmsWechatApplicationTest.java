package com.haier.rrswl.itms;

import com.haier.rrswl.itms.config.RedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HtmsWechatApplicationTest {

    @Resource
    private RedisClient redisClient;

    @Test
    public void test(){
        redisClient.rateLimit("test1","3","200");
    }


    @Test
    public void test4() throws Exception {
        File file = ResourceUtils.getFile("classpath:rateLimit.lua");
        Files.readAllLines(Paths.get(file.getPath())).forEach(e -> {
            System.out.println(e);
        });
    }


}
