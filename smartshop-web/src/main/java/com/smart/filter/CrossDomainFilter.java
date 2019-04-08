package com.smart.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class  CrossDomainFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpServletRequest req = (HttpServletRequest) request;
            String clientOrigin = req.getHeader("Origin");
            resp.setHeader("Access-Control-Allow-Origin", clientOrigin);
            resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            resp.setHeader("Access-Control-Max-Age", "0");
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, _security_token, TT,Souche-Security-Token,souche-security-token");
            chain.doFilter(request, resp);
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            System.out.println("BB");
        }

        @Override
        public void destroy() {
        }
    }

