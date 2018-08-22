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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
