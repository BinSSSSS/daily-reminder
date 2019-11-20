package cn.tblack.reminder.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import cn.tblack.reminder.entity.Schedule;

public interface ScheduleService {
	List<Schedule> findAll();

	List<Schedule> findAll(Sort sort);

	List<Schedule> findAllById(Iterable<Integer> ids);

	List<Schedule> saveAll(Iterable<Schedule> entities);

	void flush();

	Schedule saveAndFlush(Schedule entity);

	void deleteInBatch(Iterable<Schedule> entities);

	void deleteAllInBatch();

	Schedule getOne(Integer id);

	Schedule save(Schedule entity);

	Schedule findById(Integer id);

	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(Schedule entity);

	void deleteAll(Iterable<? extends Schedule> entities);

	void deleteAll();
}
