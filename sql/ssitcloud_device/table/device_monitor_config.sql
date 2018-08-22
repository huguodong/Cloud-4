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

/*Table structure for table `device_monitor_config` */

DROP TABLE IF EXISTS `device_monitor_config`;

CREATE TABLE `device_monitor_config` (
  `device_ext_type` int(11) NOT NULL COMMENT '模板状态标识 0模板 1数据',
  `device_mon_tpl_idx` int(11) NOT NULL COMMENT '显示模板ID或设备ID',
  `logic_obj_idx` int(11) NOT NULL COMMENT '逻辑对象ID',
  `table_name` varchar(50) NOT NULL COMMENT '表名',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `visiable` int(11) NOT NULL COMMENT '是否可见',
  `alert` int(11) NOT NULL COMMENT '是否告警',
  `color` int(11) NOT NULL COMMENT '显示颜色',
  `threshold` int(11) NOT NULL COMMENT '阈值',
  `meta_log_obj_idx` int(11) DEFAULT NULL COMMENT 'metadata_logic_obj主键',
  KEY `service_group_id` (`device_mon_tpl_idx`),
  KEY `m_logic_obj_mloID` (`logic_obj_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备监控的配置';

/*Data for the table `device_monitor_config` */

insert  into `device_monitor_config`(`device_ext_type`,`device_mon_tpl_idx`,`logic_obj_idx`,`table_name`,`library_idx`,`visiable`,`alert`,`color`,`threshold`,`meta_log_obj_idx`) values (0,33,0,'time_out',0,1,1,0,50,0),(0,39,2,'metadata_logic_obj',0,1,1,0,0,2),(0,39,8,'metadata_logic_obj',0,1,1,1,0,8),(0,39,0,'time_out',0,1,1,0,5000,0),(0,40,1,'metadata_logic_obj',0,1,1,2,0,1),(0,40,2,'metadata_logic_obj',0,1,1,2,0,2),(0,40,5,'metadata_logic_obj',0,1,1,2,0,5),(0,40,0,'time_out',0,1,1,0,50,0),(0,44,1,'metadata_logic_obj',0,1,1,0,0,1),(0,44,2,'metadata_logic_obj',0,1,1,0,0,2),(0,44,7,'monitor_logic_obj',0,1,1,1,10000000,3),(0,44,11,'monitor_logic_obj',0,1,1,1,10000,4),(0,44,5,'metadata_logic_obj',0,1,1,2,0,5),(0,44,6,'metadata_logic_obj',0,1,1,2,0,6),(0,44,7,'metadata_logic_obj',0,1,1,3,0,7),(0,44,8,'metadata_logic_obj',0,1,1,3,0,8),(0,44,1,'plc_logic_obj',0,1,1,3,0,9),(0,44,24,'plc_logic_obj',0,1,1,1,0,9),(0,44,25,'plc_logic_obj',0,1,1,2,0,9),(0,44,26,'plc_logic_obj',0,1,1,3,0,9),(0,44,28,'plc_logic_obj',0,1,1,3,0,9),(0,44,24,'plc_logic_obj',0,1,1,2,0,10),(0,44,0,'time_out',0,1,1,0,10000,0),(0,55,13,'metadata_logic_obj',0,1,1,1,0,13),(0,55,0,'time_out',0,1,1,0,50,0),(0,56,2,'metadata_logic_obj',0,1,1,2,0,2),(0,56,5,'metadata_logic_obj',0,1,1,2,0,5),(0,56,8,'metadata_logic_obj',0,1,1,3,0,8),(0,56,0,'time_out',0,1,1,0,120,0),(0,57,1,'metadata_logic_obj',0,1,1,0,0,1),(0,57,2,'metadata_logic_obj',0,1,1,1,0,2),(0,57,5,'metadata_logic_obj',0,1,1,2,0,5),(0,57,6,'metadata_logic_obj',0,1,1,1,0,6),(0,57,8,'metadata_logic_obj',0,1,1,2,0,8),(0,57,12,'metadata_logic_obj',0,1,1,4,0,12),(0,57,0,'time_out',0,1,1,0,1000,0),(0,28,1,'metadata_logic_obj',0,1,0,0,0,1),(0,28,2,'metadata_logic_obj',0,1,0,4,0,2),(0,28,5,'metadata_logic_obj',0,1,1,4,0,5),(0,28,6,'metadata_logic_obj',0,1,1,0,0,6),(0,28,8,'metadata_logic_obj',0,1,1,2,0,8),(0,28,12,'metadata_logic_obj',0,1,1,4,0,12),(0,28,0,'time_out',0,1,1,0,5000,0),(0,29,2,'metadata_logic_obj',0,1,0,2,0,2),(0,29,8,'metadata_logic_obj',0,1,1,2,0,8),(0,29,12,'metadata_logic_obj',0,1,1,2,0,12),(0,29,0,'time_out',0,1,1,0,500,0),(0,60,1,'metadata_logic_obj',0,1,1,1,0,1),(0,60,2,'metadata_logic_obj',0,1,1,1,0,2),(0,60,5,'metadata_logic_obj',0,1,1,1,0,5),(0,60,6,'metadata_logic_obj',0,1,1,1,0,6),(0,60,8,'metadata_logic_obj',0,1,1,1,0,8),(0,60,12,'metadata_logic_obj',0,1,1,1,0,12),(0,60,0,'time_out',0,1,1,0,100,0),(0,58,1,'metadata_logic_obj',0,1,1,0,0,1),(0,58,2,'metadata_logic_obj',0,1,1,0,0,2),(0,58,5,'metadata_logic_obj',0,1,1,0,0,5),(0,58,6,'metadata_logic_obj',0,1,0,1,0,6),(0,58,8,'metadata_logic_obj',0,1,1,0,0,8),(0,58,12,'metadata_logic_obj',0,1,1,1,0,12),(0,58,0,'time_out',0,1,1,0,0,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
