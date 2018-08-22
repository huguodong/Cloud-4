/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.21-log : Database - ssitcloud_device
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

/*Table structure for table `device` */

DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `device_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(20) NOT NULL COMMENT '设备ID',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `device_model_idx` int(11) NOT NULL COMMENT '设备类型id',
  `device_name` varchar(100) NOT NULL COMMENT '设备名',
  `device_code` varchar(100) NOT NULL COMMENT '设备特征码。设备自动计算获得',
  `device_desc` varchar(100) DEFAULT NULL COMMENT '设备描述',
  `library_login_name` varchar(20) DEFAULT NULL COMMENT '自助服务登录用户名',
  `library_login_pwd` varchar(20) DEFAULT NULL COMMENT '自助服务登录密码',
  `logistics_number` varchar(20) DEFAULT NULL COMMENT '物流编号',
  `circule_location` varchar(200) DEFAULT NULL COMMENT '流通地点',
  PRIMARY KEY (`device_idx`),
  UNIQUE KEY `device_id` (`device_id`),
  UNIQUE KEY `device_code` (`device_code`),
  KEY `device_model_dmID` (`device_model_idx`),
  CONSTRAINT `device_ibfk_1` FOREIGN KEY (`device_model_idx`) REFERENCES `metadata_devicetype` (`meta_devicetype_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='设备表，存储设备信息。device_type字段由metadata_devicetype表定义';

/*Data for the table `device` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
