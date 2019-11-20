package cn.tblack.reminder.config.database;

import static cn.tblack.reminder.constant.DataBaseConstant.DS_JOB_STORE;
import static cn.tblack.reminder.constant.DataBaseConstant.TM_JOB_STORE;
import static cn.tblack.reminder.constant.DataBaseConstant.SF_JOB_STORE;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

/**
 * @定时调度任务对象存储相关配置
 * @author TD唐登
 * @Date:2019年11月19日
 * @Version: 1.0(测试版)
 */
@Configuration
public class ScheduleJobStoreConfiguration {

	@Autowired
	private QuartzProperties quartzProperties;

	@Bean(name = DS_JOB_STORE)
	@ConfigurationProperties("spring.datasource.druid.job-store")
	public DataSource jobStoreDataSource() {

		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = TM_JOB_STORE)
	public PlatformTransactionManager jobStoreTransactionManager(@Qualifier(DS_JOB_STORE) DataSource dataSource) {

		return new DataSourceTransactionManager(dataSource);
	}

	@Primary
	@Bean(name = SF_JOB_STORE)
	public SchedulerFactoryBean schedulerFactoryBean(@Qualifier(DS_JOB_STORE) DataSource dataSource) {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

		Properties properties = new Properties();
		properties.putAll(quartzProperties.getProperties());

		schedulerFactoryBean.setDataSource(dataSource);

		schedulerFactoryBean.setQuartzProperties(properties);

		// 延时启动
		schedulerFactoryBean.setStartupDelay(1);
		// 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
		schedulerFactoryBean.setOverwriteExistingJobs(true);

		return schedulerFactoryBean;
	}

}
