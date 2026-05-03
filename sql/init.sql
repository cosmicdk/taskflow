-- ============================================
-- TaskFlow 数据库初始化脚本
-- 适用：MySQL 8.0+
-- ============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS taskflow
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE taskflow;

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`          BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
  `username`    VARCHAR(50)   NOT NULL                 COMMENT '用户名',
  `password`    VARCHAR(200)  NOT NULL                 COMMENT '密码（BCrypt加密）',
  `nickname`    VARCHAR(50)   DEFAULT NULL             COMMENT '昵称',
  `avatar`      VARCHAR(500)  DEFAULT NULL             COMMENT '头像URL',
  `email`       VARCHAR(100)  DEFAULT NULL             COMMENT '邮箱',
  `status`      TINYINT       DEFAULT 1               COMMENT '状态：1-正常 0-禁用',
  `create_time` DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`     TINYINT       DEFAULT 0               COMMENT '逻辑删除：0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- 任务分类表
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id`          BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
  `name`        VARCHAR(50)   NOT NULL                 COMMENT '分类名称',
  `color`       VARCHAR(20)   DEFAULT '#409EFF'        COMMENT '分类颜色',
  `user_id`     BIGINT        NOT NULL                 COMMENT '所属用户ID',
  `sort_order`  INT           DEFAULT 0               COMMENT '排序顺序',
  `create_time` DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`     TINYINT       DEFAULT 0               COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务分类表';

-- ----------------------------
-- 任务表
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id`          BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
  `title`       VARCHAR(200)  NOT NULL                 COMMENT '任务标题',
  `description` TEXT          DEFAULT NULL             COMMENT '任务描述',
  `priority`    VARCHAR(20)   DEFAULT 'MEDIUM'         COMMENT '优先级：LOW/MEDIUM/HIGH/URGENT',
  `status`      VARCHAR(20)   DEFAULT 'TODO'           COMMENT '状态：TODO/IN_PROGRESS/DONE',
  `category_id` BIGINT        DEFAULT NULL             COMMENT '分类ID',
  `user_id`     BIGINT        NOT NULL                 COMMENT '所属用户ID',
  `due_date`    DATE          DEFAULT NULL             COMMENT '截止日期',
  `completed_at`DATETIME      DEFAULT NULL             COMMENT '完成时间',
  `create_time` DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`     TINYINT       DEFAULT 0               COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务表';