package cn.tblack.reminder.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.reminder.dao.UserDao;
import cn.tblack.reminder.entity.User;
import cn.tblack.reminder.service.UserService;

@Service
@Transactional()
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	
	
	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public List<User> findAll(Sort sort) {
		return userDao.findAll(sort);
	}

	@Override
	public List<User> findAllById(Iterable<Integer> ids) {
		return userDao.findAllById(ids);
	}

	@Override
	public List<User> saveAll(Iterable<User> entities) {
		return userDao.saveAll(entities);
	}

	@Override
	public void flush() {
		userDao.flush();
	}

	@Override
	public User saveAndFlush(User entity) {
		return userDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<User> entities) {
		userDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		userDao.deleteAllInBatch();
	}

	@Override
	public User getOne(Integer id) {
		return userDao.getOne(id);
	}

	@Override
	public User save(User entity) {
		return userDao.save(entity);
	}

	@Override
	public User findById(Integer id) {
		return userDao.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return userDao.existsById(id);
	}

	@Override
	public long count() {
		return userDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		userDao.deleteById(id);
	}

	@Override
	public void delete(User entity) {
		userDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends User> entities) {
		userDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		userDao.deleteAll();
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public boolean canBeRegister(User user) {
		return userDao.findByUsername(user.getUsername()) ==  null &&
				userDao.findByEmail(user.getEmail()) == null;
	}

	@Override
	public Page<User> findAllByUsername(String searchText, Pageable pageable) {
		return userDao.findAllByUsername(searchText,pageable);
	}

	@Override
	@Transactional
	public void incrReminderCount(User user) {
		userDao.updateReminderCount(user.getId(), user.getReminderCount() + 1);
	}

	@Override
	@Transactional
	public void descReminderCount(User user) {
		userDao.updateReminderCount(user.getId(), user.getReminderCount() - 1);
	}

}
