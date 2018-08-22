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

/*Table structure for table `metadata_run_config` */

DROP TABLE IF EXISTS `metadata_run_config`;

CREATE TABLE `metadata_run_config` (
  `meta_run_cfg_idx` int(11) NOT NULL AUTO_INCREMENT,
  `run_conf_type` varchar(100) NOT NULL,
  `run_conf_type_desc` varchar(100) DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`meta_run_cfg_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `metadata_run_config` */

insert  into `metadata_run_config`(`meta_run_cfg_idx`,`run_conf_type`,`run_conf_type_desc`,`updatetime`) values (1,'ACS_config','{\"zh_CN\":\"图书馆ACS服务器配置\"}',NULL),(2,'center_config','{\"zh_CN\":\"云中心服务器配置\"}',NULL),(3,'function_config','{\"zh_CN\":\"功能选项配置\"}',NULL),(4,'language_config','{\"zh_CN\":\"界面语言配置\"}',NULL),(5,'localdb_config','{\"zh_CN\":\"本地数据库配置\"}',NULL),(6,'print_config','{\"zh_CN\":\"打印凭据配置\"}',NULL),(7,'register_config','{\"zh_CN\":\"办证类型配置\"}',NULL),(8,'runlog_config','{\"zh_CN\":\"运行日志配置\"}',NULL),(9,'scheduletask_config','{\"zh_CN\":\"定时任务配置\"}',NULL),(10,'timeout_config','{\"zh_CN\":\"超时时间配置\"}',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
