package cn.tblack.reminder.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.reminder.dao.ScheduleDao;
import cn.tblack.reminder.entity.Schedule;
import cn.tblack.reminder.service.ScheduleService;

@Service
@Transactional()
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleDao scheduleDao;

	@Override
	public List<Schedule> findAll() {
		return scheduleDao.findAll();
	}

	@Override
	public List<Schedule> findAll(Sort sort) {
		return scheduleDao.findAll(sort);
	}

	@Override
	public List<Schedule> findAllById(Iterable<Integer> ids) {
		return scheduleDao.findAllById(ids);
	}

	@Override
	public List<Schedule> saveAll(Iterable<Schedule> entities) {
		return scheduleDao.saveAll(entities);
	}

	@Override
	public void flush() {
		scheduleDao.flush();
	}

	@Override
	public Schedule saveAndFlush(Schedule entity) {
		return scheduleDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<Schedule> entities) {
		scheduleDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		scheduleDao.deleteAllInBatch();
	}

	@Override
	public Schedule getOne(Integer id) {
		return scheduleDao.getOne(id);
	}

	@Override
	public Schedule save(Schedule entity) {
		return scheduleDao.save(entity);
	}

	@Override
	public Schedule findById(Integer id) {
		return scheduleDao.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return scheduleDao.existsById(id);
	}

	@Override
	public long count() {
		return scheduleDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		scheduleDao.deleteById(id);
	}

	@Override
	public void delete(Schedule entity) {
		scheduleDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends Schedule> entities) {
		scheduleDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		scheduleDao.deleteAll();
	}

}
