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

/*Table structure for table `device_ext_template` */

DROP TABLE IF EXISTS `device_ext_template`;

CREATE TABLE `device_ext_template` (
  `device_tpl_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_tpl_ID` varchar(10) DEFAULT NULL COMMENT '模板ID',
  `device_tpl_desc` varchar(200) DEFAULT NULL COMMENT '模板描述',
  `device_type` int(11) DEFAULT NULL COMMENT '设备类型IDX',
  PRIMARY KEY (`device_tpl_idx`),
  UNIQUE KEY `device_tpl_desc` (`device_tpl_desc`),
  UNIQUE KEY `device_tpl_ID` (`device_tpl_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

/*Data for the table `device_ext_template` */

insert  into `device_ext_template`(`device_tpl_idx`,`device_tpl_ID`,`device_tpl_desc`,`device_type`) values (12,'STATPL001','馆员工作站硬件配置模板001',57),(13,'SCHTPL001','自助借还机硬件模板001',2),(15,'SCH001','借还模板',2),(18,'12315615','李泽鹏硬件',62),(19,'46489','测试被引用',64),(20,'5648548478','测试被引用11',65),(21,'11111','11111',67),(22,'541','修改完成没',62),(23,'REG','REG',3),(24,'STA-T1U','超高频馆员工作站硬件模板',63),(25,'SHYJ001','sh硬件模板001',2),(26,'HUB-YJ','hub模板',63);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
