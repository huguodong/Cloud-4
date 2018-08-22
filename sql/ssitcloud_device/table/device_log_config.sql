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

/*Table structure for table `device_log_config` */

DROP TABLE IF EXISTS `device_log_config`;

CREATE TABLE `device_log_config` (
  `device_log_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_idx` int(11) NOT NULL COMMENT '设备ID',
  `runlog_level` int(11) NOT NULL COMMENT '日志级别，0-不记录，1-error，2-info，3-warning，4-debug',
  `runlog_type` int(11) NOT NULL COMMENT '保存方式，0-db，1-file',
  `runlog_filesize` int(11) NOT NULL COMMENT '日志保存文件的大小',
  `library_idx` int(11) NOT NULL,
  PRIMARY KEY (`device_log_idx`),
  KEY `device_id` (`device_idx`,`library_idx`),
  KEY `device_id_2` (`device_idx`),
  KEY `library_lID` (`library_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='设备运行日志的配置';

/*Data for the table `device_log_config` */

insert  into `device_log_config`(`device_log_idx`,`device_idx`,`runlog_level`,`runlog_type`,`runlog_filesize`,`library_idx`) values (1,2,1,3,2,1),(2,2,3,1,6,5),(4,2,0,3,0,0),(5,2,0,3,0,0),(6,2,0,3,0,0),(7,2,0,3,0,0),(8,2,0,3,0,0),(9,2,0,3,0,0),(10,2,1,3,2,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
