package cn.tblack.reminder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new WebMethodInterceptor());
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		// 将前台传递的日期格式进行转换
		registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
	}

}
