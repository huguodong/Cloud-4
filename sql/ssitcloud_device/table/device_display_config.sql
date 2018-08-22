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

/*Table structure for table `device_display_config` */

DROP TABLE IF EXISTS `device_display_config`;

CREATE TABLE `device_display_config` (
  `display_type_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `device_type_idx` int(11) DEFAULT NULL COMMENT '关联设备类型id',
  `display_type_id` varchar(20) DEFAULT NULL COMMENT '风格编号',
  `display_type_name` varchar(20) DEFAULT NULL COMMENT '风格名称',
  `display_type_url` varchar(100) DEFAULT NULL COMMENT '跳转的url',
  `display_type_desc` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`display_type_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `device_display_config` */

insert  into `device_display_config`(`display_type_idx`,`device_type_idx`,`display_type_id`,`display_type_name`,`display_type_url`,`display_type_desc`) values (1,57,'style1','style1','http://device.seaeverit.com:8003/SEAMATE_STA/views/CN/index.html#/','style13'),(2,3,'style1','风格1','http://device.seaeverit.com:8003/VERTICAL_STA-C/views/CN/index.html#/','风格1'),(3,4,'style1','风格1','http://device.seaeverit.com:8003/EVERMATE_SCH/views/CN/index.html#/','风格1'),(4,59,'style1','风格2','http://device.seaeverit.com:8003/VERTICAL_STA-C/views/CN/index.html#/','风格2'),(6,2,'style1','风格1','http://device.seaeverit.com:8003/EVERMATE_SCH/views/CN/index.html#/','风格1'),(7,1,'style1','风格4','http://device.seaeverit.com:8003/EVERMATE_SCH/views/CN/index.html#/','风格4'),(8,58,'style1','风格1','http://device.seaeverit.com:8003/VERTICAL_STA-C/views/CN/index.html#/','风格1'),(9,60,'style1','风格1','http://device.seaeverit.com:8003/VERTICAL_STA-C/views/CN/index.html#/','风格1'),(10,63,'style2','风格3','http://device.seaeverit.com:8003/SEAMATE_STA/views/CN/index.html#/','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
