package cn.tblack.reminder.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import cn.tblack.reminder.entity.MailSender;

/**
 * @邮件发送信息相关数据库操作
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
public interface MailSenderDao extends JpaRepository<MailSender, Integer>{

	
	/**
	 * @通过传递一个MailSender Id和一个表示该邮件是否发送成功的标识来更新数据库
	 * @param id
	 * @param success
	 * @return
	 */
	@Modifying
	@Query("UPDATE #{#entityName} m SET m.success = :success WHERE m.id = :id")
	int updateSendState(@Param("id") Integer id,@Param("success")short success);

	@Modifying
	@Query("UPDATE #{#entityName} m SET m.success = :success, m.sendTime=:nextSendTime WHERE m.id = :id")
	void updateSendStateAndNextSendTime(@Param("id") Integer id,@Param("success") Short success,@Param("nextSendTime") Date nextExecutionDate);
	
}
