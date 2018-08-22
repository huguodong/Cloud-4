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

/*Table structure for table `plc_logic_obj` */

DROP TABLE IF EXISTS `plc_logic_obj`;

CREATE TABLE `plc_logic_obj` (
  `plc_logic_obj_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PLC状态IDX',
  `plc_logic_obj` varchar(50) DEFAULT NULL COMMENT '逻辑部件名',
  `plc_logic_obj_desc` varchar(100) DEFAULT NULL COMMENT '逻辑部件描述',
  PRIMARY KEY (`plc_logic_obj_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `plc_logic_obj` */

insert  into `plc_logic_obj`(`plc_logic_obj_idx`,`plc_logic_obj`,`plc_logic_obj_desc`) values (1,'Connect','PLC连接状态'),(2,'Shelf1','1层书架状态'),(3,'Shelf2','2层书架状态'),(4,'Shelf3','3层书架状态'),(5,'Clutch1','1层离合状态'),(6,'Clutch2','2层离合状态'),(7,'Clutch3','3层离合状态'),(8,'Pushhandle1','1层推书杆状态'),(9,'Pushhandle2 ','2层推书杆状态'),(10,'Pushhandle3','3层推书杆状态'),(11,'Drawer1','特型书盒1状态'),(12,'Drawer2','特型书盒2状'),(13,'Drawer3','特型书盒3状态'),(14,'Drawer4','特型书盒4状态'),(15,'Drawer5','特型书盒5状态'),(16,'Drawer6','特型书盒6状态'),(17,'Pusher1','特型书盒1推书杆状'),(18,'Pusher2','特型书盒2推书杆状态'),(19,'Pusher3','特型书盒3推书杆状态'),(20,'Pusher4','特型书盒4推书杆状态'),(21,'Pusher5','特型书盒5推书杆状态'),(22,'Pusher6','特型书盒6推书杆状态'),(23,'Shelf','书架变频器状态'),(24,'Bin','书箱变频器状态'),(25,'Belt','1号皮带变频器状态'),(26,'Belt2','2号皮带变频器状态'),(27,'Door','还书门状态'),(28,'Alert','开门报警'),(29,'Emergency','急停状态'),(30,'Arm','机械手状态'),(31,'Armjam','机械手卡书状');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
