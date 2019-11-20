package cn.tblack.reminder.util;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

/**
 * @用于对于CronUtils提供一些额外的帮助函数
 * @author TD唐登
 * @Date:2019年11月5日
 * @Version: 1.0(测试版)
 */
public class CronUtilsHelper {

	public static final CronDefinition CRON_DEFINITION = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
	public static final CronParser CRON_PARSER = new CronParser(CRON_DEFINITION);

	/**
	 * @通过一个合法的Cron表达式来计算出一个下次的执行时间
	 * @param cron
	 * @return
	 */
	public static Date nextExecutionDate(String cron) {
		ZonedDateTime now = ZonedDateTime.now();
		ExecutionTime executionTime = ExecutionTime.forCron(CRON_PARSER.parse(cron));

		ZonedDateTime nextExecution = executionTime.nextExecution(now).get();

		Date date = Date.from(nextExecution.toInstant());
		return date;
	}

	/**
	 * @通过传递一个合法的Cron表达式和该Cron表达式的执行时间
	 * @来计算出Cron表达式的执行时间之后一个月的执行次数-
	 * @param cron
	 * @param executionDate
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> nextExecutionDateList(String cron, Date executionDate) throws ParseException {

		CronTriggerImpl cronTrigger = new CronTriggerImpl();
		cronTrigger.setCronExpression(cron);

		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(executionDate); // 设置开始时间
		endCalendar.set(Calendar.MONTH, endCalendar.get(Calendar.MONTH) + 1); //设置开始时间的1个月之后
//		endCalendar.set(Calendar.YEAR, endCalendar.get(Calendar.YEAR) + 1); // 设置开始时间的1年之后

		List<Date> executionDateList = TriggerUtils.computeFireTimesBetween(cronTrigger, null, new Date(),
				endCalendar.getTime());

		return executionDateList;

	}

	public static void main(String[] args) throws ParseException {
		System.err.println(nextExecutionDateList("0 31 15 5 11/12 ?",nextExecutionDate("0 31 15 5 11/12 ?")));
	}

}
