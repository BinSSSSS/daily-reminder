package cn.tblack.reminder.config.database;

import static cn.tblack.reminder.constant.DataBaseConstant.DS_REMINDER;
import static cn.tblack.reminder.constant.DataBaseConstant.EMF_REMINDER;
import static cn.tblack.reminder.constant.DataBaseConstant.PU_REMINDER;
import static cn.tblack.reminder.constant.DataBaseConstant.TM_REMINDER;

import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

/**
 * @ 用于配置 DailyReminder的JPA数据库信息。
 * 
 * @author TD唐登
 * @Date:2019年11月19日
 * @Version: 1.0(测试版)
 */
@Configuration
@EnableJpaRepositories(basePackages = {
		"cn.tblack.reminder.dao" }, transactionManagerRef = TM_REMINDER, entityManagerFactoryRef = EMF_REMINDER)
public class ReminderDBConguration {

	@Autowired
	private QuartzProperties quartzProperties;

	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	private HibernateProperties hibernateProperties;

	@Primary
	@Bean(name = DS_REMINDER)
	@ConfigurationProperties("spring.datasource.druid.reminder")
	public DataSource reminderDataSource() {

		return DruidDataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = EMF_REMINDER)
	public LocalContainerEntityManagerFactoryBean reminderEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier(DS_REMINDER) DataSource dataSource) {

		Map<String, Object> map = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(),
				new HibernateSettings());

		LocalContainerEntityManagerFactoryBean factory = builder.dataSource(dataSource).properties(map)
				.packages("cn.tblack.reminder.entity").persistenceUnit(PU_REMINDER).build();

		return factory;
	}

	@Primary
	@Bean(name = TM_REMINDER)
	public PlatformTransactionManager reminderTransactionManager(EntityManagerFactoryBuilder builder,
			@Qualifier(DS_REMINDER) DataSource dataSource) {

		return new JpaTransactionManager(reminderEntityManagerFactory(builder, dataSource).getObject());
	}

//	@Bean(name = SF_REMINDER)
//	@DependsOn(TM_REMINDER)
	public SchedulerFactoryBean schedulerFactoryBean(@Qualifier(DS_REMINDER) DataSource dataSource) {
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
