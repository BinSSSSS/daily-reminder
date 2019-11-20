package cn.tblack.reminder.constant;

import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

/**
 * @保存着Web应用中的多种配置信息
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
public class WebConfigProperties {
	
	
	/**
	 * @不进行拦截的路径，该配置读取 web-config.properties中的 allowPath字段
	 */
	public static  String[] ALLOW_PATH;	
	
	public static  Integer THREAD_CORE_SIZE;  //线程池最小线程数
	
	public static Integer THREAD_MAX_SIZE;		//线程池最大线程数
	
	public static Integer QUEUE_CAPACITY;  	//线程队列数
	
	public static Integer KEEP_ALIVE_SECONDS; //线程最大空闲等待时间（秒）
	
	public static String THREAD_NAME_PREFIX;  //线程池创建线程的名称前缀
	
	public static Integer VMAIL_SEND_INTERVAL;	//验证邮件发送的间隔时间
	
	public static String UPLOAD_LOCATION;	//上传文件保存的根目录
	
	public static Integer WRITE_BUFFER_SIZE;  //写入文件时的缓冲大小
	
	public static Integer ALLOW_REMINDER_COUNT;	//默认允许的提醒数量
	
	//静态代码初始化， 当该类被加载使用的时候初始化。
	static {
		
		//加载配置文件
		ClassPathResource webConfigProperties = new ClassPathResource("web-config.properties");
		
			
		if(webConfigProperties.exists()) {
			
			Properties properties =  new Properties();
			
			
			try {
				//使用Properties进行加载配置
				properties.load(webConfigProperties.getInputStream());
				
				//拿到 allowPath				
				String tmpAllowPath =  properties.getProperty("allowPath");
				ALLOW_PATH =  tmpAllowPath == null ? null : tmpAllowPath.split(",");
				
				THREAD_CORE_SIZE = Integer.parseInt(properties.getProperty("thread_core_size"));
				THREAD_MAX_SIZE = Integer.parseInt(properties.getProperty("thread_max_size"));
				QUEUE_CAPACITY = Integer.parseInt(properties.getProperty("queue_capacity"));
				KEEP_ALIVE_SECONDS = Integer.parseInt(properties.getProperty("keep_alive_seconds"));
				THREAD_NAME_PREFIX = properties.getProperty("thread_name_prefix");
				VMAIL_SEND_INTERVAL = Integer.parseInt(properties.getProperty("vmail_send_interval"));
				UPLOAD_LOCATION =  properties.getProperty("upload_location");
				WRITE_BUFFER_SIZE = Integer.parseInt(properties.getProperty("write_buffer_size"));
				ALLOW_REMINDER_COUNT = Integer.parseInt(properties.getProperty("allow_reminder_count"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//如果设置属性的时候抛出异常，那么验证邮件间隔时间可能为空，那么则需要赋值一个默认值
			VMAIL_SEND_INTERVAL = VMAIL_SEND_INTERVAL ==  null ?  5 * 60000 :  VMAIL_SEND_INTERVAL;
			
		}
		
	}
}
