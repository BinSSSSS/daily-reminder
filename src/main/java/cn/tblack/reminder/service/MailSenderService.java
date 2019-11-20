package cn.tblack.reminder.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;

import cn.tblack.reminder.entity.MailSender;

public interface MailSenderService extends Serializable {

	void updateSendState(Integer id, short success);

	List<MailSender> findAll();

	List<MailSender> findAll(Sort sort);

	List<MailSender> findAllById(Iterable<Integer> ids);

	List<MailSender> saveAll(Iterable<MailSender> entities);

	void flush();

	MailSender saveAndFlush(MailSender entity);

	void deleteInBatch(Iterable<MailSender> entities);

	void deleteAllInBatch();

	MailSender getOne(Integer id);

	MailSender save(MailSender entity);

	MailSender findById(Integer id);

	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(MailSender entity);

	void deleteAll(Iterable<? extends MailSender> entities);

	void deleteAll();

	void updateSendStateAndNextSendTime(Integer id, Short success, Date nextExecutionDate);
}
