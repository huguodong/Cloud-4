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

/*Table structure for table `rel_operator_service_group` */

DROP TABLE IF EXISTS `rel_operator_service_group`;

CREATE TABLE `rel_operator_service_group` (
  `rel_oper_serv_idx` int(11) NOT NULL AUTO_INCREMENT,
  `operator_group_idx` int(11) NOT NULL COMMENT '操作员组ID',
  `service_group_idx` int(11) NOT NULL COMMENT '业务组ID',
  `library_idx` int(11) NOT NULL,
  PRIMARY KEY (`rel_oper_serv_idx`),
  KEY `operator_group_id` (`operator_group_idx`,`service_group_idx`),
  KEY `operator_group_id_2` (`operator_group_idx`),
  KEY `service_group_id` (`service_group_idx`),
  KEY `library_lID` (`library_idx`),
  CONSTRAINT `rel_operator_service_group_ibfk_3` FOREIGN KEY (`service_group_idx`) REFERENCES `service_group` (`service_group_idx`),
  CONSTRAINT `rel_operator_service_group_ibfk_4` FOREIGN KEY (`operator_group_idx`) REFERENCES `operator_group` (`operator_group_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8 COMMENT='操作员组和业务组关联表，操作员组和业务组之间是多对多的关系';

/*Data for the table `rel_operator_service_group` */

insert  into `rel_operator_service_group`(`rel_oper_serv_idx`,`operator_group_idx`,`service_group_idx`,`library_idx`) values (51,1,5,1),(76,2,2,1),(78,26,3,1),(81,138,2,1),(88,25,3,1),(101,112,2,1),(126,148,25,3),(130,151,3,7),(143,23,2,9),(148,150,25,5),(165,149,25,4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
