package cn.tblack.reminder.job;

import java.io.Serializable;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.tblack.reminder.schudler.ReminderScheduler;
import cn.tblack.reminder.util.SchedulerUtils;

/**
 * @延迟调度任务， 该任务用于在执行的时候创建一个新的调度任务并添加到调度任务中心
 * @author TD唐登
 * @Date:2019年11月3日
 * @Version: 1.0(测试版)
 */
public class DelayReminderJob implements Job,Serializable {


	private static final long serialVersionUID = 1L;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		//拿到调度任务中心
		ReminderScheduler reminderScheduler = SchedulerUtils.getReminderScheduler();
		
		//拿到需要添加的调度任务
		ReminderTask task =  (ReminderTask) context.getMergedJobDataMap().get("task");
		
		//将调度任务注册到调度任务中心
		reminderScheduler.addReminderJob(task);
		
		//在调度中心删除本延时调度任务 
		reminderScheduler.killReminderJob(context.getJobDetail().getKey());
	}

}
