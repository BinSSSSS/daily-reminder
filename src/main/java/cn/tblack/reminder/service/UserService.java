package cn.tblack.reminder.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import cn.tblack.reminder.entity.User;

public interface UserService {
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	/**
	 * @传递一个User对象，判断该User对象是否能够被注册。 首先检查用户名是否被注册过，其次检查邮箱是否被注册过
	 * @param user
	 * @return
	 */
	boolean canBeRegister(User user);
	
	List<User> findAll();

	List<User> findAll(Sort sort);

	List<User> findAllById(Iterable<Integer> ids);

	List<User> saveAll(Iterable<User> entities);

	void flush();

	User saveAndFlush(User entity);

	void deleteInBatch(Iterable<User> entities);

	void deleteAllInBatch();

	User getOne(Integer id);

	User save(User entity);

	User findById(Integer id);

	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(User entity);

	void deleteAll(Iterable<? extends User> entities);

	void deleteAll();

	Page<User> findAllByUsername(String searchText, Pageable pageable);

	void incrReminderCount(User user);

	void descReminderCount(User user);

}
