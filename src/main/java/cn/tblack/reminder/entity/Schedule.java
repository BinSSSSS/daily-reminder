package cn.tblack.reminder.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @提醒的调度任务信息 (包括定时型、重复性）
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Entity
public class Schedule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String cron;

	@Column(name = "is_repeat")
	private Short repeat;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Short getRepeat() {
		return repeat;
	}

	public void setRepeat(Short repeat) {
		this.repeat = repeat;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", cron=" + cron + ", isRepeat=" + repeat + "]";
	}

}
