use reminder;

DROP TABLE `user_roles`;
DROP TABLE `reminder`;
DROP TABLE `user`;
DROP TABLE `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `role`(role) 
	VALUES('ROLE_USER');
	
INSERT INTO `role`(role) 
	VALUES('ROLE_ADMIN');
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(50) NOT NULL COMMENT '用户的邮箱地址',
  `reminder_count` int(5) NOT NULL DEFAULT '0' COMMENT '已经创建的定时任务个数',
  `allow_reminder_count` int(5) NOT NULL DEFAULT '5' COMMENT '允许的最大创建定时任务个数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user`(username,password,email,reminder_count,allow_reminder_count)
	VALUES('Black','62696e73313233345444403939382e63','1395926989@qq.com',0,100);
	
	
	CREATE TABLE `user_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ROLE_ID_USER_ROLES_idx` (`role_id`),
  KEY `FK_USER_ID_USER_ROLES_idx` (`user_id`),
  CONSTRAINT `FK_ROLE_ID_USER_ROLES` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_USER_ID_USER_ROLES` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user_roles`(`user_id`,`role_id`)
	VALUES(1,1);
INSERT INTO `user_roles`(`user_id`,`role_id`)
	VALUES(1,2);
    
DROP TABLE `schedule`;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cron` varchar(50) NOT NULL COMMENT '定时任务表达式.其中就包含了是否重复等信息',
  `is_repeat` int(1) NOT NULL COMMENT '是否需要重复，1表示重复，0表示不重复',
  PRIMARY KEY (`id`),
  KEY `FK_USER_ID_idx` (`cron`),
  KEY `FK_SCHEDULE_ID_idx` (`is_repeat`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE `mail_sender`;

CREATE TABLE `mail_sender` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `send_to` varchar(50) NOT NULL COMMENT '发送到哪个邮箱',
  `title` varchar(200) DEFAULT '无标题' COMMENT '发送的标题名称',
  `content` longtext NOT NULL COMMENT '发送的主题内容',
  `files` text COMMENT '发送的文件信息,用逗号分隔地址',
  `Attachments` text COMMENT '发送的附件信息， 用逗号分隔',
  `send_time` datetime NOT NULL COMMENT '发送的时间',
  `is_success` int(1) DEFAULT '0' COMMENT '是否发送成功,1表示成功，0表示失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reminder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '创建的用户',
  `schedule_id` int(11) NOT NULL COMMENT '调度任务的id',
  `mail_sender_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_deprecated` int(1) DEFAULT '0' COMMENT '是否被弃用了,1表示被弃用，0表示仍有效',
  `finished_time` datetime DEFAULT NULL COMMENT '最后一次的完成时间',
  `finished_count` int(11) DEFAULT '0' COMMENT '已经完成的次数',
  PRIMARY KEY (`id`),
  KEY `FK_USER_ID_idx` (`user_id`),
  KEY `FK_SCHEDULE_ID_idx` (`schedule_id`),
  KEY `FK_MAIL_SENDER_ID_idx` (`mail_sender_id`),
  CONSTRAINT `FK_MAIL_SENDER_ID` FOREIGN KEY (`mail_sender_id`) REFERENCES `mail_sender` (`id`),
  CONSTRAINT `FK_SCHEDULE_ID` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`),
  CONSTRAINT `FK_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;    


DROP TABLE `verification_mail`;
CREATE TABLE `verification_mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipient_mail` varchar(50) NOT NULL COMMENT '接收方邮件地址',
  `create_time` datetime NOT NULL COMMENT '发送邮件的时间,精确到秒',
  `code` varchar(45) NOT NULL COMMENT '发送的验证码信息',
  `deadline` datetime NOT NULL COMMENT '截至的有效时间，精确到秒',
  `weights` double(12,0) NOT NULL COMMENT '权重值， 在一个用户发送过多条邮件之后，,越早创建的权重值越低，权重值设置方法由创建的时间来决定，,使用创建的时间的字符串转换为数字作为权重值。,如 2018-10-2/ 10:20 的权重值为 201810021020,之后查询数据的时候只需要拿到权重值最大的一条来判断是否过期即可',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;