package cn.tblack.reminder.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class WebMethodInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.err.println("当前请求页面为: " + request.getRequestURL());
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
