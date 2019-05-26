package com.cc.libra.interceptors;

import com.cc.libra.models.Constants;
import com.cc.libra.models.UserInfo;
import com.cc.libra.service.CacheService;
import com.cc.libra.utils.ToolsSpringContextUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用sso拦截登录需要如下步骤
 * 1.配置初始化SpringSSOInterceptor
 *      配置loginUrlPrefix 不配会报错
 *      放入对应的缓存service  不放则用本地的缓存校验登录信息
 * 2.配置SSOServlet urlPatten为ssoLogin
 * 3.配置初始化SSOAction 放入对应的缓存service  不放则用本地的缓存校验登录信息 配置beanId为：ssoAction
 * Created by chenchen39 on 2018/9/30.
 */
public class SpringSSOInterceptor implements HandlerInterceptor {

    private String serviceName = "cacheService";
    private String loginUrlPrefix = null;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        CacheService cacheService = ToolsSpringContextUtil.getBean(serviceName);
        if(cacheService == null) {
            throw new IllegalStateException("sso缓存service未注入");
        }
        if(StringUtils.isBlank(loginUrlPrefix)) {
            throw new IllegalStateException("loginUrl未注入");
        }
        String sourceUrl = request.getRequestURL().toString();
        String redirectUrl = loginUrlPrefix + "/ssoLogin?" + Constants.RETURN_URL +"=" + sourceUrl;

        String ticketName = getCookieValue(request, Constants.SSO_COOKIE);
        if(StringUtils.isBlank(ticketName)) {
            response.sendRedirect(redirectUrl);
            return false;
        }
        UserInfo userInfo = cacheService.getCacheUser(ticketName);
        if(userInfo == null) {
            response.sendRedirect(redirectUrl);
            return false;
        }
        return true;
    }

    public static String getCookieValue(HttpServletRequest request,String cookieKey) {
        if(request == null || StringUtils.isBlank(cookieKey)) {
            return null;
        }
        Cookie [] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0) {
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookieKey.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLoginUrlPrefix() {
        return loginUrlPrefix;
    }

    public void setLoginUrlPrefix(String loginUrlPrefix) {
        this.loginUrlPrefix = loginUrlPrefix;
    }
}
