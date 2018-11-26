package com.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session监听器
 * @author Administrator
 */
@WebListener
public class SessionListener implements HttpSessionListener{

    private int onlineCount = 0;//记录session的数量
    
    /**
     * session创建后执行
     */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		onlineCount++;
        System.out.println("【HttpSessionListener监听器】 sessionCreated, onlineCount:" + onlineCount);
        se.getSession().getServletContext().setAttribute("onlineCount", onlineCount);
	}

	/**
	 * session失效后执行
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		if (onlineCount > 0) {
			onlineCount--;
		}
        System.out.println("【HttpSessionListener监听器】 sessionDestroyed, onlineCount:" + onlineCount);
        se.getSession().getServletContext().setAttribute("onlineCount", onlineCount);
	}

}
