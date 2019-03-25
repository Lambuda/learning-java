package com.haier.rrswl.itms.config;

import com.haier.rrswl.itms.filter.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 生成一个过滤器，对 /remoting 连接下的资源进行过滤
 *
 * @Author: Lin
 * @CreateDate: 2019/3/25 9:16
 */
@Configuration
public class JwtCfg {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtCfg.class);

    @Bean
    public FilterRegistrationBean jwtFilter() {

        LOGGER.info("Remoting 资源过滤器启用 ~~~");

        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/remoting/*");
        return registrationBean;
    }
}
