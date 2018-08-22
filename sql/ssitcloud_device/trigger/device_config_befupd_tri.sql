/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.21-log : Database - ssitcloud_device
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

/* Trigger structure for table `device_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_config_befupd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `device_config_befupd_tri` BEFORE UPDATE ON `device_config` FOR EACH ROW BEGIN
INSERT INTO device_config_old( `device_idx`,  `library_idx`,`device_ext_tpl_idx`,`device_ext_tpl_flg`,`device_monitor_tpl_idx`,`device_monitor_tpl_flg`, `device_run_tpl_idx`,
  `device_run_tpl_flg`,  `device_dbsync_tpl_idx`,  `device_dbsync_tpl_flg`)  SELECT `device_idx`,  `library_idx`,`device_ext_tpl_idx`,`device_ext_tpl_flg`,`device_monitor_tpl_idx`,`device_monitor_tpl_flg`, `device_run_tpl_idx`,
  `device_run_tpl_flg`,  `device_dbsync_tpl_idx`,  `device_dbsync_tpl_flg` FROM device_config WHERE device_idx=old.device_idx;
    END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
