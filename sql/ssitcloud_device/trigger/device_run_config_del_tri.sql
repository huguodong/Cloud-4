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

/* Trigger structure for table `device_run_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_run_config_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_run_config_del_tri` BEFORE DELETE ON `device_run_config` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.device_run_id AND data_type=old.device_tpl_type AND requestTime IS NULL AND table_name='device_run_config' AND change_state='D')	THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_run_config',old.device_run_id,old.device_tpl_type,'D');
	     END IF;
    END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
