package cn.tblack.reminder.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.tblack.reminder.dao.UserDao;
import cn.tblack.reminder.encoder.CustomMD5PasswordEncoder;
import cn.tblack.reminder.entity.Role;
import cn.tblack.reminder.entity.User;

/**
 * @自定义 UserDetailService， 从数据库中拿到用户对象
 * @RD表示的是 Reminder
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Service
public class RDUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private CustomMD5PasswordEncoder passworEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// 从数据库中查询对象
		User user = userDao.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("当前用户不存在");
		}

		// 添加角色信息
		Set<Role> roles = user.getRoles();

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		// 添加当前用户拥有的角色信息
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}

		String password = passworEncoder.encode(user.getPassword()); // 经过加密和编码之后的密码

//		System.err.println("当前用户信息为：" + user);
//		System.err.println("用户权限为: " + authorities);

		return new org.springframework.security.core.userdetails.User(username, password, authorities);
	}

}
