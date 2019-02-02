/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : model_admin

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-08 11:43:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(48) NOT NULL COMMENT 'ID',
  `parent_id` varchar(48) NOT NULL COMMENT '父级ID',
  `name` varchar(20) NOT NULL COMMENT '权限名称',
  `level` tinyint(2) NOT NULL DEFAULT '0' COMMENT '级别(0:主权限1：副权限)',
  `sort` tinyint(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `url` varchar(50) NOT NULL COMMENT '路径',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态(0弃用，1启用)',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(48) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(48) DEFAULT NULL COMMENT '修改ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改人时间',
  `removed` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除(0否，1是)',
  PRIMARY KEY (`id`),
  KEY `index_sys_permission` (`parent_id`,`sort`,`status`,`removed`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('0071f028-2326-4f35-823c-f628cb19a484', '2aebef19-adb3-42af-af21-16a606c6844e', '删除', '1', '2', '/authority/sysUser/delete.do', '1', '', '01', '2018-05-29 14:37:04', '01', '2018-05-29 14:37:59', '0');
INSERT INTO `sys_permission` VALUES ('023c47e1-1cee-4332-a1b1-46fb47dc2781', '0897e2ff-3c07-4932-8009-bf9615f022c6', '编辑', '1', '3', '/authority/sysRole/toUpdate.do', '1', '', '01', '2018-05-29 15:18:58', null, null, '0');
INSERT INTO `sys_permission` VALUES ('07ad6360-0ac1-4742-b039-b16023db18c6', '1bebf3f9-6560-454b-9df8-3ba7f32119e4', '编辑', '1', '3', '/authority/sysPermission/toUpdate.do', '1', '', '01', '2018-05-29 15:30:37', null, null, '0');
INSERT INTO `sys_permission` VALUES ('0897e2ff-3c07-4932-8009-bf9615f022c6', '4f769a33-fefe-47c6-9aec-86b4f9af68ff', '角色管理', '0', '1', '/authority/sysRole/manager.do', '1', null, '01', '2018-05-21 14:51:12', null, null, '0');
INSERT INTO `sys_permission` VALUES ('09a37704-a41c-46f5-bd69-1f0f34de2896', '1bebf3f9-6560-454b-9df8-3ba7f32119e4', '添加', '1', '1', '/authority/sysPermission/toAdd.do', '1', '', '01', '2018-05-29 15:27:33', null, null, '0');
INSERT INTO `sys_permission` VALUES ('14e50511-bdc8-452c-afbb-fdf55529f6ab', '44a9ff61-953a-4763-87a2-811c32b2ed9f', '保存', '1', '0', '/authority/main/updatePwdSave.do', '1', '', '01', '2018-05-29 17:12:01', null, null, '0');
INSERT INTO `sys_permission` VALUES ('173125e2-83a0-4028-9b80-9c35632d1b2c', '0897e2ff-3c07-4932-8009-bf9615f022c6', '添加', '1', '1', '/authority/sysRole/toAdd.do', '1', '', '01', '2018-05-29 15:16:09', null, null, '0');
INSERT INTO `sys_permission` VALUES ('1a787821-d966-408e-a1dd-746d4fd8c48b', '1bebf3f9-6560-454b-9df8-3ba7f32119e4', '列表', '1', '0', '/authority/sysPermission/list.do', '1', '', '01', '2018-05-29 15:28:59', null, null, '0');
INSERT INTO `sys_permission` VALUES ('1bebf3f9-6560-454b-9df8-3ba7f32119e4', '4f769a33-fefe-47c6-9aec-86b4f9af68ff', '权限管理', '0', '2', '/authority/sysPermission/manager.do', '1', null, '01', '2018-05-21 14:51:59', null, null, '0');
INSERT INTO `sys_permission` VALUES ('253b7ba5-a0b7-49d5-83ee-b41f341c467c', 'bf7c4d31-6ebf-412d-bcd8-4215d2b2f9b5', '审核', '1', '1', '/logic/goods/check.do', '1', '', '01', '2018-06-06 21:42:47', null, null, '0');
INSERT INTO `sys_permission` VALUES ('2aebef19-adb3-42af-af21-16a606c6844e', '4f769a33-fefe-47c6-9aec-86b4f9af68ff', '用户管理', '0', '0', '/authority/sysUser/manager.do', '1', null, '01', '2018-05-21 14:50:13', null, null, '0');
INSERT INTO `sys_permission` VALUES ('3743c429-45a0-4a94-b6d8-efea8fd87bc6', '173125e2-83a0-4028-9b80-9c35632d1b2c', '保存', '1', '0', '/authority/sysRole/add.do', '1', '', '01', '2018-05-29 15:18:12', null, null, '0');
INSERT INTO `sys_permission` VALUES ('3ab05c4c-a6e1-4cc8-ae80-4774809c6840', '0', '首页', '1', '0', '/authority/main/index.do', '1', '', '01', '2018-05-29 17:10:02', '01', '2018-06-08 11:35:56', '0');
INSERT INTO `sys_permission` VALUES ('40dad7f9-32ff-47b1-93a5-da474b79a46f', '0897e2ff-3c07-4932-8009-bf9615f022c6', '分配权限', '1', '3', '/authority/sysPermission/allotPermission.do', '1', '', '01', '2018-05-29 15:19:52', null, null, '0');
INSERT INTO `sys_permission` VALUES ('44a9ff61-953a-4763-87a2-811c32b2ed9f', '3ab05c4c-a6e1-4cc8-ae80-4774809c6840', '修改密码', '1', '0', '/authority/main/updatePwd.do', '1', '', '01', '2018-05-29 17:11:32', null, null, '0');
INSERT INTO `sys_permission` VALUES ('4f769a33-fefe-47c6-9aec-86b4f9af68ff', '0', '系统管理', '0', '10', '#', '1', '', '01', '2018-05-21 14:48:48', '01', '2018-05-29 11:41:56', '0');
INSERT INTO `sys_permission` VALUES ('4fd8b092-fc14-4401-9a3b-63bfac6ee173', '2aebef19-adb3-42af-af21-16a606c6844e', '列表', '1', '0', '/authority/sysUser/list.do', '1', '', '01', '2018-05-29 14:32:53', '01', '2018-05-29 14:37:44', '0');
INSERT INTO `sys_permission` VALUES ('5976b6e8-7f79-4fec-9d45-fb3be15463ad', '2aebef19-adb3-42af-af21-16a606c6844e', '添加', '1', '1', '/authority/sysUser/toAdd.do', '1', '', '01', '2018-05-29 14:35:41', '01', '2018-05-29 15:14:42', '0');
INSERT INTO `sys_permission` VALUES ('635f3a24-738b-4b65-a23a-88ef739eed71', 'fb9b9844-7396-4af8-a7d8-f060ba1dda84', '客户信息', '0', '0', '/logic/clientUser/manager.do', '1', '', '01', '2018-06-05 15:31:23', null, null, '0');
INSERT INTO `sys_permission` VALUES ('6a4c3846-e1de-47f8-9a2a-ff7eb0899060', '82c05308-1d0a-4131-8c7c-0ba66f2ce49b', '保存', '1', '0', '/authority/sysRole/saveRole.do', '1', '', '01', '2018-05-29 15:03:43', '01', '2018-05-29 15:04:16', '0');
INSERT INTO `sys_permission` VALUES ('7cefbcff-a562-4225-a22d-e258e06ac0e3', '023c47e1-1cee-4332-a1b1-46fb47dc2781', '保存', '1', '0', '/authority/sysRole/update.do', '1', '', '01', '2018-05-29 15:20:27', null, null, '0');
INSERT INTO `sys_permission` VALUES ('82c05308-1d0a-4131-8c7c-0ba66f2ce49b', 'dbd288de-0df5-49dc-93f0-68ad4cad535c', '树形', '1', '0', '/authority/sysRole/allotRoleTree.do', '1', '', '01', '2018-05-29 15:03:11', null, null, '0');
INSERT INTO `sys_permission` VALUES ('98ad6647-6013-4492-9c37-fb0960f9cec1', '1bebf3f9-6560-454b-9df8-3ba7f32119e4', '删除', '1', '2', '/authority/sysPermission/delete.do', '1', '', '01', '2018-05-29 15:28:24', null, null, '0');
INSERT INTO `sys_permission` VALUES ('a9881455-41ed-4703-88f6-9fd6055eae3d', '5976b6e8-7f79-4fec-9d45-fb3be15463ad', '保存', '1', '0', '/authority/sysUser/add.do', '1', '', '01', '2018-05-29 15:00:03', null, null, '0');
INSERT INTO `sys_permission` VALUES ('a998c8c2-9e33-4abc-be50-3d636faa1fb0', 'bf7c4d31-6ebf-412d-bcd8-4215d2b2f9b5', '列表', '1', '0', '/logic/goods/list.do', '1', '', '01', '2018-06-06 20:58:46', null, null, '0');
INSERT INTO `sys_permission` VALUES ('b3de2c48-233e-4ac7-b03c-85fca50a5204', 'c2a93f0c-1742-4712-8e5e-3ee7fbdad3b7', '保存', '1', '0', '/authority/sysUser/update.do', '1', '', '01', '2018-05-29 15:02:22', null, null, '0');
INSERT INTO `sys_permission` VALUES ('b72aa585-19fd-4d45-9eb1-66929b4be867', '635f3a24-738b-4b65-a23a-88ef739eed71', '审核', '1', '1', '/logic/clientUser/check.do', '1', '', '01', '2018-06-05 16:00:49', null, null, '0');
INSERT INTO `sys_permission` VALUES ('b9c1c2cd-fcb3-4651-a802-50a6d6aaf967', '635f3a24-738b-4b65-a23a-88ef739eed71', '列表', '1', '0', '/logic/clientUser/list.do', '1', '', '01', '2018-06-05 15:38:32', null, null, '0');
INSERT INTO `sys_permission` VALUES ('bf015f98-cdb3-45cf-b758-2d69817f0edd', '3ab05c4c-a6e1-4cc8-ae80-4774809c6840', '树形菜单', '1', '1', '/authority/main/tree.do', '1', '', '01', '2018-05-29 17:10:51', null, null, '0');
INSERT INTO `sys_permission` VALUES ('bf7c4d31-6ebf-412d-bcd8-4215d2b2f9b5', 'c880bcbc-0442-4884-bd98-3942aef33bc3', '商品信息', '0', '0', '/logic/goods/manager.do', '1', '', '01', '2018-06-06 20:58:09', null, null, '0');
INSERT INTO `sys_permission` VALUES ('c2a93f0c-1742-4712-8e5e-3ee7fbdad3b7', '2aebef19-adb3-42af-af21-16a606c6844e', '编辑', '1', '3', '/authority/sysUser/toUpdate.do', '1', '', '01', '2018-05-29 15:00:52', null, null, '0');
INSERT INTO `sys_permission` VALUES ('c5f8671e-4bdd-423d-9c4f-58c8be638f97', '1bebf3f9-6560-454b-9df8-3ba7f32119e4', '树形', '1', '4', '/authority/sysPermission/sysPermissionTree.do', '1', '', '01', '2018-05-29 15:31:46', null, null, '0');
INSERT INTO `sys_permission` VALUES ('c880bcbc-0442-4884-bd98-3942aef33bc3', '0', '商品管理', '0', '2', '#', '1', '', '01', '2018-06-06 20:57:05', '01', '2018-06-06 20:57:15', '0');
INSERT INTO `sys_permission` VALUES ('c8f6e779-0db5-4d17-8367-b0522f81f9e6', 'd6efd7a1-6a2a-4b91-adb3-9a5728ebe51b', '保存', '1', '0', '/authority/sysPermission/savePermission.do', '1', '', '01', '2018-05-29 15:22:17', null, null, '0');
INSERT INTO `sys_permission` VALUES ('cacb9136-771f-40d4-afdd-35ae3c0ef7f1', '0', '订单管理', '0', '3', '#', '1', '', '01', '2018-06-07 12:45:17', null, null, '0');
INSERT INTO `sys_permission` VALUES ('cdf10886-c135-49ae-993d-693bdd273baa', '0897e2ff-3c07-4932-8009-bf9615f022c6', '列表', '1', '0', '/authority/sysRole/list.do', '1', '', '01', '2018-05-29 15:17:25', null, null, '0');
INSERT INTO `sys_permission` VALUES ('d1f58cbd-c790-44a7-b19b-389d48b0a77d', '0897e2ff-3c07-4932-8009-bf9615f022c6', '删除', '1', '2', '/authority/sysRole/delete.do', '1', '', '01', '2018-05-29 15:16:50', null, null, '0');
INSERT INTO `sys_permission` VALUES ('d6efd7a1-6a2a-4b91-adb3-9a5728ebe51b', '40dad7f9-32ff-47b1-93a5-da474b79a46f', '树形', '1', '0', '/authority/sysPermission/allotPermissionTree.do', '1', '', '01', '2018-05-29 15:21:30', null, null, '0');
INSERT INTO `sys_permission` VALUES ('dbd288de-0df5-49dc-93f0-68ad4cad535c', '2aebef19-adb3-42af-af21-16a606c6844e', '分配角色', '1', '4', '/authority/sysRole/allotRole.do', '1', '', '01', '2018-05-29 15:01:18', null, null, '0');
INSERT INTO `sys_permission` VALUES ('edc8237a-28ff-48c1-9573-449dfbd83b2e', '09a37704-a41c-46f5-bd69-1f0f34de2896', '保存', '1', '0', '/authority/sysPermission/add.do', '1', '', '01', '2018-05-29 15:29:27', null, null, '0');
INSERT INTO `sys_permission` VALUES ('ef036b7e-fe59-4fdc-8f7d-3214e487c706', 'f93fa7a8-6915-4f27-bc4d-0710ff44bd05', '列表', '1', '0', '/logic/order/list.do', '1', '', '01', '2018-06-07 12:46:17', null, null, '0');
INSERT INTO `sys_permission` VALUES ('f88273b4-e576-4d60-bee1-9577a8fe12cb', '07ad6360-0ac1-4742-b039-b16023db18c6', '保存', '1', '0', '/authority/sysPermission/update.do', '1', '', '01', '2018-05-29 15:31:08', null, null, '0');
INSERT INTO `sys_permission` VALUES ('f93fa7a8-6915-4f27-bc4d-0710ff44bd05', 'cacb9136-771f-40d4-afdd-35ae3c0ef7f1', '订单信息', '0', '0', '/logic/order/manager.do', '1', '', '01', '2018-06-07 12:45:48', null, null, '0');
INSERT INTO `sys_permission` VALUES ('fb9b9844-7396-4af8-a7d8-f060ba1dda84', '0', '客户管理', '0', '1', '#', '1', '', '01', '2018-06-05 15:21:40', null, null, '0');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(48) NOT NULL COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '角色名称',
  `create_user` varchar(48) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(48) DEFAULT NULL COMMENT '修改ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改人时间',
  `removed` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除(0否，1是)',
  PRIMARY KEY (`id`),
  KEY `index_role` (`removed`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '超级管理员', '01', '2018-05-23 13:23:15', '01', '2018-06-08 11:36:04', '0');
INSERT INTO `sys_role` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '管理员', '01', '2018-05-23 13:23:26', '01', '2018-06-04 20:40:36', '0');

-- ----------------------------
-- Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` varchar(48) NOT NULL COMMENT '角色ID',
  `permission_id` varchar(48) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与权限关联表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '0071f028-2326-4f35-823c-f628cb19a484');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '023c47e1-1cee-4332-a1b1-46fb47dc2781');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '07ad6360-0ac1-4742-b039-b16023db18c6');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '0897e2ff-3c07-4932-8009-bf9615f022c6');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '09a37704-a41c-46f5-bd69-1f0f34de2896');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '14e50511-bdc8-452c-afbb-fdf55529f6ab');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '173125e2-83a0-4028-9b80-9c35632d1b2c');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '1a787821-d966-408e-a1dd-746d4fd8c48b');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '1bebf3f9-6560-454b-9df8-3ba7f32119e4');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '253b7ba5-a0b7-49d5-83ee-b41f341c467c');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '2aebef19-adb3-42af-af21-16a606c6844e');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '3743c429-45a0-4a94-b6d8-efea8fd87bc6');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '3ab05c4c-a6e1-4cc8-ae80-4774809c6840');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '40dad7f9-32ff-47b1-93a5-da474b79a46f');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '44a9ff61-953a-4763-87a2-811c32b2ed9f');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '4f769a33-fefe-47c6-9aec-86b4f9af68ff');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '4fd8b092-fc14-4401-9a3b-63bfac6ee173');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '5976b6e8-7f79-4fec-9d45-fb3be15463ad');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '635f3a24-738b-4b65-a23a-88ef739eed71');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '6a4c3846-e1de-47f8-9a2a-ff7eb0899060');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '7cefbcff-a562-4225-a22d-e258e06ac0e3');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '82c05308-1d0a-4131-8c7c-0ba66f2ce49b');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', '98ad6647-6013-4492-9c37-fb0960f9cec1');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'a9881455-41ed-4703-88f6-9fd6055eae3d');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'a998c8c2-9e33-4abc-be50-3d636faa1fb0');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'b3de2c48-233e-4ac7-b03c-85fca50a5204');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'b72aa585-19fd-4d45-9eb1-66929b4be867');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'b9c1c2cd-fcb3-4651-a802-50a6d6aaf967');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'bf015f98-cdb3-45cf-b758-2d69817f0edd');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'bf7c4d31-6ebf-412d-bcd8-4215d2b2f9b5');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'c2a93f0c-1742-4712-8e5e-3ee7fbdad3b7');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'c5f8671e-4bdd-423d-9c4f-58c8be638f97');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'c880bcbc-0442-4884-bd98-3942aef33bc3');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'c8f6e779-0db5-4d17-8367-b0522f81f9e6');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'cacb9136-771f-40d4-afdd-35ae3c0ef7f1');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'cdf10886-c135-49ae-993d-693bdd273baa');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'd1f58cbd-c790-44a7-b19b-389d48b0a77d');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'd6efd7a1-6a2a-4b91-adb3-9a5728ebe51b');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'dbd288de-0df5-49dc-93f0-68ad4cad535c');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'edc8237a-28ff-48c1-9573-449dfbd83b2e');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'ef036b7e-fe59-4fdc-8f7d-3214e487c706');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'f88273b4-e576-4d60-bee1-9577a8fe12cb');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'f93fa7a8-6915-4f27-bc4d-0710ff44bd05');
INSERT INTO `sys_role_permission` VALUES ('5468ab71-d798-4d59-8a89-9d6b630ba731', 'fb9b9844-7396-4af8-a7d8-f060ba1dda84');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '0071f028-2326-4f35-823c-f628cb19a484');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '023c47e1-1cee-4332-a1b1-46fb47dc2781');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '07ad6360-0ac1-4742-b039-b16023db18c6');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '0897e2ff-3c07-4932-8009-bf9615f022c6');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '09a37704-a41c-46f5-bd69-1f0f34de2896');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '14e50511-bdc8-452c-afbb-fdf55529f6ab');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '173125e2-83a0-4028-9b80-9c35632d1b2c');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '1a787821-d966-408e-a1dd-746d4fd8c48b');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '1bebf3f9-6560-454b-9df8-3ba7f32119e4');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '253b7ba5-a0b7-49d5-83ee-b41f341c467c');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '2aebef19-adb3-42af-af21-16a606c6844e');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '3743c429-45a0-4a94-b6d8-efea8fd87bc6');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '3ab05c4c-a6e1-4cc8-ae80-4774809c6840');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '40dad7f9-32ff-47b1-93a5-da474b79a46f');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '44a9ff61-953a-4763-87a2-811c32b2ed9f');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '4f769a33-fefe-47c6-9aec-86b4f9af68ff');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '4fd8b092-fc14-4401-9a3b-63bfac6ee173');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '5976b6e8-7f79-4fec-9d45-fb3be15463ad');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '635f3a24-738b-4b65-a23a-88ef739eed71');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '6a4c3846-e1de-47f8-9a2a-ff7eb0899060');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '7cefbcff-a562-4225-a22d-e258e06ac0e3');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '82c05308-1d0a-4131-8c7c-0ba66f2ce49b');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', '98ad6647-6013-4492-9c37-fb0960f9cec1');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'a9881455-41ed-4703-88f6-9fd6055eae3d');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'a998c8c2-9e33-4abc-be50-3d636faa1fb0');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'b3de2c48-233e-4ac7-b03c-85fca50a5204');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'b72aa585-19fd-4d45-9eb1-66929b4be867');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'b9c1c2cd-fcb3-4651-a802-50a6d6aaf967');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'bf015f98-cdb3-45cf-b758-2d69817f0edd');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'bf7c4d31-6ebf-412d-bcd8-4215d2b2f9b5');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'c2a93f0c-1742-4712-8e5e-3ee7fbdad3b7');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'c5f8671e-4bdd-423d-9c4f-58c8be638f97');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'c880bcbc-0442-4884-bd98-3942aef33bc3');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'c8f6e779-0db5-4d17-8367-b0522f81f9e6');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'cacb9136-771f-40d4-afdd-35ae3c0ef7f1');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'cdf10886-c135-49ae-993d-693bdd273baa');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'd1f58cbd-c790-44a7-b19b-389d48b0a77d');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'd6efd7a1-6a2a-4b91-adb3-9a5728ebe51b');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'dbd288de-0df5-49dc-93f0-68ad4cad535c');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'edc8237a-28ff-48c1-9573-449dfbd83b2e');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'ef036b7e-fe59-4fdc-8f7d-3214e487c706');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'f88273b4-e576-4d60-bee1-9577a8fe12cb');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'f93fa7a8-6915-4f27-bc4d-0710ff44bd05');
INSERT INTO `sys_role_permission` VALUES ('63afd5cb-00f4-4ec2-a81d-2a7c7d10617a', 'fb9b9844-7396-4af8-a7d8-f060ba1dda84');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(48) NOT NULL COMMENT '主键ID',
  `real_name` varchar(20) NOT NULL COMMENT '真实姓名',
  `name` varchar(20) NOT NULL COMMENT '登陆名',
  `pwd` varchar(38) NOT NULL COMMENT '密码',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态(0:弃用，1启用)',
  `create_user` varchar(48) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(48) DEFAULT NULL COMMENT '修改ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改人时间',
  `removed` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除(0否，1是)',
  PRIMARY KEY (`id`),
  KEY `index_sys_user` (`status`,`removed`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('01', '系统管理员', 'admin', 'BA9885DCC13FC602D11A4C62B46C0129', '', '1', '01', '2018-05-18 11:39:33', '01', '2018-05-23 12:51:16', '0');
INSERT INTO `sys_user` VALUES ('5ada7e62-ec8b-4251-9d42-39fb3d21975a', '测试', 'cs', 'A5C83367D1103412877DC345D4E64F63', '', '1', '01', '2018-05-23 12:51:34', '01', '2018-06-08 10:36:21', '0');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(48) NOT NULL COMMENT '管理员用户ID',
  `role_id` varchar(48) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理用户与角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('01', '5468ab71-d798-4d59-8a89-9d6b630ba731');
INSERT INTO `sys_user_role` VALUES ('5ada7e62-ec8b-4251-9d42-39fb3d21975a', '63afd5cb-00f4-4ec2-a81d-2a7c7d10617a');