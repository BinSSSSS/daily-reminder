package cn.tblack.reminder.controller;

import static cn.tblack.reminder.constant.WebConfigProperties.VMAIL_SEND_INTERVAL;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import cn.tblack.reminder.constant.VMailContent;
import cn.tblack.reminder.entity.User;
import cn.tblack.reminder.entity.VerificationMail;
import cn.tblack.reminder.mail.EmailSender;
import cn.tblack.reminder.result.VCodeResult;
import cn.tblack.reminder.result.ValidateResult;
import cn.tblack.reminder.result.WebResult;
import cn.tblack.reminder.service.UserRoleService;
import cn.tblack.reminder.service.UserService;
import cn.tblack.reminder.service.VerificationMailService;
import cn.tblack.reminder.util.CodeGenerator;
import cn.tblack.reminder.util.MD5Utils;
import cn.tblack.reminder.util.WeightsUtils;

/**
 * @用户注册用户的控制器
 * @author TD唐登
 * @Date:2019年10月31日
 * @Version: 1.0(测试版)
 */
@RestController
public class RegisterController {

	private Logger log = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private VerificationMailService verificationMailService;

	@Autowired
	private EmailSender emailSender;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public WebResult register(User user, String code) {

		WebResult result = new WebResult();

		// 在数据库中查询是否存在该用户和邮箱。 在前台每次填写完成之后，我们都会异步的进行检查是否存在该用户名或者是邮箱
		// 这里再次检查是为了放置出现其他意外
		if (userService.canBeRegister(user)) {
			// 如果该用户没有被注册过，那么则检查传递的邮箱验证码是否正确
			VerificationMail vm = verificationMailService.findLastMail(user.getEmail()); // 拿到最后一封发送给该邮箱的验证码对象

			// 检查vm是否已经过期
			Date date = vm.getDeadline();

			// 如果未过期的话，检查验证码是否相同
			if (date.getTime() > System.currentTimeMillis()) {

				// 如果验证码相同,注册该用户
				if (vm.getCode().equalsIgnoreCase(code)) {
					
					//将密码进行加密
					user.setPassword(MD5Utils.encrypt(user.getPassword()));
					try {
						userService.save(user);
						//给该用户分配角色
						userRoleService.grantUserRole(user);
						result.setMsg("注册成功");
						result.setSuccess(true);
					}catch(Exception e) {
						log.error("注册用户失败:[  " + user + "], 失败原因为:" +  e.getMessage());
						e.printStackTrace();
						result.setSuccess(false);
						result.setMsg("服务器出了点小差~稍后再试");
					}
				}
			}
		}

		return result;
	}

	@RequestMapping(value = "/verify-username", method = RequestMethod.POST)
	/**
	 * @检测某个用户名是否可用
	 * @return
	 */
	public ValidateResult isAvailableUsername(String username) {
		
		ValidateResult result = new ValidateResult(false);
		
		if (userService.findByUsername(username) == null) {
			result.setValidate(true);
		}
		
		return result;
	}

	@RequestMapping(value = "/verify-email", method = RequestMethod.POST)
	/**
	 * @验证某个邮箱地址是否可用
	 * @param email
	 * @return
	 */
	public ValidateResult isAvailableEmail(@Email String email) {
		ValidateResult result = new ValidateResult(false);

		if (userService.findByEmail(email) == null) {
			result.setValidate(true);
		}

		return result;
	}

	@RequestMapping(value = "/send-email-vcode", method = RequestMethod.POST)
	public VCodeResult sendVerificationCode(@Email @NotNull String email) {

		VCodeResult result = new VCodeResult(false, "该邮箱已经发送过邮件！ 请查看邮箱");

		// 首先在数据库中查找该邮箱是否已经发送给邮件并拿到最后发送的邮件信息
		VerificationMail vm = verificationMailService.findLastMail(email);

		Calendar calendar = new GregorianCalendar();
		
		boolean needSend =  true;
		if (vm != null) {
			Date date = vm.getDeadline();

			
			Date current = calendar.getTime();

			// 如果该邮件未失效， 那么则设置还需要等待的时间到前台
			if (date.getTime() > current.getTime()) {
				
				result.setSeconds(
						Long.valueOf((date.getTime() - current.getTime()) / 1000)  //将毫秒除1000拿到秒
						.intValue());
				needSend = false;
			}
		}
		if(needSend) {
			VerificationMail newVm = new VerificationMail();

			String vcode = CodeGenerator.createFourNumberVerficationCode();
			newVm.setCreateTime(calendar.getTime());
			newVm.setDeadline(new Date(calendar.getTimeInMillis() + VMAIL_SEND_INTERVAL));
			newVm.setCode(vcode);
			newVm.setRecipientAddress(email);
			newVm.setWeights(WeightsUtils.timeWeights(calendar));

			// 发送邮件--
			Future<Boolean> success = emailSender.send(email, VMailContent.TITLE,
					VMailContent.getDefaultContent(vcode, VMAIL_SEND_INTERVAL));

			// 如果发送成功，才向数据库内写入数据，否则等待
			try {
				if (success.get()) {
					// 需要对数据库进行插入操作
					verificationMailService.save(newVm);
				
//					return new AsyncResult<VCodeResult>(new VCodeResult(true,"发送成功!",VMAIL_SEND_INTERVAL / 1000));
					return new VCodeResult(true, "发送成功!",VMAIL_SEND_INTERVAL / 1000);
				}else {
					//邮件发送失败！检查邮箱地址是否正确
//					return new AsyncResult<VCodeResult>(new VCodeResult(false,"邮件发送失败！检查邮箱地址是否正确！",1));
					return new VCodeResult(false, "邮件发送失败！检查邮箱地址是否正确！",1);
				}
			} catch (InterruptedException | ExecutionException e) {
				
				e.printStackTrace();
			}
		}
		return result;
//		return new AsyncResult<VCodeResult>(result);
	}
}
