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

/* Trigger structure for table `operator_maintenance_info` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `operator_maintenance_info_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `operator_maintenance_info_del_tri` BEFORE DELETE ON `operator_maintenance_info` FOR EACH ROW BEGIN
	SET @lib_idx=(SELECT library_idx FROM reader_type WHERE type_distinction='2' AND type_idx=old.maintenance_idx LIMIT 1);
	IF @lib_idx!='' AND @lib_idx IS NOT NULL THEN 	
		IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=@lib_idx AND requestTime IS NULL AND table_name='operator_maintenance_info' AND change_state='D') THEN
			INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('operator_maintenance_info',@lib_idx,'D');
		END IF;
	END IF;
    END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
