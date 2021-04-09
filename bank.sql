/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : bank

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 30/03/2021 00:42:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `id_number` bigint(0) NOT NULL COMMENT '身份证号',
  `balance` decimal NOT NULL DEFAULT 0 COMMENT '余额',
  `credit_rate` int(0) NULL DEFAULT NULL COMMENT '信用等级',
  `loan_amount` decimal NULL DEFAULT NULL COMMENT '贷款金额',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(0) NULL DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 1, 430, 1, 800, NULL, NULL);

-- ----------------------------
-- Table structure for client_product
-- ----------------------------
DROP TABLE IF EXISTS `client_product`;
CREATE TABLE `client_product`  (
  `id` int(0) NOT NULL,
  `client_id` int(0) NOT NULL COMMENT '客户id',
  `fpd_id` int(0) NOT NULL COMMENT '产品id',
  `buy_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '购买时间',
  `principal` double(20, 0) NOT NULL COMMENT '本金',
  `interest_rate` double(20, 0) NULL DEFAULT NULL COMMENT '利率（对于定期',
  `profit` double(20, 0) NOT NULL DEFAULT 0 COMMENT '收益',
  `type` int(0) NOT NULL COMMENT '类型，0为定期，1为股票',
  `expiration_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for finance_product
-- ----------------------------
DROP TABLE IF EXISTS `finance_product`;
CREATE TABLE `finance_product`  (
  `id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `type` int(0) NOT NULL COMMENT '类型，0为定期，1为股票',
  `price` double(10, 2) NOT NULL COMMENT '价格，定期的金额或股票的每股价格',
  `interest_rate` double(255, 0) NOT NULL COMMENT '日利率，单位为 %',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for loan_record
-- ----------------------------
DROP TABLE IF EXISTS `loan_record`;
CREATE TABLE `loan_record`  (
  `id` int(0) NOT NULL,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `expiration_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到期时间',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '状态：0，未还；1，已还',
  `client_id` int(0) NOT NULL COMMENT '客户id',
  `total_account` double NOT NULL COMMENT '总金额',
  `current_account` double NOT NULL COMMENT '本期待还金额',
  `nper` int(0) NULL DEFAULT NULL COMMENT '期数',
  `fine` double NOT NULL DEFAULT 0 COMMENT '罚金：0为无罚金或已还清',
  PRIMARY KEY (`id`, `fine`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
INSERT into `loan_record` VALUES(1,'Tue Apr 06 17:44:42 CST 2021','Tue Apr 06 17:44:42 CST 2021',0,1,400,400,0,0);
INSERT into `loan_record` VALUES(2,'Tue Apr 06 17:44:42 CST 2021','Tue Apr 06 17:44:42 CST 2021',0,1,400,400,0,0);
-- ----------------------------
-- Table structure for water_log
-- ----------------------------
DROP TABLE IF EXISTS `water_log`;
CREATE TABLE `water_log`  (
  `id` int(0) NOT NULL,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` int(0) NOT NULL,
  `account_change` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '余额变动情况，e.g -100，+100',
  `type` int(0) NULL DEFAULT NULL COMMENT '业务类型：0，存款；1，偿还贷款；2，扣除罚金；3，购买理财产品',
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
