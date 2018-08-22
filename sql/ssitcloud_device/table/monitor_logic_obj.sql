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

/*Table structure for table `monitor_logic_obj` */

DROP TABLE IF EXISTS `monitor_logic_obj`;

CREATE TABLE `monitor_logic_obj` (
  `monitor_logic_obj_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '逻辑部件 IDX',
  `monitor_logic_obj` varchar(50) DEFAULT NULL COMMENT '逻辑部件ID',
  `monitor_logic_obj_desc` varchar(100) DEFAULT NULL COMMENT '逻辑部件描述',
  `monitor_logic_obj_main` int(11) DEFAULT NULL COMMENT '逻辑部件主ID',
  PRIMARY KEY (`monitor_logic_obj_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `monitor_logic_obj` */

insert  into `monitor_logic_obj`(`monitor_logic_obj_idx`,`monitor_logic_obj`,`monitor_logic_obj_desc`,`monitor_logic_obj_main`) values (1,'StartupTime','系统启动时间',NULL),(2,'UpdateTime','系统最后更新时间',NULL),(3,'Logistics_starttime','物流到达时间',NULL),(4,'Logistics_endtime','物流离开时间',NULL),(5,'bin_state','卡钱箱同步消息',NULL),(6,'cardbin','卡箱状态',5),(7,'amount','卡数量',6),(8,'state','状态',6),(9,'cashbin','钱箱状态',5),(10,'subtype','纸币面值',9),(11,'amount','纸制数量',9),(12,'state','状态',9),(13,'bookbin','书箱状态',5),(14,'binno','书箱号',13),(15,'subtype','书箱类型',13),(16,'desc','书箱类型描述',13),(17,'amount','书本数量',13),(18,'state','状态',13),(19,'bookrack_state','书架数据',NULL),(20,'Level1','1层书盒',19),(21,'Level2','2层书盒',19),(22,'Level3','3层书盒',19),(23,'Exbox','特型书盒',19),(24,'Precheckout','预借书',19);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
