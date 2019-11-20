package cn.tblack.reminder.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cn.tblack.reminder.mail.EmailSender;
import cn.tblack.reminder.schudler.ReminderScheduler;
import cn.tblack.reminder.util.EmailSenderUtils;
import cn.tblack.reminder.util.SchedulerUtils;

@Component
public class WebInitListener implements ApplicationListener<ContextRefreshedEvent>{

	private static Logger log = LoggerFactory.getLogger(WebInitListener.class);
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private ReminderScheduler reminderScheduler;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		log.info("初始填入一个 EmailSender 对象:  " + emailSender);
		log.info("初始填入一个 ReminderScheduler 对象:  " + reminderScheduler);
		EmailSenderUtils.setEmailSender(emailSender);
		
		SchedulerUtils.setReminderScheduler(reminderScheduler);
	}

}
