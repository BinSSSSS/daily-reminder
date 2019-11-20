package cn.tblack.reminder.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.reminder.dao.RoleDao;
import cn.tblack.reminder.entity.Role;
import cn.tblack.reminder.service.RoleService;

@Service
@Transactional()
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	@Override
	public List<Role> findAll(Sort sort) {
		return roleDao.findAll(sort);
	}

	@Override
	public List<Role> findAllById(Iterable<Integer> ids) {
		return roleDao.findAllById(ids);
	}

	@Override
	public List<Role> saveAll(Iterable<Role> entities) {
		return roleDao.saveAll(entities);
	}

	@Override
	public void flush() {
		roleDao.flush();
	}

	@Override
	public Role saveAndFlush(Role entity) {
		return roleDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<Role> entities) {
		roleDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		roleDao.deleteAllInBatch();
	}

	@Override
	public Role getOne(Integer id) {
		return roleDao.getOne(id);
	}

	@Override
	public Role save(Role entity) {
		return roleDao.save(entity);
	}

	@Override
	public Role findById(Integer id) {
		return roleDao.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return roleDao.existsById(id);
	}

	@Override
	public long count() {
		return roleDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		roleDao.deleteById(id);
	}

	@Override
	public void delete(Role entity) {
		roleDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends Role> entities) {
		roleDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		roleDao.deleteAll();
	}

	@Override
	public Role findByRoleName(String roleName) {
		return roleDao.findByRoleName(roleName);
	}

}
