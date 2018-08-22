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

/* Trigger structure for table `device` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `device_insert_tri` AFTER INSERT ON `device` FOR EACH ROW BEGIN
    IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.device_idx AND requestTime IS NULL AND table_name='device' AND change_state='I')	THEN
	insert into table_change_tri (table_name,data_idx,change_state) values('device',New.device_idx,'I');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `device` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `device_upd_tri` AFTER UPDATE ON `device` FOR EACH ROW BEGIN
    IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.device_idx AND requestTime IS NULL AND table_name='device' AND change_state='U')	THEN
	insert into table_change_tri (table_name,data_idx,change_state) values('device',old.device_idx,'U');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `device` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `device_del_tri` AFTER DELETE ON `device` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.device_idx AND requestTime IS NULL AND table_name='device' AND change_state='D')	THEN
		insert into table_change_tri (table_name,data_idx,change_state) values('device',old.device_idx,'D');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_acs_logininfo` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_acs_logininfo_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_acs_logininfo_insert_tri` AFTER INSERT ON `device_acs_logininfo` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.device_idx AND requestTime IS NULL AND table_name='device_acs_logininfo' AND change_state='I') THEN
		INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('device_acs_logininfo',new.device_idx,'I');	
	END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_acs_logininfo` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_acs_logininfo_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_acs_logininfo_upd_tri` AFTER UPDATE ON `device_acs_logininfo` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.device_idx AND requestTime IS NULL AND table_name='device_acs_logininfo' AND change_state='U') THEN
		INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('device_acs_logininfo',new.device_idx,'U');
	END IF;	
    END */$$


DELIMITER ;

/* Trigger structure for table `device_acs_logininfo` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_acs_logininfo_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_acs_logininfo_del_tri` BEFORE DELETE ON `device_acs_logininfo` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.device_idx AND requestTime IS NULL AND table_name='device_acs_logininfo' AND change_state='D') THEN
		INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('device_acs_logininfo',old.device_idx,'D');
	END IF;	
    END */$$


DELIMITER ;

/* Trigger structure for table `device_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_config_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_config_insert_tri` AFTER INSERT ON `device_config` FOR EACH ROW BEGIN
	INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('device_config',new.device_idx,'I');	
    END */$$


DELIMITER ;

/* Trigger structure for table `device_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_config_befupd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `device_config_befupd_tri` BEFORE UPDATE ON `device_config` FOR EACH ROW BEGIN
INSERT INTO device_config_old( `device_idx`,  `library_idx`,`device_ext_tpl_idx`,`device_ext_tpl_flg`,`device_monitor_tpl_idx`,`device_monitor_tpl_flg`, `device_run_tpl_idx`,
  `device_run_tpl_flg`,  `device_dbsync_tpl_idx`,  `device_dbsync_tpl_flg`)  SELECT `device_idx`,  `library_idx`,`device_ext_tpl_idx`,`device_ext_tpl_flg`,`device_monitor_tpl_idx`,`device_monitor_tpl_flg`, `device_run_tpl_idx`,
  `device_run_tpl_flg`,  `device_dbsync_tpl_idx`,  `device_dbsync_tpl_flg` FROM device_config WHERE device_idx=old.device_idx;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_config_aftupd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `device_config_aftupd_tri` AFTER UPDATE ON `device_config` FOR EACH ROW BEGIN
IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.device_idx AND requestTime IS NULL AND table_name='device_config' AND change_state='U') THEN
INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_config',old.device_idx,'','U');
END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_config_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_config_del_tri` BEFORE DELETE ON `device_config` FOR EACH ROW BEGIN
	INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('device_config',old.device_idx,'D');	
    END */$$


DELIMITER ;

