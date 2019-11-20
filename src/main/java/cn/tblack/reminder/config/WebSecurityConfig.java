package cn.tblack.reminder.config;

import static cn.tblack.reminder.constant.WebConfigProperties.ALLOW_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import cn.tblack.reminder.encoder.CustomMD5PasswordEncoder;
import cn.tblack.reminder.handler.NoPermissionHandler;
import cn.tblack.reminder.service.RDUserDetailsService;

/**
 * @配置当前Web应用的安全配置。 如： 放行静态资源、设置登录页面、添加自定义编码器和自定义的 UserDetailService
 * 
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启@Pre*注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	/**
	 * @自定义的UserDetailsService，从数据库中拿到数据
	 */
	private RDUserDetailsService mUserDetailsService;

	@Autowired
	/**
	 * @无权限访问时处理器
	 */
	private NoPermissionHandler noPermissionHandler;
	
	@Bean
	/**
	 * @使用自定义的密码编码器进行对密码进行编码解码比较处理
	 * @return
	 */
	public CustomMD5PasswordEncoder customPasswordEncoder() {

		return new CustomMD5PasswordEncoder();
	}

	@Override
	/**
	 * @配置不需要拦截的资源路径
	 */
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(ALLOW_PATH); // 配置不需要拦截的路径， 该路径在配置文件中配置
		
//		for (String s : ALLOW_PATH) {
//			
//			System.err.println(s);
//		}
//		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// 配置自定义的编码器进行编码比较
		auth.userDetailsService(mUserDetailsService).passwordEncoder(customPasswordEncoder());
	}

	@Override
	/**
	 * @配置登录页面、表单等
	 */
	protected void configure(HttpSecurity http) throws Exception {

		// 设置登录功能相关配置, permitAll表示该 login/** 路径请求全部放行， anyRequest 是任何请求，
		// authenticated表示允许所有的验证用户
		http.authorizeRequests().antMatchers("/login/**").permitAll()
				.anyRequest().authenticated().and().formLogin()
				.loginPage("/login") // 设置登录页面
				.loginProcessingUrl("/login") // 设置触发登录的url链接
				.usernameParameter("username") // 登录页面的用户提交字段名
				.passwordParameter("password") // 登录页面的密码提交字段名
				.defaultSuccessUrl("/add-reminder") // 登录成功之后跳转到的页面
				.and()
				.exceptionHandling() // 异常处理器
				.accessDeniedHandler(noPermissionHandler) // 当请求被拒绝时进行的处理器
				.and().logout() // 登出处理
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout"); // 登出成功的url
		
	}
}
