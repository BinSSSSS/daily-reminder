package cn.tblack.reminder.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.tblack.reminder.entity.UserRoles;

public interface UserRoleDao extends JpaRepository<UserRoles, Integer>{
	
}
