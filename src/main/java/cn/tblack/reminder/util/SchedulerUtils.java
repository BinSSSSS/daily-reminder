package cn.tblack.reminder.util;


import cn.tblack.reminder.schudler.ReminderScheduler;

public class SchedulerUtils {

	private static ReminderScheduler reminderScheduler;
	
	public static ReminderScheduler getReminderScheduler() {
		
		return reminderScheduler;
	}
	
	public static void setReminderScheduler(ReminderScheduler scheduler) {
		reminderScheduler = scheduler;
	}
}
