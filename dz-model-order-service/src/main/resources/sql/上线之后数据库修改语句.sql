/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : model_order

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-08 11:46:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mq_message`
-- ----------------------------
DROP TABLE IF EXISTS `mq_message`;
CREATE TABLE `mq_message` (
  `id` varchar(48) NOT NULL COMMENT '主键ID',
  `mq_name` varchar(48) NOT NULL COMMENT 'mq名称',
  `body` varchar(1000) NOT NULL COMMENT '信息体',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='mq信息表';

-- ----------------------------
-- Records of mq_message
-- ----------------------------

-- ----------------------------
-- Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` varchar(48) NOT NULL COMMENT '主键ID',
  `ip_address` varchar(15) NOT NULL COMMENT '客户端ip地址',
  `user_id` varchar(48) NOT NULL COMMENT '用户ID',
  `seller_id` varchar(48) NOT NULL COMMENT '商家ID',
  `discount_amount` int(11) NOT NULL DEFAULT '0' COMMENT '优惠金额',
  `total_amount` int(11) NOT NULL DEFAULT '0' COMMENT '总金额',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态(0:待付款，1:代发货，2:待收货，3:带评价，4:已完成)',
  `create_time` datetime NOT NULL COMMENT '下单时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('11239b4c-8df7-4b42-aa28-6045f720a228', '127.0.0.1', '0c612f80-380d-4fe9-960a-64c843aee938', '0c612f80-380d-4fe9-960a-64c843aee938', '1200', '198800', '0', '2018-06-08 11:20:19');
INSERT INTO `order` VALUES ('bf315717-103b-4eeb-a468-b779af672d65', '127.0.0.1', '0c612f80-380d-4fe9-960a-64c843aee938', '0c612f80-380d-4fe9-960a-64c843aee938', '100', '499900', '0', '2018-06-08 11:21:25');

-- ----------------------------
-- Table structure for `order_details`
-- ----------------------------
DROP TABLE IF EXISTS `order_details`;
CREATE TABLE `order_details` (
  `id` varchar(48) NOT NULL COMMENT '主键ID',
  `order_id` varchar(48) NOT NULL COMMENT '订单ID',
  `goods_id` varchar(48) NOT NULL COMMENT '商品ID',
  `price` int(11) NOT NULL DEFAULT '0' COMMENT '成交单价',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';

-- ----------------------------
-- Records of order_details
-- ----------------------------
INSERT INTO `order_details` VALUES ('4e4a4cb6-d3d2-4988-a26f-183cf82078d0', '11239b4c-8df7-4b42-aa28-6045f720a228', '70db8344-740d-4724-8abb-1a39fbe5ec87', '198800', '1');
INSERT INTO `order_details` VALUES ('fc5f3367-ef0b-4e7c-88aa-47f7e6520108', 'bf315717-103b-4eeb-a468-b779af672d65', 'ee13b4d1-6965-4dfe-81dd-41ac64a6dc08', '499900', '1');