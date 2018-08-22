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

/*Table structure for table `device_selfcheck_protocol_config` */

DROP TABLE IF EXISTS `device_selfcheck_protocol_config`;

CREATE TABLE `device_selfcheck_protocol_config` (
  `protocol_idx` int(11) NOT NULL COMMENT '模板ID号',
  `device_tpl_type` int(11) NOT NULL COMMENT '模板区分 0模板',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `protocol_type` int(11) NOT NULL COMMENT '指令类型 1SIP2  2NCIP',
  `protocol_library_idx` int(11) NOT NULL COMMENT '指令字段描述，外关连metadata_protocol',
  `protocol_reqrule` varchar(500) DEFAULT NULL COMMENT '请求规则',
  `protocol_resprule` varchar(500) DEFAULT NULL COMMENT '响应规则',
  `protocol_description` varchar(1000) DEFAULT NULL COMMENT '描述',
  `operator_idx` int(11) NOT NULL COMMENT '操作员ID',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  KEY `protocol_idx` (`protocol_idx`),
  CONSTRAINT `fk_protocol_idx` FOREIGN KEY (`protocol_idx`) REFERENCES `protocol_config_template` (`protocol_tpl_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_selfcheck_protocol_config` */

insert  into `device_selfcheck_protocol_config`(`protocol_idx`,`device_tpl_type`,`library_idx`,`protocol_type`,`protocol_library_idx`,`protocol_reqrule`,`protocol_resprule`,`protocol_description`,`operator_idx`,`createtime`,`updatetime`) values (43,0,3,1,22,'1','1','1',1,'2016-11-22 15:27:07','2016-11-22 15:27:07'),(45,0,5,1,22,'1','1','1',1,'2016-11-23 09:21:39','2016-11-23 09:21:39'),(44,0,4,1,22,'1','1','1',1,'2016-12-07 11:15:34','2016-12-07 11:15:34'),(42,0,1,1,22,'1','1','1',1,'2016-12-08 13:32:11','2016-12-08 13:32:11');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
