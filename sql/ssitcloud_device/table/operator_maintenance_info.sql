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

/*Table structure for table `operator_maintenance_info` */

DROP TABLE IF EXISTS `operator_maintenance_info`;

CREATE TABLE `operator_maintenance_info` (
  `operator_idx` int(11) NOT NULL COMMENT '操作员IDX',
  `maintenance_idx` int(11) NOT NULL COMMENT '维护卡IDX',
  KEY `maintance_idx` (`maintenance_idx`),
  CONSTRAINT `maintance_idx` FOREIGN KEY (`maintenance_idx`) REFERENCES `reader_type` (`type_idx`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `operator_maintenance_info` */

insert  into `operator_maintenance_info`(`operator_idx`,`maintenance_idx`) values (220,3),(225,3),(226,3),(224,22),(331,4),(205,22),(221,4),(227,3),(330,47),(58,9517),(47,9517);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
