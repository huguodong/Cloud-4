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

/*Table structure for table `metadata_devicetype` */

DROP TABLE IF EXISTS `metadata_devicetype`;

CREATE TABLE `metadata_devicetype` (
  `meta_devicetype_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_type` varchar(100) DEFAULT NULL COMMENT '设备类型',
  `device_type_desc` varchar(100) DEFAULT NULL COMMENT '设备类型描述',
  `device_type_mark` varchar(200) DEFAULT NULL COMMENT '设备类型备注',
  `device_logic_list` varchar(200) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`meta_devicetype_idx`),
  UNIQUE KEY `device_type` (`device_type`),
  UNIQUE KEY `device_type_desc` (`device_type_desc`),
  KEY `dmID` (`meta_devicetype_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COMMENT='设备类型描述元数据表，描述设备的类型信息，如自助借还机，SSL';

/*Data for the table `metadata_devicetype` */

insert  into `metadata_devicetype`(`meta_devicetype_idx`,`device_type`,`device_type_desc`,`device_type_mark`,`device_logic_list`,`createTime`) values (1,'SSL','24小时自助图书馆','24小时自助图书馆','AuthorityBarcode,AuthorityReader,CardDispenser,CashAcceptor,IdentityReader,ItemBarcode,ItemLoadReader,ItemRfidReader,PlcSorter,PlcSSL,Printer,RegisterReader','2016-04-18 11:30:17'),(2,'SCH','借还书机','自助借还机','AuthorityBarcode,AuthorityReader,IdentityReader,ItemBarcode,ItemRfidReader,Printer','2016-04-18 11:30:24'),(3,'REG','办证机','自助办证机','AuthorityBarcode,AuthorityReader,CardDispenser,CashAcceptor,IdentityReader,Printer,RegisterReader','2016-04-19 11:45:30'),(4,'BDR','还书机','自助还书机','AuthorityBarcode,AuthorityReader,IdentityReader,ItemBarcode,ItemRfidReader,PlcReturn,Printer','2016-04-19 11:45:32'),(57,'STA','馆员工作站','馆员工作站-借还、办证功能','AuthorityBarcode,AuthorityReader,IdentityReader,ItemBarcode,ItemRfidReader,Printer','2016-04-20 17:38:59'),(58,'STA-C','点检车','馆员工作站-点检车功能','AuthorityBarcode,AuthorityReader,ItemBarcode,ItemRfidReader','2016-04-20 17:40:28'),(59,'POR','安全门','安全门，读取读者证的EAS位信息','AuthorityReader','2016-04-22 20:12:10'),(60,'STA-V','标签加工','馆员工作站-标签加工功能','AuthorityReader,IdentityReader,ItemBarcode,ItemRfidReader','2016-04-22 20:12:10'),(63,'STA-T1U','超高频馆员工作站（一体式）','','AuthorityBarcode,AuthorityReader,IdentityReader,ItemBarcode,ItemRfidReader,Printer','2016-11-20 17:39:16');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
