package cn.tblack.reminder.service;

import java.util.List;
import org.springframework.data.domain.Sort;

import cn.tblack.reminder.entity.Role;

public interface RoleService {
	
	List<Role> findAll();

	List<Role> findAll(Sort sort);

	List<Role> findAllById(Iterable<Integer> ids);

	List<Role> saveAll(Iterable<Role> entities);

	void flush();

	Role saveAndFlush(Role entity);

	void deleteInBatch(Iterable<Role> entities);

	void deleteAllInBatch();

	Role getOne(Integer id);

	Role save(Role entity);

	Role findById(Integer id);
	
	Role findByRoleName(String roleName);

	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(Role entity);

	void deleteAll(Iterable<? extends Role> entities);

	void deleteAll();
}
