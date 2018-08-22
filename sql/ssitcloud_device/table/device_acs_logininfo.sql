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

/*Table structure for table `device_acs_logininfo` */

DROP TABLE IF EXISTS `device_acs_logininfo`;

CREATE TABLE `device_acs_logininfo` (
  `logininfo_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID号',
  `protocol_tpl_idx` int(11) NOT NULL COMMENT 'ACS模板号',
  `device_idx` int(11) NOT NULL COMMENT '设备IDX',
  `library_idx` int(11) NOT NULL COMMENT '图书馆IDX',
  `login_ip` varchar(20) NOT NULL COMMENT '登录IP',
  `login_port` int(11) NOT NULL COMMENT '登录端口号',
  `login_username` varchar(50) DEFAULT NULL COMMENT '登录用户名',
  `login_pwd` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `acs_service_name` varchar(50) NOT NULL COMMENT 'ACS后台服务名',
  PRIMARY KEY (`logininfo_idx`),
  KEY `protocol_tpl_idx` (`protocol_tpl_idx`),
  CONSTRAINT `protocol_tpl_idx` FOREIGN KEY (`protocol_tpl_idx`) REFERENCES `protocol_config_template` (`protocol_tpl_idx`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_acs_logininfo` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
