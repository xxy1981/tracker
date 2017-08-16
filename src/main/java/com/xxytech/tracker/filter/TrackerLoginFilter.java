package com.xxytech.tracker.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

@Service("trackerLoginFilter")
public class TrackerLoginFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(TrackerLoginFilter.class);

    public static final String  LOGIN_SEESION_USER_INFO = "l_s_u_i";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                              ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession();
        String loginUser = (String) session.getAttribute(LOGIN_SEESION_USER_INFO);
        
        String uri = httpReq.getRequestURI();
        if(uri.startsWith("/partner") || uri.startsWith("/campaign")){
            if (StringUtils.isBlank(loginUser)) {
                httpResp.sendRedirect("/login");
            }
        }
        
        chain.doFilter(request, response);
        return;
    }
}
