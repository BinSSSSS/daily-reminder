package cn.tblack.reminder.util;

import java.util.Random;

/**
 * @随机验证码生成器。 
 * @author TD唐登
 * @Date:2019年10月31日
 * @Version: 1.0(测试版)
 */
public class CodeGenerator {

	private static Random rand = new Random();
	
	public static String createFourNumberVerficationCode() {

		/* @ 产生 1000 - 9999 之间的随机数 */
		int code = (int) Math.abs(rand.nextDouble() * 9000  + 1000);

		return "" +  code;
	}
	
}
