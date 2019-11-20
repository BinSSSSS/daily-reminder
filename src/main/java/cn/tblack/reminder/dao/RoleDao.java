package cn.tblack.reminder.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.tblack.reminder.entity.Role;

/**
 * @角色数据库操作方法
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
public interface RoleDao extends JpaRepository<Role, Integer>{

	@Query("SELECT r FROM #{#entityName} r WHERE role = :roleName")
	Role findByRoleName(String roleName);
	
}
