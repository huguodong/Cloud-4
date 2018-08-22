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

/*Table structure for table `password_history` */

DROP TABLE IF EXISTS `password_history`;

CREATE TABLE `password_history` (
  `password_idx` int(11) NOT NULL AUTO_INCREMENT,
  `operator_idx` int(11) NOT NULL COMMENT '操作员表ID',
  `password` varchar(100) NOT NULL COMMENT '历史密码',
  `modifyTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  PRIMARY KEY (`password_idx`),
  KEY `operator_oID` (`operator_idx`),
  CONSTRAINT `password_history_ibfk_1` FOREIGN KEY (`operator_idx`) REFERENCES `operator` (`operator_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户历史密码表，存储用户已经使用过的密码信息，用户修改密码需要查询的和修改这个表，以保证密码不重复';

/*Data for the table `password_history` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
