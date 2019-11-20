package cn.tblack.reminder.service.impl;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.reminder.dao.MailSenderDao;
import cn.tblack.reminder.entity.MailSender;
import cn.tblack.reminder.service.MailSenderService;

@Service
@Transactional
public class MailSenderServiceImpl implements MailSenderService {

	private static final long serialVersionUID = -3569958728312267298L;
	@Autowired
	private MailSenderDao mailSenderDao;

	@Override
	@Transactional
	public void updateSendState(Integer id, short success) {
		mailSenderDao.updateSendState(id, success);
	}

	@Override
	public List<MailSender> findAll() {
		return null;
	}

	@Override
	public List<MailSender> findAll(Sort sort) {
		return null;
	}

	@Override
	public List<MailSender> findAllById(Iterable<Integer> ids) {
		return null;
	}

	@Override
	public List<MailSender> saveAll(Iterable<MailSender> entities) {
		return null;
	}

	@Override
	public void flush() {
	}

	@Override
	public MailSender saveAndFlush(MailSender entity) {
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<MailSender> entities) {
	}

	@Override
	public void deleteAllInBatch() {
	}

	@Override
	public MailSender getOne(Integer id) {
		return null;
	}

	@Override
	public MailSender save(MailSender entity) {
		return mailSenderDao.save(entity);
	}

	@Override
	public MailSender findById(Integer id) {
		return mailSenderDao.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return mailSenderDao.existsById(id);
	}

	@Override
	public long count() {
		return mailSenderDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		mailSenderDao.deleteById(id);
	}

	@Override
	public void delete(MailSender entity) {
		mailSenderDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends MailSender> entities) {
		mailSenderDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		mailSenderDao.deleteAll();
	}

	@Override
	@Transactional
	public void updateSendStateAndNextSendTime(Integer id, Short success, Date nextExecutionDate) {
		mailSenderDao.updateSendStateAndNextSendTime(id,success,nextExecutionDate);
	}

}
