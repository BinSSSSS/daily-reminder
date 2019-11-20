package cn.tblack.reminder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.reminder.dao.VerificationMailDao;
import cn.tblack.reminder.entity.VerificationMail;
import cn.tblack.reminder.service.VerificationMailService;

@Service
@Transactional()
public class VerificationMailServiceImpl implements VerificationMailService{
	
	@Autowired
	private VerificationMailDao verificationMailDao;

	@Override
	public List<VerificationMail> findAll() {
		return verificationMailDao.findAll();
	}

	@Override
	public List<VerificationMail> findAll(Sort sort) {
		return verificationMailDao.findAll(sort);
	}

	@Override
	public List<VerificationMail> findAllById(Iterable<Integer> ids) {
		return verificationMailDao.findAllById(ids);
	}

	@Override
	public List<VerificationMail> saveAll(Iterable<VerificationMail> entities) {
		return verificationMailDao.saveAll(entities);
	}

	@Override
	public void flush() {
		verificationMailDao.flush();
	}

	@Override
	public VerificationMail saveAndFlush(VerificationMail entity) {
		return verificationMailDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<VerificationMail> entities) {
		verificationMailDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		verificationMailDao.deleteAllInBatch();
	}
	

	@Override
	public VerificationMail getOne(Integer id) {
		return verificationMailDao.getOne(id);
	}

	@Override
	public VerificationMail save(VerificationMail entity) {
		return verificationMailDao.save(entity);
	}

	@Override
	public VerificationMail findById(Integer id) {
		return verificationMailDao.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return verificationMailDao.existsById(id);
	}

	@Override
	public long count() {
		return verificationMailDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		verificationMailDao.deleteById(id);
	}

	@Override
	public void delete(VerificationMail entity) {
		verificationMailDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends VerificationMail> entities) {
		verificationMailDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		verificationMailDao.deleteAll();
	}

	@Override
	public VerificationMail findLastMail(String email) {
		return verificationMailDao.findLastMail(email);
	}
	
	
}
