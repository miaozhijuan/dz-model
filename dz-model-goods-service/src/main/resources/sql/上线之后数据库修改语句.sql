/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : model_goods

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-08 11:45:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` varchar(48) NOT NULL COMMENT '主键ID',
  `user_id` varchar(48) NOT NULL COMMENT '用户ID',
  `name` varchar(20) NOT NULL COMMENT '商品名称',
  `price` int(11) NOT NULL DEFAULT '0' COMMENT '价格',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `withhold_amount` int(11) NOT NULL DEFAULT '0' COMMENT '预扣数量',
  `online` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否在线(0:离线1:在线)',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态(0:审核中，1已通过，2:驳回)',
  `create_time` datetime NOT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('70db8344-740d-4724-8abb-1a39fbe5ec87', '0c612f80-380d-4fe9-960a-64c843aee938', '小米', '200000', '99', '1', '0', '1', '2018-06-08 11:17:59');
INSERT INTO `goods` VALUES ('ee13b4d1-6965-4dfe-81dd-41ac64a6dc08', '0c612f80-380d-4fe9-960a-64c843aee938', 'iphone', '500000', '19', '1', '0', '0', '2018-06-08 11:17:36');

-- ----------------------------
-- Table structure for `goods_water`
-- ----------------------------
DROP TABLE IF EXISTS `goods_water`;
CREATE TABLE `goods_water` (
  `id` varchar(48) NOT NULL COMMENT '主键ID',
  `goods_id` varchar(48) NOT NULL COMMENT '商品ID',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态(0:出库，1入库)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of goods_water
-- ----------------------------
INSERT INTO `goods_water` VALUES ('7ee1fd07-275d-4d13-a9e4-e081a4499e7c', 'ee13b4d1-6965-4dfe-81dd-41ac64a6dc08', '20', '1', '2018-06-08 11:17:36');
INSERT INTO `goods_water` VALUES ('93fe288a-12d4-4442-84f0-c17fc1299038', 'ee13b4d1-6965-4dfe-81dd-41ac64a6dc08', '1', '0', '2018-06-08 11:21:25');
INSERT INTO `goods_water` VALUES ('c3b1d19d-4eb7-4704-8eb7-2b64c34a5d56', '70db8344-740d-4724-8abb-1a39fbe5ec87', '100', '1', '2018-06-08 11:17:59');
INSERT INTO `goods_water` VALUES ('f2cd6bca-119a-4bf0-a401-874955730a77', '70db8344-740d-4724-8abb-1a39fbe5ec87', '1', '0', '2018-06-08 11:20:20');