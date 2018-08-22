/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.30-log : Database - ssitcloud_device
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ssitcloud_device` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ssitcloud_device`;

/*Table structure for table `protocol_config_template` */

DROP TABLE IF EXISTS `protocol_config_template`;

CREATE TABLE `protocol_config_template` (
  `protocol_tpl_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '模板表自增主键',
  `protocol_type` int(11) NOT NULL COMMENT '1 SIP2 2 NCIP',
  `protocol_tpl_desc` varchar(50) DEFAULT NULL COMMENT '备注',
  `library_idx` int(11) NOT NULL COMMENT '图书馆IDX',
  PRIMARY KEY (`protocol_tpl_idx`),
  UNIQUE KEY `protocol_tpl_desc` (`protocol_tpl_desc`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `protocol_config_template` */

insert  into `protocol_config_template`(`protocol_tpl_idx`,`protocol_type`,`protocol_tpl_desc`,`library_idx`) values (42,1,'SIP2001',1),(43,1,'上海图书馆SIP',3),(44,1,'hubmb',4),(45,1,'上海图书馆分馆',5);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
