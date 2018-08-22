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

/*Table structure for table `patch_info` */

DROP TABLE IF EXISTS `patch_info`;

CREATE TABLE `patch_info` (
  `patch_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID号',
  `patch_version` varchar(20) NOT NULL COMMENT '版本号',
  `patch_desc` varchar(200) NOT NULL COMMENT '版本说明',
  `patch_type` varchar(50) DEFAULT NULL COMMENT '版本类型 1全网 2馆约束 3设备类型约束',
  `restrict_info` varchar(100) DEFAULT NULL COMMENT '约束说明',
  `patch_directory` varchar(200) DEFAULT NULL COMMENT '远程下载路径路径',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`patch_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

/*Data for the table `patch_info` */

insert  into `patch_info`(`patch_idx`,`patch_version`,`patch_desc`,`patch_type`,`restrict_info`,`patch_directory`,`create_time`) values (2,'V1.2','测试版本','2','[{\"library_id\": \"1\"},{\"library_id\": \"0002\"}]','ftp://172.16.0.41/V1R1/SD/DBService/','2016-04-11 14:34:24'),(4,'V1.3','测试描述','3','[{\"device_type\": \"1\"},{\"device_type\": \"2\"},{\"device_type\": \"3\"}]','aabb','2016-04-11 14:34:25'),(5,'V1.4','测试版本','1',NULL,'ftp://172.16.0.41/xxx/ssss','2016-04-26 14:30:20'),(6,'V1.5','自助借还机全部升级','3','[{\"device_type\":\"66\"},{\"device_type\":\"1\"}]','','2016-08-25 16:25:24'),(22,'V2.0','测试','3','[{\"device_type\":\"1\"}]','','2016-08-25 16:41:43'),(23,'V2.0','24小时自助借还机','3','[{\"device_type\":\"2\"},{\"device_type\":\"1\"}]','','2016-08-25 16:41:43');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
