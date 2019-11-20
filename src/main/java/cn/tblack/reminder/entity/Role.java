package cn.tblack.reminder.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
/**
 * @数据库内角色实体表。 表中存在多种角色用来进行权限控制；如普通用户、管理员等
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Entity
public class Role implements Serializable{


	private static final long serialVersionUID = 319280278405751964L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull(message = "角色不能为空")
	private String role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}

}
