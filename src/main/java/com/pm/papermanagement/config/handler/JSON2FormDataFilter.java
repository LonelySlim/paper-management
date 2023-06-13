//package com.pm.papermanagement.config.handler;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.MediaType;
//
//import java.io.IOException;
//
//public class JSON2FormDataFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException, IOException {
//        String contentType = request.getContentType();
//        // 1. 如果不是json类型的数据，就直接放行并且return了
//        if (!MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
//            filterChain.doFilter(request, response);
//            return; // tmd，一开始忘记return了，返回结果无比奇怪。。。。。。
//        }
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String requestURI = httpServletRequest.getRequestURI();
//
//        // 2. 如果url是登录url，就重新封装请求，json类型变成
//        if ("/api/user/login".equals(requestURI)) {
//            // 2.1 ParameterRequestWrapper是Request一个装饰器。
//            // 为什么要使用装饰器？因为内置Request不提供set方法重新setParameter，那就只能重新装饰getter了
//            ParameterRequestWrapper convertedRequest = new ParameterRequestWrapper(httpServletRequest);
//            filterChain.doFilter(convertedRequest, response);
//        } else {
//            filterChain.doFilter(request, response);
//        }
//    }
//}
