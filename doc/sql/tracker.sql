/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.28 : Database - tracker
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tracker` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tracker`;

/*Table structure for table `callback_activate` */

DROP TABLE IF EXISTS `callback_activate`;

CREATE TABLE `callback_activate` (
  `sid` varchar(64) NOT NULL COMMENT '流水号',
  `idfa` varchar(64) DEFAULT NULL COMMENT 'iOS激活设备ID',
  `androidid_md5` varchar(64) DEFAULT NULL COMMENT 'Android下的AndroidID，原文转大写，计算MD5，再转大写',
  `androidid_sha1` varchar(64) DEFAULT NULL COMMENT 'Android_ID 的sha-1加密值，40位小写，iOS投放不传',
  `imei` varchar(64) DEFAULT NULL COMMENT 'Android下的IMEI，原文，明文',
  `view_attributed` char(1) DEFAULT NULL COMMENT 'CTA匹配传0,VTA匹配传1',
  `partner_id` varchar(64) DEFAULT NULL COMMENT '客户公司拼音或英文缩写',
  `app_id` varchar(64) DEFAULT NULL COMMENT 'InMobi系统内的appId,由InMobi提供',
  `ip` varchar(32) NOT NULL COMMENT '客户端IP',
  `ua` varchar(256) DEFAULT NULL COMMENT 'UserAgent',
  `event_name` varchar(64) DEFAULT NULL COMMENT '监控的事件',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `callback_activate` */


/*Table structure for table `campaign` */

DROP TABLE IF EXISTS `campaign`;

CREATE TABLE `campaign` (
  `id` varchar(64) NOT NULL COMMENT '活动ID',
  `name` varchar(128) DEFAULT NULL COMMENT '活动名称',
  `third_code` varchar(64) DEFAULT NULL COMMENT '三方平台CODE',
  `third_name` varchar(64) DEFAULT NULL COMMENT '三方平台名称',
  `third_url` varchar(256) DEFAULT NULL COMMENT '三方平台URL',
  `app_id` varchar(64) DEFAULT NULL COMMENT '应用ID',
  `app_name` varchar(64) DEFAULT NULL COMMENT '应用名称',
  `channel` varchar(32) DEFAULT NULL COMMENT '渠道',
  `partner_id` varchar(64) DEFAULT NULL COMMENT '客户ID',
  `partner_name` varchar(64) DEFAULT NULL COMMENT '客户名称',
  `url` varchar(256) DEFAULT NULL COMMENT '活动外链',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `campaign` */

insert  into `campaign`(`id`,`name`,`third_code`,`third_name`,`third_url`,`app_id`,`app_name`,`channel`,`partner_id`,`partner_name`,`url`,`create_time`) values ('A1B2c3D','A1B2c3D推广','talkingdata','TalkingData','https://lnk0.com/A1B2c3D','KXXXL','开心消消乐','inmobi','SINA','新浪','http://47.94.5.254/A1B2c3D?action={action}&sid={sid}&idfa={idfa}&o1={o1}&subChn={subChn}','2017-08-13 20:38:02'),('FlIVhg','FlIVhg推广','talkingdata','TalkingData','https://lnk0.com/FlIVhg','WZRY','王者荣耀','inmobi','TENCENT','腾讯','http://47.94.5.254/FlIVhg?action={action}&sid={sid}&idfa={idfa}&o1={o1}&subChn={subChn}','2017-08-13 20:36:27');

/*Table structure for table `partner` */

DROP TABLE IF EXISTS `partner`;

CREATE TABLE `partner` (
  `id` varchar(64) NOT NULL COMMENT '公司拼音或英文缩写',
  `name` varchar(64) DEFAULT NULL COMMENT '公司名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `partner` */

insert  into `partner`(`id`,`name`,`create_time`) values ('IBM','IBM','2017-08-13 20:33:09'),('SINA','新浪','2017-08-13 20:32:37'),('SOHU','搜狐','2017-08-13 20:32:48'),('TENCENT','腾讯','2017-08-13 20:32:18');

/*Table structure for table `tracker` */

DROP TABLE IF EXISTS `tracker`;

CREATE TABLE `tracker` (
  `sid` varchar(64) NOT NULL COMMENT '流水号',
  `idfa` varchar(64) DEFAULT NULL COMMENT 'iOS设备ID，android投放不传',
  `androidid_md5` varchar(64) DEFAULT NULL COMMENT 'Android下的AndroidID，原文转大写，计算MD5，再转大写',
  `androidid_sha1` varchar(64) DEFAULT NULL COMMENT 'Android_ID 的sha-1加密值，40位小写，iOS投放不传',
  `imei` varchar(64) DEFAULT NULL COMMENT 'Android下的IMEI，原文，明文',
  `campaign_id` varchar(64) DEFAULT NULL COMMENT '活动ID',
  `channel` varchar(64) DEFAULT NULL COMMENT '渠道',
  `sub_channel` varchar(64) DEFAULT NULL COMMENT '子渠道（二级渠道）',
  `ip` varchar(32) DEFAULT NULL COMMENT '用户外网ip，X-Forward-for方法获取',
  `ua` varchar(256) DEFAULT NULL COMMENT '用户Webview UA，x-device-user-agent 获取',
  `device_type` varchar(8) DEFAULT NULL COMMENT '设备类型IOS或者ANDROID',
  `partner_id` varchar(64) DEFAULT NULL COMMENT '客户公司拼音或英文缩写',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tracker` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
