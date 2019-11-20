package cn.tblack.reminder.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.reminder.entity.User;

/**
 * @Reminder用户操作类
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
public interface UserDao extends JpaRepository<User, Integer>{

	@Query("SELECT u FROM #{#entityName} u WHERE username = :username")
	/**
	 * @根据用户名查询用户
	 * @param username
	 * @return
	 */
	public User findByUsername(@Param("username")String username);

	@Query("SELECT u FROM #{#entityName} u WHERE u.email = :email")
	User findByEmail(@Param("email") String email);

	@Query("SELECT u FROM #{#entityName} u WHERE u.username LIKE %:username%")
	public Page<User> findAllByUsername(@Param("username") String username, Pageable pageable);

	@Modifying
	@Query("UPDATE #{#entityName} u SET u.reminderCount = :reminderCount where u.id = :id")
	public void updateReminderCount(@Param("id") Integer id,@Param("reminderCount") Integer reminderCount);
	
	
	
}
