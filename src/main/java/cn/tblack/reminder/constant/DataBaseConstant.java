package cn.tblack.reminder.constant;

/**
 * @ 保存定义数据库内一些数据源、事务管理、数据库名的常量名
 * @author TD唐登
 * @Date:2019年11月19日
 * @Version: 1.0(测试版)
 */
public class DataBaseConstant {
	
	//DataSource数据源************************************************

	public static final String DS_JOB_STORE = "jobStoreDataBase";
	
	public static final String DS_REMINDER = "reminderDataBase";
	
	
	//事务管理器************************************************
	
	public static final String TM_JOB_STORE = "jobStoreTransactionManager";
	

	public static final String TM_REMINDER = "reminderTransactionManager";
	
	
	//EntityManagerFactory************************************************
	
	public static final String EMF_REMINDER = "reminderEntityManagerFactory";

	
	//PersistanceUnit************************************************
	
	public static final String PU_REMINDER =  "reminderPersistanceUnit";

	//SchedulerFactoryBean************************************************
	
	public static final String SF_REMINDER =  "reminderScheuldelFactory";
	
	public static final String SF_JOB_STORE =  "jobStoreScheuldelFactory";
}
