package com.aop;

import com.util.DateUtil;
import com.util.JsonUtils;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;



/**
 * 系统日志，切面处理类
 */
@Aspect
@Configuration
public class SysLogAspect {
	
	/**
	 * 定义切入点（pointcut）对连接点进行拦截的定义
	 */
	@Pointcut("execution (* com.hystrix.*Hystrix.*(..))") //用于匹配子表达式。
	public void logPointCut() { 
		System.out.println("logPointCut() run...");
	}
	
	/**
	 * 通知（advice） 前置通知：在某连接点之前执行的通知
	 * 定义增强处理，增强处理就是在AOP框架为普通业务组件织入的处理动作
	 * @param joinPoint 连接点（joinpoint）被拦截到的点，因为Spring只支持方法类型的连接点，所以在Spring中连接点指的就是被拦截到的方法
	 */
	@Before("logPointCut()")
	public void printBeforeAdvice(JoinPoint joinPoint) {
		System.out.println(this);
//		System.out.println(joinPoint.toString());
//		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//		Method method = signature.getMethod();
//		
//		String className = joinPoint.getTarget().getClass().getName();
//		//请求的方法名
//		String methodName = signature.getName();
//		System.out.println(className + "." + methodName + "()");
		//请求的参数
		Object[] args = joinPoint.getArgs();
		String params = JsonUtils.object2Json(args);
		//获取request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//设置IP地址
		System.out.println(joinPoint.toString() + ":start^^^^^^" + DateUtil.getCurrentDate(DateUtil.DATE_24FULL_MS_STR));
		System.out.println(getIpAddr(request));
		System.out.println(params);
	}
	
	/**
	 * 最终通知。当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）
	 * @param joinPoint 连接点
	 */
	@After("logPointCut()")
	public void printAfterAdvice(JoinPoint joinPoint){
		System.out.println(joinPoint.toString() + ":end^^^^^^" + DateUtil.getCurrentDate(DateUtil.DATE_24FULL_MS_STR));
	}
	/**
	 * 获取IP地址
	 * 
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public String getIpAddr(HttpServletRequest request) {
    	String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
        	System.err.println("IPUtils ERROR:" + e);
        }
        
//        //使用代理，则获取第一个IP地址
//        if(StringUtils.isEmpty(ip) && ip.length() > 15) {
//			if(ip.indexOf(",") > 0) {
//				ip = ip.substring(0, ip.indexOf(","));
//			}
//		}
        
        return ip;
    }
}
