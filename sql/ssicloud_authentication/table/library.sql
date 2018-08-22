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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
