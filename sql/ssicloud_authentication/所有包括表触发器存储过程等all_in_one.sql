/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.21-log : Database - ssitcloud_authentication
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ssitcloud_authentication` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ssitcloud_authentication`;

/*Table structure for table `ip_white` */

DROP TABLE IF EXISTS `ip_white`;

CREATE TABLE `ip_white` (
  `operator_idx` int(11) NOT NULL COMMENT '用户ID',
  `ipaddr` varchar(64) NOT NULL COMMENT '白名单IP',
  PRIMARY KEY (`operator_idx`),
  KEY `ipaddr` (`ipaddr`),
  CONSTRAINT `ip_white_ibfk_1` FOREIGN KEY (`operator_idx`) REFERENCES `operator` (`operator_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='IP白名单，存储允许用户登陆的客户端IP地址';

/*Data for the table `ip_white` */

/*Table structure for table `library` */

DROP TABLE IF EXISTS `library`;

CREATE TABLE `library` (
  `library_idx` int(11) NOT NULL AUTO_INCREMENT,
  `lib_id` varchar(20) NOT NULL COMMENT '图书馆ID',
  `lib_name` varchar(100) DEFAULT NULL COMMENT '名称',
  `lib_service_tpl_id` int(11) DEFAULT NULL COMMENT '图书馆模板id',
  `service_start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '服务开始时间',
  `service_expire_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '服务截止时间',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`library_idx`),
  UNIQUE KEY `lib_id` (`lib_id`),
  UNIQUE KEY `lib_name` (`lib_name`),
  KEY `lib_service_tpl_id` (`lib_service_tpl_id`),
  CONSTRAINT `library_ibfk_1` FOREIGN KEY (`lib_service_tpl_id`) REFERENCES `library_service_template` (`lib_service_tpl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `library` */

insert  into `library`(`library_idx`,`lib_id`,`lib_name`,`lib_service_tpl_id`,`service_start_date`,`service_expire_date`,`createtime`) values (0,'0','云平台',0,'2016-10-12 11:40:58','2016-11-30 17:03:41','2016-10-12 11:40:58');

/*Table structure for table `library_info` */

DROP TABLE IF EXISTS `library_info`;

CREATE TABLE `library_info` (
  `library_idx` int(11) NOT NULL COMMENT '图书馆id',
  `infotype_idx` int(11) NOT NULL COMMENT '信息类型',
  `info_value` varchar(100) DEFAULT NULL COMMENT '信息值',
  KEY `library_lID` (`library_idx`),
  KEY `m_info_type_mitID` (`infotype_idx`),
  CONSTRAINT `library_info_ibfk_3` FOREIGN KEY (`library_idx`) REFERENCES `library` (`library_idx`),
  CONSTRAINT `library_info_ibfk_4` FOREIGN KEY (`infotype_idx`) REFERENCES `metadata_infotype` (`infotype_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书馆信息表';

/*Data for the table `library_info` */

/*Table structure for table `library_service_template` */

DROP TABLE IF EXISTS `library_service_template`;

CREATE TABLE `library_service_template` (
  `lib_service_tpl_id` int(11) NOT NULL AUTO_INCREMENT,
  `lib_service_tpl_desc` varchar(100) DEFAULT NULL COMMENT '馆服务描述',
  `service_cycle` int(11) DEFAULT NULL COMMENT '周期（月为单位）',
  `max_device_count` int(11) DEFAULT NULL COMMENT '最大设备数',
  `max_operator_count` int(11) DEFAULT NULL COMMENT '最大用户数',
  `max_sublib_count` int(11) DEFAULT NULL COMMENT '分馆数',
  PRIMARY KEY (`lib_service_tpl_id`),
  UNIQUE KEY `tpl_desc` (`lib_service_tpl_desc`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `library_service_template` */

insert  into `library_service_template`(`lib_service_tpl_id`,`lib_service_tpl_desc`,`service_cycle`,`max_device_count`,`max_operator_count`,`max_sublib_count`) values (0,'云平台',1,200,200,0);

/*Table structure for table `log_message` */

DROP TABLE IF EXISTS `log_message`;

CREATE TABLE `log_message` (
  `log_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `log_code` varchar(6) NOT NULL COMMENT '消息代码',
  `log_desc` varchar(50) NOT NULL COMMENT '消息说明',
  `log_mark` varchar(200) DEFAULT NULL COMMENT '数据参数说明',
  PRIMARY KEY (`log_idx`),
  KEY `log_code` (`log_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5203 DEFAULT CHARSET=utf8;

/*Data for the table `log_message` */

insert  into `log_message`(`log_idx`,`log_code`,`log_desc`,`log_mark`) values (2,'5002','用户退出系统','馆ID｜用户ID｜用户名｜'),(3,'1101','设备注册','监控模板ID｜设备模板ID｜运行模板ID｜数据同步模板ID'),(4,'2110','监控管理','馆IDX｜维护指令｜设备ID组'),(5,'3101','新增用户','馆IDX｜用户IDX｜用户名｜'),(6,'3102','修改用户','馆IDX｜用户IDX｜用户名｜'),(7,'3103','删除用户','馆IDX｜用户IDX｜用户名｜'),(10,'3201','新加用户组','馆IDX｜用户组IDX｜用户组名｜'),(11,'3202','修改用户组信息','馆IDX｜用户组IDX｜用户组名｜'),(12,'3203','删除用户组信息','馆IDX｜用户组IDX｜用户组名｜'),(13,'3301','修改密码','馆IDX｜用户IDX｜用户名｜'),(15,'4101','新增图书馆信息','馆IDX｜馆名'),(16,'4102','修改图书馆信息','馆IDX｜馆名'),(17,'4103','删除图书馆信息','馆IDX｜馆名'),(18,'4201','新增读者流通类型','馆IDX｜流通类型IDX｜流通类型名'),(19,'4202','修改读者流通类型','馆IDX｜流通类型IDX｜流通类型名'),(20,'4203','删除读者流通类型','馆IDX｜流通类型IDX｜流通类型名'),(21,'4301','新增自助借还协议模板','馆IDX｜协议IDX｜协议内容'),(22,'4302','修改自助借还协议模板','馆IDX｜协议IDX｜协议内容'),(23,'4303','删除自助借还协议模板','馆IDX｜协议IDX｜协议内容'),(24,'4401','新增图书馆服务模板','馆IDX｜服务模板IDX｜服务模板名'),(25,'4402','修改图书馆服务模板','馆IDX｜服务模板IDX｜服务模板名'),(26,'4403','删除图书馆服务模板','馆IDX｜服务模板IDX｜服务模板名'),(27,'4501','新增数据库同步模板配置','馆IDX｜同步模板IDX｜同步模板名'),(28,'4502','修改数据库同步模板配置','馆IDX｜同步模板IDX｜同步模板名'),(29,'4503','删除数据库同步模板配置','馆IDX｜同步模板IDX｜同步模板名'),(30,'4601','新增数据查询模板','馆IDX｜查询模板IDX｜查询模板名'),(31,'4602','修改数据查询模板','馆IDX｜查询模板IDX｜查询模板名'),(32,'4603','删除数据查询模板','馆IDX｜查询模板IDX｜查询模板名'),(33,'4701','新增密码模板','馆IDX｜密码模板IDX｜密码模板名'),(34,'4702','修改密码模板','馆IDX｜密码模板IDX｜密码模板名'),(35,'4703','删除密码模板','馆IDX｜密码模板IDX｜密码模板名'),(36,'4801','新增统计模板','馆IDX｜统计模板IDX｜统计模板名'),(37,'4802','修改统计模板','馆IDX｜统计模板IDX｜统计模板名'),(38,'4803','删除统计模板','馆IDX｜统计模板IDX｜统计模板名'),(39,'5101','新增设备基本信息','馆IDX｜设备IDX｜设备名'),(40,'5102','修改设备基本信息','馆IDX｜设备IDX｜设备名'),(41,'5103','删除设备基本信息','馆IDX｜设备IDX｜设备名'),(42,'5201','新增设备组信息','馆IDX｜设备组IDX｜设备组名'),(43,'5202','修改设备组信息','馆IDX｜设备组IDX｜设备组名'),(44,'5203','删除设备组信息','馆IDX｜设备组IDX｜设备组名'),(45,'5311','新增硬件与逻辑名配置模板','硬件与逻辑名配置模板IDX｜硬件与逻辑名配置模板名'),(46,'5312','修改硬件与逻辑名配置模板','硬件与逻辑名配置模板IDX｜硬件与逻辑名配置模板名'),(47,'5313','删除硬件与逻辑名配置模板','硬件与逻辑名配置模板IDX｜硬件与逻辑名配置模板名'),(48,'5321','新增亮灯模板配置','亮灯模板IDX｜亮灯模板名'),(49,'5322','修改亮灯模板配置','亮灯模板IDX｜亮灯模板名'),(50,'5323','删除亮灯模板配置','亮灯模板IDX｜亮灯模板名'),(51,'5331','新增运行日志配置','运行日志模板IDX｜运行日志模板名'),(52,'5332','修改运行日志配置','运行日志模板IDX｜运行日志模板名'),(53,'5333','删除运行日志配置','运行日志模板IDX｜运行日志模板名'),(54,'5341','新增同步数据配置','同步数据配置IDX｜同步数据配置名'),(55,'5342','修改同步数据配置','同步数据配置IDX｜同步数据配置名'),(56,'5343','删除同步数据配置','同步数据配置IDX｜同步数据配置名'),(57,'5401','新增设备服务管理逻辑','馆IDX｜设备组IDX｜服务组IDX｜设备服务管理名'),(58,'5402','修改设备服务管理逻辑','馆IDX｜设备组IDX｜服务组IDX｜设备服务管理名'),(59,'5403','删除设备服务管理逻辑','馆IDX｜设备组IDX｜服务组IDX｜设备服务管理名'),(60,'6100','系统日志查询与导出','馆IDX｜库类型｜｜'),(61,'6200','数据库备份','馆IDX｜库类型｜｜'),(62,'5501','新增设备类型','IDX|名称'),(63,'5502','修改设备类型','IDX|名称'),(64,'5503','删除设备类型','IDX'),(65,'3401','新增权限分组','IDX|分组名'),(66,'3402','修改权限分组','IDX|分组名'),(67,'3403','删除权限分组','IDX'),(5201,'5001','登录验证','失败次数|||'),(5202,'6300','数据库还原','馆IDX｜库类型｜｜');

/*Table structure for table `metadata_infotype` */

DROP TABLE IF EXISTS `metadata_infotype`;

CREATE TABLE `metadata_infotype` (
  `infotype_idx` int(11) NOT NULL AUTO_INCREMENT,
  `info_type` varchar(100) NOT NULL COMMENT '信息类型 1用户  2图书馆',
  `info_type_desc` varchar(100) NOT NULL COMMENT '信息类型描述，身份证，电话号码，QQ号',
  PRIMARY KEY (`infotype_idx`),
  KEY `mitID` (`infotype_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='信息描述元数据表，描述图书馆的站点信息';

/*Data for the table `metadata_infotype` */

insert  into `metadata_infotype`(`infotype_idx`,`info_type`,`info_type_desc`) values (1,'1','出生日期'),(2,'1','身份证号'),(3,'1','户照号'),(4,'1','港澳通行证号'),(5,'1','市民卡号'),(6,'1','固话'),(7,'1','手机'),(8,'1','E-mail'),(9,'1','QQ'),(10,'1','微信'),(11,'1','工作单位'),(12,'1','公司地址'),(13,'1','家庭地址'),(14,'1','网址'),(15,'2','固话'),(16,'2','地址'),(17,'2','网址'),(18,'2','服务邮箱'),(19,'2','服务QQ号'),(20,'2','服务电话'),(21,'2','微信公众号'),(22,'2','微博'),(23,'2','休馆时间');

/*Table structure for table `operation_log` */

DROP TABLE IF EXISTS `operation_log`;

CREATE TABLE `operation_log` (
  `operation_log_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `operator_idx` int(11) NOT NULL COMMENT '操作员ID',
  `client_ip` varchar(64) NOT NULL COMMENT '客户端IP',
  `client_port` varchar(10) NOT NULL COMMENT '客户端端口号',
  `operation_cmd` varchar(10) NOT NULL COMMENT '操作类型',
  `operation_result` varchar(10) NOT NULL COMMENT '操作结果',
  `operation` text COMMENT '详细说明',
  `error_code` varchar(10) DEFAULT NULL COMMENT '错误码',
  PRIMARY KEY (`operation_log_idx`),
  KEY `operation_time` (`operation_time`),
  KEY `operation_result` (`operation_result`)
) ENGINE=InnoDB AUTO_INCREMENT=222 DEFAULT CHARSET=utf8;

/*Data for the table `operation_log` */

/*Table structure for table `operator` */

DROP TABLE IF EXISTS `operator`;

CREATE TABLE `operator` (
  `operator_idx` int(11) NOT NULL AUTO_INCREMENT,
  `operator_id` varchar(64) NOT NULL COMMENT '用户ID',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `sox_tpl_id` int(11) NOT NULL COMMENT 'SOX模板ID',
  `operator_name` varchar(64) NOT NULL COMMENT '用户名',
  `operator_pwd` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `operator_type` int(11) NOT NULL COMMENT '用户类型，1-云平台系统管理员，2-海恒维护、3-图书馆系统管理员、4-图书馆用户、5－设备用户',
  `isActive` tinyint(1) NOT NULL COMMENT '是否激活',
  `isLock` tinyint(1) NOT NULL COMMENT '是否锁定',
  `isLogged` tinyint(4) NOT NULL COMMENT '是否已经登录',
  `last_login_ip` varchar(64) DEFAULT NULL COMMENT '最后登录IP',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_lock_time` timestamp NULL DEFAULT NULL COMMENT '最后锁定时间',
  `last_chgpwd_time` timestamp NULL DEFAULT NULL COMMENT '最后修改密码时间',
  `login_fail_times` smallint(6) DEFAULT NULL COMMENT '登录失败次数，满足配置自动锁定用户',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`operator_idx`),
  UNIQUE KEY `operator_id` (`operator_id`),
  KEY `lib_id` (`library_idx`,`operator_id`),
  KEY `sox_tpl_id` (`sox_tpl_id`),
  CONSTRAINT `operator_ibfk_4` FOREIGN KEY (`sox_tpl_id`) REFERENCES `sox_template` (`sox_tpl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `operator` */

insert  into `operator`(`operator_idx`,`operator_id`,`library_idx`,`sox_tpl_id`,`operator_name`,`operator_pwd`,`operator_type`,`isActive`,`isLock`,`isLogged`,`last_login_ip`,`last_login_time`,`last_lock_time`,`last_chgpwd_time`,`login_fail_times`,`createtime`) values (1,'admin',0,2,'admin','nBmdp2RO+xa1aXbPJGZ+34csOXIS5ZSkSuioT7b5F1yVhO51f+nTo7kvA0kQA0xctIQWGQ9A/9Xo\noO76CclOd09WqdxusAKT/UEm53abCNUprjGmBU/J4bH4zuZoxl7pTSoJBWuww5E2wIAKy9t5JC26\nexsyXsAENNfFy9cJalE=',1,1,1,0,'127.0.0.1','2016-08-29 16:14:45','2016-10-13 15:46:16','2016-08-29 16:14:55',2,'2016-08-29 16:14:59');

/*Table structure for table `operator_info` */

DROP TABLE IF EXISTS `operator_info`;

CREATE TABLE `operator_info` (
  `operator_idx` int(11) NOT NULL,
  `infotype_idx` int(11) NOT NULL,
  `info_value` varchar(100) NOT NULL,
  KEY `m_info_type_mitID` (`infotype_idx`),
  KEY `operator_idx` (`operator_idx`),
  CONSTRAINT `operator_info_ibfk_4` FOREIGN KEY (`infotype_idx`) REFERENCES `metadata_infotype` (`infotype_idx`),
  CONSTRAINT `operator_info_ibfk_5` FOREIGN KEY (`operator_idx`) REFERENCES `operator` (`operator_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表，存储用户的身份信息，添加、修改、删除、查询用户需要操作这个表';

/*Data for the table `operator_info` */

/*Table structure for table `password_history` */

DROP TABLE IF EXISTS `password_history`;

CREATE TABLE `password_history` (
  `password_idx` int(11) NOT NULL AUTO_INCREMENT,
  `operator_idx` int(11) NOT NULL COMMENT '操作员表ID',
  `password` varchar(100) NOT NULL COMMENT '历史密码',
  `modifyTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  PRIMARY KEY (`password_idx`),
  KEY `operator_oID` (`operator_idx`),
  CONSTRAINT `password_history_ibfk_1` FOREIGN KEY (`operator_idx`) REFERENCES `operator` (`operator_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户历史密码表，存储用户已经使用过的密码信息，用户修改密码需要查询的和修改这个表，以保证密码不重复';

/*Data for the table `password_history` */

/*Table structure for table `patch_info` */

DROP TABLE IF EXISTS `patch_info`;

CREATE TABLE `patch_info` (
  `patch_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID号',
  `patch_version` varchar(20) NOT NULL COMMENT '版本号',
  `patch_desc` varchar(200) NOT NULL COMMENT '版本说明',
  `patch_type` varchar(50) DEFAULT NULL COMMENT '版本类型',
  `restrict_info` varchar(100) DEFAULT NULL COMMENT '约束说明',
  `patch_directory` varchar(200) NOT NULL COMMENT '客户端路径',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`patch_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `patch_info` */

/*Table structure for table `rel_libs` */

DROP TABLE IF EXISTS `rel_libs`;

CREATE TABLE `rel_libs` (
  `master_lib_id` int(11) NOT NULL COMMENT '总馆id',
  `slave_lib_id` int(11) NOT NULL COMMENT '分馆id',
  `rel_type` varchar(20) NOT NULL COMMENT '关系类型，从属关系，并列关系',
  PRIMARY KEY (`master_lib_id`,`slave_lib_id`),
  KEY `slave_lib_id` (`slave_lib_id`),
  CONSTRAINT `rel_libs_ibfk_1` FOREIGN KEY (`master_lib_id`) REFERENCES `library` (`library_idx`),
  CONSTRAINT `rel_libs_ibfk_2` FOREIGN KEY (`slave_lib_id`) REFERENCES `library` (`library_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书馆主从关系表';

/*Data for the table `rel_libs` */

/*Table structure for table `rsa_key` */

DROP TABLE IF EXISTS `rsa_key`;

CREATE TABLE `rsa_key` (
  `rsa_idx` int(11) NOT NULL AUTO_INCREMENT,
  `publicKey` varchar(2000) NOT NULL COMMENT '公钥',
  `privateKey` varchar(2000) NOT NULL COMMENT '私钥',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`rsa_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `rsa_key` */

insert  into `rsa_key`(`rsa_idx`,`publicKey`,`privateKey`,`createtime`) values (1,'<RSAKeyValue><Modulus>ALIXPJ3UkxV/kCndHTj+DFKuXOlIdUMdax4SOqtIKo0gT6Gfe0abdWtu1mMyHamKw/MmHMvDHU/mzwkGNKtYJjcJr91T0jFg6fJ/Gln2ah5qty3KXqtD+SNc8a7ghWmcUeoDOSWGzh9KWaSUhG3fcZkSesPr86Bw62EvsfaUVNOR</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>','<RSAKeyValue><Modulus>ALIXPJ3UkxV/kCndHTj+DFKuXOlIdUMdax4SOqtIKo0gT6Gfe0abdWtu1mMyHamKw/MmHMvDHU/mzwkGNKtYJjcJr91T0jFg6fJ/Gln2ah5qty3KXqtD+SNc8a7ghWmcUeoDOSWGzh9KWaSUhG3fcZkSesPr86Bw62EvsfaUVNOR</Modulus><Exponent>AQAB</Exponent><P>ANgGzRDJR0rUI4zARACpOp35RZdv5d8IvPZ7ArSoCvAeVEsKWqIdJBuvjOwev7+k6smKPbSVOceLDqZfvnr0REk=</P><Q>ANMLadSWwSTCj+dnI+3uSrabs27ffdQt6AvITub5wbYZ5HWYHrG8aDsIMqtLl7bLwaJb+ZESGoyPBblQDTchBQk=</Q><DP>DqlngZwnmoyLXSIve1wA/nfMoVqW32xYZuIybNB67ZEhZ3ZscFRJ/xcLGXt8yCUJSmR3i6oVIdXuSJx28SFjAQ==</DP><DQ>APtvnaap9XLWWpxXRXczb9AfsKdnnYItL0jaXSbSaPeL4aQ4mFkpHwU1vyhV52rhVtEYwz0TVI4h16/wr83+iQ==</DQ><InverseQ>B8G9tRvIHXnNHC94zIgSZsksWNbfVeYLJNFrRrN3jZFXApKd6zraxgEwWVLFEhzdicU/F3ErBMw6GzWI6bD1FQ==</InverseQ><D>NVybEBui2r03QCP93pbYsGmIc2n/oNWOxBroM2xTO6gj1CTKNlTccQ1r9ZsNokBvCEsyM6fJ/6gD/ws5+uFVyYYJLNZJJJVywSARvY20f6RPKYpZnbL1ZBDd0BqVLPHcru1p4jz+ZY3Uw51HFNHvsfkT57TohaHY4PAuxhS2v0E=</D></RSAKeyValue>','2016-04-13 19:17:09');

/*Table structure for table `sox_template` */

DROP TABLE IF EXISTS `sox_template`;

CREATE TABLE `sox_template` (
  `sox_tpl_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  `sox_tpl_name` varchar(100) NOT NULL COMMENT '模板名称',
  `password_length` smallint(6) DEFAULT NULL COMMENT '密码长度',
  `password_charset` varchar(20) DEFAULT NULL COMMENT '密码字符集1大写，2小写，3数字，4特殊字符^%&'',;=?$/',
  `login_fail_times` smallint(6) DEFAULT NULL COMMENT '登录失败次数',
  `lock_time` smallint(6) DEFAULT NULL COMMENT '锁定时长',
  `first_login_chgpwd` smallint(6) DEFAULT NULL COMMENT '首次登陆修改密码1是，0否',
  `count_history_password` smallint(6) DEFAULT NULL COMMENT '历史密码保留个数',
  `password_validdays` smallint(6) DEFAULT NULL COMMENT '密码有效天数',
  `password_remind` smallint(6) DEFAULT NULL COMMENT '提前提醒天数',
  `vaild_time` varchar(100) DEFAULT NULL COMMENT '用户可用时间段,登录时间段如8:00-9:00',
  PRIMARY KEY (`sox_tpl_id`),
  UNIQUE KEY `sox_tpl_id` (`sox_tpl_id`),
  KEY `sox_tpl_name` (`sox_tpl_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='sox模板表，存储控制用户可用性方面的配置';

/*Data for the table `sox_template` */

insert  into `sox_template`(`sox_tpl_id`,`sox_tpl_name`,`password_length`,`password_charset`,`login_fail_times`,`lock_time`,`first_login_chgpwd`,`count_history_password`,`password_validdays`,`password_remind`,`vaild_time`) values (1,'device',9,'2',3,6,1,3,1,1,'1:00-24:00'),(2,'operator',10,'2',2,1,0,1,0,1,'1:00-24:00');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
