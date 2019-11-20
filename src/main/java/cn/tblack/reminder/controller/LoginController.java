package cn.tblack.reminder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	/**
	 * 返回登录视图
	 * @param error
	 * @param logout
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();

		if (error != null) {
			mav.addObject("error", "用户名或密码错误");
		}
		if (logout != null) {
			mav.addObject("msg", "注销成功!");
		}

		mav.setViewName("/login");
		return mav;
	}

}