/* Trigger structure for table `device_dbsync_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_dbsync_config_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `device_dbsync_config_insert_tri` AFTER INSERT ON `device_dbsync_config` FOR EACH ROW BEGIN
	 IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.device_dbsync_id AND data_type=new.device_tpl_type AND requestTime IS NULL AND table_name='device_dbsync_config' and change_state='I')	THEN
	INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_dbsync_config',new.device_dbsync_id,new.device_tpl_type,'I');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_dbsync_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_dbsync_config_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `device_dbsync_config_upd_tri` AFTER UPDATE ON `device_dbsync_config` FOR EACH ROW BEGIN
	    IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.device_dbsync_id AND data_type=old.device_tpl_type AND requestTime IS NULL and table_name='device_dbsync_config' AND change_state='U')	THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_dbsync_config',old.device_dbsync_id,old.device_tpl_type,'U');
	     end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_dbsync_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_dbsync_config_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `device_dbsync_config_del_tri` BEFORE DELETE ON `device_dbsync_config` FOR EACH ROW BEGIN
	if not exists(select 1 from table_change_tri where data_idx=old.device_dbsync_id AND data_type=old.device_tpl_type and requestTime is null AND table_name='device_dbsync_config' AND change_state='D')	then
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_dbsync_config',old.device_dbsync_id,old.device_tpl_type,'D');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_ext_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_ext_config_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_ext_config_insert_tri` BEFORE INSERT ON `device_ext_config` FOR EACH ROW BEGIN
    IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.device_ext_id AND data_type=new.device_tpl_type AND requestTime IS NULL AND table_name='device_ext_config' AND change_state='I')	THEN
	INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_ext_config',new.device_ext_id,new.device_tpl_type,'I');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_ext_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_ext_config_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_ext_config_upd_tri` AFTER UPDATE ON `device_ext_config` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.device_ext_id AND data_type=old.device_tpl_type AND requestTime IS NULL AND table_name='device_ext_config' AND change_state='U')	THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_ext_config',old.device_ext_id,old.device_tpl_type,'U');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_ext_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_ext_config_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_ext_config_del_tri` BEFORE DELETE ON `device_ext_config` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.device_ext_id AND data_type=old.device_tpl_type AND requestTime IS NULL AND table_name='device_ext_config' AND change_state='D')	THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_ext_config',old.device_ext_id,old.device_tpl_type,'D');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_run_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_run_config_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_run_config_insert_tri` AFTER INSERT ON `device_run_config` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.device_run_id AND data_type=new.device_tpl_type AND requestTime IS NULL AND table_name='device_run_config' AND change_state='I')	THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_run_config',new.device_run_id,new.device_tpl_type,'I');
	     END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_run_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_run_config_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_run_config_upd_tri` AFTER UPDATE ON `device_run_config` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.device_run_id AND data_type=old.device_tpl_type AND requestTime IS NULL AND table_name='device_run_config' AND change_state='U')	THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_run_config',old.device_run_id,old.device_tpl_type,'U');
	     END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `device_run_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `device_run_config_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `device_run_config_del_tri` BEFORE DELETE ON `device_run_config` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.device_run_id AND data_type=old.device_tpl_type AND requestTime IS NULL AND table_name='device_run_config' AND change_state='D')	THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_run_config',old.device_run_id,old.device_tpl_type,'D');
	     END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_ext_model` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `ext_model_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `ext_model_insert_tri` BEFORE INSERT ON `metadata_ext_model` FOR EACH ROW BEGIN
    IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.meta_ext_idx AND requestTime IS NULL AND table_name='metadata_ext_model' AND change_state='I') THEN
	INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_ext_model',new.meta_ext_idx,'I');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_ext_model` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `ext_model_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `ext_model_upd_tri` AFTER UPDATE ON `metadata_ext_model` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.meta_ext_idx  AND requestTime IS NULL AND table_name='metadata_ext_model' AND change_state='U') THEN
		INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_ext_model',old.meta_ext_idx,'U');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_ext_model` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `ext_model_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `ext_model_del_tri` BEFORE DELETE ON `metadata_ext_model` FOR EACH ROW BEGIN
    IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.meta_ext_idx AND requestTime IS NULL AND table_name='metadata_ext_model' AND change_state='D') THEN
	INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_ext_model',old.meta_ext_idx,'D');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_logic_obj` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `logic_obj_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `logic_obj_insert_tri` AFTER INSERT ON `metadata_logic_obj` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.meta_log_obj_idx AND requestTime IS NULL AND table_name='metadata_logic_obj' AND change_state='I') THEN
		INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_logic_obj',new.meta_log_obj_idx,'I');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_logic_obj` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `logic_obj_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `logic_obj_upd_tri` AFTER UPDATE ON `metadata_logic_obj` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.meta_log_obj_idx AND requestTime IS NULL AND table_name='metadata_logic_obj' AND change_state='U') THEN
		INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_logic_obj',old.meta_log_obj_idx,'U');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_logic_obj` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `logic_obj_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `logic_obj_del_tri` AFTER DELETE ON `metadata_logic_obj` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.meta_log_obj_idx AND requestTime IS NULL AND table_name='metadata_logic_obj' AND change_state='D') THEN
		INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_logic_obj',old.meta_log_obj_idx,'D');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_opercmd` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `metadata_opercmd_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `metadata_opercmd_insert_tri` AFTER INSERT ON `metadata_opercmd` FOR EACH ROW BEGIN
    IF new.opercmd_typeinfo=2 THEN
    IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.meta_opercmd_idx AND requestTime IS NULL AND table_name='metadata_opercmd' AND change_state='I') THEN
	INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_opercmd',new.meta_opercmd_idx,'I');
	end if;
 end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_opercmd` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `metadata_opercmd_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `metadata_opercmd_upd_tri` AFTER UPDATE ON `metadata_opercmd` FOR EACH ROW BEGIN
    IF new.opercmd_typeinfo=2 THEN
    IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.meta_opercmd_idx AND requestTime IS NULL AND table_name='metadata_opercmd' AND change_state='U') THEN
	INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_opercmd',new.meta_opercmd_idx,'U');
	end if;
end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_opercmd` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `metadata_opercmd_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `metadata_opercmd_del_tri` BEFORE DELETE ON `metadata_opercmd` FOR EACH ROW BEGIN
	if old.opercmd_typeinfo=2 then
		IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.meta_opercmd_idx AND requestTime IS NULL AND table_name='metadata_opercmd' AND change_state='D') THEN
			INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_opercmd',old.meta_opercmd_idx,'D');
		end if;
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_order` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `metadata_order_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `metadata_order_insert_tri` AFTER INSERT ON `metadata_order` FOR EACH ROW BEGIN
    IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.order_idx AND requestTime IS NULL AND table_name='metadata_order' AND change_state='I') THEN
	INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_order',new.order_idx,'I');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_order` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `metadata_order_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `metadata_order_upd_tri` AFTER UPDATE ON `metadata_order` FOR EACH ROW BEGIN
    IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.order_idx AND requestTime IS NULL AND table_name='metadata_order' AND change_state='U') THEN
	INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_order',old.order_idx,'U');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_order` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `metadata_order_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `metadata_order_del_tri` BEFORE DELETE ON `metadata_order` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.order_idx AND requestTime IS NULL AND table_name='metadata_order' AND change_state='D') THEN
		INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_order',old.order_idx,'D');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_run_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `metadata_run_config_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `metadata_run_config_insert_tri` AFTER INSERT ON `metadata_run_config` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.meta_run_cfg_idx AND requestTime IS NULL AND table_name='metadata_run_config' AND change_state='I') THEN
		INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_run_config',new.meta_run_cfg_idx,'I');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_run_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `metadata_run_config_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `metadata_run_config_upd_tri` AFTER UPDATE ON `metadata_run_config` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.meta_run_cfg_idx AND requestTime IS NULL AND table_name='metadata_run_config' AND change_state='U') THEN
		INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_run_config',old.meta_run_cfg_idx,'U');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `metadata_run_config` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `metadata_run_config_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'::1' */ /*!50003 TRIGGER `metadata_run_config_del_tri` BEFORE DELETE ON `metadata_run_config` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.meta_run_cfg_idx AND requestTime IS NULL AND table_name='metadata_run_config' AND change_state='D') THEN
		INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('metadata_run_config',old.meta_run_cfg_idx,'D');
	end if;
    END */$$


