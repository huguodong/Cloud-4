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

/*Table structure for table `device_dbsync_config` */

DROP TABLE IF EXISTS `device_dbsync_config`;

CREATE TABLE `device_dbsync_config` (
  `device_dbsync_id` int(11) NOT NULL COMMENT '模板ID或设备ID',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `device_tpl_type` int(11) NOT NULL COMMENT '模板区分 0模板 1数据',
  `db_name` varchar(50) NOT NULL COMMENT '数据库名',
  `table_name` varchar(50) NOT NULL COMMENT '数据库表名',
  `issync` int(11) DEFAULT NULL COMMENT '是否同步',
  `sync_cycle` varchar(10) DEFAULT NULL COMMENT '同步周期',
  `last_sync_time` timestamp NULL DEFAULT NULL COMMENT '最后同步时间',
  `last_modify_time` timestamp NULL DEFAULT NULL COMMENT '最后修改时间',
  KEY `lib_id` (`library_idx`),
  KEY `device_id` (`library_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备本地数据库的同步配置';

/*Data for the table `device_dbsync_config` */

insert  into `device_dbsync_config`(`device_dbsync_id`,`library_idx`,`device_tpl_type`,`db_name`,`table_name`,`issync`,`sync_cycle`,`last_sync_time`,`last_modify_time`) values (12,0,0,'device_config','device_ext_config',1,'1D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_config','device_run_config',1,'1D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_library','book',1,'1D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_library','patron',1,'1D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_library','reader_type',1,'1D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_library','sip2_config',1,'2D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_operlog','operation_log_1',1,'1D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_operlog','operation_log_3',1,'1D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_operlog','operation_log_4',1,'1D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_operlog','operation_log_5',1,'1D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_operlog','operation_log_6',1,'1D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_operlog','operation_log_7',1,'1D',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_state','bin_state',1,'200S',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_state','bookrack_state',1,'200S',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_state','ext_state',1,'200S',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_state','localoperator',1,'200S',NULL,'2016-07-13 17:00:02'),(12,0,0,'device_state','soft_state',1,'200S',NULL,'2016-07-13 17:00:02'),(15,0,0,'device_library','book',1,'1D',NULL,'2016-08-26 08:54:51'),(16,0,0,'device_config','device_ext_config',1,'1D',NULL,'2016-09-20 15:29:30'),(17,0,0,'device_config','device_ext_config',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_config','device_run_config',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_library','book',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_library','patron',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_library','reader_type',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_library','sip2_config',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_operlog','operation_log_1',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_operlog','operation_log_2',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_state','bin_state',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_state','bookrack_state',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_state','ext_state',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_state','localoperator',1,'1D',NULL,'2016-11-09 16:59:08'),(17,0,0,'device_state','soft_state',1,'1D',NULL,'2016-11-09 16:59:08'),(18,0,0,'device_config','device_ext_config',1,'30S',NULL,'2016-11-20 17:43:39'),(18,0,0,'device_config','device_run_config',1,'30S',NULL,'2016-11-20 17:43:39'),(18,0,0,'device_state','ext_state',1,'30S',NULL,'2016-11-20 17:43:39'),(18,0,0,'device_state','localoperator',1,'30S',NULL,'2016-11-20 17:43:39'),(18,0,0,'device_state','soft_state',1,'30S',NULL,'2016-11-20 17:43:39'),(20,0,0,'device_config','device_ext_config',1,'1D',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_config','device_run_config',1,'1D',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_library','book',1,'1D',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_library','patron',1,'1D',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_library','reader_type',1,'1D',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_library','sip2_config',1,'1D',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_operlog','operation_log_1',1,'1D',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_operlog','operation_log_2',1,'1D',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_operlog','operation_log_3',1,'1D',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_operlog','operation_log_4',1,'1D',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_operlog','operation_log_5',1,'1D',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_operlog','operation_log_6',1,'1S',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_operlog','operation_log_7',1,'1S',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_state','bin_state',1,'1S',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_state','bookrack_state',1,'1S',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_state','ext_state',1,'1S',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_state','localoperator',1,'1S',NULL,'2016-12-05 10:58:20'),(20,0,0,'device_state','soft_state',1,'1S',NULL,'2016-12-05 10:58:20'),(13,0,0,'device_config','device_ext_config',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_config','device_run_config',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_library','book',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_library','patron',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_library','reader_type',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_library','sip2_config',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_operlog','operation_log_1',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_operlog','operation_log_2',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_operlog','operation_log_3',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_operlog','operation_log_4',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_operlog','operation_log_6',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_operlog','operation_log_7',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_state','bin_state',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_state','bookrack_state',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_state','ext_state',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_state','localoperator',1,'30S',NULL,'2016-12-09 09:23:36'),(13,0,0,'device_state','soft_state',1,'30S',NULL,'2016-12-09 09:23:36'),(19,0,0,'device_config','device_ext_config',1,'31S',NULL,'2016-12-09 10:42:10'),(19,0,0,'device_config','device_run_config',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_library','book',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_library','patron',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_library','reader_type',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_library','sip2_config',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_operlog','operation_log_1',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_operlog','operation_log_2',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_operlog','operation_log_3',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_operlog','operation_log_4',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_operlog','operation_log_5',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_operlog','operation_log_6',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_operlog','operation_log_7',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_state','bin_state',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_state','bookrack_state',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_state','ext_state',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_state','localoperator',1,'30S',NULL,'2016-12-09 10:42:11'),(19,0,0,'device_state','soft_state',1,'30S',NULL,'2016-12-09 10:42:11');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
