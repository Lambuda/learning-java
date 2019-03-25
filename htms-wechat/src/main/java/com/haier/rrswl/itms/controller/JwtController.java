package com.haier.rrswl.itms.controller;

import com.haier.rrswl.itms.annotation.RateLimit;
import com.haier.rrswl.itms.domain.ReqPerson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.Date;

/**
 * jwt 资源认证 控制器
 *
 * @Author: Lin
 * @CreateDate: 2019/3/25 9:28
 */
@RestController
@RequestMapping("/jwt")
public class JwtController {

    //认证标记
    private static final String REMOTING_AUTHENTICATION_SIGN = "itms_remoting";

    //超时时间
    private static final Long TOKEN_TIME_OUT = 7200 * 1000L;

    /**
     * 校验用户 返回 token
     * 12个小时 只允许认证10次
     *
     * @param reqPerson
     * @return jwt token
     * @throws ServletException
     */
    @RateLimit(key = "jwt_authentic", time = "43200", count = "10")
    @PostMapping("/auth")
    public String authentic(@RequestBody ReqPerson reqPerson) throws ServletException {
        if (StringUtils.isEmpty(reqPerson.getPassword()) || StringUtils.isEmpty(reqPerson.getUserName()) || StringUtils.isEmpty(reqPerson.getSign()))
            throw new ServletException("参数不能为空");

        // 认证标记 是否匹配
        if (!REMOTING_AUTHENTICATION_SIGN.equals(reqPerson.getSign())) throw new ServletException("认证标记 不符");

        // 创建 Twt token
        String jwtToken = Jwts.builder()
                .setSubject(reqPerson.getUserName())
                .claim("roles", "member")
                .setIssuedAt(new Date())
                //设置 token 超时时间2个小时
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_TIME_OUT))
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();

        return jwtToken;
    }
}
