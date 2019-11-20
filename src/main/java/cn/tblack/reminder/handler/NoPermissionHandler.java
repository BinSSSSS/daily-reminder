package cn.tblack.reminder.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @触发没有权限访问的处理对象
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Component
public class NoPermissionHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
//		System.err.println("被拒绝的请求路径为: " + request.getRequestURL());
		// 判断前台请求数据是以 Ajax 的方式还是直接请求的方式
		if (isAjax(request)) {
			reponseWithJson(response); // 使用流的方式写回数据
		} else {
			responseWithForward(request, response); // 使用转发的方式响应数据
		}
	}

	/**
	 * @ 使用 请求加转发的方式进行响应
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void responseWithForward(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/error/403").forward(request, response);
	}

	/**
	 * @ 使用流的方式进行相应
	 * 
	 * @param response
	 */
	private void reponseWithJson(HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");

		try {

			System.err.println("进行数据的响应");

			PrintWriter writer = response.getWriter();
			Map<String, Object> result = new HashMap<>();

			result.put("code", 403);
			result.put("message", "当前用户无权限进行访问");
			// 以 Json 数据格式进行写入
			writer.write(new ObjectMapper().writeValueAsString(result));
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}
}
