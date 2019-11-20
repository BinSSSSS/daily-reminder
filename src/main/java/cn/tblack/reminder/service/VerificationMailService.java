package cn.tblack.reminder.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import cn.tblack.reminder.entity.VerificationMail;

public interface VerificationMailService {
	List<VerificationMail> findAll();

	List<VerificationMail> findAll(Sort sort);

	List<VerificationMail> findAllById(Iterable<Integer> ids);

	List<VerificationMail> saveAll(Iterable<VerificationMail> entities);

	void flush();

	VerificationMail saveAndFlush(VerificationMail entity);

	void deleteInBatch(Iterable<VerificationMail> entities);

	void deleteAllInBatch();

	VerificationMail getOne(Integer id);

	VerificationMail save(VerificationMail entity);

	VerificationMail findById(Integer id);

	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(VerificationMail entity);

	void deleteAll(Iterable<? extends VerificationMail> entities);

	void deleteAll();

	VerificationMail findLastMail(String email);
}
