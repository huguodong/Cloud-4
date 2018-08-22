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

/*Table structure for table `service_group` */

DROP TABLE IF EXISTS `service_group`;

CREATE TABLE `service_group` (
  `service_group_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '业务组ID',
  `service_group_id` varchar(50) NOT NULL COMMENT 'ID',
  `library_idx` int(11) NOT NULL COMMENT '站点ID',
  `service_group_name` varchar(100) NOT NULL COMMENT '业务组名称',
  `service_group_desc` varchar(100) DEFAULT NULL COMMENT '业务组描述',
  PRIMARY KEY (`service_group_idx`),
  UNIQUE KEY `service_group_id` (`service_group_id`,`library_idx`),
  UNIQUE KEY `service_group_name` (`service_group_name`,`library_idx`),
  KEY `library_lID` (`library_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='业务组，图书馆通过业务组将操作员允许执行的业务操作分类，便于管理';

/*Data for the table `service_group` */

insert  into `service_group`(`service_group_idx`,`service_group_id`,`library_idx`,`service_group_name`,`service_group_desc`) values (2,'0002',1,'设备注册与监控','测试23'),(3,'0003',1,'机器业务','设备分组'),(5,'0005',1,'同步权限','设备的数据同步权限'),(8,'00016',1,'监控权限',''),(9,'00017',1,'系统部管理权限','都市都是'),(10,'0019',1,'硬件维护部权限',''),(11,'0020',1,'注册权限',''),(12,'0021',1,'监控组权限','的萨达'),(18,'G003',1,'测试权限23',''),(24,'007',1,'组名称low到不行',''),(25,'上海图书馆馆员权限',3,'上海图书馆馆员权限','什么权限都没有....'),(26,'008',4,'HUB权限',''),(27,'上海图书馆分馆馆员权限',5,'上海图书馆分馆馆员权限','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
