package cn.tblack.reminder.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static cn.tblack.reminder.constant.WebConfigProperties.*;

/**
 * @创建一个用于执行异步任务的线程池
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Configuration
public class TaskExecutorPoolConfig {

	@Bean("asyncTaskExecutor")
	public Executor taskExecutor() {
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		
		executor.setCorePoolSize(THREAD_CORE_SIZE); //线程池最少数量
		executor.setMaxPoolSize(THREAD_MAX_SIZE);  //线程池最大数量
		executor.setQueueCapacity(QUEUE_CAPACITY);	//最大队列数
		executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS); //允许的最大空闲时间
		executor.setThreadNamePrefix(THREAD_NAME_PREFIX); //创建的线程名称前缀
		return executor;
	}
}
