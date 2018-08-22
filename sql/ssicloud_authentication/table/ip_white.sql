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

/*Table structure for table `ip_white` */

DROP TABLE IF EXISTS `ip_white`;

CREATE TABLE `ip_white` (
  `operator_idx` int(11) NOT NULL COMMENT '用户ID',
  `ipaddr` varchar(64) NOT NULL COMMENT '白名单IP',
  PRIMARY KEY (`operator_idx`),
  KEY `ipaddr` (`ipaddr`),
  CONSTRAINT `ip_white_ibfk_1` FOREIGN KEY (`operator_idx`) REFERENCES `operator` (`operator_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='IP白名单，存储允许用户登陆的客户端IP地址';

/*Data for the table `ip_white` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
