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

/*Table structure for table `metadata_ext_model` */

DROP TABLE IF EXISTS `metadata_ext_model`;

CREATE TABLE `metadata_ext_model` (
  `meta_ext_idx` int(11) NOT NULL AUTO_INCREMENT,
  `ext_model` varchar(100) NOT NULL COMMENT '外设型号',
  `ext_model_desc` varchar(100) NOT NULL COMMENT '外设型号描述',
  `ext_model_version` varchar(100) DEFAULT NULL COMMENT '外设版本',
  `ext_type` varchar(100) DEFAULT NULL COMMENT '外设类型',
  `ext_model_driver_path` varchar(100) NOT NULL,
  PRIMARY KEY (`meta_ext_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='元数据表，外设型号，TAGSYS，NV200等';

/*Data for the table `metadata_ext_model` */

insert  into `metadata_ext_model`(`meta_ext_idx`,`ext_model`,`ext_model_desc`,`ext_model_version`,`ext_type`,`ext_model_driver_path`) values (1,'CRV100D','{\"zh_CN\":\"华视身份证阅读器\"}','V3','idreader',''),(2,'YK','{\"zh_CN\":\"研科打印机\"}','-','printer',''),(3,'CZC100RMB','{\"zh_CN\":\"创自收钞机\"}','-','cashacceptor',''),(4,'TYCD1500','{\"zh_CN\":\"天毅发卡机\"}','-','carddispenser',''),(5,'BWUHF','{\"zh_CN\":\"博纬UHF阅读器\"}','-','uhfreader',''),(6,'PLCSSL','{\"zh_CN\":\"自助图书馆PLC\"}','-','plcssl',''),(7,'PLCSORTER','{\"zh_CN\":\"自助分拣机PLC\"}','-','plcsorter',''),(8,'PLCRETURN','{\"zh_CN\":\"自助还书机PLC\"}','-','plcreturn',''),(9,'RR3036','{\"zh_CN\":\"荣瑞高频阅读器\"}','-','hfreader',''),(10,'FS16','{\"zh_CN\":\"条码枪FS16\"}','-','barcode',''),(11,'BCL','{\"zh_CN\":\"条码枪BCL8\"}','-','barcode',''),(12,'RR288M','{\"zh_CN\":\"荣瑞超高频阅读器\"}','-','uhfreader','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
