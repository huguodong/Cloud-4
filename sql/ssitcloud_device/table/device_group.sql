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

/*Table structure for table `device_group` */

DROP TABLE IF EXISTS `device_group`;

CREATE TABLE `device_group` (
  `device_group_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备组IDX',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `device_group_id` varchar(20) NOT NULL COMMENT '设备组ID',
  `device_group_name` varchar(100) NOT NULL COMMENT '设备组名',
  `device_group_desc` varchar(100) DEFAULT NULL COMMENT '设备组描述',
  PRIMARY KEY (`device_group_idx`),
  UNIQUE KEY `device_group_id` (`library_idx`,`device_group_id`),
  UNIQUE KEY `device_group_name` (`library_idx`,`device_group_name`),
  KEY `library_lID` (`library_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='设备组表，图书馆通过设备组和具体设备关联起来';

/*Data for the table `device_group` */

insert  into `device_group`(`device_group_idx`,`library_idx`,`device_group_id`,`device_group_name`,`device_group_desc`) values (1,1,'10001','借还机','自助借还'),(3,1,'3333','21','工作'),(4,1,'1','自动办卡','办卡'),(5,19,'XIXIANG','西乡超高频测试环境','大幅杀跌'),(6,1,'TS002','设备组测试2','备注测试'),(7,1,'TS003','设备组测试3','备注测试'),(8,1,'TS001','设备测试组',''),(21,24,'5','6',''),(22,3,'SHSBFZ','上海图书馆设备分组',''),(23,5,'SHFG01SBFZ','上海图书馆分馆01设备分组','456156156156156156156156156156154158145151561561'),(24,4,'HUB-FZ','hub分组','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
