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

/*Table structure for table `rel_libs` */

DROP TABLE IF EXISTS `rel_libs`;

CREATE TABLE `rel_libs` (
  `master_lib_id` int(11) NOT NULL COMMENT '总馆id',
  `slave_lib_id` int(11) NOT NULL COMMENT '分馆id',
  `rel_type` varchar(20) NOT NULL COMMENT '关系类型，从属关系，并列关系',
  PRIMARY KEY (`master_lib_id`,`slave_lib_id`),
  KEY `slave_lib_id` (`slave_lib_id`),
  CONSTRAINT `rel_libs_ibfk_1` FOREIGN KEY (`master_lib_id`) REFERENCES `library` (`library_idx`),
  CONSTRAINT `rel_libs_ibfk_2` FOREIGN KEY (`slave_lib_id`) REFERENCES `library` (`library_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书馆主从关系表';

/*Data for the table `rel_libs` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
