package com.itheima.regiie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.regiie.common.BaseContext;
import com.itheima.regiie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName="loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    // 路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();

        log.info("拦截到请求"+request.getRequestURI());
        // 不需要处理的地址
        String[] urls = {"/employee/login","/employee/logout","/backend/**","/front/**","/common/**","/user/sendMsg","/user/login"};
        boolean check = check(urls,requestURI);

        if(check){
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("employee")!=null){
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
               return;
        }
        if(request.getSession().getAttribute("user")!=null){
            Long userId = (Long) request.getSession().getAttribute("userId");
            BaseContext.setCurrentId(userId);
        }

        response.getWriter().write(JSON.toJSONString(R.error("NoLogon")));
        return;
    }
    public boolean check(String[] urls,String requestURI){
        for(String url:urls){
           boolean match = PATH_MATCHER.match(url,requestURI);
           if(match){
               return true;
           }
        }
        return false;
    }

}
