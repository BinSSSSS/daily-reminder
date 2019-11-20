package cn.tblack.reminder.controller;

import static cn.tblack.reminder.constant.CustomBoolean.FALSE;
import static cn.tblack.reminder.constant.CustomBoolean.TRUE;
import static cn.tblack.reminder.constant.WebConfigProperties.UPLOAD_LOCATION;
import static cn.tblack.reminder.constant.WebConfigProperties.WRITE_BUFFER_SIZE;
import static cn.tblack.reminder.util.CronUtilsHelper.CRON_PARSER;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.transaction.TransactionManager;

import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cronutils.model.Cron;

import cn.tblack.reminder.entity.MailSender;
import cn.tblack.reminder.entity.Reminder;
import cn.tblack.reminder.entity.Schedule;
import cn.tblack.reminder.entity.User;
import cn.tblack.reminder.job.ReminderTask;
import cn.tblack.reminder.mail.EmailSender;
import cn.tblack.reminder.result.ValidateResult;
import cn.tblack.reminder.result.WebResult;
import cn.tblack.reminder.schudler.ReminderScheduler;
import cn.tblack.reminder.service.MailSenderService;
import cn.tblack.reminder.service.ReminderService;
import cn.tblack.reminder.service.ScheduleService;
import cn.tblack.reminder.service.UserService;
import cn.tblack.reminder.util.CronUtilsHelper;
import cn.tblack.reminder.util.FileWriter;
import cn.tblack.reminder.util.WeightsUtils;

