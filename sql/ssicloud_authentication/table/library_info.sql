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

/*Table structure for table `library_info` */

DROP TABLE IF EXISTS `library_info`;

CREATE TABLE `library_info` (
  `library_idx` int(11) NOT NULL COMMENT '图书馆id',
  `infotype_idx` int(11) NOT NULL COMMENT '信息类型',
  `info_value` varchar(100) DEFAULT NULL COMMENT '信息值',
  KEY `library_lID` (`library_idx`),
  KEY `m_info_type_mitID` (`infotype_idx`),
  CONSTRAINT `library_info_ibfk_3` FOREIGN KEY (`library_idx`) REFERENCES `library` (`library_idx`),
  CONSTRAINT `library_info_ibfk_4` FOREIGN KEY (`infotype_idx`) REFERENCES `metadata_infotype` (`infotype_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书馆信息表';

/*Data for the table `library_info` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
