/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.21-log : Database - ssitcloud_authentication
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ssitcloud_authentication` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ssitcloud_authentication`;

/*Table structure for table `metadata_infotype` */

DROP TABLE IF EXISTS `metadata_infotype`;

CREATE TABLE `metadata_infotype` (
  `infotype_idx` int(11) NOT NULL AUTO_INCREMENT,
  `info_type` varchar(100) NOT NULL COMMENT '信息类型 1用户  2图书馆',
  `info_type_desc` varchar(100) NOT NULL COMMENT '信息类型描述，身份证，电话号码，QQ号',
  PRIMARY KEY (`infotype_idx`),
  KEY `mitID` (`infotype_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='信息描述元数据表，描述图书馆的站点信息';

/*Data for the table `metadata_infotype` */

insert  into `metadata_infotype`(`infotype_idx`,`info_type`,`info_type_desc`) values (1,'1','出生日期'),(2,'1','身份证号'),(3,'1','户照号'),(4,'1','港澳通行证号'),(5,'1','市民卡号'),(6,'1','固话'),(7,'1','手机'),(8,'1','E-mail'),(9,'1','QQ'),(10,'1','微信'),(11,'1','工作单位'),(12,'1','公司地址'),(13,'1','家庭地址'),(14,'1','网址'),(15,'2','固话'),(16,'2','地址'),(17,'2','网址'),(18,'2','服务邮箱'),(19,'2','服务QQ号'),(20,'2','服务电话'),(21,'2','微信公众号'),(22,'2','微博'),(23,'2','休馆时间');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
