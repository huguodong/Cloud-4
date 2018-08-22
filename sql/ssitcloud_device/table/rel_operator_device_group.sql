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

/*Table structure for table `rel_operator_device_group` */

DROP TABLE IF EXISTS `rel_operator_device_group`;

CREATE TABLE `rel_operator_device_group` (
  `rel_oper_dev_idx` int(11) NOT NULL AUTO_INCREMENT,
  `operator_group_idx` int(11) NOT NULL COMMENT '操作员组ID',
  `device_group_idx` int(11) NOT NULL COMMENT '设备组ID',
  `library_idx` int(11) NOT NULL,
  PRIMARY KEY (`rel_oper_dev_idx`),
  KEY `operator_group_id` (`operator_group_idx`,`device_group_idx`),
  KEY `device_group_id` (`device_group_idx`),
  KEY `operator_group_id_2` (`operator_group_idx`),
  KEY `lib_id` (`library_idx`),
  CONSTRAINT `rel_operator_device_group_ibfk_1` FOREIGN KEY (`operator_group_idx`) REFERENCES `operator_group` (`operator_group_idx`),
  CONSTRAINT `rel_operator_device_group_ibfk_2` FOREIGN KEY (`device_group_idx`) REFERENCES `device_group` (`device_group_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=461 DEFAULT CHARSET=utf8 COMMENT='操作员组和设备组关联，操作员组和设备组之间是多对多的关系';

/*Data for the table `rel_operator_device_group` */

insert  into `rel_operator_device_group`(`rel_oper_dev_idx`,`operator_group_idx`,`device_group_idx`,`library_idx`) values (52,1,4,1),(77,2,1,1),(81,26,1,1),(86,138,1,1),(87,138,4,1),(89,25,1,1),(90,25,3,1),(91,25,4,1),(118,112,1,1),(230,148,22,3),(252,23,1,9),(253,23,3,9),(254,23,4,9),(255,23,5,9),(256,23,6,9),(257,23,7,9),(258,23,8,9),(283,150,23,5),(450,149,1,4),(451,149,3,4),(452,149,4,4),(453,149,5,4),(454,149,6,4),(455,149,7,4),(456,149,8,4),(457,149,21,4),(458,149,22,4),(459,149,23,4),(460,149,24,4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
