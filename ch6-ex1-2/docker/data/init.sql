GRANT ALL PRIVILEGES ON *.* TO 'svc_user'@'%';

DROP DATABASE IF EXISTS `spring`;
CREATE DATABASE `spring` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

DROP TABLE IF EXISTS `spring`.`user`;
DROP TABLE IF EXISTS `spring`.`authority`;

CREATE TABLE IF NOT EXISTS `spring`.`user` (
    `id`        INT         NOT NULL AUTO_INCREMENT,
    `username`  VARCHAR(45) NOT NULL,
    `password`  TEXT        NOT NULL,
    `algorithm` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `spring`.`authority` (
    `id`        INT         NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(45) NOT NULL,
    `user`      INT         NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `spring`.`product` (
    `id`        INT         NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(45) NOT NULL,
    `price`     VARCHAR(45) NOT NULL,
    `currency`  VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT IGNORE INTO `spring`.`user` (`id`, `username`, `password`, `algorithm`) VALUES ('1', 'ycshin', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT');

INSERT IGNORE INTO `spring`.`authority` (`id`, `name`, `user`) VALUES ('1', 'READ', '1');
INSERT IGNORE INTO `spring`.`authority` (`id`, `name`, `user`) VALUES ('2', 'WRITE', '1');

INSERT IGNORE INTO `spring`.`product` (`id`, `name`, `price`, `currency`) VALUES ('1', 'Chocolate', '10', 'USD');