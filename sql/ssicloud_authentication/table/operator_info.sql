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

/*Table structure for table `operator_info` */

DROP TABLE IF EXISTS `operator_info`;

CREATE TABLE `operator_info` (
  `operator_idx` int(11) NOT NULL,
  `infotype_idx` int(11) NOT NULL,
  `info_value` varchar(100) NOT NULL,
  KEY `m_info_type_mitID` (`infotype_idx`),
  KEY `operator_idx` (`operator_idx`),
  CONSTRAINT `operator_info_ibfk_4` FOREIGN KEY (`infotype_idx`) REFERENCES `metadata_infotype` (`infotype_idx`),
  CONSTRAINT `operator_info_ibfk_5` FOREIGN KEY (`operator_idx`) REFERENCES `operator` (`operator_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表，存储用户的身份信息，添加、修改、删除、查询用户需要操作这个表';

/*Data for the table `operator_info` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
