package cn.tblack.reminder.job;

import static cn.tblack.reminder.constant.CustomBoolean.TRUE;

import java.io.Serializable;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import cn.tblack.reminder.util.SchedulerUtils;
/**
 * @用于给用户发送提醒的类
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
public class ReminderJob implements Job ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		//拿到存在在Map当中的Task对象并通过线程池进行执行
		ReminderTask task = (ReminderTask) context.getMergedJobDataMap().get("task");
		
		if(task != null) {
			
			//如果本个任务已经被停止了，那么则需要杀死当前的调度任务
			if(task.getReminder().getDeprecated() == TRUE) {
				
				JobKey key = context.getJobDetail().getKey();
				
				//暂停该调度任务
				boolean state = SchedulerUtils.getReminderScheduler().pauseReminderJob(key);
				
				//如果成功暂停- 
				if(state) {
					
				}
				//如果暂停失败。
				else {
					
				}
				return;
			}
			
			task.run();  //执行发送邮件操作。
			
		}
		else
			throw new RuntimeException("当前无 ReminderTask 对象被添加!");
		
		
	}

}
