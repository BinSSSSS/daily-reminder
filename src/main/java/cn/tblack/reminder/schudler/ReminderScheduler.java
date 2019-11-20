package cn.tblack.reminder.schudler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import cn.tblack.reminder.entity.Reminder;
import cn.tblack.reminder.job.DelayReminderJob;
import cn.tblack.reminder.job.ReminderJob;
import cn.tblack.reminder.job.ReminderTask;
import cn.tblack.reminder.job.TestJob;

@Configuration
public class ReminderScheduler implements Serializable{


	private static final long serialVersionUID = 2959608278457182558L;

	private static Logger log = LoggerFactory.getLogger(ReminderScheduler.class);

	@Autowired
	private Scheduler scheduler;

	private static String DELAY_JOB_PREFIX = "temp_";

	/**
	 * @添加一个延时的调度任务.该调度任务用于执行之后的重复调度任务
	 * @param task      需要被重复执行的任务
	 * @param delayCron 延时执行的单次Cron表达式.通过该Cron执行一个重复Cron表达式
	 * @return
	 */
	public boolean addDelayReminderJob(ReminderTask task, String delayCron) {

		// 拿到Reminder对象
		Reminder reminder = task.getReminder();

		// 将提醒id作为Job名称,将创建该提醒的用户名作为组名--延时执行的调度任务需要添加一个前缀
		JobKey key = new JobKey(DELAY_JOB_PREFIX + reminder.getId(), reminder.getUser().getUsername());

		Map<String, Serializable> params = new HashMap<String, Serializable>(); // 用于在JobDetail中的DataMap存放的对象

		params.put("task", task);
		params.put("reminderScheduler", this);

		return addJob(key, DelayReminderJob.class, delayCron, params);
	}

	/**
	 * @添加一个定时调度任务
	 * @param job
	 * @return
	 */
	public boolean addReminderJob(ReminderTask task) {

		// 拿到Reminder对象
		Reminder reminder = task.getReminder();

		// 将提醒id作为Job名称,将创建该提醒的用户名作为组名
		JobKey key = new JobKey("" + reminder.getId(), reminder.getUser().getUsername());

		Map<String, Serializable> params = new HashMap<String, Serializable>(); // 用于在JobDetail中的DataMap存放的对象

		params.put("task", task);

		return addJob(key, ReminderJob.class, reminder.getSchedule().getCron(), params);
	}

	/**
	 * @暂停一个延时的调度任务。 如果传递的该任务不存在的话，那么则表示需要删除一个 temp开头的延时调度任务
	 * 
	 * @param jobKey 为重复执行的调度任务key值，
	 * @return
	 */
	public boolean pauseDelayReminderJob(JobKey jobKey) {

		try {
			if (scheduler.getJobDetail(jobKey) == null) {
				// 表示该调度任务还不存在,暂停一个延时启动该调度任务的Job
				pauseReminderJob(new JobKey(DELAY_JOB_PREFIX + jobKey.getName(), jobKey.getGroup()));
			}
			// 如果当前任务已经存在。
			else {
				pauseReminderJob(jobKey);
			}
			log.info("暂停 [" + jobKey + "]成功");
			return true;
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @暂停某个调度任务
	 * @param key
	 * @return
	 */
	public boolean pauseReminderJob(JobKey key) {

		try {
			scheduler.pauseJob(key);
			log.info("Pause Job: " + key);
			return true;
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @暂停所有的调度任务
	 * @return
	 */
	public boolean pauseAllReminderJob() {

		try {
			scheduler.pauseAll();
			log.info("Pause all reminder job");
			return true;
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @删除某个调度任务
	 * @param key
	 * @return
	 */
	public boolean killReminderJob(JobKey key) {

		try {
			scheduler.deleteJob(key);
			log.info("Kill reminder job: " + key);
			return true;
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @开始一个之前暂停了的调度任务
	 * @param jobKey
	 */
	public boolean startReminderJob(JobKey jobKey) {

		try {
			scheduler.resumeJob(jobKey);
			log.info("Start reminder job: " + jobKey);
			return true;
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * @开始一个之前暂停了的延时调度任务
	 * @param jobKey
	 */
	public boolean startDelayReminderJob(JobKey jobKey) {

		try {

			if (scheduler.getJobDetail(jobKey) == null) {
				// 表示该调度任务还不存在,启动该延时调度任务
				startReminderJob(new JobKey(DELAY_JOB_PREFIX + jobKey.getName(), jobKey.getGroup()));
			}
			// 如果当前任务已经存在。
			else {
				startReminderJob(jobKey);
			}
			log.info("Start reminder job: " + jobKey);
			return true;
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * @用于添加不同的Job类型到Scheduler中。
	 * 
	 * @param key    用于注册的Job认证
	 * @param clazz  类的类型
	 * @param cron   cron表达式
	 * @param params 用于存放在JobDetail的DatMap中的数据
	 * @return
	 */
	private boolean addJob(JobKey key, Class<? extends Job> clazz, String cron, Map<String, ? extends Serializable> params) {

		JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(key.getName(), key.getGroup()).build();

		 
		
		Map<String, Object> dataMap = jobDetail.getJobDataMap();
		for (Entry<String, ? extends Serializable> param : params.entrySet()) {
//			transient Serializable value  = param.getValue();
			dataMap.put(param.getKey(), param.getValue());
		}
		CronScheduleBuilder cronBuilder = CronScheduleBuilder.cronSchedule(cron);
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(key.getName(), key.getGroup())
				.withSchedule(cronBuilder).build();

		try {
			// 将新添加的任务到调度任务中心
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start(); // 开始调度任务
			log.info("Add new Reminder Job : " + key);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return false;

	}

	public void addJob() {
		addJob(new JobKey("key","group"),TestJob.class,"0/10 * * * * ?",new HashMap<>());
	}
}
