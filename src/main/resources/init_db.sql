CREATE SCHEMA IF NOT EXISTS `chat_db` DEFAULT CHARACTER SET utf8;
USE `chat_db`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `messages`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `login` varchar(255) NOT NULL,
                         `is_deleted` tinyint DEFAULT FALSE,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `messages` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `text` varchar(255) NOT NULL,
                            `send_time` datetime NOT NULL,
                            `user_id` bigint NOT NULL,
                            `is_deleted` tinyint DEFAULT FALSE,
                            PRIMARY KEY (`id`),
                            KEY `FK_user` (`user_id`),
                            CONSTRAINT `FK_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
