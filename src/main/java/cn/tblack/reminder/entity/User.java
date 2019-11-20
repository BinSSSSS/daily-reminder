package cn.tblack.reminder.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import static cn.tblack.reminder.constant.WebConfigProperties.ALLOW_REMINDER_COUNT;
/**
 * @表示数据库内的user数据表
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Entity
public class User implements Serializable{


	private static final long serialVersionUID = -6434641704030506031L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull(message = "用户名不能为空")
	private String username;
	@NotNull(message = "用户密码不能为空")
	private String password;
	@Email
	private String email;

	@Column(name = "reminder_count")
	private Integer reminderCount = 0;

	@Column(name = "allow_reminder_count")
	private Integer allowReminderCount = ALLOW_REMINDER_COUNT;

	@OneToMany(targetEntity = Role.class, fetch = FetchType.EAGER,
			// 级联实体持久化操作和级联实体合并操作
			cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	// 级联查询通过外键进行操作,inverseJoinColumns表示的就是外键
	@JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	@OrderBy("id")
	private Set<Role> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Integer getReminderCount() {
		return reminderCount;
	}

	public void setReminderCount(Integer reminderCount) {
		this.reminderCount = reminderCount;
	}

	public Integer getAllowReminderCount() {
		return allowReminderCount;
	}

	public void setAllowReminderCount(Integer allowReminderCount) {
		this.allowReminderCount = allowReminderCount;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}

}
