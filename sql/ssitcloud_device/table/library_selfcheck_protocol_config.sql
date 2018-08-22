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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
