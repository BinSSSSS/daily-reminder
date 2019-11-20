package cn.tblack.reminder.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @该类作为启动项添加一个Scheduler到ApplicationContext中
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Configuration
public class QuartzSchudlerInit {

	@Bean
	public Scheduler scheduler() {
		try {
			return new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return null;
	}
}