DELIMITER ;

/* Trigger structure for table `operator_maintenance_info` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `operator_maintenance_info_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `operator_maintenance_info_insert_tri` AFTER INSERT ON `operator_maintenance_info` FOR EACH ROW BEGIN
	SET @lib_idx=(SELECT library_idx FROM reader_type WHERE type_distinction='2' AND type_idx=new.maintenance_idx LIMIT 1);
	IF @lib_idx!='' AND @lib_idx IS NOT NULL THEN 	
		IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=@lib_idx AND requestTime IS NULL AND table_name='operator_maintenance_info' AND change_state='I') THEN
			INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('operator_maintenance_info',@lib_idx,'I');
		END IF;
	END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `operator_maintenance_info` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `operator_maintenance_info_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `operator_maintenance_info_upd_tri` AFTER UPDATE ON `operator_maintenance_info` FOR EACH ROW BEGIN
	SET @lib_idx=(SELECT library_idx FROM reader_type WHERE type_distinction='2' AND type_idx=old.maintenance_idx LIMIT 1);
	IF @lib_idx!='' AND @lib_idx IS NOT NULL THEN 	
		IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=@lib_idx AND requestTime IS NULL AND table_name='operator_maintenance_info' AND change_state='U') THEN
			INSERT INTO table_change_tri (table_name,data_idx,change_state) VALUES('operator_maintenance_info',@lib_idx,'U');
		END IF;
	END IF;
    END */$$


