package cn.tblack.reminder.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @控制器的全局的异常捕获器
 * @author TD唐登
 * @Date:2019年11月6日
 * @Version: 1.0(测试版)
 */
@ControllerAdvice
public class ControllerGlobalExceptionHandler {
	
	private Logger log = LoggerFactory.getLogger(ControllerGlobalExceptionHandler.class);
	
	@ExceptionHandler
	/**
	 * @捕获所有类型的异常并转发到Error错误页面。
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 */
	public String exceptionGlobalHandler(HttpServletRequest request,HttpServletResponse response,Exception e) {
		
		request.setAttribute("url", request.getRequestURL());
		request.setAttribute("errorMessage", e.getMessage());
		request.setAttribute("stackTrace", e.getStackTrace());
		
		log.error("请求 链接: 【" + request.getRequestURL() + " 】出现了异常: " + e.getClass().getName());
		log.error( "异常信息为: " +  e.getMessage());
		
		
		return "error";
	}
}
