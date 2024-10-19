-- ----------------------------------------------------------------------------
-- MySQL Workbench Migration
-- Migrated Schemata: bookstore
-- Source Schemata: bookstore
-- Created: Wed Sep 18 08:35:09 2024
-- Workbench Version: 8.0.36
-- ----------------------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------------------------------------------
-- Schema bookstore
-- ----------------------------------------------------------------------------
DROP SCHEMA IF EXISTS `bookstore` ;
CREATE SCHEMA IF NOT EXISTS `bookstore` ;

-- ----------------------------------------------------------------------------
-- Table bookstore.authorities
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`authorities` (
  `username` VARCHAR(50) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  UNIQUE INDEX `authorities4_idx_1` (`username` ASC, `authority` ASC),
  CONSTRAINT `authorities4_ibfk_1`
    FOREIGN KEY (`username`)
    REFERENCES `bookstore`.`users` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- ----------------------------------------------------------------------------
-- Table bookstore.book
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `url_id` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `author` VARCHAR(255) NOT NULL,
  `stars` INT NULL DEFAULT NULL,
  `price` DECIMAL(10,0) NULL DEFAULT NULL,
  `favorite` BIT(1) NULL DEFAULT NULL,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `url_id` (`url_id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 35
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table bookstore.book_tag
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`book_tag` (
  `book_id` INT NOT NULL,
  `tag_id` INT NOT NULL,
  PRIMARY KEY (`book_id`, `tag_id`),
  INDEX `FK_TAG` (`tag_id` ASC) VISIBLE,
  CONSTRAINT `FK_BOOK`
    FOREIGN KEY (`book_id`)
    REFERENCES `bookstore`.`book` (`id`),
  CONSTRAINT `FK_TAG`
    FOREIGN KEY (`tag_id`)
    REFERENCES `bookstore`.`tag` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table bookstore.tag
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table bookstore.users
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`users` (
  `username` VARCHAR(50) NOT NULL,
  `password` CHAR(68) NOT NULL,
  `enabled` TINYINT NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
SET FOREIGN_KEY_CHECKS = 1;