DELIMITER ;

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

/* Trigger structure for table `patch_info` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `patch_info_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `patch_info_insert_tri` AFTER INSERT ON `patch_info` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.patch_idx AND requestTime IS NULL AND table_name='patch_info' AND change_state='I') THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('patch_info',new.patch_idx,new.patch_type,'I');
	END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `patch_info` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `patch_info_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `patch_info_upd_tri` AFTER UPDATE ON `patch_info` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.patch_idx AND requestTime IS NULL AND table_name='patch_info' AND change_state='U') THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('patch_info',old.patch_idx,old.patch_type,'U');
	END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `patch_info` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `patch_info_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `patch_info_del_tri` BEFORE DELETE ON `patch_info` FOR EACH ROW BEGIN
	    IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.patch_idx AND requestTime IS NULL AND table_name='patch_info' AND change_state='D') THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('patch_info',old.patch_idx,old.patch_type,'D');
	END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `reader_type` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `reader_type_insert_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `reader_type_insert_tri` AFTER INSERT ON `reader_type` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=new.type_idx AND requestTime IS NULL AND table_name='reader_type' AND change_state='I') THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('reader_type',new.library_idx,new.type_distinction,'I');
	END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `reader_type` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `reader_type_upd_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `reader_type_upd_tri` AFTER UPDATE ON `reader_type` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.type_idx AND requestTime IS NULL AND table_name='reader_type' AND change_state='U') THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('reader_type',old.library_idx,old.type_distinction,'U');
	END IF;
    END */$$


DELIMITER ;

/* Trigger structure for table `reader_type` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `reader_type_del_tri` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `reader_type_del_tri` BEFORE DELETE ON `reader_type` FOR EACH ROW BEGIN
	IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.type_idx AND requestTime IS NULL AND table_name='reader_type' AND change_state='D') THEN
		INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('reader_type',old.library_idx,old.type_distinction,'D');
	END IF;
    END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
