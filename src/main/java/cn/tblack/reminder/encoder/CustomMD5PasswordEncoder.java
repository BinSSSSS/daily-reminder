package cn.tblack.reminder.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cn.tblack.reminder.util.MD5Utils;

/**
 * @该类为一个自定义的密码编码器，在传递给父类进行比较的时候，首先需要对前台传递过来的密码进行MD5加密。
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
public class CustomMD5PasswordEncoder extends BCryptPasswordEncoder{
	
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
	
	//	boolean state = super.matches(MD5Utils.encrypt(rawPassword.toString()), encodedPassword);
	
	//	System.err.println("是否相同:" + state);
	//	return state;
		
		return super.matches(MD5Utils.encrypt(rawPassword.toString()), encodedPassword);
	}
}
