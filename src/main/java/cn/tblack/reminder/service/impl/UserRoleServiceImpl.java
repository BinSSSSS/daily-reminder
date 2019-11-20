package cn.tblack.reminder.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tblack.reminder.dao.UserRoleDao;
import cn.tblack.reminder.entity.Role;
import cn.tblack.reminder.entity.User;
import cn.tblack.reminder.entity.UserRoles;
import cn.tblack.reminder.service.RoleService;
import cn.tblack.reminder.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RoleService roleService;

	private static final String ROLE_USER = "ROLE_USER";
	private static final String ROLE_ADMIN = "ROLE_ADMIN";

	@Override
	public boolean grantUserRole(User user) {

		Role role = roleService.findByRoleName(ROLE_USER);
		return grantRole(user, role);
	}

	@Override
	public boolean grantAdminRole(User user) {

		Role role = roleService.findByRoleName(ROLE_ADMIN);
		return grantRole(user, role);
	}

	@Transactional
	private boolean grantRole(User user, Role role) {
		UserRoles userRoles = new UserRoles();

		userRoles.setUser(user);
		userRoles.setRole(role);

		if (userRoleDao.save(userRoles) == null) {
			return false;
		}
		return true;
	}
}
