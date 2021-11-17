package com.IndieAn.GoFundIndie.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
    @Value("#{info['gofund.url1']}")
    private String url1;

    @Value("#{info['gofund.url2']}")
    private String url2;

    @Value("#{info['gofund.url3']}")
    private String url3;

    @Value("#{info['gofund.url4']}")
    private String url4;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String ori = request.getHeader("Origin");
        if(ori != null){
            if(ori.equals(url1) || ori.equals(url2) || ori.equals(url3) || ori.equals(url4)){
                response.setHeader("Access-Control-Allow-Origin", ori);
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Methods","GET, POST, PUT, DELETE");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers",
                        "Origin, accesstoken, refreshtoken, X-Requested-With, Content-Type, Accept, Authorization, Access-Control-Allow-Methods, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Access-Control-Allow-Credentials");
            }
        }

        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {}
}