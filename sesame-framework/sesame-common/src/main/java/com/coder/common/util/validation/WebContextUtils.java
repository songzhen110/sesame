package com.coder.common.util.validation;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * WebContextUtils implements ApplicationContextAware after ，could get IOC
 * @author lucas
 * @see org.springframework.context.ApplicationContextAware
 * @since 1.0
 */
public final class WebContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static final String COMPUTER   = "C"         ;
    public static final String MOBILE     = "M"         ;
    public static final String UNKNOWN    = "Unknown"   ;
    public static final String USER_AGENT = "User-Agent";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WebContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 从IOC中获得对象: 根据类型
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 从IOC中获得对象: 根据id
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    /**
     * 从IOC中获得多个对象(实现接口或继承的)
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
        return applicationContext.getBeansOfType(requiredType);
    }

    /**
     * 获得当前请求对象
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取请求路径
     */
    public static String getRealPath(String path) {
        return getHttpServletRequest().getServletContext().getRealPath(path);
    }

    /**
     * 取得设备类型
     */
    public static String getDeviceType() {
        String userAgent = getHttpServletRequest().getHeader(USER_AGENT);
        return getDeviceType(userAgent);
    }

    /**
     * 获得设备类型
     */
    private static String getDeviceType(String userAgentHeader) {
        if (userAgentHeader.contains("computer")) {
            return COMPUTER;
        }
        if (userAgentHeader.contains("Andriod") || userAgentHeader.contains("iPhone") || userAgentHeader.contains("iPad")) {
            return MOBILE;
        }
        return UNKNOWN;
    }

    /**
     * 取得浏览器
     */
    public static String getBrowser(String userAgentHeader) {
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentHeader);
        String brower = userAgent.getBrowser().getName();

        //考虑手机端的浏览器访问,此处采用解析为Unknown时空格分隔取第一个
        if (Browser.UNKNOWN.getName().equals(brower) && userAgentHeader != null) {
            brower = userAgentHeader.split(" ")[0];
        }
        return brower;
    }

    /**
     * 取得操作系统
     */
    public static String getOsName(String userAgentHeader) {
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentHeader);
        return userAgent.getOperatingSystem().getName();
    }

    /**
     * 根据Request获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，
     * 而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        // 代理处理
        String ip = request.getHeader("x-forwarded-for");
        if (blankOrUnknown(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (blankOrUnknown(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (blankOrUnknown(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (blankOrUnknown(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        // 多级代理
        if (StringUtils.hasLength(ip) && ip.contains(",")) {
            ip = ip.split(",")[0];
        }

        // 正常处理
        if (blankOrUnknown(ip)) {
            ip = request.getRemoteAddr();
        }

        // 特殊设置
        if ("0:0:0:0:0:0:0:1".equals(ip) || "localhost".equals(ip)) {
            ip = "127.0.0.1";
        }

        //非空限定
        if (ip == null) {
            ip = "unknown";
        }

        return ip;
    }

    /**
     * IP地址为空(白)或为unknown
     */
    private static boolean blankOrUnknown(String ip) {
        return !StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip);
    }

    /**
     * 是否为ajax请求
     */
    public static boolean isAjax(HttpServletRequest request) {
        //如果是ajax请求响应头会有，x-requested-with
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        }
        return false;
    }
}
