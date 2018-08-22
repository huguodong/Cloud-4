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

/*Table structure for table `metadata_logic_obj` */

DROP TABLE IF EXISTS `metadata_logic_obj`;

CREATE TABLE `metadata_logic_obj` (
  `meta_log_obj_idx` int(11) NOT NULL AUTO_INCREMENT,
  `logic_obj` varchar(100) NOT NULL COMMENT '逻辑部件ID',
  `logic_obj_desc` varchar(100) NOT NULL COMMENT '逻辑部件描述',
  PRIMARY KEY (`meta_log_obj_idx`),
  KEY `mloID` (`meta_log_obj_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='元数据表，逻辑对象，图书rfid阅读器、图书条码枪、PLC';

/*Data for the table `metadata_logic_obj` */

insert  into `metadata_logic_obj`(`meta_log_obj_idx`,`logic_obj`,`logic_obj_desc`) values (1,'AuthorityBarcode','{\"zh_CN\":\"读者证条码枪\"}'),(2,'AuthorityReader','{\"zh_CN\":\"读者证阅读器\"}'),(3,'CardDispenser','{\"zh_CN\":\"发卡机\"}'),(4,'CashAcceptor','{\"zh_CN\":\"收钞机\"}'),(5,'IdentityReader','{\"zh_CN\":\"身份证阅读器\"}'),(6,'ItemBarcode','{\"zh_CN\":\"图书条码枪\"}'),(7,'ItemLoadReader','{\"zh_CN\":\"补书阅读器\"}'),(8,'ItemRfidReader','{\"zh_CN\":\"图书阅读器\"}'),(9,'PlcReturn','{\"zh_CN\":\"还书机PLC\"}'),(10,'PlcSorter','{\"zh_CN\":\"分拣机PLC\"}'),(11,'PlcSSL','{\"zh_CN\":\"自助图书馆PLC\"}'),(12,'Printer','{\"zh_CN\":\"打印机\"}'),(13,'RegisterReader','{\"zh_CN\":\"发卡阅读器\"}');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
