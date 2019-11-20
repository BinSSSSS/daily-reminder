package cn.tblack.reminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableScheduling		//开启定时调度任务
@EnableAsync			//开启异步处理
@EnableTransactionManagement
public class DailyReminderApplication {

	public static void main(String[] args) {
		
		
		SpringApplication.run(DailyReminderApplication.class,args);
	}

}
