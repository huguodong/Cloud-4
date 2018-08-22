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

/*Table structure for table `device_ext_config` */

DROP TABLE IF EXISTS `device_ext_config`;

CREATE TABLE `device_ext_config` (
  `library_idx` int(11) NOT NULL,
  `device_tpl_type` int(11) NOT NULL COMMENT '模板区分 0模板 1数据',
  `device_ext_id` int(11) NOT NULL COMMENT '设备idx或者模板idx',
  `ext_comm_conf` varchar(100) DEFAULT NULL COMMENT '外设通信方式',
  `ext_extend_conf` text COMMENT '外设其它信息',
  `logic_obj_idx` int(11) NOT NULL COMMENT '外设类型',
  `ext_model_idx` int(11) NOT NULL COMMENT '外设型号',
  `updatetime` timestamp NULL DEFAULT NULL,
  KEY `m_logic_obj_mloID` (`logic_obj_idx`),
  KEY `m_ext_model_memID` (`ext_model_idx`),
  KEY `device_id` (`library_idx`),
  KEY `ext_id` (`ext_model_idx`),
  CONSTRAINT `ext_id` FOREIGN KEY (`ext_model_idx`) REFERENCES `metadata_ext_model` (`meta_ext_idx`),
  CONSTRAINT `logic_obj` FOREIGN KEY (`logic_obj_idx`) REFERENCES `metadata_logic_obj` (`meta_log_obj_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备外设硬件的配置';

/*Data for the table `device_ext_config` */

insert  into `device_ext_config`(`library_idx`,`device_tpl_type`,`device_ext_id`,`ext_comm_conf`,`ext_extend_conf`,`logic_obj_idx`,`ext_model_idx`,`updatetime`) values (0,0,13,'{\"COM\":\"5\",\"TYPE\":\"com\",\"BAUD\":\"12280\"}','',2,12,'2016-07-27 11:27:44'),(0,0,13,'{\"COM\":\"3\",\"TYPE\":\"com\",\"BAUD\":\"56010\"}','',5,1,'2016-07-27 11:27:44'),(0,0,13,'{\"COM\":\"5\",\"TYPE\":\"com\",\"BAUD\":\"12800\"}','',8,9,'2016-07-27 11:27:44'),(0,0,13,'{\"COM\":\"6\",\"TYPE\":\"com\",\"BAUD\":\"20145\"}','',12,2,'2016-07-27 11:27:44'),(0,0,15,'{\"TYPE\":\"usb\",\"USB\":\"1\"}','',1,10,'2016-08-26 08:40:31'),(0,0,15,'{\"COM\":\"2\",\"TYPE\":\"com\",\"BAUD\":\"9200\"}','',8,5,'2016-08-26 08:40:31'),(0,0,15,'{\"TYPE\":\"lpt\"}','',12,2,'2016-08-26 08:40:31'),(0,0,18,'{}','',1,10,'2016-09-20 11:40:56'),(0,0,18,'{}','',2,9,'2016-09-20 11:40:56'),(0,0,18,'{\"TYPE\":\"lpt\"}','',3,4,'2016-09-20 11:40:56'),(0,0,18,'{\"TYPE\":\"lpt\"}','',5,1,'2016-09-20 11:40:56'),(0,0,19,'{}','',2,5,'2016-09-20 15:59:51'),(0,0,19,'{}','',6,11,'2016-09-20 15:59:51'),(0,0,19,'{}','',10,7,'2016-09-20 15:59:51'),(0,0,20,'{}','',2,5,'2016-09-20 16:09:15'),(0,0,20,'{}','',6,10,'2016-09-20 16:09:15'),(0,0,20,'{}','',10,7,'2016-09-20 16:09:15'),(0,0,21,'{}','',2,5,'2016-09-20 16:27:33'),(0,0,21,'{}','',6,10,'2016-09-20 16:27:33'),(0,0,21,'{}','',10,7,'2016-09-20 16:27:33'),(0,0,22,'{}','',1,11,'2016-09-21 09:53:08'),(0,0,23,'{\"COM\":\"5\",\"TYPE\":\"com\",\"BAUD\":\"4\"}','',3,4,'2016-11-09 16:53:44'),(0,0,23,'{\"COM\":\"5\",\"TYPE\":\"com\",\"BAUD\":\"9600\"}','{\"CURRENCY\":\"CNY\",\"CHANNEL\":\"1,5,10,20,50,100\"}',4,3,'2016-11-09 16:53:44'),(0,0,23,'{\"COM\":\"7\",\"TYPE\":\"com\",\"BAUD\":\"9600\"}','',5,1,'2016-11-09 16:53:44'),(1,1,162,NULL,'{COM=5, TYPE=com, BAUD=4}',3,4,'2016-11-14 16:43:38'),(1,1,162,NULL,'{CURRENCY=CNY, CHANNEL=1,5,10,20,50,100}',4,3,'2016-11-14 16:43:38'),(1,1,162,NULL,'{COM=7, TYPE=com, BAUD=9600}',5,1,'2016-11-14 16:43:38'),(1,1,162,NULL,NULL,13,5,'2016-11-14 16:43:38'),(0,0,24,'{\"COM\":\"111\",\"TYPE\":\"com\",\"BAUD\":\"111\"}','',2,12,'2016-11-20 17:42:09'),(0,0,24,'{\"COM\":\"111\",\"TYPE\":\"com\",\"BAUD\":\"111\"}','',5,1,'2016-11-20 17:42:09'),(0,0,24,'{\"COM\":\"111\",\"TYPE\":\"com\",\"BAUD\":\"111\"}','',8,12,'2016-11-20 17:42:09'),(0,0,26,'{\"COM\":\"5\",\"TYPE\":\"com\",\"BAUD\":\"57600\"}','',1,10,'2016-12-05 11:15:04'),(0,0,26,'{\"COM\":\"5\",\"TYPE\":\"com\",\"BAUD\":\"57600\"}','',2,12,'2016-12-05 11:15:04'),(0,0,26,'{\"COM\":\"5\",\"TYPE\":\"com\",\"BAUD\":\"57600\"}','',5,1,'2016-12-05 11:15:04'),(0,0,26,'{\"COM\":\"5\",\"TYPE\":\"com\",\"BAUD\":\"57600\"}','',6,10,'2016-12-05 11:15:04'),(0,0,26,'{\"COM\":\"5\",\"TYPE\":\"com\",\"BAUD\":\"57600\"}','',8,9,'2016-12-05 11:15:04'),(0,0,26,'{\"TYPE\":\"lpt\"}','',12,2,'2016-12-05 11:15:04'),(0,0,25,'{\"COM\":\"7\",\"TYPE\":\"com\",\"BAUD\":\"9600\"}','',2,5,'2016-12-08 13:55:28'),(0,0,25,'{\"COM\":\"6\",\"TYPE\":\"com\",\"BAUD\":\"5600\"}','',8,12,'2016-12-08 13:55:28'),(0,0,25,'{\"COM\":\"13\",\"TYPE\":\"com\",\"BAUD\":\"9600\"}','',12,2,'2016-12-08 13:55:28'),(0,0,12,'{\"COM\":\"3\",\"TYPE\":\"com\",\"BAUD\":\"9600\"}','',2,9,'2016-12-10 11:41:04'),(0,0,12,'{\"COM\":\"50\",\"TYPE\":\"com\",\"BAUD\":\"11200\"}','',8,5,'2016-12-10 11:41:04'),(0,0,12,'{\"TYPE\":\"usb\",\"USB\":\"\"}','',12,2,'2016-12-10 11:41:04');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
