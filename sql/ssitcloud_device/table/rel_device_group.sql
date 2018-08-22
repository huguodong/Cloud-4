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

/*Table structure for table `rel_device_group` */

DROP TABLE IF EXISTS `rel_device_group`;

CREATE TABLE `rel_device_group` (
  `rel_device_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_group_idx` int(11) NOT NULL COMMENT '设备组ID',
  `device_idx` int(11) NOT NULL COMMENT '设备ID',
  `library_idx` int(11) NOT NULL,
  PRIMARY KEY (`rel_device_idx`),
  KEY `device_group_id` (`device_group_idx`),
  KEY `device_id` (`device_idx`),
  KEY `lib_id` (`library_idx`),
  CONSTRAINT `rel_device_group_ibfk_4` FOREIGN KEY (`device_idx`) REFERENCES `device` (`device_idx`),
  CONSTRAINT `rel_device_group_ibfk_5` FOREIGN KEY (`device_group_idx`) REFERENCES `device_group` (`device_group_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='设备分组关联表，设备和设备组之间是多对多的关系';

/*Data for the table `rel_device_group` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
