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

/*Table structure for table `device_monitor_template` */

DROP TABLE IF EXISTS `device_monitor_template`;

CREATE TABLE `device_monitor_template` (
  `device_mon_tpl_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_mon_tpl_id` varchar(10) NOT NULL COMMENT '显示模板ID',
  `device_mon_tpl_desc` varchar(200) NOT NULL COMMENT '显示模板描述',
  `device_mon_type_idx` int(11) NOT NULL COMMENT '设备类型',
  PRIMARY KEY (`device_mon_tpl_idx`),
  UNIQUE KEY `device_mon_tpl_desc` (`device_mon_tpl_desc`),
  UNIQUE KEY `device_mon_tpl_id` (`device_mon_tpl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

/*Data for the table `device_monitor_template` */

insert  into `device_monitor_template`(`device_mon_tpl_idx`,`device_mon_tpl_id`,`device_mon_tpl_desc`,`device_mon_type_idx`) values (28,'STATPL001','馆员工作站监控模板001',57),(29,'SCHTPL001','自助借还机监控模板001',2),(39,'s656','大打书',2),(40,'T0021','借还书机监控模板',2),(44,'10024','李泽鹏监控',62),(55,'REG','REG',3),(56,'STA-T1U','超高频馆员工作站监控模板',63),(57,'SHJK001','sh设备监控001',2),(58,'SHJK01','SH设备监控模板',57),(60,'HUB-JK','hub监控模板',63);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
