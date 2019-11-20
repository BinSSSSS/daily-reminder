package cn.tblack.reminder.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.reminder.entity.MailSender;
import cn.tblack.reminder.entity.Schedule;
import cn.tblack.reminder.mail.EmailSender;
import cn.tblack.reminder.schudler.ReminderScheduler;
import cn.tblack.reminder.service.MailSenderService;
import cn.tblack.reminder.service.ReminderService;
@RestController
public class WebController {

	@SuppressWarnings("unused")
	@Autowired
	private ReminderService reminderService;

	@SuppressWarnings("unused")
	@Autowired
	private MailSenderService mailSenderService;
	@SuppressWarnings("unused")
	@Autowired
	private ReminderScheduler reminderScheduler;
	@SuppressWarnings("unused")
	@Autowired
	private EmailSender emailSender;
	
	@RequestMapping("/home")
//	@PreAuthorize("hasRole('USER')")
	public String home()   {

//		Reminder reminder = reminderService.findById(1);
//
//		System.err.println(reminder);
	
		
	//	ReminderTask task = new ReminderTask(reminder, emailSender);
		
//		reminderScheduler.addReminderJob(task);
		
//		throw new AuthException("无权限访问");
		
		return "home";
	}
	
	@RequestMapping(value= "/reminders")
	public String reminder(MailSender mailSender,Schedule schedule, String delayCron) {
		
		
		//在创建一个重复性的定时任务的时候， 则需要注意： 这个重复的定时任务将会在就将来的某个时刻进行完成。
		//mailSender表示的是邮件消息主体。包含标题、附件、内容等、
		//但是要发送到哪个邮箱则需要Username来进行在User表中查找
		//其次：schedule对象内存在着一个下次执行的时间。
		//并且需要有一个间隔时间的参数被传递进来，以小时为单位
		
		//判断间隔时长-我们初始设置的最大间隔时长为一个月一次。也就是意味着最大不超过 24 * 30
		
//		System.err.println(request.getParameterMap());
		System.err.println("当前用户信息: " + SecurityContextHolder.getContext().getAuthentication());
		System.err.println("MailSender:" + mailSender);
		System.err.println("Schedule:" + schedule);
		System.err.println("DelayCron:"+delayCron);
		return "reminder";
	}
}
