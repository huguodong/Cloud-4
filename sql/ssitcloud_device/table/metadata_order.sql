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

/*Table structure for table `metadata_order` */

DROP TABLE IF EXISTS `metadata_order`;

CREATE TABLE `metadata_order` (
  `order_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '监控指令id',
  `order_desc` varchar(100) NOT NULL COMMENT '监控指令描述',
  `order_cmd` varchar(100) NOT NULL COMMENT '监控指令命令',
  PRIMARY KEY (`order_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='元数据表，监控指令，重启，关机等';

/*Data for the table `metadata_order` */

insert  into `metadata_order`(`order_idx`,`order_desc`,`order_cmd`) values (1,'{\"zh_CN\":\"关机\"}','shutdown -s -t 3'),(2,'{\"zh_CN\":\"重启\"}','shutdown -r -t 3'),(3,'{\"zh_CN\":\"查日志\"}',''),(4,'{\"zh_CN\":\"远程维护锁屏\"}',''),(5,'{\"zh_CN\":\"取消操作\"}','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
