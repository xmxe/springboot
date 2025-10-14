/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50721 (5.7.21)
 Source Host           : localhost:13306
 Source Schema         : ds0

 Target Server Type    : MySQL
 Target Server Version : 50721 (5.7.21)
 File Encoding         : 65001

 Date: 14/10/2025 14:57:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_address
-- ----------------------------
DROP TABLE IF EXISTS `t_address`;
CREATE TABLE `t_address`  (
  `address_id` bigint(20) NOT NULL,
  `address_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`address_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_address
-- ----------------------------

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `order_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1537702437438140420 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (1537702437308116994, 2, 'acb2', '2');
INSERT INTO `t_order` VALUES (1537702437308116996, 4, 'acb4', '4');
INSERT INTO `t_order` VALUES (1537702437375225858, 6, 'acb6', '6');
INSERT INTO `t_order` VALUES (1537702437438140417, 8, 'acb8', '8');
INSERT INTO `t_order` VALUES (1537702437438140419, 10, 'acb10', '10');

-- ----------------------------
-- Table structure for t_order_1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1`  (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `order_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1537703429948932099 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_1
-- ----------------------------
INSERT INTO `t_order_1` VALUES (1537703429806325764, 4, 'acb4', '4');
INSERT INTO `t_order_1` VALUES (1537703429948932098, 8, 'acb8', '8');

-- ----------------------------
-- Table structure for t_order_2
-- ----------------------------
DROP TABLE IF EXISTS `t_order_2`;
CREATE TABLE `t_order_2`  (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `order_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1537703429948932100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_2
-- ----------------------------
INSERT INTO `t_order_2` VALUES (1537703426140504066, 1, 'acb1', '1');
INSERT INTO `t_order_2` VALUES (1537703429877628929, 5, 'acb5', '5');
INSERT INTO `t_order_2` VALUES (1537703429948932099, 9, 'acb9', '9');

-- ----------------------------
-- Table structure for t_order_3
-- ----------------------------
DROP TABLE IF EXISTS `t_order_3`;
CREATE TABLE `t_order_3`  (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `order_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1537703429948932101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_3
-- ----------------------------
INSERT INTO `t_order_3` VALUES (1537703429806325762, 2, 'acb2', '2');
INSERT INTO `t_order_3` VALUES (1537703429877628930, 6, 'acb6', '6');
INSERT INTO `t_order_3` VALUES (1537703429948932100, 10, 'acb10', '10');

-- ----------------------------
-- Table structure for t_order_4
-- ----------------------------
DROP TABLE IF EXISTS `t_order_4`;
CREATE TABLE `t_order_4`  (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `order_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1537703429877628932 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_4
-- ----------------------------
INSERT INTO `t_order_4` VALUES (1537703429806325763, 3, 'acb3', '3');
INSERT INTO `t_order_4` VALUES (1537703429877628931, 7, 'acb7', '7');

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`  (
  `order_item_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` bigint(20) NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '张三1');
INSERT INTO `t_user` VALUES (2, '张三2');
INSERT INTO `t_user` VALUES (3, '张三3');
INSERT INTO `t_user` VALUES (4, '张三4');
INSERT INTO `t_user` VALUES (5, '张三5');
INSERT INTO `t_user` VALUES (6, '张三6');
INSERT INTO `t_user` VALUES (7, '张三7');
INSERT INTO `t_user` VALUES (8, '张三8');
INSERT INTO `t_user` VALUES (9, '张三9');
INSERT INTO `t_user` VALUES (10, '张三10');

SET FOREIGN_KEY_CHECKS = 1;
