SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------------------------------------------
-- Table authorities
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `authorities` (
  `username` VARCHAR(50) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  UNIQUE INDEX `authorities4_idx_1` (`username` ASC, `authority` ASC),
  CONSTRAINT `authorities4_ibfk_1`
    FOREIGN KEY (`username`)
    REFERENCES `users` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- ----------------------------------------------------------------------------
-- Table book
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `url_id` VARCHAR(255) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `author` VARCHAR(255) NOT NULL,
  `stars` INT NULL DEFAULT NULL,
  `price` DECIMAL(10,0) NULL DEFAULT NULL,
  `favorite` BIT(1) NULL DEFAULT NULL,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  `last_modified` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `url_id` (`url_id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 35
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table book_tag
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_tag` (
  `book_id` INT NOT NULL,
  `tag_id` INT NOT NULL,
  PRIMARY KEY (`book_id`, `tag_id`),
  INDEX `FK_TAG` (`tag_id` ASC) VISIBLE,
  CONSTRAINT `FK_BOOK`
    FOREIGN KEY (`book_id`)
    REFERENCES `book` (`id`),
  CONSTRAINT `FK_TAG`
    FOREIGN KEY (`tag_id`)
    REFERENCES `tag` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table tag
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table users
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `username` VARCHAR(50) NOT NULL,
  `password` CHAR(68) NOT NULL,
  `enabled` TINYINT NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
SET FOREIGN_KEY_CHECKS = 1;
