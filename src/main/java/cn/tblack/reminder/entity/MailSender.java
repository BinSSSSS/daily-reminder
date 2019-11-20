package cn.tblack.reminder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import cn.tblack.reminder.constant.CustomBoolean;

/**
 * @表示用户设置要定时发送的邮件信息
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
@Entity
@Table(name = "mail_sender")
public class MailSender implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Email
	@Column(name = "send_to")
	private String sendTo;

	private String title;

	private String content;

	private String files;

	private String attachments;

	@Column(name = "send_time")
	private Date sendTime;

	@Column(name = "is_success")
	private Short success = CustomBoolean.FALSE;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Short getSuccess() {
		return success;
	}

	public void setSuccess(Short success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "MailSender [id=" + id + ", sendTo=" + sendTo + ", title=" + title + ", content=" + content + ", files="
				+ files + ", attchments=" + attachments + ", sendTime=" + sendTime + ", success=" + success + "]";
	}

}
