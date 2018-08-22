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

/*Table structure for table `metadata_library_selfcheck_protocol` */

DROP TABLE IF EXISTS `metadata_library_selfcheck_protocol`;

CREATE TABLE `metadata_library_selfcheck_protocol` (
  `protocol_idx` int(11) NOT NULL COMMENT '模板ID号或设备ID号',
  `device_tpl_type` int(11) NOT NULL COMMENT '模板区分 0模板 1数据',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `protocol_type` int(11) NOT NULL COMMENT '指令类型 1SIP2  2NCIP',
  `protocol_show` varchar(50) NOT NULL COMMENT '指令代码  63、64等',
  `protocol_library_idx` int(11) NOT NULL COMMENT '指令字段描述，外关连library_selfcheck_protocol_config',
  `protocol_start` int(11) DEFAULT NULL COMMENT '指令起始位置',
  `protocol_end` int(11) DEFAULT NULL COMMENT '指令结束位置',
  `protocol_code` varchar(50) DEFAULT NULL COMMENT '指令标识符',
  `protocol_defalut` varchar(50) DEFAULT NULL COMMENT '默认值',
  `operator_idx` int(11) NOT NULL COMMENT '操作员ID',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  KEY `protocol_idx` (`protocol_idx`),
  CONSTRAINT `fk_protocal_tpl_idx` FOREIGN KEY (`protocol_idx`) REFERENCES `protocol_config_template` (`protocol_tpl_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `metadata_library_selfcheck_protocol` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
