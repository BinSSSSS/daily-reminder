package cn.tblack.reminder.service;

import cn.tblack.reminder.entity.User;

public interface UserRoleService {

	/**
	 * @分配一个用户角色给指定的用户
	 * @param user
	 * @return
	 */
	boolean grantUserRole(User user);
	
	/**
	 * @分配一个管理员角色给指定的用户
	 * @param user
	 * @return
	 */
	boolean grantAdminRole(User user);
}
