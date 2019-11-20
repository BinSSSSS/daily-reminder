package cn.tblack.reminder.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.reminder.dao.ReminderDao;
import cn.tblack.reminder.entity.Reminder;
import cn.tblack.reminder.service.ReminderService;

@Service
@Transactional
public class ReminderServiceImpl implements ReminderService {


	private static final long serialVersionUID = -368689618316149104L;
	@Autowired
	private ReminderDao reminderDao;

	@Override
	@Transactional
	public void updateFinishedStateById(Integer reminderId, Date finishedTime) {
		 reminderDao.updateFinishedStateById(reminderId, finishedTime);
	}

	@Override
	public List<Reminder> findAll() {
		return reminderDao.findAll();
	}

	@Override
	public List<Reminder> findAll(Sort sort) {
		return reminderDao.findAll(sort);
	}

	@Override
	public List<Reminder> findAllById(Iterable<Integer> ids) {
		return reminderDao.findAllById(ids);
	}

	@Override
	public List<Reminder> saveAll(Iterable<Reminder> entities) {
		return reminderDao.saveAll(entities);
	}

	@Override
	public void flush() {
		reminderDao.flush();
	}

	@Override
	public Reminder saveAndFlush(Reminder entity) {
		return reminderDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<Reminder> entities) {
		reminderDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		reminderDao.deleteAllInBatch();
	}

	@Override
	public Reminder getOne(Integer id) {
		return reminderDao.getOne(id);
	}

	@Override
	public Reminder save(Reminder entity) {
		return reminderDao.save(entity);
	}

	@Override
	public Reminder findById(Integer id) {
		return reminderDao.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return reminderDao.existsById(id);
	}

	@Override
	public long count() {
		return reminderDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		reminderDao.deleteById(id);
	}

	@Override
	public void delete(Reminder entity) {
		reminderDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends Reminder> entities) {
		reminderDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		reminderDao.deleteAll();
	}

	@Override
	public Page<Reminder> findAll(Pageable pageable) {
	
		return reminderDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void updateDeprecated(Integer id, Short deprecated) {
		reminderDao.updateDeprecated(id,deprecated);
	}

	@Override
	public Page<Reminder> findRemindersByUserId(Integer userId, Pageable pageable) {
		return reminderDao.findRemindersByUserId(userId,pageable);
	}

}
