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

/*Table structure for table `device_config` */

DROP TABLE IF EXISTS `device_config`;

CREATE TABLE `device_config` (
  `device_idx` int(11) NOT NULL COMMENT '设备ID',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `device_ext_tpl_idx` int(11) NOT NULL COMMENT '硬件配置模板ID',
  `device_ext_tpl_flg` int(11) NOT NULL COMMENT '是否采用模板 1是  0否',
  `device_monitor_tpl_idx` int(11) NOT NULL COMMENT '显示配置模板ID',
  `device_monitor_tpl_flg` int(11) NOT NULL COMMENT '显示是否采用模板 1是  0否',
  `device_run_tpl_idx` int(11) NOT NULL COMMENT '设备运行模板ID',
  `device_run_tpl_flg` int(11) NOT NULL COMMENT '运行是否采用模板 1是  0否',
  `device_dbsync_tpl_idx` int(11) NOT NULL COMMENT '数据同步模板ID',
  `device_dbsync_tpl_flg` int(11) NOT NULL COMMENT '同步是否采用模板 1是 0否',
  KEY `library_lID` (`library_idx`),
  KEY `device_dID` (`device_idx`),
  KEY `device_tpl_id` (`device_ext_tpl_idx`),
  KEY `device_config_ibfk_4` (`device_monitor_tpl_idx`),
  KEY `device_config_ibfk_5` (`device_run_tpl_idx`),
  KEY `device_config_ibfk_6` (`device_dbsync_tpl_idx`),
  CONSTRAINT `device_config_ibfk_2` FOREIGN KEY (`device_idx`) REFERENCES `device` (`device_idx`),
  CONSTRAINT `device_config_ibfk_3` FOREIGN KEY (`device_ext_tpl_idx`) REFERENCES `device_ext_template` (`device_tpl_idx`),
  CONSTRAINT `device_config_ibfk_4` FOREIGN KEY (`device_monitor_tpl_idx`) REFERENCES `device_monitor_template` (`device_mon_tpl_idx`),
  CONSTRAINT `device_config_ibfk_5` FOREIGN KEY (`device_run_tpl_idx`) REFERENCES `device_run_template` (`device_tpl_idx`),
  CONSTRAINT `device_config_ibfk_6` FOREIGN KEY (`device_dbsync_tpl_idx`) REFERENCES `device_dbsync_template` (`device_tpl_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_config` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
