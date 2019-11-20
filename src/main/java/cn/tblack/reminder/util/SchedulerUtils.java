package cn.tblack.reminder.util;

import org.springframework.context.ApplicationContext;

import cn.tblack.reminder.schudler.ReminderScheduler;

public class SchedulerUtils {

	private static ApplicationContext appContext;
	
	public static ReminderScheduler getReminderScheduler() {
		
		return appContext.getBean(ReminderScheduler.class);
	}
	
	public static void setApplicationContext(ApplicationContext context) {
		appContext = context;
	}
}
