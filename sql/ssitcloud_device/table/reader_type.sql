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

/*Table structure for table `reader_type` */

DROP TABLE IF EXISTS `reader_type`;

CREATE TABLE `reader_type` (
  `type_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '流通类型IDX',
  `library_idx` int(11) NOT NULL COMMENT '馆IDX',
  `type_distinction` varchar(2) NOT NULL COMMENT '类型区分 1读者流通类型  2设备维护卡',
  `type_id` varchar(50) NOT NULL COMMENT '类型代码',
  `type_name` varchar(50) NOT NULL COMMENT '类型名',
  `type_deposit` int(11) DEFAULT '0' COMMENT '押金',
  `card_fee` int(11) DEFAULT '0' COMMENT '工本费',
  `verification_fee` int(11) DEFAULT '0' COMMENT '验证费',
  PRIMARY KEY (`type_idx`),
  UNIQUE KEY `type_id` (`library_idx`,`type_id`),
  UNIQUE KEY `type_name` (`library_idx`,`type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=9524 DEFAULT CHARSET=utf8;

/*Data for the table `reader_type` */

insert  into `reader_type`(`type_idx`,`library_idx`,`type_distinction`,`type_id`,`type_name`,`type_deposit`,`card_fee`,`verification_fee`) values (3,1,'2','0440090250222','维护卡2',0,0,0),(4,1,'2','0440090250221','维护卡1',0,0,0),(5,1,'2','0440090250223','维护卡3',0,0,0),(17,19,'1','CARD_2','100无读者证',100,0,0),(22,9,'2','12121','的撒旦',0,0,0),(36,52,'1','1011','社区馆集体证',88,0,0),(47,1,'2','W04212352','自助设备维护卡1',0,0,0),(9500,1,'1','STC001','100元普通证借10本',100,0,0),(9501,1,'1','STC002','imyaasd ',0,0,0),(9502,1,'1','STC003','测试',1000,0,0),(9503,2,'2','04400905246321','24小时自助设备维护卡1',0,0,0),(9512,19,'1','9a9a','9',9,9,9),(9514,9,'2','3111231231','1231213132',0,0,0),(9515,9,'2','asdfasdf','asdasdfdsdddd',0,0,0),(9517,3,'2','2016112100199','上海图书馆馆员',0,0,0),(9518,3,'1','SH','上海图书馆读者流通类型',1000,5,0),(9520,5,'2','201600001222001','上海图书馆分馆维护卡',0,0,0),(9521,4,'1','STC004','HUB-STC1',10000,10,10);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
