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

/* Procedure structure for procedure `P_GET_DEVICE_MAIN_PAGE_GROUPBY_LIB` */

/*!50003 DROP PROCEDURE IF EXISTS  `P_GET_DEVICE_MAIN_PAGE_GROUPBY_LIB` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `P_GET_DEVICE_MAIN_PAGE_GROUPBY_LIB`(IN device_name VARCHAR(50), IN device_id VARCHAR(30), IN device_type VARCHAR(30),IN in_library_idx INT,IN current_page INT, IN page_size INT)
BEGIN
  DECLARE var_library_idx INT; 
  DECLARE var_count INT; 
  DECLARE total_pages INT DEFAULT 0; 
  DECLARE lib_pages INT; 
  DECLARE beginIndex INT; 
  DECLARE count_sql VARCHAR(500);
  DECLARE ext_sql VARCHAR(500);
  DECLARE done INT DEFAULT FALSE;
  
  DECLARE cur_lib_idx CURSOR FOR
  SELECT DISTINCT library_idx FROM device ORDER BY `library_idx`;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  OPEN cur_lib_idx; 
loop_label:
LOOP
  
  FETCH cur_lib_idx INTO var_library_idx;
  IF done=TRUE THEN 
    LEAVE loop_label; 
  END IF;
  IF in_library_idx !="" AND in_library_idx IS NOT NULL THEN 
     SET var_library_idx=in_library_idx;
  END IF;
  SET count_sql=CONCAT('SELECT COUNT(device_idx) into @v_count FROM device INNER JOIN metadata_devicetype ON device.device_model_idx=metadata_devicetype.meta_devicetype_idx WHERE library_idx in (', var_library_idx,')');
  
  
  IF device_id != "" AND device_id IS NOT NULL THEN
    SET count_sql = CONCAT(count_sql,' and FIND_IN_SET(device_id,', device_id, ')');
  END IF;
  
  IF device_name != "" AND device_name IS NOT NULL THEN
    SET count_sql = CONCAT(count_sql,' AND device_name LIKE "%',device_name,'%"');
  END IF;
  IF device_type != "" AND device_type IS NOT NULL THEN
    SET count_sql=CONCAT(count_sql,' AND device_type LIKE "%',device_type,'%"');
  END IF;
  SET @count_sql = count_sql; 
  PREPARE stmt FROM @count_sql; 
  EXECUTE stmt; 
  DEALLOCATE PREPARE stmt; 
  
  IF @v_count % page_size = 0 THEN 
    SET lib_pages = @v_count DIV page_size;
  ELSE
    
    SET lib_pages = @v_count DIV page_size + 1;
  END IF;
  
  
  
  IF current_page = lib_pages OR current_page<lib_pages THEN
    SET beginIndex = (current_page - 1) * page_size;
    
    SET ext_sql=CONCAT('SELECT * from device INNER JOIN metadata_devicetype ON device.device_model_idx=metadata_devicetype.meta_devicetype_idx WHERE library_idx in (',var_library_idx,')');
    IF device_id != "" AND device_id IS NOT NULL THEN
       SET ext_sql=CONCAT(ext_sql,' AND FIND_IN_SET(device_id,', device_id, ')');
    END IF;
    IF device_name != "" AND device_name IS NOT NULL THEN
      SET ext_sql=CONCAT(ext_sql,' AND device_name LIKE "%',device_name,'%"');
    END IF;
    IF device_type != "" AND device_type IS NOT NULL THEN
      SET ext_sql=CONCAT(ext_sql,' AND device_type LIKE "%',device_type,'%"');
    END IF;
    SET ext_sql=CONCAT(ext_sql,' ORDER BY library_idx LIMIT ',beginIndex,',',page_size);
    SET @ext_sql = ext_sql; 
    PREPARE stmt FROM @ext_sql; 
    EXECUTE stmt; 
    DEALLOCATE PREPARE stmt; 
    LEAVE loop_label;
    CLOSE cur_lib_idx;
 
  ELSE 
    
    
    SET current_page = current_page-lib_pages;
    ITERATE loop_label;
  END IF;
   
   IF in_library_idx !="" AND in_library_idx IS NOT NULL THEN 
    LEAVE loop_label; 
   END IF;
END LOOP;
  CLOSE cur_lib_idx;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