@RestController
@RequestMapping("/reminder")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class ReminderController {

	@Autowired
	private UserService userService;
	@Autowired
	private MailSenderService mailSendService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ReminderService reminderService;
	@Autowired
	private EmailSender emailSender;
	@Autowired
	private ReminderScheduler reminderScheduler;
	
	private static Logger log = LoggerFactory.getLogger(ReminderController.class);

	@Autowired(required = false)
	private TransactionManager ts;
	
	@Autowired(required =  false)
	private PlatformTransactionManager ts2;
	
	@Autowired(required = false)
	private LocalContainerEntityManagerFactoryBean factory;
	
//	@RequestMapping(value = "/test")
	/**
	 * @ 测试Job任务中无法实现更新删除的数据库操作---
	 */
	public void test() {
		try {
			
			Map<String,Object> map = factory.getJpaPropertyMap();
			log.info(map.toString());
			log.info("默认的TransactionManager为: " +  ts);
			log.info("默认的PlatformTransactionManager为: " +  ts2);
			log.info("默认的EntityManagerFactory为: " +  factory);
			log.info("Jpa配置信息为: " + factory.getJpaPropertyMap() + "\n" + "[ " + factory.getPersistenceUnitInfo().getManagedClassNames() + "]");
//			EmailSender.setStaticReminderService(reminderService);
			reminderScheduler.addJob();
			//		EmailSender.test();
		//	log.info("更新成功");
		}catch(Exception e) {
			
			//log.error("出错信息为: " + e.getMessage());
		}
		
	}
	
//	@RequestMapping(value = "/test2")
	public void test2() {
		try {
//			EmailSender.setStaticReminderService(reminderService);
//			EmailSender.test();
		//	log.info("更新成功");
		}catch(Exception e) {
			
			//log.error("出错信息为: " + e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/verify-cron-expression", method = RequestMethod.POST)
	/**
	 * @验证某个Cron表达式是否合法
	 * @param cron
	 * @return
	 */
	public ValidateResult verifyCronExpression(String cron) {

		log.info("开始验证一个Cron表达式:  " + cron);

		ValidateResult result = new ValidateResult(false);
		try {

			Cron quartzCron = CRON_PARSER.parse(cron);
			quartzCron.validate();
			result.setValidate(true);
		} catch (Exception e) {
			log.error("用户[ " + SecurityContextHolder.getContext().getAuthentication().getName() + "]传递了一个非法的Cron表达式 ");
			log.error("出错信息为: " + e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/addReminder", method = RequestMethod.POST)
	/**
	 * @添加一个新提醒
	 * @param mailSender
	 * @param schedule
	 * @param delayCron
	 * @return
	 */
	public WebResult addReminder(MailSender mailSender, Schedule schedule, String delayCron,
			@RequestParam("attachment") MultipartFile file) {

		
		WebResult result = new WebResult();
	
		try {

			User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			
			//当前用户的提醒数量是否已经满了，如果满了的话，那么则无法添加新提醒
			if(user.getReminderCount() >= user.getAllowReminderCount() ) {
				
				return new WebResult(false,"当前提醒已满！清理提醒后再试！");
			}
			
			
			mailSender.setSendTo(user.getEmail());

			Date nextExecutionDate = CronUtilsHelper.nextExecutionDate(schedule.getCron());

			Reminder reminder = new Reminder();

			
			
			// 检查任务是否是一个重复执行的任务
			if (CronUtilsHelper.nextExecutionDateList(schedule.getCron(), nextExecutionDate).size() < 2) {

				// 表示是一个单次的提醒任务
				schedule.setRepeat(FALSE);
			} else {
				schedule.setRepeat(TRUE);
			}
			String fileName = file.getOriginalFilename();  //原始文件名
			// 如果存在附件的话，将该附件保存在本地
			if (file != null && !fileName.isEmpty()) {
				
				File dir = new File(UPLOAD_LOCATION + user.getUsername());

				// 如果当前文件夹不存在
				if (!dir.exists()) {
					// 创建文件夹
					dir.mkdirs();
				}
				//将文件名加上时间戳--
				fileName += "_" + WeightsUtils.secondTimeWeights(new GregorianCalendar());
				String savePath = dir.getAbsolutePath() + "/" + fileName;
				// 保存该文件
				FileWriter.writeToFile(file.getInputStream(), (int) file.getSize(), WRITE_BUFFER_SIZE,
						savePath);
				
				//设置该附件的地址
				mailSender.setAttachments(savePath);
				
				log.info("当前用户上传了一个附件: " + fileName + ",文件被保存在: " + savePath);
			}

			// 给数据赋值
			reminder.setDeprecated(FALSE);
			reminder.setCreateTime(new Date());
			reminder.setUser(user);
			reminder.setSchedule(schedule);
			mailSender.setSendTime(nextExecutionDate); // 计算到下次执行的时间并发送
			
			// 将数据插入到数据库中
			scheduleService.save(schedule);
			mailSendService.save(mailSender);
			reminder.setMailSender(mailSender);
			reminder.setSchedule(schedule);
			reminderService.save(reminder);
			
			//更新当前用户的提醒个数
			userService.incrReminderCount(user);
			
			// 直接创建一个ReminderTask任务
			ReminderTask task = new ReminderTask(reminder);

			// 表示的不是延时重复提醒- 那么则无需要创建DelayRemindJob对象
			if (delayCron == null) {

				reminderScheduler.addReminderJob(task);
			} else {

				// 是一个延时的调度任务，需要添加成为延时调度任务
				reminderScheduler.addDelayReminderJob(task, delayCron);
			}
			result.setMsg("添加成功");
			result.setSuccess(true);
		} catch (Exception e) {

			log.error("添加一个新的提醒失败: " + e.getMessage());
			e.printStackTrace();
			result.setMsg("添加提醒失败");
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping(value = "/search")
	public Page<Reminder> searchReminder(@RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
			@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
			@RequestParam(name = "searchText", defaultValue = "") String searchText) {

		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		// 拿到当前用户的所有提醒
		return reminderService.findRemindersByUserId(user.getId(), PageRequest.of(pageNo, pageSize));
//		return reminderService.findAll(PageRequest.of(pageNo, pageSize));
	}

	@RequestMapping(value  = "/delete-reminder", method = RequestMethod.POST)
	public WebResult deleteReminder(Integer id) {

		WebResult result = new WebResult();
		// 拿到当前用户
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		// 删除指定的Reminder对象
		try {
			// 从数据库中删除
			reminderService.deleteById(id);
			// 在当前的调度任务中进行删除本调度任务
			reminderScheduler.killReminderJob(new JobKey("" + id, user.getUsername()));
			result.setMsg("删除成功");
			result.setSuccess(true);
			//减少当前用户的提醒数量
			userService.descReminderCount(user);
			log.info("用户 [ " + user.getUsername() + "]删除了一个提醒对象 " + id);
		} catch (Exception e) {
			log.error("删除一个提醒对象失败:  " + id + "； 出错信息: " + e.getMessage());
			result.setMsg("删除失败");
			result.setSuccess(false);
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/pause-reminder",method = RequestMethod.POST)
	/**
	 * @暂停一个正在执行的调度任务
	 * @param id
	 * @return
	 */
	public WebResult pauseReminder(Integer id) {

		WebResult result = new WebResult();
		// 拿到当前用户
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		try {
			// 从数据库中更改reminder的状态
			reminderService.updateDeprecated(id, TRUE);

			// 在当前的调度任务中进行暂停本调度任务--这里需要判断- 本调度任务是否是一个延时的调度任务

			// 如果任务调度中心找不到该调度任务，那么则调度任务可能是一个延时调度任务
			// 暂时直接使用了全部的调度任务判断是否是一个延时调度任务
			reminderScheduler.pauseDelayReminderJob(new JobKey("" + id, user.getUsername()));
			result.setMsg("操作成功");
			result.setSuccess(true);
			log.info("用户 [ " + user.getUsername() + "]暂停了一个提醒对象 " + id);
		} catch (Exception e) {
			log.error("暂停一个提醒对象失败:  " + id + "； 出错信息: " + e.getMessage());
			result.setMsg("操作失败");
			result.setSuccess(false);
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/start-reminder",method = RequestMethod.POST)
	/**
	 * @开始一个已经暂停了的调度任务
	 * @param id
	 * @return
	 */
	public WebResult startReminder(Integer id) {

		WebResult result = new WebResult();
		// 拿到当前用户
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		try {
			// 从数据库中更改reminder的状态
			reminderService.updateDeprecated(id, FALSE);

			reminderScheduler.startDelayReminderJob(new JobKey("" + id, user.getUsername()));
			result.setMsg("操作成功");
			result.setSuccess(true);
			log.info("用户 [ " + user.getUsername() + "]开始了一个提醒对象 " + id);
		} catch (Exception e) {
			log.error("开始一个提醒对象失败:  " + id + "； 出错信息: " + e.getMessage());
			result.setMsg("操作失败");
			result.setSuccess(false);
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * @发送一个测试的邮件到指定的邮箱内
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/send-test-reminder",method = RequestMethod.POST)
	public WebResult sendTestReminder(Integer id) {

		WebResult result = new WebResult();
		// 拿到当前用户
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		try {
			//拿到当前提醒
			Reminder reminder =  reminderService.findById(id);
			
			//向该提醒的内容类发送一封测试邮件
			emailSender.send(reminder.getMailSender());
			
			result.setMsg("发送成功");
			result.setSuccess(true);
			log.info("用户 [ " + user.getUsername() + "]发送了一封测试邮件 " + id);
		} catch (Exception e) {
			log.error("暂停一个提醒对象失败:  " + id + "； 出错信息: " + e.getMessage());
			result.setMsg("发送失败");
			result.setSuccess(false);
			e.printStackTrace();
		}

		return result;
	}
}
