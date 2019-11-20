package cn.tblack.reminder.service;

import java.io.Serializable;
import java.time.Clock;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.tblack.reminder.entity.Reminder;

public interface ReminderService extends Serializable{

	void updateFinishedStateById(Integer reminderId, Date finishedTime);

	List<Reminder> findAll();

	List<Reminder> findAll(Sort sort);

	List<Reminder> findAllById(Iterable<Integer> ids);

	List<Reminder> saveAll(Iterable<Reminder> entities);

	Page<Reminder> findAll(Pageable pageable);

	void flush();

	Reminder saveAndFlush(Reminder entity);

	void deleteInBatch(Iterable<Reminder> entities);

	void deleteAllInBatch();

	Reminder getOne(Integer id);

	Reminder save(Reminder entity);

	Reminder findById(Integer id);

	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(Reminder entity);

	void deleteAll(Iterable<? extends Reminder> entities);

	void deleteAll();

	public static void main(String[] args) {
		System.err.println(Clock.systemDefaultZone());
	}

	void updateDeprecated(Integer id, Short deprecated);

	Page<Reminder> findRemindersByUserId(Integer userId, Pageable pageable);
}
