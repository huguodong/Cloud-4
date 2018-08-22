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

/*Table structure for table `device` */

DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `device_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(20) NOT NULL COMMENT '设备ID',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `device_model_idx` int(11) NOT NULL COMMENT '设备类型id',
  `device_name` varchar(100) NOT NULL COMMENT '设备名',
  `device_code` varchar(100) NOT NULL COMMENT '设备特征码。设备自动计算获得',
  `device_desc` varchar(100) DEFAULT NULL COMMENT '设备描述',
  `library_login_name` varchar(20) DEFAULT NULL COMMENT '自助服务登录用户名',
  `library_login_pwd` varchar(20) DEFAULT NULL COMMENT '自助服务登录密码',
  `logistics_number` varchar(20) DEFAULT NULL COMMENT '物流编号',
  `circule_location` varchar(200) DEFAULT NULL COMMENT '流通地点',
  PRIMARY KEY (`device_idx`),
  UNIQUE KEY `device_id` (`device_id`),
  UNIQUE KEY `device_code` (`device_code`),
  KEY `device_model_dmID` (`device_model_idx`),
  CONSTRAINT `device_ibfk_1` FOREIGN KEY (`device_model_idx`) REFERENCES `metadata_devicetype` (`meta_devicetype_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='设备表，存储设备信息。device_type字段由metadata_devicetype表定义';

/*Data for the table `device` */

/*Table structure for table `device_acs_logininfo` */

DROP TABLE IF EXISTS `device_acs_logininfo`;

