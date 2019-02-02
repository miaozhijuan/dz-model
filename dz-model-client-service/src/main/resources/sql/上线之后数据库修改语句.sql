/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : model_client

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-08 11:45:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `client_user`
-- ----------------------------
DROP TABLE IF EXISTS `client_user`;
CREATE TABLE `client_user` (
  `id` varchar(48) NOT NULL COMMENT '主键ID',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `pwd` varchar(38) NOT NULL COMMENT '密码',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态(0:弃用，1启用)',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of client_user
-- ----------------------------
INSERT INTO `client_user` VALUES ('0c612f80-380d-4fe9-960a-64c843aee938', '13435754311', '4B4D3AB1E255124EA67A9E4EA9EC93D7', '1', '2018-06-08 11:14:57');
INSERT INTO `client_user` VALUES ('2c21cd5f-0ae8-4bee-a6ce-9fd6990c5d12', '13435754312', '22C3D2B8B6E33135F9CB84AC8BD72A1C', '1', '2018-06-08 11:15:05');