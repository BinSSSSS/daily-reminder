package cn.tblack.reminder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

/**
 * @存放发送验证码信息
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Entity
public class VerificationMail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Email
	@Column(name = "recipient_mail")
	private String recipientMail;

	@Column(name = "create_time")
	private Date createTime;

	private String code;

	private Date deadline;

	private Long weights;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRecipientAddress() {
		return recipientMail;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientMail = recipientAddress;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Long getWeights() {
		return weights;
	}

	public void setWeights(Long weights) {
		this.weights = weights;
	}

	@Override
	public String toString() {
		return "VerificationMail [id=" + id + ", recipientAddress=" + recipientMail + ", createTime=" + createTime
				+ ", code=" + code + ", deadline=" + deadline + ", weights=" + weights + "]";
	}

}
