package com.haier.rrswl.itms.controller;

import com.haier.rrswl.itms.annotation.RateLimit;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * 限流接口
 */
@Controller
@RequestMapping("/api/")
public class LimiterController {

    private int count;

    // 10 秒中，可以访问10次
    @RateLimit(key = "LimiterController.luaLimiter", time = "10", count = "10")
    @GetMapping("/test")
    @ResponseBody
    public String luaLimiter() {
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
        count++;
        return date + " 累计访问次数：" + count;
    }
}
