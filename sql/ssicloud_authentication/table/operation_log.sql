/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.21-log : Database - ssitcloud_authentication
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ssitcloud_authentication` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ssitcloud_authentication`;

/*Table structure for table `operation_log` */

DROP TABLE IF EXISTS `operation_log`;

CREATE TABLE `operation_log` (
  `operation_log_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `operator_idx` int(11) NOT NULL COMMENT '操作员ID',
  `client_ip` varchar(64) NOT NULL COMMENT '客户端IP',
  `client_port` varchar(10) NOT NULL COMMENT '客户端端口号',
  `operation_cmd` varchar(10) NOT NULL COMMENT '操作类型',
  `operation_result` varchar(10) NOT NULL COMMENT '操作结果',
  `operation` text COMMENT '详细说明',
  `error_code` varchar(10) DEFAULT NULL COMMENT '错误码',
  PRIMARY KEY (`operation_log_idx`),
  KEY `operation_time` (`operation_time`),
  KEY `operation_result` (`operation_result`)
) ENGINE=InnoDB AUTO_INCREMENT=222 DEFAULT CHARSET=utf8;

/*Data for the table `operation_log` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
