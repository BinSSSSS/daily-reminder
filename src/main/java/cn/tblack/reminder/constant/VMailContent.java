package cn.tblack.reminder.constant;

/**
 * @VerificationEmail的一些默认内容
 * @author TD唐登
 * @Date:2019年10月31日
 * @Version: 1.0(测试版)
 */
public class VMailContent {

	/**
	 * @验证邮件的标题信息
	 */
	public static final String TITLE =  "DailyReminder验证码";
	
	/**
	 * @验证码邮件的头内容
	 */
	public static final String VMAIL_HEADER = "你收到了一份惊喜,当前的惊喜注册验证码为: ";
	
	/**
	 * @验证码邮件的尾内容
	 */
	public static final String VMAIL_FOOTER = "<br><strong style='font-size: 14px'>--本邮件由 Daily Reminder 发送。"
			+ " 详情访问 <strong><a href='http://www.tblack.cn' style='text-decoration: none; color: blue;'>书栈</a></strong></strong>";
	/**
	 * @传递一个失效的时间和一个验证码来返回一个默认的邮件内容
	 * @param interval
	 * @return
	 */
	public static String getDefaultContent(String vcode,Integer expiredTime) {
		
		//计算失效时间是秒还是分钟等-
		String timeUnit = "秒钟";
		int expired = 0;
		//计算时间单位， 简单起见，只计算整除部分
		//超过了小时
		if(expiredTime > 60 * 1000 * 60) {
			timeUnit =  "小时";
			expired = expiredTime / (60 * 1000 * 60); 
		}
		//超过了分钟
		else if(expiredTime > 60 * 1000) {
			timeUnit = "分钟";
			expired = expiredTime / (60 * 1000) ;
		}
		
		return 
			"<div style='font-family: Consolas,幼圆; font-size: 16px;'>" +
			"<p>" + VMAIL_HEADER +  "<strong>" + vcode +"</strong>" + "</p>" +
			"<p>验证码将于" + expired + timeUnit + "后失效。请尽快填写</p>" +
			VMAIL_FOOTER +
			"</div>";
	}
}
