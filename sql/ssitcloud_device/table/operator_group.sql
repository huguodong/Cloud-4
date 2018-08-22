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

/*Table structure for table `operator_group` */

DROP TABLE IF EXISTS `operator_group`;

CREATE TABLE `operator_group` (
  `operator_group_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作员组ID',
  `operator_group_id` varchar(50) NOT NULL,
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `operator_group_name` varchar(100) NOT NULL COMMENT '操作员组名',
  `operator_group_desc` varchar(100) DEFAULT NULL COMMENT '操作组描述',
  `operator_idx` int(11) NOT NULL COMMENT '创建者IDX',
  PRIMARY KEY (`operator_group_idx`),
  UNIQUE KEY `groupid` (`operator_group_id`,`library_idx`),
  UNIQUE KEY `groupname` (`library_idx`,`operator_group_name`),
  KEY `library_lID` (`library_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8 COMMENT='操作员组表，图书馆通过操作员组和操作员关联起来，并且简历操作员组和设备组、业务组的关联关系';

/*Data for the table `operator_group` */

insert  into `operator_group`(`operator_group_idx`,`operator_group_id`,`library_idx`,`operator_group_name`,`operator_group_desc`,`operator_idx`) values (1,'0001',1,'数据同步权限组','数据同步权限组',1),(2,'0002',1,'系统管理员','日常工作管理',1),(23,'00015',9,'硬件维护分组','D大调',1),(25,'00111',1,'机器业务组测试','',1),(26,'007',1,'借还书机','',1),(112,'0013',1,'设备注册与监控','',1),(138,'666',1,'办证与借还','',1),(148,'上海图书馆操作员分组',3,'上海图书馆操作员分组','',1),(149,'008',4,'HUB组','',1),(150,'上海图书馆分馆操作员分组管理',5,'上海图书馆分馆操作员分组管理','',1),(151,'TEST005',7,'TEST005无外键','',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