CREATE TABLE `device_acs_logininfo` (
  `logininfo_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID号',
  `protocol_tpl_idx` int(11) NOT NULL COMMENT 'ACS模板号',
  `device_idx` int(11) NOT NULL COMMENT '设备IDX',
  `library_idx` int(11) NOT NULL COMMENT '图书馆IDX',
  `login_ip` varchar(20) NOT NULL COMMENT '登录IP',
  `login_port` int(11) NOT NULL COMMENT '登录端口号',
  `login_username` varchar(50) DEFAULT NULL COMMENT '登录用户名',
  `login_pwd` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `acs_service_name` varchar(50) NOT NULL COMMENT 'ACS后台服务名',
  PRIMARY KEY (`logininfo_idx`),
  KEY `protocol_tpl_idx` (`protocol_tpl_idx`),
  CONSTRAINT `protocol_tpl_idx` FOREIGN KEY (`protocol_tpl_idx`) REFERENCES `protocol_config_template` (`protocol_tpl_idx`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_acs_logininfo` */

/*Table structure for table `device_config` */

DROP TABLE IF EXISTS `device_config`;

CREATE TABLE `device_config` (
  `device_idx` int(11) NOT NULL COMMENT '设备ID',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `device_ext_tpl_idx` int(11) NOT NULL COMMENT '硬件配置模板ID',
  `device_ext_tpl_flg` int(11) NOT NULL COMMENT '是否采用模板 1是  0否',
  `device_monitor_tpl_idx` int(11) NOT NULL COMMENT '显示配置模板ID',
  `device_monitor_tpl_flg` int(11) NOT NULL COMMENT '显示是否采用模板 1是  0否',
  `device_run_tpl_idx` int(11) NOT NULL COMMENT '设备运行模板ID',
  `device_run_tpl_flg` int(11) NOT NULL COMMENT '运行是否采用模板 1是  0否',
  `device_dbsync_tpl_idx` int(11) NOT NULL COMMENT '数据同步模板ID',
  `device_dbsync_tpl_flg` int(11) NOT NULL COMMENT '同步是否采用模板 1是 0否',
  KEY `library_lID` (`library_idx`),
  KEY `device_dID` (`device_idx`),
  KEY `device_tpl_id` (`device_ext_tpl_idx`),
  KEY `device_config_ibfk_4` (`device_monitor_tpl_idx`),
  KEY `device_config_ibfk_5` (`device_run_tpl_idx`),
  KEY `device_config_ibfk_6` (`device_dbsync_tpl_idx`),
  CONSTRAINT `device_config_ibfk_2` FOREIGN KEY (`device_idx`) REFERENCES `device` (`device_idx`),
  CONSTRAINT `device_config_ibfk_3` FOREIGN KEY (`device_ext_tpl_idx`) REFERENCES `device_ext_template` (`device_tpl_idx`),
  CONSTRAINT `device_config_ibfk_4` FOREIGN KEY (`device_monitor_tpl_idx`) REFERENCES `device_monitor_template` (`device_mon_tpl_idx`),
  CONSTRAINT `device_config_ibfk_5` FOREIGN KEY (`device_run_tpl_idx`) REFERENCES `device_run_template` (`device_tpl_idx`),
  CONSTRAINT `device_config_ibfk_6` FOREIGN KEY (`device_dbsync_tpl_idx`) REFERENCES `device_dbsync_template` (`device_tpl_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_config` */

/*Table structure for table `device_config_old` */

DROP TABLE IF EXISTS `device_config_old`;

CREATE TABLE `device_config_old` (
  `device_idx` int(11) NOT NULL COMMENT '设备ID',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `device_ext_tpl_idx` int(11) NOT NULL COMMENT '硬件配置模板ID',
  `device_ext_tpl_flg` int(11) NOT NULL COMMENT '是否采用模板 1是  0否',
  `device_monitor_tpl_idx` int(11) NOT NULL COMMENT '显示配置模板ID',
  `device_monitor_tpl_flg` int(11) NOT NULL COMMENT '显示是否采用模板 1是  0否',
  `device_run_tpl_idx` int(11) NOT NULL COMMENT '设备运行模板ID',
  `device_run_tpl_flg` int(11) NOT NULL COMMENT '运行是否采用模板 1是  0否',
  `device_dbsync_tpl_idx` int(11) NOT NULL COMMENT '数据同步模板ID',
  `device_dbsync_tpl_flg` int(11) NOT NULL COMMENT '同步是否采用模板 1是 0否',
  `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_config_old` */

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

/*Table structure for table `device_dbsync_template` */

DROP TABLE IF EXISTS `device_dbsync_template`;

CREATE TABLE `device_dbsync_template` (
  `device_tpl_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_tpl_ID` varchar(10) DEFAULT NULL COMMENT '模板ID',
  `device_tpl_desc` varchar(100) DEFAULT NULL COMMENT '模板描述',
  `device_type` int(11) DEFAULT NULL COMMENT '设备类型IDX',
  PRIMARY KEY (`device_tpl_idx`),
  UNIQUE KEY `device_tpl_desc` (`device_tpl_desc`),
  UNIQUE KEY `device_tpl_ID` (`device_tpl_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `device_dbsync_template` */

/*Table structure for table `device_ext_config` */

DROP TABLE IF EXISTS `device_ext_config`;

CREATE TABLE `device_ext_config` (
  `library_idx` int(11) NOT NULL,
  `device_tpl_type` int(11) NOT NULL COMMENT '模板区分 0模板 1数据',
  `device_ext_id` int(11) NOT NULL COMMENT '设备idx或者模板idx',
  `ext_comm_conf` varchar(100) DEFAULT NULL COMMENT '外设通信方式',
  `ext_extend_conf` text COMMENT '外设其它信息',
  `logic_obj_idx` int(11) NOT NULL COMMENT '外设类型',
  `ext_model_idx` int(11) NOT NULL COMMENT '外设型号',
  `updatetime` timestamp NULL DEFAULT NULL,
  KEY `m_logic_obj_mloID` (`logic_obj_idx`),
  KEY `m_ext_model_memID` (`ext_model_idx`),
  KEY `device_id` (`library_idx`),
  KEY `ext_id` (`ext_model_idx`),
  CONSTRAINT `ext_id` FOREIGN KEY (`ext_model_idx`) REFERENCES `metadata_ext_model` (`meta_ext_idx`),
  CONSTRAINT `logic_obj` FOREIGN KEY (`logic_obj_idx`) REFERENCES `metadata_logic_obj` (`meta_log_obj_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备外设硬件的配置';

/*Data for the table `device_ext_config` */

/*Table structure for table `device_ext_template` */

DROP TABLE IF EXISTS `device_ext_template`;

CREATE TABLE `device_ext_template` (
  `device_tpl_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_tpl_ID` varchar(10) DEFAULT NULL COMMENT '模板ID',
  `device_tpl_desc` varchar(200) DEFAULT NULL COMMENT '模板描述',
  `device_type` int(11) DEFAULT NULL COMMENT '设备类型IDX',
  PRIMARY KEY (`device_tpl_idx`),
  UNIQUE KEY `device_tpl_desc` (`device_tpl_desc`),
  UNIQUE KEY `device_tpl_ID` (`device_tpl_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `device_ext_template` */

/*Table structure for table `device_group` */

DROP TABLE IF EXISTS `device_group`;

CREATE TABLE `device_group` (
  `device_group_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备组IDX',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `device_group_id` varchar(20) NOT NULL COMMENT '设备组ID',
  `device_group_name` varchar(100) NOT NULL COMMENT '设备组名',
  `device_group_desc` varchar(100) DEFAULT NULL COMMENT '设备组描述',
  PRIMARY KEY (`device_group_idx`),
  UNIQUE KEY `device_group_id` (`library_idx`,`device_group_id`),
  UNIQUE KEY `device_group_name` (`library_idx`,`device_group_name`),
  KEY `library_lID` (`library_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备组表，图书馆通过设备组和具体设备关联起来';

/*Data for the table `device_group` */

/*Table structure for table `device_log_config` */

DROP TABLE IF EXISTS `device_log_config`;

CREATE TABLE `device_log_config` (
  `device_log_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_idx` int(11) NOT NULL COMMENT '设备ID',
  `runlog_level` int(11) NOT NULL COMMENT '日志级别，0-不记录，1-error，2-info，3-warning，4-debug',
  `runlog_type` int(11) NOT NULL COMMENT '保存方式，0-db，1-file',
  `runlog_filesize` int(11) NOT NULL COMMENT '日志保存文件的大小',
  `library_idx` int(11) NOT NULL,
  PRIMARY KEY (`device_log_idx`),
  KEY `device_id` (`device_idx`,`library_idx`),
  KEY `device_id_2` (`device_idx`),
  KEY `library_lID` (`library_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备运行日志的配置';

/*Data for the table `device_log_config` */

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

/*Table structure for table `device_monitor_template` */

DROP TABLE IF EXISTS `device_monitor_template`;

CREATE TABLE `device_monitor_template` (
  `device_mon_tpl_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_mon_tpl_id` varchar(10) NOT NULL COMMENT '显示模板ID',
  `device_mon_tpl_desc` varchar(200) NOT NULL COMMENT '显示模板描述',
  `device_mon_type_idx` int(11) NOT NULL COMMENT '设备类型',
  PRIMARY KEY (`device_mon_tpl_idx`),
  UNIQUE KEY `device_mon_tpl_desc` (`device_mon_tpl_desc`),
  UNIQUE KEY `device_mon_tpl_id` (`device_mon_tpl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `device_monitor_template` */

/*Table structure for table `device_run_config` */

DROP TABLE IF EXISTS `device_run_config`;

CREATE TABLE `device_run_config` (
  `device_tpl_type` int(11) NOT NULL COMMENT '模板区分 0模板 1数据',
  `device_run_id` int(11) NOT NULL COMMENT '模板IDX或设备IDX',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `run_config_idx` int(11) NOT NULL COMMENT '设备端参数配置初始数据',
  `run_conf_value` mediumtext NOT NULL COMMENT '基础配置参数',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `library_lID` (`library_idx`),
  KEY `m_run_config_mrcID` (`run_config_idx`),
  CONSTRAINT `device_run_config_ibfk_2` FOREIGN KEY (`run_config_idx`) REFERENCES `metadata_run_config` (`meta_run_cfg_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_run_config` */

/*Table structure for table `device_run_template` */

DROP TABLE IF EXISTS `device_run_template`;

CREATE TABLE `device_run_template` (
  `device_tpl_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_tpl_ID` varchar(10) DEFAULT NULL COMMENT '模板ID',
  `device_tpl_desc` varchar(200) DEFAULT NULL COMMENT '模板描述',
  `device_type` int(11) DEFAULT NULL COMMENT '设备类型IDX',
  PRIMARY KEY (`device_tpl_idx`),
  UNIQUE KEY `device_tpl_desc` (`device_tpl_desc`),
  UNIQUE KEY `device_tpl_ID` (`device_tpl_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `device_run_template` */

/*Table structure for table `device_selfcheck_protocol_config` */

DROP TABLE IF EXISTS `device_selfcheck_protocol_config`;

CREATE TABLE `device_selfcheck_protocol_config` (
  `protocol_idx` int(11) NOT NULL COMMENT '模板ID号',
  `device_tpl_type` int(11) NOT NULL COMMENT '模板区分 0模板',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `protocol_type` int(11) NOT NULL COMMENT '指令类型 1SIP2  2NCIP',
  `protocol_library_idx` int(11) NOT NULL COMMENT '指令字段描述，外关连metadata_protocol',
  `protocol_reqrule` varchar(500) DEFAULT NULL COMMENT '请求规则',
  `protocol_resprule` varchar(500) DEFAULT NULL COMMENT '响应规则',
  `protocol_description` varchar(1000) DEFAULT NULL COMMENT '描述',
  `operator_idx` int(11) NOT NULL COMMENT '操作员ID',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  KEY `protocol_idx` (`protocol_idx`),
  CONSTRAINT `fk_protocol_idx` FOREIGN KEY (`protocol_idx`) REFERENCES `protocol_config_template` (`protocol_tpl_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_selfcheck_protocol_config` */

/*Table structure for table `library_selfcheck_protocol_config` */

DROP TABLE IF EXISTS `library_selfcheck_protocol_config`;

CREATE TABLE `library_selfcheck_protocol_config` (
  `protocol_field_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `protocol_field_name` varchar(50) NOT NULL COMMENT '指令描述',
  `protocol_field_desc` varchar(200) NOT NULL COMMENT '指令名',
  `protocol_field_mark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `protocol_show` varchar(50) NOT NULL COMMENT '指令代码',
  `protocol_type` int(11) NOT NULL COMMENT '1 SIP2 2 NCIP',
  PRIMARY KEY (`protocol_field_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

/*Data for the table `library_selfcheck_protocol_config` */

insert  into `library_selfcheck_protocol_config`(`protocol_field_idx`,`protocol_field_name`,`protocol_field_desc`,`protocol_field_mark`,`protocol_show`,`protocol_type`) values (1,'patronStatus','64状态',NULL,'64',1),(2,'languagge','64语种',NULL,'64',1),(3,'transcatonDate','64处理时间',NULL,'64',1),(4,'holdItemsCount','64可借数',NULL,'64',1),(5,'overdueItemsCount','64过期文献数',NULL,'64',1),(6,'chargedItemsCount','64收费文献数',NULL,'64',1),(7,'fineItemsCount','64罚款文献数',NULL,'64',1),(8,'recallItemsCount','64续借文献数',NULL,'64',1),(9,'unavailableHoldsCount','64不可借馆藏数',NULL,'64',1),(10,'institutionId','64说明ID',NULL,'64',1),(11,'patronIdentified','64证号',NULL,'64',1),(12,'personalName','64姓名',NULL,'64',1),(13,'holdItemsLimit','64剩余可借数',NULL,'64',1),(14,'overdueItemsLimit','64过期文献可借数',NULL,'64',1),(15,'chargedItemsLimit','64收费文献可借数',NULL,'64',1),(16,'validPatron','64有效证',NULL,'64',1),(17,'validPatronPassword','64有效密码',NULL,'64',1),(18,'currentType','64类型',NULL,'64',1),(19,'feeAmount','64押金',NULL,'64',1),(20,'feeLimit','64欠款',NULL,'64',1),(21,'holdItems','64借书条码号',NULL,'64',1),(22,'overdueItems','64过期条码号',NULL,'64',1),(23,'chargedItems','64收费条码号',NULL,'64',1),(24,'fineItems','64欠款条码号',NULL,'64',1),(25,'recallItems','64续借条码号',NULL,'64',1),(26,'unavailableHoldItems','64',NULL,'64',1),(27,'homeAddress','64家庭住址',NULL,'64',1),(28,'e-mailAddress','64邮箱',NULL,'64',1),(29,'homePhoneNumber','64电话号码',NULL,'64',1),(30,'screenMessage','64返回消息',NULL,'64',1),(31,'printLine','64验证码',NULL,'64',1),(32,'language','63语言',NULL,'63',1),(33,'transcationDate','63处理时间',NULL,'63',1),(34,'summary','63概要',NULL,'63',1),(35,'institutionId','63',NULL,'63',1),(36,'patronIdentifier','63证号',NULL,'63',1),(37,'terminalPassword','63密码',NULL,'63',1),(38,'patronPassword','63证密码',NULL,'63',1),(39,'startItem','63开始项',NULL,'63',1),(40,'endItem','63结束项',NULL,'63',1),(41,'transactionDate','17日期',NULL,'17',1),(42,'institutionId','17机构代码',NULL,'17',1),(43,'itemIdentifier','17条码号',NULL,'17',1),(44,'terminalPassword','17密码',NULL,'17',1),(45,'circulationStatus','18状态',NULL,'18',1),(46,'securityMarket','18标识',NULL,'18',1),(47,'feeType','18类型',NULL,'18',1),(48,'transactionDate','18日期',NULL,'18',1),(49,'holdQueueLength','18',NULL,'18',1),(50,'dueDate','18发生日期',NULL,'18',1),(51,'recallDate','18续借时间',NULL,'18',1),(52,'holdPickupDate','18借出时间',NULL,'18',1),(53,'itemIdentifier','18条码号',NULL,'18',1),(54,'titleIdentifier','18书名',NULL,'18',1),(55,'owner','18作者',NULL,'18',1),(56,'currentType','18流通类型',NULL,'18',1),(57,'feeAmount','18单价',NULL,'18',1),(58,'mediaType','18载体类型',NULL,'18',1),(59,'permanentLocation','18常久位置',NULL,'18',1),(60,'currentLocation','18当前位置',NULL,'18',1),(61,'itemProperties','18属性',NULL,'18',1),(62,'screenMessage','18说明',NULL,'18',1),(63,'printLine','18验证码',NULL,'18',1);

/*Table structure for table `metadata_devicetype` */

DROP TABLE IF EXISTS `metadata_devicetype`;

CREATE TABLE `metadata_devicetype` (
  `meta_devicetype_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_type` varchar(100) DEFAULT NULL COMMENT '设备类型',
  `device_type_desc` varchar(100) DEFAULT NULL COMMENT '设备类型描述',
  `device_type_mark` varchar(200) DEFAULT NULL COMMENT '设备类型备注',
  `device_logic_list` varchar(200) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`meta_devicetype_idx`),
  UNIQUE KEY `device_type` (`device_type`),
  UNIQUE KEY `device_type_desc` (`device_type_desc`),
  KEY `dmID` (`meta_devicetype_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='设备类型描述元数据表，描述设备的类型信息，如自助借还机，SSL';

/*Data for the table `metadata_devicetype` */

insert  into `metadata_devicetype`(`meta_devicetype_idx`,`device_type`,`device_type_desc`,`device_type_mark`,`device_logic_list`,`createTime`) values (1,'SSL','24小时自助图书馆','24小时自助图书馆','AuthorityBarcode,AuthorityReader,CardDispenser,CashAcceptor,IdentityReader,ItemBarcode,ItemLoadReader,ItemRfidReader,PlcSorter,PlcSSL,Printer,RegisterReader','2016-04-18 11:30:17'),(2,'SCH','借还书机','自助借还机','AuthorityBarcode,AuthorityReader,IdentityReader,ItemBarcode,ItemRfidReader,Printer','2016-04-18 11:30:24'),(3,'REG','办证机','自助办证机','AuthorityBarcode,AuthorityReader,CardDispenser,CashAcceptor,IdentityReader,Printer,RegisterReader','2016-04-19 11:45:30'),(4,'BDR','还书机','自助还书机','AuthorityBarcode,AuthorityReader,IdentityReader,ItemBarcode,ItemRfidReader,PlcReturn,Printer','2016-04-19 11:45:32'),(5,'STA','馆员工作站','馆员工作站-借还、办证功能','AuthorityBarcode,AuthorityReader,IdentityReader,ItemBarcode,ItemRfidReader,Printer','2016-04-20 17:38:59'),(6,'STA-C','点检车','馆员工作站-点检车功能','AuthorityBarcode,AuthorityReader,ItemBarcode,ItemRfidReader','2016-04-20 17:40:28'),(7,'POR','安全门','安全门，读取读者证的EAS位信息','AuthorityReader','2016-04-22 20:12:10'),(8,'STA-V','标签加工','馆员工作站-标签加工功能','AuthorityReader,IdentityReader,ItemBarcode,ItemRfidReader','2016-04-22 20:12:10');

/*Table structure for table `metadata_ext_model` */

DROP TABLE IF EXISTS `metadata_ext_model`;

CREATE TABLE `metadata_ext_model` (
  `meta_ext_idx` int(11) NOT NULL AUTO_INCREMENT,
  `ext_model` varchar(100) NOT NULL COMMENT '外设型号',
  `ext_model_desc` varchar(100) NOT NULL COMMENT '外设型号描述',
  `ext_model_version` varchar(100) DEFAULT NULL COMMENT '外设版本',
  `ext_type` varchar(100) DEFAULT NULL COMMENT '外设类型',
  `ext_model_driver_path` varchar(100) NOT NULL,
  PRIMARY KEY (`meta_ext_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='元数据表，外设型号，TAGSYS，NV200等';

/*Data for the table `metadata_ext_model` */

insert  into `metadata_ext_model`(`meta_ext_idx`,`ext_model`,`ext_model_desc`,`ext_model_version`,`ext_type`,`ext_model_driver_path`) values (1,'CRV100D','{\"zh_CN\":\"华视身份证阅读器\"}','V3','idreader',''),(2,'YK347I','{\"zh_CN\":\"研科打印机\"}','-','printer',''),(3,'CZC100RMB','{\"zh_CN\":\"创自收钞机\"}','-','cashacceptor',''),(4,'TYCD1500','{\"zh_CN\":\"天毅发卡机\"}','-','carddispenser',''),(5,'BWUHF','{\"zh_CN\":\"博纬UHF阅读器\"}','-','uhfreader',''),(6,'PLCSSL','{\"zh_CN\":\"自助图书馆PLC\"}','-','plcssl',''),(7,'PLCSORTER','{\"zh_CN\":\"自助分拣机PLC\"}','-','plcsorter',''),(8,'PLCRETURN','{\"zh_CN\":\"自助还书机PLC\"}','-','plcreturn',''),(9,'RR3036','{\"zh_CN\":\"荣瑞高频阅读器\"}','-','hfreader',''),(10,'FS16','{\"zh_CN\":\"条码枪FS16\"}','-','barcode',''),(11,'BCL','{\"zh_CN\":\"条码枪BCL8\"}','-','barcode',''),(12,'RR288M','{\"zh_CN\":\"荣瑞超高频阅读器\"}','-','uhfreader','');

/*Table structure for table `metadata_library_selfcheck_protocol` */

DROP TABLE IF EXISTS `metadata_library_selfcheck_protocol`;

CREATE TABLE `metadata_library_selfcheck_protocol` (
  `protocol_idx` int(11) NOT NULL COMMENT '模板ID号或设备ID号',
  `device_tpl_type` int(11) NOT NULL COMMENT '模板区分 0模板 1数据',
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `protocol_type` int(11) NOT NULL COMMENT '指令类型 1SIP2  2NCIP',
  `protocol_show` varchar(50) NOT NULL COMMENT '指令代码  63、64等',
  `protocol_library_idx` int(11) NOT NULL COMMENT '指令字段描述，外关连library_selfcheck_protocol_config',
  `protocol_start` int(11) DEFAULT NULL COMMENT '指令起始位置',
  `protocol_end` int(11) DEFAULT NULL COMMENT '指令结束位置',
  `protocol_code` varchar(50) DEFAULT NULL COMMENT '指令标识符',
  `protocol_defalut` varchar(50) DEFAULT NULL COMMENT '默认值',
  `operator_idx` int(11) NOT NULL COMMENT '操作员ID',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  KEY `protocol_idx` (`protocol_idx`),
  CONSTRAINT `fk_protocal_tpl_idx` FOREIGN KEY (`protocol_idx`) REFERENCES `protocol_config_template` (`protocol_tpl_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `metadata_library_selfcheck_protocol` */

/*Table structure for table `metadata_logic_obj` */

DROP TABLE IF EXISTS `metadata_logic_obj`;

CREATE TABLE `metadata_logic_obj` (
  `meta_log_obj_idx` int(11) NOT NULL AUTO_INCREMENT,
  `logic_obj` varchar(100) NOT NULL COMMENT '逻辑部件ID',
  `logic_obj_desc` varchar(100) NOT NULL COMMENT '逻辑部件描述',
  PRIMARY KEY (`meta_log_obj_idx`),
  KEY `mloID` (`meta_log_obj_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='元数据表，逻辑对象，图书rfid阅读器、图书条码枪、PLC';

/*Data for the table `metadata_logic_obj` */

insert  into `metadata_logic_obj`(`meta_log_obj_idx`,`logic_obj`,`logic_obj_desc`) values (1,'AuthorityBarcode','{\"zh_CN\":\"读者证条码枪\"}'),(2,'AuthorityReader','{\"zh_CN\":\"读者证阅读器\"}'),(3,'CardDispenser','{\"zh_CN\":\"发卡机\"}'),(4,'CashAcceptor','{\"zh_CN\":\"收钞机\"}'),(5,'IdentityReader','{\"zh_CN\":\"身份证阅读器\"}'),(6,'ItemBarcode','{\"zh_CN\":\"图书条码枪\"}'),(7,'ItemLoadReader','{\"zh_CN\":\"补书阅读器\"}'),(8,'ItemRfidReader','{\"zh_CN\":\"图书阅读器\"}'),(9,'PlcReturn','{\"zh_CN\":\"还书机PLC\"}'),(10,'PlcSorter','{\"zh_CN\":\"分拣机PLC\"}'),(11,'PlcSSL','{\"zh_CN\":\"自助图书馆PLC\"}'),(12,'Printer','{\"zh_CN\":\"打印机\"}'),(13,'RegisterReader','{\"zh_CN\":\"发卡阅读器\"}');

/*Table structure for table `metadata_opercmd` */

DROP TABLE IF EXISTS `metadata_opercmd`;

CREATE TABLE `metadata_opercmd` (
  `meta_opercmd_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `opercmd_typeinfo` int(5) NOT NULL COMMENT '命令类型区分 1云端命令  2设备端',
  `operbusinesstype` varchar(100) DEFAULT NULL COMMENT '主目录例系统管理',
  `opercmd` varchar(100) DEFAULT NULL COMMENT '次目录ID号',
  `opercmd_desc` varchar(100) DEFAULT NULL COMMENT '次目录说明例：新增馆操作',
  `opercmd_url` varchar(200) DEFAULT NULL COMMENT '链接地址',
  PRIMARY KEY (`meta_opercmd_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8 COMMENT='操作指令元数据表，描述业务操作指令信息';

/*Data for the table `metadata_opercmd` */

insert  into `metadata_opercmd`(`meta_opercmd_idx`,`opercmd_typeinfo`,`operbusinesstype`,`opercmd`,`opercmd_desc`,`opercmd_url`) values (1,1,'设备注册','0101000000','注册设备',NULL),(2,1,'数据同步','0104000000','数据同步',NULL),(3,1,'监控管理','0103000000','监控管理',''),(4,1,'系统管理','0102000000','系统管理',NULL),(5,1,'数据同步','0104000000','数据同步','heartBeat,askControl,controlResult,askCfgSync,askCloudTime'),(6,1,'数据同步','0104010000','数据下载','askVersion'),(7,1,'数据同步','0104020000','数据上传','transData,uploadRunLog'),(8,1,'数据同步','0104030000','硬件配置修改','downloadCfgSync,uploadcfgSyn'),(9,1,'监控管理','0103000000','监控管理主页面','/device/deviceMain'),(10,1,'监控管理','0103010000','监控列表',NULL),(11,1,'监控管理','0103020000','监控详情',NULL),(12,1,'监控管理','0103030000','远程控制',NULL),(13,1,'监控管理','0103030100','关机',NULL),(14,1,'监控管理','0103030200','重启',NULL),(15,1,'监控管理','0103030300','下载日志',NULL),(16,1,'监控管理','0103030400','设备端锁定',NULL),(17,1,'监控管理','0103030500','取消操作',NULL),(18,1,'监控管理','0103040000','修改密码',NULL),(19,1,'系统管理','0102010000','用户管理',NULL),(20,1,'系统管理','0102020000','图书馆管理',NULL),(21,1,'系统管理','0102030000','设备管理',NULL),(22,1,'系统管理','0102040000','系统管理',NULL),(23,1,'系统管理','0102010100','用户基本信息管理',NULL),(24,1,'系统管理','0102010101','新增用户',NULL),(25,1,'系统管理','0102010102','修改用户',NULL),(26,1,'系统管理','0102010103','删除用户',NULL),(27,1,'系统管理','0102010200','用户组管理',NULL),(28,1,'系统管理','0102010201','新增用户组',NULL),(29,1,'系统管理','0102010202','修改用户组',NULL),(30,1,'系统管理','0102010203','删除用户组',NULL),(31,1,'系统管理','01020100300','修改密码',NULL),(32,1,'系统管理','0102020100','图书馆基本信息管理','/page/librarymgmt.jsp'),(33,1,'系统管理','0102020101','新增图书馆基本信息',NULL),(34,1,'系统管理','0102020102','修改图书馆基本信息',NULL),(35,1,'系统管理','0102020103','删除图书馆基本信息',NULL),(36,1,'系统管理','0102020200','图书馆服务模板管理','/page/libraryconfig.jsp'),(37,1,'系统管理','0102020201','新增图书馆模板',NULL),(38,1,'系统管理','0102020202','修改图书馆模板',NULL),(39,1,'系统管理','0102020203','删除图书馆模板',NULL),(40,1,'系统管理','0102020300','密码模板管理',NULL),(41,1,'系统管理','0102020301','新增密码模板',NULL),(42,1,'系统管理','0102020302','修改密码模板',NULL),(43,1,'系统管理','0102020303','删除密码模板',NULL),(44,1,'系统管理','0102030100','ACS协议配置模板',NULL),(45,1,'系统管理','0102030101','新增ACS协议配置模板',NULL),(46,1,'系统管理','0102030102','修改ACS协议配置模板',NULL),(47,1,'系统管理','0102030103','删除ACS协议配置模板',NULL),(48,1,'系统管理','0102030200','读者流通类型管理','/page/system-readertype.jsp'),(49,1,'系统管理','0102030201','新增流通类型',NULL),(50,1,'系统管理','0102030202','修改流通类型',NULL),(51,1,'系统管理','0102030203','删除流通类型',NULL),(52,1,'系统管理','0102030300','数据查询模板',NULL),(53,1,'系统管理','0102030301','新增数据查询模板',NULL),(54,1,'系统管理','0102030302','修改数据查询模板',NULL),(55,1,'系统管理','0102030303','删除数据查询模板',NULL),(56,1,'系统管理','0102030400','统计模板',NULL),(57,1,'系统管理','0102030401','新增统计模板',NULL),(58,1,'系统管理','0102030402','修改统计模板',NULL),(59,1,'系统管理','0102030403','删除统计模板',NULL),(60,1,'系统管理','0102030500','设备基本信息管理','/page/devicemgmt.jsp'),(61,1,'系统管理','0102030501','新增设备',NULL),(62,1,'系统管理','0102030502','修改设备','/page/deviceedit.jsp'),(63,1,'系统管理','0102030503','删除设备',NULL),(64,1,'系统管理','0102030600','设备组管理','/page/devicegroup.jsp'),(65,1,'系统管理','0102030601','新增设备组',NULL),(66,1,'系统管理','0102030602','修改设备组',NULL),(67,1,'系统管理','0102030603','删除设备组',NULL),(68,1,'系统管理','0102030700','亮灯模板',NULL),(69,1,'系统管理','0102030800','硬件与逻辑名配置模板',NULL),(70,1,'系统管理','0102030900','运行配置模板',NULL),(71,1,'系统管理','0102031000','数据同步配置模板','page/devmgmt/databasesync_config.jsp'),(72,1,'系统管理','0102030701','新增亮灯模板',NULL),(73,1,'系统管理','0102030702','修改亮灯模板',NULL),(74,1,'系统管理','0102030703','删除亮灯模板',NULL),(75,1,'系统管理','0102030801','新增硬件与逻辑名配置',NULL),(76,1,'系统管理','0102030802','修改硬件与逻辑名配置',NULL),(77,1,'系统管理','0102030803','删除硬件与逻辑名配置',NULL),(78,1,'系统管理','0102030901','新增运行日志配置',NULL),(79,1,'系统管理','0102030902','修改运行日志配置',NULL),(80,1,'系统管理','0102030903','删除运行日志配置',NULL),(81,1,'系统管理','0102031001','新增数据同步配置',NULL),(82,1,'系统管理','0102031002','修改数据同步配置',NULL),(83,1,'系统管理','0102031003','删除数据同步配置',NULL),(84,1,'系统管理','0102031100','设备服务管理',NULL),(85,1,'系统管理','0102031101','新增设备服务列表',NULL),(86,1,'系统管理','0102031102','修改设备服务列表',NULL),(87,1,'系统管理','0102031103','删除设备服务列表',NULL),(88,1,'系统管理','0102031200','设备类型管理','/page/devmgmt/devicetype.jsp'),(89,1,'系统管理','0102031201','新增设备类型',''),(90,1,'系统管理','0102031202','修改设备类型',NULL),(91,1,'系统管理','0102031203','删除设备类型',NULL),(92,1,'系统管理','0102040100','系统日志查询与导出',NULL),(93,1,'系统管理','0102040101','系统日志查询',NULL),(94,1,'系统管理','0102040102','系统日志导出',NULL),(95,1,'系统管理','0102040200','数据库备份',NULL),(96,1,'系统管理','0102040201','mysql库备份',NULL),(97,1,'系统管理','0102040202','MongoDB库备份',NULL),(98,2,'设备端操作','00010001','acs登录',NULL),(99,2,'设备端操作','00010002','acs登出',NULL),(100,2,'设备端操作','00010101','借书',NULL),(101,2,'设备端操作','00010102','还书',NULL),(102,2,'设备端操作','00010103','续借',NULL),(103,2,'设备端操作','00010104','预借',NULL),(104,2,'设备端操作','00010201','办证有卡',NULL),(105,2,'设备端操作','00010202','办证无卡',NULL),(106,2,'设备端操作','00010301','收款',NULL),(107,2,'设备端操作','00010302','划账支出',NULL),(108,2,'设备端操作','00010303','ACS扣缴',NULL),(109,2,'设备端操作','00010304','一卡通扣缴',NULL),(110,2,'设备端操作','00030101','图书上架',NULL),(111,2,'设备端操作','00030102','图书下架',NULL),(112,2,'设备端操作','00030201','图书入箱',NULL),(113,2,'设备端操作','00030202','图书出箱',NULL),(114,2,'设备端操作','00030301','卡入箱',NULL),(115,2,'设备端操作','00030302','卡出箱',NULL),(116,2,'设备端操作','00030401','钱入箱',NULL),(117,2,'设备端操作','00030402','钱出箱',NULL),(118,1,'系统管理','0102040300','数据库还原',NULL),(119,1,'系统管理','0102040301','mysql库还原',NULL),(120,1,'系统管理','0102040302','MongoDB库备份',NULL);

/*Table structure for table `metadata_order` */

DROP TABLE IF EXISTS `metadata_order`;

CREATE TABLE `metadata_order` (
  `order_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '监控指令id',
  `order_desc` varchar(100) NOT NULL COMMENT '监控指令描述',
  `order_cmd` varchar(100) NOT NULL COMMENT '监控指令命令',
  PRIMARY KEY (`order_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='元数据表，监控指令，重启，关机等';

/*Data for the table `metadata_order` */

insert  into `metadata_order`(`order_idx`,`order_desc`,`order_cmd`) values (1,'{\"zh_CN\":\"关机\"}','shutdown -s -t 3'),(2,'{\"zh_CN\":\"重启\"}','shutdown -r -t 3'),(3,'{\"zh_CN\":\"查日志\"}',''),(4,'{\"zh_CN\":\"远程维护锁屏\"}',''),(5,'{\"zh_CN\":\"取消操作\"}','');

/*Table structure for table `metadata_protocol` */

DROP TABLE IF EXISTS `metadata_protocol`;

CREATE TABLE `metadata_protocol` (
  `protocol_field_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `protocol_show` varchar(50) NOT NULL COMMENT '指令代码',
  `protocol_field_name` varchar(50) NOT NULL COMMENT '指令名',
  `protocol_field_desc` varchar(200) NOT NULL COMMENT '指令说明',
  `protocol_field_sort` int(3) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`protocol_field_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `metadata_protocol` */

insert  into `metadata_protocol`(`protocol_field_idx`,`protocol_show`,`protocol_field_name`,`protocol_field_desc`,`protocol_field_sort`) values (1,'chargedeposit','交押金','',25),(2,'chargeimprest','交预存款','',26),(3,'checkin','还书','',10),(4,'checkout','借书','',11),(5,'checkpatronunique','查重','',12),(6,'createpatron','办证','',13),(7,'deductfinefromimprest','扣缴','',14),(8,'enablepatron','激活读者','',15),(9,'endpatronsession','结束当前读者会话','',16),(10,'sc_login','系统登录','',8),(11,'sc_logout','系统退出','',9),(12,'querybookinfo','查询图书','',17),(13,'queryfinanceaccount','查询财经','',18),(14,'querypatroninfo','查询读者信息','',7),(15,'querypatroninfo_ex','扩展查询读者信息','可以用身份证查询',19),(16,'renew','续借','',4),(17,'renewall','续借全部','不是所有的系统都支持，根据实际情况使用',5),(18,'sc_status','系统状态','',6),(19,'updatebooklocstatus','更新图书馆藏状态','',20),(20,'updatepatronpassword','更新读者密码','',21),(21,'updatepatronstatus','更新读者状态','',22),(22,'connect','连接','',1),(23,'disconnect','断开','',2),(24,'patronstatus','查询读者状态','',3),(25,'updatephoto','更新读者相片','',23),(26,'checkpatronvalid','检查读者有效','',24);

/*Table structure for table `metadata_run_config` */

DROP TABLE IF EXISTS `metadata_run_config`;

CREATE TABLE `metadata_run_config` (
  `meta_run_cfg_idx` int(11) NOT NULL AUTO_INCREMENT,
  `run_conf_type` varchar(100) NOT NULL,
  `run_conf_type_desc` varchar(100) DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`meta_run_cfg_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `metadata_run_config` */

insert  into `metadata_run_config`(`meta_run_cfg_idx`,`run_conf_type`,`run_conf_type_desc`,`updatetime`) values (1,'ACS_config','{\"zh_CN\":\"图书馆ACS服务器配置\"}',NULL),(2,'center_config','{\"zh_CN\":\"云中心服务器配置\"}',NULL),(3,'function_config','{\"zh_CN\":\"功能选项配置\"}',NULL),(4,'language_config','{\"zh_CN\":\"界面语言配置\"}',NULL),(5,'localdb_config','{\"zh_CN\":\"本地数据库配置\"}',NULL),(6,'print_config','{\"zh_CN\":\"打印凭据配置\"}',NULL),(7,'register_config','{\"zh_CN\":\"办证类型配置\"}',NULL),(8,'runlog_config','{\"zh_CN\":\"运行日志配置\"}',NULL),(9,'scheduletask_config','{\"zh_CN\":\"定时任务配置\"}',NULL),(10,'timeout_config','{\"zh_CN\":\"超时时间配置\"}',NULL);

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

/*Table structure for table `operator_group` */

DROP TABLE IF EXISTS `operator_group`;

CREATE TABLE `operator_group` (
  `operator_group_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作员组ID',
  `operator_group_id` varchar(50) NOT NULL,
  `library_idx` int(11) NOT NULL COMMENT '馆ID',
  `operator_group_name` varchar(100) NOT NULL COMMENT '操作员组名',
  `operator_group_desc` varchar(100) DEFAULT NULL COMMENT '操作组描述',
  `operator_idx` int(11) NOT NULL COMMENT '创建者IDX',
  PRIMARY KEY (`operator_group_idx`),
  UNIQUE KEY `groupid` (`operator_group_id`,`library_idx`),
  UNIQUE KEY `groupname` (`library_idx`,`operator_group_name`),
  KEY `library_lID` (`library_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='操作员组表，图书馆通过操作员组和操作员关联起来，并且简历操作员组和设备组、业务组的关联关系';

/*Data for the table `operator_group` */

/*Table structure for table `operator_maintenance_info` */

DROP TABLE IF EXISTS `operator_maintenance_info`;

CREATE TABLE `operator_maintenance_info` (
  `operator_idx` int(11) NOT NULL COMMENT '操作员IDX',
  `maintenance_idx` int(11) NOT NULL COMMENT '维护卡IDX',
  KEY `maintance_idx` (`maintenance_idx`),
  CONSTRAINT `maintance_idx` FOREIGN KEY (`maintenance_idx`) REFERENCES `reader_type` (`type_idx`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `operator_maintenance_info` */

/*Table structure for table `patch_info` */

DROP TABLE IF EXISTS `patch_info`;

CREATE TABLE `patch_info` (
  `patch_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID号',
  `patch_version` varchar(20) NOT NULL COMMENT '版本号',
  `patch_desc` varchar(200) NOT NULL COMMENT '版本说明',
  `patch_type` varchar(50) DEFAULT NULL COMMENT '版本类型 1全网 2馆约束 3设备类型约束',
  `restrict_info` varchar(100) DEFAULT NULL COMMENT '约束说明',
  `patch_directory` varchar(200) DEFAULT NULL COMMENT '远程下载路径路径',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`patch_idx`),
  UNIQUE KEY `Patch_version` (`patch_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `patch_info` */

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

/*Table structure for table `protocol_config_template` */

DROP TABLE IF EXISTS `protocol_config_template`;

CREATE TABLE `protocol_config_template` (
  `protocol_tpl_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '模板表自增主键',
  `protocol_type` int(11) NOT NULL COMMENT '1 SIP2 2 NCIP',
  `protocol_tpl_desc` varchar(50) DEFAULT NULL COMMENT '备注',
  `library_idx` int(11) NOT NULL COMMENT '图书馆IDX',
  PRIMARY KEY (`protocol_tpl_idx`),
  UNIQUE KEY `protocol_tpl_desc` (`protocol_tpl_desc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `protocol_config_template` */

/*Table structure for table `reader_type` */

DROP TABLE IF EXISTS `reader_type`;

CREATE TABLE `reader_type` (
  `type_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '流通类型IDX',
  `library_idx` int(11) NOT NULL COMMENT '馆IDX',
  `type_distinction` varchar(2) NOT NULL COMMENT '类型区分 1读者流通类型  2设备维护卡',
  `type_id` varchar(50) NOT NULL COMMENT '类型代码',
  `type_name` varchar(50) NOT NULL COMMENT '类型名',
  `type_deposit` int(11) DEFAULT '0' COMMENT '押金',
  `card_fee` int(11) DEFAULT '0' COMMENT '工本费',
  `verification_fee` int(11) DEFAULT '0' COMMENT '验证费',
  PRIMARY KEY (`type_idx`),
  UNIQUE KEY `type_id` (`library_idx`,`type_id`),
  UNIQUE KEY `type_name` (`library_idx`,`type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `reader_type` */

/*Table structure for table `rel_device_group` */

DROP TABLE IF EXISTS `rel_device_group`;

CREATE TABLE `rel_device_group` (
  `rel_device_idx` int(11) NOT NULL AUTO_INCREMENT,
  `device_group_idx` int(11) NOT NULL COMMENT '设备组ID',
  `device_idx` int(11) NOT NULL COMMENT '设备ID',
  `library_idx` int(11) NOT NULL,
  PRIMARY KEY (`rel_device_idx`),
  KEY `device_group_id` (`device_group_idx`),
  KEY `device_id` (`device_idx`),
  KEY `lib_id` (`library_idx`),
  CONSTRAINT `rel_device_group_ibfk_4` FOREIGN KEY (`device_idx`) REFERENCES `device` (`device_idx`),
  CONSTRAINT `rel_device_group_ibfk_5` FOREIGN KEY (`device_group_idx`) REFERENCES `device_group` (`device_group_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备分组关联表，设备和设备组之间是多对多的关系';

/*Data for the table `rel_device_group` */

/*Table structure for table `rel_operator_device_group` */

DROP TABLE IF EXISTS `rel_operator_device_group`;

CREATE TABLE `rel_operator_device_group` (
  `rel_oper_dev_idx` int(11) NOT NULL AUTO_INCREMENT,
  `operator_group_idx` int(11) NOT NULL COMMENT '操作员组ID',
  `device_group_idx` int(11) NOT NULL COMMENT '设备组ID',
  `library_idx` int(11) NOT NULL,
  PRIMARY KEY (`rel_oper_dev_idx`),
  KEY `operator_group_id` (`operator_group_idx`,`device_group_idx`),
  KEY `device_group_id` (`device_group_idx`),
  KEY `operator_group_id_2` (`operator_group_idx`),
  KEY `lib_id` (`library_idx`),
  CONSTRAINT `rel_operator_device_group_ibfk_1` FOREIGN KEY (`operator_group_idx`) REFERENCES `operator_group` (`operator_group_idx`),
  CONSTRAINT `rel_operator_device_group_ibfk_2` FOREIGN KEY (`device_group_idx`) REFERENCES `device_group` (`device_group_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员组和设备组关联，操作员组和设备组之间是多对多的关系';

/*Data for the table `rel_operator_device_group` */

/*Table structure for table `rel_operator_group` */

DROP TABLE IF EXISTS `rel_operator_group`;

CREATE TABLE `rel_operator_group` (
  `rel_operator_idx` int(11) NOT NULL AUTO_INCREMENT,
  `operator_group_idx` int(11) NOT NULL COMMENT '操作员组ID',
  `operator_idx` int(11) NOT NULL COMMENT '操作员ID',
  `library_idx` int(11) NOT NULL,
  PRIMARY KEY (`rel_operator_idx`),
  KEY `operator_group_id` (`operator_group_idx`),
  KEY `library_lID` (`library_idx`),
  KEY `operator_oID` (`operator_idx`),
  CONSTRAINT `rel_operator_group_ibfk_1` FOREIGN KEY (`operator_group_idx`) REFERENCES `operator_group` (`operator_group_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='操作员分组关联表，操作员和操作员组之间是多对多的关系';

/*Data for the table `rel_operator_group` */

/*Table structure for table `rel_operator_service_group` */

DROP TABLE IF EXISTS `rel_operator_service_group`;

CREATE TABLE `rel_operator_service_group` (
  `rel_oper_serv_idx` int(11) NOT NULL AUTO_INCREMENT,
  `operator_group_idx` int(11) NOT NULL COMMENT '操作员组ID',
  `service_group_idx` int(11) NOT NULL COMMENT '业务组ID',
  `library_idx` int(11) NOT NULL,
  PRIMARY KEY (`rel_oper_serv_idx`),
  KEY `operator_group_id` (`operator_group_idx`,`service_group_idx`),
  KEY `operator_group_id_2` (`operator_group_idx`),
  KEY `service_group_id` (`service_group_idx`),
  KEY `library_lID` (`library_idx`),
  CONSTRAINT `rel_operator_service_group_ibfk_3` FOREIGN KEY (`service_group_idx`) REFERENCES `service_group` (`service_group_idx`),
  CONSTRAINT `rel_operator_service_group_ibfk_4` FOREIGN KEY (`operator_group_idx`) REFERENCES `operator_group` (`operator_group_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='操作员组和业务组关联表，操作员组和业务组之间是多对多的关系';

/*Data for the table `rel_operator_service_group` */

/*Table structure for table `rel_service_group` */

DROP TABLE IF EXISTS `rel_service_group`;

CREATE TABLE `rel_service_group` (
  `rel_service_idx` int(11) NOT NULL AUTO_INCREMENT,
  `service_group_idx` int(11) NOT NULL COMMENT '业务组ID',
  `meta_opercmd_idx` int(11) NOT NULL COMMENT '业务命令',
  `library_idx` int(11) NOT NULL,
  PRIMARY KEY (`rel_service_idx`),
  KEY `service_group_id` (`service_group_idx`),
  KEY `moID` (`meta_opercmd_idx`),
  KEY `library_lID` (`library_idx`),
  CONSTRAINT `rel_service_group_ibfk_2` FOREIGN KEY (`meta_opercmd_idx`) REFERENCES `metadata_opercmd` (`meta_opercmd_idx`),
  CONSTRAINT `rel_service_group_ibfk_3` FOREIGN KEY (`service_group_idx`) REFERENCES `service_group` (`service_group_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8 COMMENT='业务分组关联表，业务和业务组之间是多对多的关系';

/*Data for the table `rel_service_group` */

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='业务组，图书馆通过业务组将操作员允许执行的业务操作分类，便于管理';

/*Data for the table `service_group` */

/*Table structure for table `table_change_tri` */

DROP TABLE IF EXISTS `table_change_tri`;

CREATE TABLE `table_change_tri` (
  `tri_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID号',
  `table_name` varchar(50) NOT NULL COMMENT '表名',
  `data_idx` int(11) NOT NULL COMMENT '数据变经的主键或设备ID',
  `data_type` varchar(5) DEFAULT NULL COMMENT '数据类型',
  `change_state` varchar(10) NOT NULL COMMENT 'I新增  U 更新   D删除',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  `requestTime` timestamp NULL DEFAULT NULL COMMENT '请求时间（离当前时间5分钟继续返回该数据）',
  PRIMARY KEY (`tri_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `table_change_tri` */

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

/* Procedure structure for procedure `P_device_config_update_set` */

/*!50003 DROP PROCEDURE IF EXISTS  `P_device_config_update_set` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`::1` PROCEDURE `P_device_config_update_set`()
BEGIN
IF NOT EXISTS(SELECT 1 FROM table_change_tri WHERE data_idx=old.device_idx AND requestTime IS NULL AND table_name='device_config' AND change_state='U') THEN
		IF EXISTS(SELECT 1 FROM device_config_old aa WHERE aa.device_idx=old.device_idx AND aa.device_ext_tpl_idx!=old.device_ext_tpl_idx) THEN
			INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_config',old.device_idx,'device_ext_tpl_idx,','U');	
		END IF;
		IF EXISTS(SELECT 1 FROM device_config_old aa WHERE aa.device_idx=old.device_idx AND aa.device_ext_tpl_flg!=old.device_ext_tpl_flg) THEN
			INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_config',old.device_idx,'device_ext_tpl_flg,','U');	
		END IF;
		IF EXISTS(SELECT 1 FROM device_config_old aa WHERE aa.device_idx=old.device_idx AND aa.device_run_tpl_idx!=old.device_run_tpl_idx) THEN
			INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_config',old.device_idx,'device_run_tpl_idx,','U');	
		END IF;
		IF EXISTS(SELECT 1 FROM device_config_old aa WHERE aa.device_idx=old.device_idx AND aa.device_run_tpl_flg!=old.device_run_tpl_flg) THEN
			INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_config',old.device_idx,'device_run_tpl_flg,','U');
		END IF;
		IF EXISTS(SELECT 1 FROM device_config_old aa WHERE aa.device_idx=old.device_idx AND aa.device_dbsync_tpl_idx!=old.device_dbsync_tpl_idx) THEN
			INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_config',old.device_idx,'device_dbsync_tpl_idx,','U');	
		END IF;
		IF EXISTS(SELECT 1 FROM device_config_old aa WHERE aa.device_idx=old.device_idx AND aa.device_dbsync_tpl_flg!=old.device_dbsync_tpl_flg) THEN
			INSERT INTO table_change_tri (table_name,data_idx,data_type,change_state) VALUES('device_config',old.device_idx,'device_dbsync_tpl_flg,','U');
		END IF;
	
	END IF;
	DELETE FROM device_config_old WHERE device_idx=old.device_idx;
    END */$$
DELIMITER ;

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
  SET count_sql=CONCAT('SELECT COUNT(device_idx) into @v_count FROM device INNER JOIN metadata_devicetype ON device.device_model_idx=metadata_devicetype.meta_devicetype_idx WHERE library_idx=', var_library_idx);
  
  
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
    
    SET ext_sql=CONCAT('SELECT * from device INNER JOIN metadata_devicetype ON device.device_model_idx=metadata_devicetype.meta_devicetype_idx WHERE library_idx =',var_library_idx);
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

/* Procedure structure for procedure `P_GET_DEVICE_MAIN_PAGE_NUMBERS_GROUPBY_LIB` */

/*!50003 DROP PROCEDURE IF EXISTS  `P_GET_DEVICE_MAIN_PAGE_NUMBERS_GROUPBY_LIB` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `P_GET_DEVICE_MAIN_PAGE_NUMBERS_GROUPBY_LIB`(IN device_name VARCHAR(50), IN device_id VARCHAR(30), IN device_type VARCHAR(30),IN in_library_idx INT, IN page_size INT)
BEGIN
  DECLARE var_library_idx INT; 
  DECLARE var_count INT; 
  DECLARE total INT DEFAULT 0; 
  DECLARE lib_pages INT; 
  DECLARE count_sql VARCHAR(500);
  DECLARE done INT DEFAULT FALSE;
  
  DECLARE cur_lib_idx CURSOR FOR
  SELECT DISTINCT library_idx FROM device ORDER BY `library_idx`;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  OPEN cur_lib_idx; 
loop_label:
LOOP
  
  FETCH cur_lib_idx INTO var_library_idx;
  IF done = TRUE THEN
    LEAVE loop_label;
  END IF;
  
  IF in_library_idx !="" AND in_library_idx IS NOT NULL THEN 
     SET var_library_idx=in_library_idx;
  END IF;
  SET count_sql=CONCAT('SELECT COUNT(device_idx) into @v_count FROM device INNER JOIN  metadata_devicetype ON device.device_model_idx=metadata_devicetype.meta_devicetype_idx WHERE library_idx=', var_library_idx);
  
  
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
    
    SET lib_pages = (@v_count DIV page_size) + 1;
  END IF;
  
  SET total = total + lib_pages;
   
   IF in_library_idx !="" AND in_library_idx IS NOT NULL THEN 
    LEAVE loop_label; 
   END IF;
END LOOP;
  SELECT total FROM DUAL; 
  CLOSE cur_lib_idx;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
