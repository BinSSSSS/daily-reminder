package cn.tblack.reminder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import cn.tblack.reminder.constant.CustomBoolean;

/**
 * @用户设置的提醒，其中包含了用户id，包含了 schedule定时任务 id
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Entity
public class Reminder implements Serializable{


	private static final long serialVersionUID = -8907735226749932126L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class,
			// 级联实体持久化操作和级联实体合并操作
			cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "user_id")
	private User user; // 创建该提醒的用户，多个提醒对应一个用户
//	private Integer userId;

	@OneToOne(fetch = FetchType.EAGER, targetEntity = Schedule.class,
			// 级联实体持久化操作和级联实体合并操作
			cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "schedule_id")
	private Schedule schedule; // 调度任务，一对一的关系
//	private Integer scheduleId;

	@OneToOne(fetch = FetchType.EAGER, targetEntity = MailSender.class,
			// 级联实体持久化操作和级联实体合并操作
			cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "mail_sender_id")
	private MailSender mailSender;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "is_deprecated")
	private Short deprecated = CustomBoolean.FALSE;

	@Column(name = "finished_time")
	private Date finishedTime;

	@Column(name = "finished_count")
	private Integer finishedCount = 0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public Integer getFinishedCount() {
		return finishedCount;
	}

	public void setFinishedCount(Integer finishedCount) {
		this.finishedCount = finishedCount;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public Short getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(Short deprecated) {
		this.deprecated = deprecated;
	}

	@Override
	public String toString() {
		return "Reminder [id=" + id + ", user=" + user + ", schedule=" + schedule + ", mailSender=" + mailSender
				+ ", createTime=" + createTime + ", deprecated=" + deprecated + ", finishedTime=" + finishedTime
				+ ", finishedCount=" + finishedCount + "]";
	}

}
