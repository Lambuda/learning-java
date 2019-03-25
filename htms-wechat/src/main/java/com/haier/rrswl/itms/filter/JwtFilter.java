package com.haier.rrswl.itms.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 声明一个JWT过滤器类，从Http请求中提取JWT的信息，并使用了”secretkey”这个密匙对JWT进行验证
 *
 * @Author: Lin
 * @CreateDate: 2019/3/25 9:18
 */
public class JwtFilter extends GenericFilterBean {


    //请求头
    private static final String HEADER = "authorization";

    //认证规则
    private static final String AUTH_HEADER = "GxTms ";

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        // 强制转换
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        // Get authorization from Http request
        final String authHeader = request.getHeader(HEADER);

        // If the Http request is OPTIONS then just return the status code 200
        // which is HttpServletResponse.SC_OK in this code
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        } else {
            // 认证 token 规则
            if (authHeader == null || !authHeader.startsWith(AUTH_HEADER)) {
                throw new ServletException("认证 token 失败");
            }
            // 根据规则 获取正确的 token
            final String token = authHeader.substring(6);
            // Use JWT parser to check if the signature is valid with the Key "secretkey"
            final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
            // Add the claim to request header
            request.setAttribute("claims", claims);
            chain.doFilter(req, res);
        }
    }

}
