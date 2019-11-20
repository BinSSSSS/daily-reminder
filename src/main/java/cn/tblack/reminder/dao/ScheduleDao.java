package cn.tblack.reminder.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.tblack.reminder.entity.Schedule;

/**
 * @调度任务相关数据库操作
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
public interface ScheduleDao extends JpaRepository<Schedule, Integer>{

}
