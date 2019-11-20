package cn.tblack.reminder.mail;

import static cn.tblack.reminder.constant.CustomBoolean.FALSE;
import static cn.tblack.reminder.constant.CustomBoolean.TRUE;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.Future;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import cn.tblack.reminder.entity.MailSender;
import cn.tblack.reminder.entity.Reminder;
import cn.tblack.reminder.service.MailSenderService;
import cn.tblack.reminder.service.ReminderService;
import cn.tblack.reminder.util.CronUtilsHelper;

/**
 * @邮件发送器， 可以发送简单的文本内容或者是富文本邮件
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Component
public class EmailSender implements Serializable{


	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(EmailSender.class);
	
//	private static ReminderService staticReminderService;
	
	@Autowired 
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String emailUsername;
	
	@Autowired
	private ReminderService reminderService;
	
	@Autowired
	private MailSenderService mailSenderService;
	
	/**
	 * @发送邮件任务，通过传递一个MailSender类，提取出该类的信息进行邮件发送
	 * @param mailInfo
	 * @return
	 */
	@Async("asyncTaskExecutor") //添加异步处理
	public  Future<Boolean> send(Reminder reminder) {
	
		
		
		boolean success =  false;
		MailSender mailInfo = reminder.getMailSender();
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		
		try {
			log.info("开始给[ " + mailInfo.getSendTo() + "]发送邮件");
			//创建MimeMessageHelper辅助类
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
			
			helper.setFrom(emailUsername);
			helper.setTo(mailInfo.getSendTo());
			helper.setSubject(mailInfo.getTitle());
			//设置为富文本文件发送
			helper.setText(mailInfo.getContent(),true);
			
			/**
			 * @进行对文件的查找和发送
			 */
			//如果存在附件
			if(mailInfo.getAttachments() != null) {
				String[] attachmentArr = mailInfo.getAttachments().split(",");
				
				//发送所有的附件
				for (String attachment : attachmentArr) {
					
					File file =  new File(attachment);
					helper.addAttachment(file.getName(), new FileSystemResource(file));
				}
			}
			//如果存在文件
			if(mailInfo.getFiles() != null) {
				
			}
			
			//发送邮件
			javaMailSender.send(mimeMessage);
			
			//如果发送成功，则需要更新当前发送的状态
			success =  true;
			
			log.info("给邮箱[" + mailInfo.getSendTo() +"]成功发送邮件!");
			
			//在数据库中更新定时任务成功的次数和最后成功的时间
			reminderService.updateFinishedStateById(reminder.getId(),
							new Date(System.currentTimeMillis()));
			
		//	return true;
		}catch(Exception e) {
			e.printStackTrace();
			log.error("发送邮件出现错误: " + e.getMessage());
		}
		try {
			//更新发送是否成功的标识以及更新下次发送的时间
			mailSenderService.updateSendStateAndNextSendTime(mailInfo.getId(), success ? TRUE : FALSE,
					CronUtilsHelper.nextExecutionDate(reminder.getSchedule().getCron()));
		}catch(Exception e) {
			log.error("发送邮件出现错误: " + e.getMessage());
		}
		
		return new AsyncResult<Boolean>(success) ;
	}

	
	/**
	 * @对于给定的用户发送一封给定内容的邮件
	 * @param emailAddress
	 * @param content
	 * @return
	 */
	@Async("asyncTaskExecutor")
	public Future<Boolean> send(String emailAddress, String title,String content){
		boolean success =  false;

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		try {
			//创建MimeMessageHelper辅助类
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
			
			helper.setFrom(emailUsername);
			helper.setTo(emailAddress);
			helper.setSubject(title);
			//设置为富文本文件发送
			helper.setText(content,true);
			
			//发送邮件
			javaMailSender.send(mimeMessage);
			success = true;
		}catch(Exception e) {
			log.error( "给邮箱[" + emailAddress +"]发送邮件失败!失败原因为: " + e.getMessage());
			e.printStackTrace();
		}
		
		return new AsyncResult<Boolean>(success);
	}
	
	/**
	 * @通过一个发送邮件的信息类向指定的邮箱发送一封邮件
	 * @param mailInfo
	 * @return
	 */
	@Async("asyncTaskExecutor")
	public Future<Boolean> send(MailSender mailInfo){
		
		boolean success =  false;
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		try {
			//创建MimeMessageHelper辅助类
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
			
			helper.setFrom(emailUsername);
			helper.setTo(mailInfo.getSendTo());
			helper.setSubject(mailInfo.getTitle());
			//设置为富文本文件发送
			helper.setText(mailInfo.getContent(),true);
			
			/**
			 * @进行对文件的查找和发送
			 */
			//如果存在附件
			if(mailInfo.getAttachments() != null) {
				String[] attachmentArr = mailInfo.getAttachments().split(",");
				
				//发送所有的附件
				for (String attachment : attachmentArr) {
					
					File file =  new File(attachment);
					helper.addAttachment(file.getName(), new FileSystemResource(file));
				}
			}
			//如果存在文件
			if(mailInfo.getFiles() != null) {
			}		
			//发送邮件
			javaMailSender.send(mimeMessage);
			
			//如果发送成功，则需要更新当前发送的状态
			success =  true;
			
			log.info("给邮箱[" + mailInfo.getSendTo() +"]成功发送邮件!");
		}catch(Exception e) {
			log.error( "给邮箱[" +  mailInfo.getSendTo() +"]发送邮件失败!失败原因为: " + e.getMessage());
		}

		return new AsyncResult<Boolean>(success) ;
	}
	
	
//	public static void test() {
//		try {
//			staticReminderService.updateFinishedStateById(2,
//					new Date(System.currentTimeMillis()));
//			log.info("更新成功");
//		}catch(Exception e) {
//			log.error("更新出错"  + e.getMessage());
//		}
//	}
//	
//	public static void setStaticReminderService(ReminderService service) {
//		staticReminderService = service;
//	}
}

