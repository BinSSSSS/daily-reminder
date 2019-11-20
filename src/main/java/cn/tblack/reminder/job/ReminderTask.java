package cn.tblack.reminder.job;

import java.io.Serializable;
import java.util.concurrent.Future;

import cn.tblack.reminder.entity.Reminder;
import cn.tblack.reminder.mail.EmailSender;
import cn.tblack.reminder.util.EmailSenderUtils;
/**
 * @具体的执行邮件发送的类。 ReminderJob 中将会调用到该类的执行方法。
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
public class ReminderTask implements Serializable{

	private static final long serialVersionUID = -290666106969477378L;
	
//	private  EmailSender emailSender; // 邮件发送器
	private  Reminder reminder; // 提醒对象

	public ReminderTask(Reminder reminder) {

		this.reminder = reminder;
	}



	public Reminder getReminder() {
		return reminder;
	}

	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}

	public void run() {
		
		
		//直接调用方法进行邮件的发送
		Future<Boolean> state =   EmailSenderUtils.getEmailSender().send(reminder);	
		
		if(state.isDone()) {
			
		}
//		 
//		try {
//			
////			System.err.println("发送邮件： " + state.get());
//			
//		} catch (InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//		}
	}

}
