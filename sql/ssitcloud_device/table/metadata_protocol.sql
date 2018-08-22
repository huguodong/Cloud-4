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

/*Table structure for table `metadata_protocol` */

DROP TABLE IF EXISTS `metadata_protocol`;

CREATE TABLE `metadata_protocol` (
  `protocol_field_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `protocol_show` varchar(50) NOT NULL COMMENT '指令代码',
  `protocol_field_name` varchar(50) NOT NULL COMMENT '指令名',
  `protocol_field_desc` varchar(200) NOT NULL COMMENT '指令说明',
  `protocol_field_sort` int(3) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`protocol_field_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `metadata_protocol` */

insert  into `metadata_protocol`(`protocol_field_idx`,`protocol_show`,`protocol_field_name`,`protocol_field_desc`,`protocol_field_sort`) values (1,'chargedeposit','交押金','',25),(2,'chargeimprest','交预存款','',26),(3,'checkin','还书','',10),(4,'checkout','借书','',11),(5,'checkpatronunique','查重','',12),(6,'createpatron','办证','',13),(7,'deductfinefromimprest','扣缴','',14),(8,'enablepatron','激活读者','',15),(9,'endpatronsession','结束当前读者会话','',16),(10,'sc_login','系统登录','',8),(11,'sc_logout','系统退出','',9),(12,'querybookinfo','查询图书','',17),(13,'queryfinanceaccount','查询财经','',18),(14,'querypatroninfo','查询读者信息','',7),(15,'querypatroninfo_ex','扩展查询读者信息','可以用身份证查询',19),(16,'renew','续借','',4),(17,'renewall','续借全部','不是所有的系统都支持，根据实际情况使用',5),(18,'sc_status','系统状态','',6),(19,'updatebooklocstatus','更新图书馆藏状态','',20),(20,'updatepatronpassword','更新读者密码','',21),(21,'updatepatronstatus','更新读者状态','',22),(22,'connect','连接','',1),(23,'disconnect','断开','',2),(24,'patronstatus','查询读者状态','',3),(25,'updatephoto','更新读者相片','',23),(26,'checkpatronvalid','检查读者有效','',24);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
