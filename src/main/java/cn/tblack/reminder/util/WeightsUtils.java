package cn.tblack.reminder.util;

import java.util.Calendar;

import static java.util.Calendar.*;
/**
 * @生成权重值的工具
 * @author TD唐登
 * @Date:2019年10月31日
 * @Version: 1.0(测试版)
 */
public class WeightsUtils {

	/**
	 * @ 根据传递的时间来生成权重值， 主要为 年 +  月 + 日 + 小时 + 分钟
	 * @param calendar
	 * @return
	 */
	public static long timeWeights(Calendar calendar) {
		
		StringBuilder sb = new StringBuilder(); 
		
	//	
		int month =  calendar.get(MONTH) + 1;
		int day = calendar.get(DAY_OF_MONTH);
		int hour = calendar.get(HOUR_OF_DAY);
		int minute = calendar.get(MINUTE);
		
		sb.append(calendar.get(YEAR) + "" 
				+ (month < 10 ? "0" + month : month)
				+ (day < 10 ? "0" + day : day)
				+ (hour < 10 ? "0" + hour : hour)
				+ (minute < 10 ? "0" + minute : minute));
		
		return Long.parseLong(sb.toString());
	}
	
	/**
	 * @ 根据传递的时间来生成字符串， 主要为 年 +  月 + 日 + 小时 + 分钟 + 秒 
	 * @param calendar
	 * @return
	 */
	public static long secondTimeWeights(Calendar calendar) {
		
		StringBuilder sb = new StringBuilder(); 
		
	//	
		int month =  calendar.get(MONTH) + 1;
		int day = calendar.get(DAY_OF_MONTH);
		int hour = calendar.get(HOUR_OF_DAY);
		int minute = calendar.get(MINUTE);
		int second  = calendar.get(SECOND);
		
		sb.append(calendar.get(YEAR) + "" 
				+ (month < 10 ? "0" + month : month)
				+ (day < 10 ? "0" + day : day)
				+ (hour < 10 ? "0" + hour : hour)
				+ (minute < 10 ? "0" + minute : minute)
				+ (second < 10 ? "0" + second : second));
		
		return Long.parseLong(sb.toString());
	}
}
