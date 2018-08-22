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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
