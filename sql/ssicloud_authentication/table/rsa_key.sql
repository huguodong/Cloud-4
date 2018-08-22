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

/*Table structure for table `rsa_key` */

DROP TABLE IF EXISTS `rsa_key`;

CREATE TABLE `rsa_key` (
  `rsa_idx` int(11) NOT NULL AUTO_INCREMENT,
  `publicKey` varchar(2000) NOT NULL COMMENT '公钥',
  `privateKey` varchar(2000) NOT NULL COMMENT '私钥',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`rsa_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `rsa_key` */

insert  into `rsa_key`(`rsa_idx`,`publicKey`,`privateKey`,`createtime`) values (1,'<RSAKeyValue><Modulus>ALIXPJ3UkxV/kCndHTj+DFKuXOlIdUMdax4SOqtIKo0gT6Gfe0abdWtu1mMyHamKw/MmHMvDHU/mzwkGNKtYJjcJr91T0jFg6fJ/Gln2ah5qty3KXqtD+SNc8a7ghWmcUeoDOSWGzh9KWaSUhG3fcZkSesPr86Bw62EvsfaUVNOR</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>','<RSAKeyValue><Modulus>ALIXPJ3UkxV/kCndHTj+DFKuXOlIdUMdax4SOqtIKo0gT6Gfe0abdWtu1mMyHamKw/MmHMvDHU/mzwkGNKtYJjcJr91T0jFg6fJ/Gln2ah5qty3KXqtD+SNc8a7ghWmcUeoDOSWGzh9KWaSUhG3fcZkSesPr86Bw62EvsfaUVNOR</Modulus><Exponent>AQAB</Exponent><P>ANgGzRDJR0rUI4zARACpOp35RZdv5d8IvPZ7ArSoCvAeVEsKWqIdJBuvjOwev7+k6smKPbSVOceLDqZfvnr0REk=</P><Q>ANMLadSWwSTCj+dnI+3uSrabs27ffdQt6AvITub5wbYZ5HWYHrG8aDsIMqtLl7bLwaJb+ZESGoyPBblQDTchBQk=</Q><DP>DqlngZwnmoyLXSIve1wA/nfMoVqW32xYZuIybNB67ZEhZ3ZscFRJ/xcLGXt8yCUJSmR3i6oVIdXuSJx28SFjAQ==</DP><DQ>APtvnaap9XLWWpxXRXczb9AfsKdnnYItL0jaXSbSaPeL4aQ4mFkpHwU1vyhV52rhVtEYwz0TVI4h16/wr83+iQ==</DQ><InverseQ>B8G9tRvIHXnNHC94zIgSZsksWNbfVeYLJNFrRrN3jZFXApKd6zraxgEwWVLFEhzdicU/F3ErBMw6GzWI6bD1FQ==</InverseQ><D>NVybEBui2r03QCP93pbYsGmIc2n/oNWOxBroM2xTO6gj1CTKNlTccQ1r9ZsNokBvCEsyM6fJ/6gD/ws5+uFVyYYJLNZJJJVywSARvY20f6RPKYpZnbL1ZBDd0BqVLPHcru1p4jz+ZY3Uw51HFNHvsfkT57TohaHY4PAuxhS2v0E=</D></RSAKeyValue>','2016-04-13 19:17:09');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
