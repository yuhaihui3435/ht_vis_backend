-- --------------------------------------------------------
-- 主机:                           123.56.24.132
-- 服务器版本:                        5.7.21 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.5.0.5261
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 ap.s_dd 结构
DROP TABLE IF EXISTS `s_dd`;
CREATE TABLE IF NOT EXISTS `s_dd` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `val` varchar(50) DEFAULT NULL COMMENT '值',
  `name` varchar(50) DEFAULT NULL COMMENT '名字',
  `opId` int(11) DEFAULT NULL COMMENT '操作员id',
  `dict` varchar(50) DEFAULT NULL COMMENT '字典名',
  `idx` int(11) DEFAULT NULL COMMENT '排序编码',
  `pId` int(11) DEFAULT NULL COMMENT '父级分类的ID',
  `lAt` datetime DEFAULT NULL,
  `cAt` datetime DEFAULT NULL COMMENT '创建日期',
  `dAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dict` (`dict`),
  KEY `pId` (`pId`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COMMENT='数据字典';

-- 数据导出被取消选择。
-- 导出  表 ap.s_log_op 结构
DROP TABLE IF EXISTS `s_log_op`;
CREATE TABLE IF NOT EXISTS `s_log_op` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `op_name` varchar(50) DEFAULT NULL COMMENT '操作名',
  `op_channel` varchar(100) DEFAULT NULL COMMENT '渠道',
  `req_method` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `req_ip` varchar(100) DEFAULT NULL COMMENT '来访ip',
  `req_at` datetime DEFAULT NULL COMMENT '创建时间',
  `req_param` text COMMENT '请求参数',
  `req_ret` text COMMENT '处理结果',
  PRIMARY KEY (`id`),
  KEY `req_at` (`req_at`),
  KEY `op_name` (`op_name`),
  KEY `op_channel` (`op_channel`),
  KEY `req_method` (`req_method`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COMMENT='操作日志记录表';

-- 数据导出被取消选择。
-- 导出  表 ap.s_param 结构
DROP TABLE IF EXISTS `s_param`;
CREATE TABLE IF NOT EXISTS `s_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `k` varchar(100) DEFAULT NULL COMMENT '键',
  `val` varchar(255) DEFAULT NULL COMMENT '值',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `opId` int(11) DEFAULT NULL,
  `lAt` datetime DEFAULT NULL,
  `cAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='系统参数表，设置系统参数信息';

-- 数据导出被取消选择。
-- 导出  表 ap.s_res 结构
DROP TABLE IF EXISTS `s_res`;
CREATE TABLE IF NOT EXISTS `s_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '资源名',
  `url` varchar(255) DEFAULT NULL COMMENT '资源url',
  `seq` int(11) DEFAULT NULL COMMENT '顺序',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `logged` char(1) DEFAULT NULL COMMENT '记录日志-1:是,0:否',
  `enabled` char(1) DEFAULT NULL COMMENT '是否可用-1:是,0:否',
  `pId` int(11) DEFAULT NULL COMMENT '父id',
  `code` varchar(50) DEFAULT NULL COMMENT '编号',
  `opId` int(11) DEFAULT NULL,
  `cAt` datetime DEFAULT NULL,
  `dAt` datetime DEFAULT NULL,
  `lAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8 COMMENT='资源';

-- 数据导出被取消选择。
-- 导出  表 ap.s_role 结构
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE IF NOT EXISTS `s_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '角色名',
  `code` varchar(50) DEFAULT NULL COMMENT '编号',
  `opId` int(11) DEFAULT NULL,
  `cAt` datetime DEFAULT NULL,
  `lAt` datetime DEFAULT NULL,
  `dAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 数据导出被取消选择。
-- 导出  表 ap.s_role_res 结构
DROP TABLE IF EXISTS `s_role_res`;
CREATE TABLE IF NOT EXISTS `s_role_res` (
  `roleCode` varchar(50) DEFAULT NULL COMMENT '角色id',
  `resCode` varchar(50) DEFAULT NULL COMMENT '资源id',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleCode`),
  KEY `resId` (`resCode`)
) ENGINE=InnoDB AUTO_INCREMENT=3371 DEFAULT CHARSET=utf8 COMMENT='角色，资源多对多关系表';

-- 数据导出被取消选择。
-- 导出  表 ap.s_role_ser 结构
DROP TABLE IF EXISTS `s_role_ser`;
CREATE TABLE IF NOT EXISTS `s_role_ser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleCode` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `serCode` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `rId` (`roleCode`),
  KEY `sId` (`serCode`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 数据导出被取消选择。
-- 导出  表 ap.s_ser 结构
DROP TABLE IF EXISTS `s_ser`;
CREATE TABLE IF NOT EXISTS `s_ser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '编号',
  `type` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '类型-0:服务',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '标题',
  `url` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT 'URL',
  `opId` int(11) DEFAULT NULL,
  `cAt` datetime DEFAULT NULL,
  `enabled` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否可用-1:是,0:否',
  `lAt` datetime DEFAULT NULL,
  `dAt` datetime DEFAULT NULL,
  `pId` int(11) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`),
  KEY `code` (`code`),
  KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务';

-- 数据导出被取消选择。
-- 导出  表 ap.s_user 结构
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE IF NOT EXISTS `s_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(50) DEFAULT NULL COMMENT '登录账号',
  `password` varchar(128) DEFAULT NULL COMMENT '登陆密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮件',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `status` varchar(1) DEFAULT NULL COMMENT '状态-0:默认,1:禁用',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐',
  `lAt` datetime DEFAULT NULL COMMENT '更新时间',
  `opId` int(11) DEFAULT NULL,
  `cAt` datetime DEFAULT NULL COMMENT '创建时间',
  `dAt` datetime DEFAULT NULL COMMENT '删除时间',
  `lastLoginTime` datetime DEFAULT NULL,
  `lastLoginIp` varchar(50) DEFAULT NULL,
  `loginTime` datetime DEFAULT NULL,
  `loginIp` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nickname` (`nickname`),
  KEY `email` (`email`),
  KEY `c_at` (`cAt`),
  KEY `phone` (`phone`),
  KEY `status` (`status`),
  KEY `d_at` (`dAt`),
  KEY `loginname` (`loginname`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- 数据导出被取消选择。
-- 导出  表 ap.s_user_role 结构
DROP TABLE IF EXISTS `s_user_role`;
CREATE TABLE IF NOT EXISTS `s_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(50) DEFAULT NULL COMMENT '用户编号',
  `roleCode` varchar(50) DEFAULT NULL COMMENT '角色编号',
  PRIMARY KEY (`id`),
  KEY `uId` (`loginname`),
  KEY `rId` (`roleCode`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='用户角色多对多关系表';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


INSERT INTO `s_user` (`id`, `loginname`, `password`, `nickname`, `phone`, `email`, `avatar`, `status`, `salt`, `lAt`, `opId`, `cAt`, `dAt`, `lastLoginTime`, `lastLoginIp`, `loginTime`, `loginIp`) VALUES (1, 'admin', '$2a$10$za9junE2mIMFu.B1M.R6We0TEvGaebGbbLOgn1O2QtVIleVCA9AaO', '小听风', '13998377271', 'xiao_tingfeng@qq.com', 'http://images.cichlid.cc/ap/img/20180622/1511611b54d14aa0ab599288b3afd7db.jpg', '0', '$2a$10$za9junE2mIMFu.B1M.R6We', '2018-06-26 11:01:39', NULL, '2018-06-13 12:17:48', NULL, '2018-06-25 14:44:57', '127.0.0.1', '2018-06-26 11:01:39', '127.0.0.1');
INSERT INTO `s_role` (`id`, `name`, `code`, `opId`, `cAt`, `lAt`, `dAt`) VALUES (1, '管理员', 'admin', NULL, '2018-06-15 13:22:04', NULL, NULL);
INSERT INTO `s_role` (`id`, `name`, `code`, `opId`, `cAt`, `lAt`, `dAt`) VALUES (2, '操作员', 'oper', NULL, '2018-06-15 13:22:14', NULL, NULL);
INSERT INTO `s_role` (`id`, `name`, `code`, `opId`, `cAt`, `lAt`, `dAt`) VALUES (3, '普通用户', 'user', NULL, '2018-06-15 13:22:26', NULL, NULL);
INSERT INTO `s_role` (`id`, `name`, `code`, `opId`, `cAt`, `lAt`, `dAt`) VALUES (4, '开发人员', 'dev', NULL, '2018-06-17 22:29:55', '2018-06-21 10:30:54', NULL);
INSERT INTO `s_res` (`id`, `name`, `url`, `seq`, `icon`, `logged`, `enabled`, `pId`, `code`, `opId`, `cAt`, `dAt`, `lAt`) VALUES (1, '系统管理', NULL, 1, NULL, '0', '1', 0, 'sysManage', NULL, '2018-06-13 21:12:39', NULL, '2018-06-21 11:55:24');
INSERT INTO `s_res` (`id`, `name`, `url`, `seq`, `icon`, `logged`, `enabled`, `pId`, `code`, `opId`, `cAt`, `dAt`, `lAt`) VALUES (2, '用户管理', '/sm/user', 1, NULL, '0', '0', 1, 'userManage', NULL, '2018-06-16 22:27:04', NULL, '2018-06-21 11:54:35');
INSERT INTO `s_res` (`id`, `name`, `url`, `seq`, `icon`, `logged`, `enabled`, `pId`, `code`, `opId`, `cAt`, `dAt`, `lAt`) VALUES (3, '角色管理', '/sm/role', 2, NULL, NULL, '0', 1, 'roleManage', NULL, '2018-06-17 15:40:50', NULL, '2018-06-21 11:54:29');
INSERT INTO `s_res` (`id`, `name`, `url`, `seq`, `icon`, `logged`, `enabled`, `pId`, `code`, `opId`, `cAt`, `dAt`, `lAt`) VALUES (4, '菜单管理', '/sm/res', 3, NULL, NULL, '0', 1, 'resManage', NULL, '2018-06-17 15:42:35', NULL, '2018-06-21 11:54:11');
INSERT INTO `s_res` (`id`, `name`, `url`, `seq`, `icon`, `logged`, `enabled`, `pId`, `code`, `opId`, `cAt`, `dAt`, `lAt`) VALUES (5, '服务管理', '/sm/ser', 4, NULL, NULL, '0', 1, 'serviceManage', NULL, '2018-06-17 15:42:50', NULL, '2018-06-21 11:54:04');
INSERT INTO `s_res` (`id`, `name`, `url`, `seq`, `icon`, `logged`, `enabled`, `pId`, `code`, `opId`, `cAt`, `dAt`, `lAt`) VALUES (6, '参数管理', '/sm/param', 6, NULL, NULL, '0', 1, 'paramManage', NULL, '2018-06-17 15:43:07', NULL, '2018-06-21 11:53:52');
INSERT INTO `s_res` (`id`, `name`, `url`, `seq`, `icon`, `logged`, `enabled`, `pId`, `code`, `opId`, `cAt`, `dAt`, `lAt`) VALUES (7, '数据字典', '/sm/dd', 5, NULL, NULL, '0', 1, 'ddManage', NULL, '2018-06-17 15:43:25', NULL, '2018-06-21 11:53:40');
INSERT INTO `s_role_res` (`roleCode`, `resCode`, `id`) VALUES ('admin', 'ddManage', 1);
INSERT INTO `s_role_res` (`roleCode`, `resCode`, `id`) VALUES ('admin', 'serviceManage', 2);
INSERT INTO `s_role_res` (`roleCode`, `resCode`, `id`) VALUES ('admin', 'paramManage', 3);
INSERT INTO `s_role_res` (`roleCode`, `resCode`, `id`) VALUES ('admin', 'resManage', 4);
INSERT INTO `s_role_res` (`roleCode`, `resCode`, `id`) VALUES ('admin', 'userManage', 5);
INSERT INTO `s_role_res` (`roleCode`, `resCode`, `id`) VALUES ('admin', 'roleManage', 6);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (1, 'userSave', '0', '新增用户', 'user/save', NULL, '2018-06-13 21:40:41', NULL, '2018-06-13 21:57:27', '2018-06-17 17:30:11', 0);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (2, 'userService', '0', '用户管理服务', 'userService', NULL, '2018-06-17 18:19:34', NULL, NULL, '2018-06-17 18:23:09', 0);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (3, 'roleService', '0', '角色管理服务', 'roleService', NULL, '2018-06-17 18:20:15', NULL, NULL, '2018-06-17 18:23:07', 0);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (4, 'resService', '0', '菜单管理服务', 'resService', NULL, '2018-06-17 18:20:42', NULL, NULL, '2018-06-17 18:23:03', 0);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (5, 'serService', '0', '服务管理服务', 'serService', NULL, '2018-06-17 18:21:10', NULL, NULL, '2018-06-17 18:23:01', 0);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (6, 'sysManageService', '0', '系统管理服务', 'sysManage', NULL, '2018-06-17 18:22:34', '1', '2018-06-21 10:55:31', NULL, 0);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (7, 'userManageService', '0', '用户管理服务', 'user', NULL, '2018-06-17 18:28:29', NULL, '2018-06-17 18:34:38', NULL, 6);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (8, 'userSaveService', '0', '用户信息保存服务', '/api/user/save', NULL, '2018-06-17 18:34:13', '0', '2018-06-21 10:56:06', NULL, 7);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (9, 'userUpdateService', '0', '用户信息更新服务', '/api/user/update', NULL, '2018-06-17 18:35:12', '0', '2018-06-21 10:56:01', NULL, 7);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (10, 'userDelService', '0', '用信息删除服务', '/api/user/del', NULL, '2018-06-17 18:35:39', '0', '2018-06-21 10:55:58', NULL, 7);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (11, 'userListService', '0', '用户列表查询服务', '/api/user/list', NULL, '2018-06-17 18:36:29', '0', '2018-06-21 10:55:52', NULL, 7);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (12, 'userPageService', '0', '用户分页查询服务', '/api/user/page', NULL, '2018-06-17 18:36:52', '0', '2018-06-21 10:55:49', NULL, 7);
INSERT INTO `s_ser` (`id`, `code`, `type`, `name`, `url`, `opId`, `cAt`, `enabled`, `lAt`, `dAt`, `pId`) VALUES (13, 'userGetService', '0', '用户详细信息查询', '/api/user/get', NULL, '2018-06-17 18:37:20', '0', '2018-06-21 10:55:46', NULL, 7);
INSERT INTO `s_user_role` (`id`, `loginname`, `roleCode`) VALUES (1, 'admin', 'admin');
INSERT INTO `s_role_ser` (`id`, `roleCode`, `serCode`) VALUES (1, 'admin', 'userDelService');
INSERT INTO `s_role_ser` (`id`, `roleCode`, `serCode`) VALUES (2, 'admin', 'userListService');
INSERT INTO `s_role_ser` (`id`, `roleCode`, `serCode`) VALUES (3, 'admin', 'userSaveService');
INSERT INTO `s_role_ser` (`id`, `roleCode`, `serCode`) VALUES (4, 'admin', 'userGetService');
INSERT INTO `s_role_ser` (`id`, `roleCode`, `serCode`) VALUES (5, 'admin', 'userPageService');
INSERT INTO `s_role_ser` (`id`, `roleCode`, `serCode`) VALUES (6, 'admin', 'userUpdateService');
INSERT INTO `s_param` (`id`, `k`, `val`, `note`, `opId`, `lAt`, `cAt`) VALUES (1, 'AK', 'XUPMPke2-CO-jdTfWOXrL20e_FU0YtwC0L6_biDF', NULL, NULL, '2018-06-24 21:44:47', NULL);
INSERT INTO `s_param` (`id`, `k`, `val`, `note`, `opId`, `lAt`, `cAt`) VALUES (2, 'SK', 'baqh-ER-8k0xs9mqp_I719KFThRRpiU_C9YnrxsV', NULL, NULL, '2018-06-24 21:44:47', NULL);
INSERT INTO `s_param` (`id`, `k`, `val`, `note`, `opId`, `lAt`, `cAt`) VALUES (3, 'qn_bucket', 'clchild', NULL, NULL, '2018-06-24 21:44:47', NULL);
INSERT INTO `s_param` (`id`, `k`, `val`, `note`, `opId`, `lAt`, `cAt`) VALUES (4, 'qn_url', 'http://images.cichlid.cc/', NULL, NULL, '2018-06-24 21:44:47', NULL);
INSERT INTO `s_param` (`id`, `k`, `val`, `note`, `opId`, `lAt`, `cAt`) VALUES (5, 'smsAppKey', '11111', NULL, NULL, '2018-06-24 21:44:46', NULL);
INSERT INTO `s_param` (`id`, `k`, `val`, `note`, `opId`, `lAt`, `cAt`) VALUES (6, 'smsAppSecret', '1111', NULL, NULL, '2018-06-24 21:44:47', NULL);
INSERT INTO `s_param` (`id`, `k`, `val`, `note`, `opId`, `lAt`, `cAt`) VALUES (7, 'userOnlineTactics', 'loginFirst', NULL, NULL, '2018-06-24 21:44:47', '2018-06-24 14:52:32');