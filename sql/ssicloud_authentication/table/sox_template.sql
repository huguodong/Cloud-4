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
