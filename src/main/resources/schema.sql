SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- CREATE DATABASE  IF NOT EXISTS `directs` ;
-- USE `directs`;

CREATE SCHEMA IF NOT EXISTS `directs` DEFAULT CHARACTER SET utf8 ;

USE `directs` ;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `pr_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `pr_created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `pr_change` varchar(50) DEFAULT NULL,
  `pr_description` varchar(255) DEFAULT NULL,
  `pr_name` varchar(100) DEFAULT NULL,
  `pr_price` decimal(19,3) DEFAULT NULL,
  `pr_quality` varchar(50) DEFAULT NULL,
  `pr_reference` varchar(50) DEFAULT NULL,
  `pr_tva` float DEFAULT NULL,
  `pr_uid` varchar(64) DEFAULT NULL,
  `pr_unit` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pr_id`),
  INDEX  (`pr_name`, `pr_reference`),
  UNIQUE KEY `UK_UID_PRODUCT` (`pr_uid`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

/*
LOCK TABLES `product` WRITE;

INSERT INTO `product` VALUES (1,NULL,'Tube 13 qualit� sup�rieure directs plast',NULL,50.72,'FIRST_CHOICE','R13',20,'3890cdee-d216-494c-a740-bb1e942ef742','METRE');

UNLOCK TABLES;
*/

--
-- Table structure for table `reduction`
--



DROP TABLE IF EXISTS `cities`;

CREATE TABLE `cities` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `population` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `cmp_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cmp_created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `cmp_uid` varchar(64) NOT NULL,
  `cmp_additional_address` varchar(255) DEFAULT NULL,
  `cmp_address` varchar(255) DEFAULT NULL,
  `cmp_city` varchar(255) DEFAULT NULL,
  `cmp_fax_number` varchar(30) DEFAULT NULL,
  `cmp_mail` varchar(255) DEFAULT NULL,
  `cmp_name` varchar(255) NOT NULL,
  `cmp_phone_number` varchar(30) DEFAULT NULL,
  `cmp_tva_number` varchar(50) NOT NULL,
  `cmp_zipe_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`cmp_id`),
  INDEX  (`cmp_name`),
  UNIQUE KEY `UK_UID_company` (`cmp_uid`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;

CREATE TABLE `contract` (
  `ctr_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `ctr_uid` varchar(64) DEFAULT NULL,
  `ctr_created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `ctr_name` varchar(255) DEFAULT NULL,
  `ctr_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ctr_id`)

) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;



DROP TABLE IF EXISTS `customer`;

CREATE TABLE `directs`.`customer` (
  `cus_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `cus_creation_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `cus_additional_address` varchar(255) DEFAULT NULL,
  `cus_address` varchar(255) DEFAULT NULL,
  `cus_city` varchar(255) DEFAULT NULL,
  `cus_fax_number` varchar(30) DEFAULT NULL,
  `cus_mail` varchar(255) DEFAULT NULL,
  `cus_name` varchar(255) NOT NULL,
  `cus_phone_number` varchar(30) DEFAULT NULL,
  `cus_tva_number` varchar(30) NOT NULL,
  `cus_uid` varchar(64) DEFAULT NULL,
  `cus_zipe_code` varchar(10) DEFAULT NULL,
  `cus_ctr_id` INT(10) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`cus_id`),
  UNIQUE KEY `UK_UID_CUSTOMER` (`cus_uid`),
  CONSTRAINT `fk_owners_users1`
	FOREIGN KEY(`cus_ctr_id`) REFERENCES `directs`.`contract`(`ctr_id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
CREATE INDEX `fk_owners_users1_idx` ON `directs`.`customer` (`cus_ctr_id` ASC);


DROP TABLE IF EXISTS `invoice`;

CREATE TABLE `invoice` (
  `inv_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `inv_uid` varchar(64) DEFAULT NULL,
  `inv_created_author` varchar(100) DEFAULT NULL,
  `inv_created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `inv_issue_date` datetime DEFAULT NULL,
  `inv_amount` decimal(19,3) DEFAULT NULL,
  `inv_number` varchar(50) DEFAULT NULL,
  `inv_pay_down` bit(1) DEFAULT NULL,
  `inv_cus_id` INT(10) UNSIGNED NOT NULL,
  INDEX (`inv_number`),
  PRIMARY KEY (`inv_id`),
  UNIQUE KEY `UK_UID_INVOICE` (`inv_uid`),
  CONSTRAINT `FK_CUSTOMER_INVOICE`
    FOREIGN KEY (`inv_cus_id`)
    REFERENCES `directs`.`customer` (`cus_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
CREATE INDEX `fk_invoice_customer_idx` ON `directs`.`invoice` (`inv_cus_id` ASC);


DROP TABLE IF EXISTS `bordereau`;

SHOW WARNINGS;

CREATE TABLE `bordereau` (
  `br_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `br_uid` varchar(64) DEFAULT NULL,
  `br_created_author` varchar(100) DEFAULT NULL,
  `br_created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `br_treatment_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `br_number` varchar(50) DEFAULT NULL,
  `br_subtotal` decimal(19,3) DEFAULT NULL,
  `br_type` varchar(30) DEFAULT NULL,
  `br_cus_id` INT(10) UNSIGNED NOT NULL,
  `br_inv_id` INT(10) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`br_id`),
  CONSTRAINT `fk_customer_bordereau`
    FOREIGN KEY (`br_cus_id`)
    REFERENCES `directs`.`customer` (`cus_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_bordereau`
    FOREIGN KEY (`br_inv_id`)
    REFERENCES `directs`.`invoice` (`inv_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  UNIQUE KEY `UK_KEY_BORDEREAU` (`br_uid`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
CREATE INDEX `fk_bordereau_invoice_idx` ON `directs`.`bordereau` (`br_inv_id` ASC);
CREATE INDEX `fk_bordereau_customer_idx` ON `directs`.`bordereau` (`br_cus_id` ASC);

--
-- Table structure for table `bordereau_detail`
--

DROP TABLE IF EXISTS `bordereau_detail`;

CREATE TABLE `bordereau_detail` (
  `brd_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `brd_uid` varchar(64) DEFAULT NULL,
  `brd_description` varchar(255) DEFAULT NULL,
  `brd_percentage` int(11) DEFAULT NULL,
  `brd_qte` int(11) DEFAULT NULL,
  `brd_total` decimal(19,3) DEFAULT NULL,
  `brd_br_id` INT(10) UNSIGNED NOT NULL,
  `brd_pr_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`brd_id`),
  CONSTRAINT `fk_bordereau_detail_product`
    FOREIGN KEY (`brd_pr_id`)
    REFERENCES `directs`.`product` (`pr_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
 CONSTRAINT `fk_bordereau_detail_bordereau`
    FOREIGN KEY (`brd_br_id`)
    REFERENCES `directs`.`bordereau` (`br_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  UNIQUE KEY `UK_UID_bordereau_detail` (`brd_uid`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;



--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `ct_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ct_uid` varchar(64) DEFAULT NULL,
  `ct_creation_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `ct_email` varchar(100) NOT NULL,
  `ct_first_name` varchar(255) DEFAULT NULL,
  `ct_gender` varchar(10) DEFAULT NULL,
  `ct_last_name` varchar(255) DEFAULT NULL,
  `ct_phone_number` varchar(255) DEFAULT NULL,
  `ct_cus_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`ct_id`),
  UNIQUE KEY `UK_UID_CUSTOMER` (`ct_cus_id`),
  CONSTRAINT `FK_contact_customer`
    FOREIGN KEY (`ct_cus_id`)
    REFERENCES `customer` (`cus_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  UNIQUE KEY `UK_UID_CONTACT` (`ct_uid`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

--
-- Table structure for table `invoice`
--


--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `pay_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pay_uid` varchar(64) DEFAULT NULL,
  `pay_amount` decimal(19,3) DEFAULT NULL,
  `pay_amount_pending` decimal(19,3) DEFAULT NULL,
  `pay_bank` varchar(50) DEFAULT NULL,
  `pay_created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `pay_name` varchar(100) DEFAULT NULL,
  `pay_inv_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`pay_id`),
  UNIQUE KEY `UK_UID_PAYMENT` (`pay_uid`),
  CONSTRAINT `FK_payment_invoice`
    FOREIGN KEY (`pay_inv_id`)
    REFERENCES `directs`.`invoice` (`inv_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
--
-- Table structure for table `payment_detail`
--

DROP TABLE IF EXISTS `payment_detail`;

CREATE TABLE `payment_detail` (
  `payd_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `payd_created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `payd_amount` decimal(19,3) DEFAULT NULL,
  `payd_issue_date` datetime DEFAULT NULL,
  `payd_transaction_id` varchar(255) DEFAULT NULL,
  `payd_type` varchar(50) DEFAULT NULL,
  `payd_pay_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`payd_id`),
  CONSTRAINT `FK_payment_detail_payment`
    FOREIGN KEY (`payd_pay_id`)
    REFERENCES `directs`.`payment` (`pay_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

DROP TABLE IF EXISTS `reduction`;

CREATE TABLE `reduction` (
  `red_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `red_uid` varchar(64) DEFAULT NULL,
  `red_description` varchar(255) DEFAULT NULL,
  `red_percentage` int(11) DEFAULT NULL,
  `red_ctr_id` INT(10) UNSIGNED DEFAULT NULL,
  `red_pr_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`red_id`),
  UNIQUE KEY `UK_UID_REDUCTION` (`red_uid`),
  CONSTRAINT `FK_reduction_contract`
    FOREIGN KEY (`red_ctr_id`)
    REFERENCES `directs`.`contract` (`ctr_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_reduction_product`
    FOREIGN KEY (`red_pr_id`)
    REFERENCES `directs`.`product` (`pr_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `is_done` bit(1) DEFAULT NULL,
  `target_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;


DROP TABLE IF EXISTS `directs`.`credentials` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `directs`.`credentials` (
  `crd_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `crd_created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `crd_login` VARCHAR(30) NOT NULL,
  `crd_password` VARCHAR(60) NULL,
  `crd_cipher_method` VARCHAR(15) NOT NULL DEFAULT 'SHA512',
  
  PRIMARY KEY (`crd_id`))
ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_crd_id` INT(10) UNSIGNED NOT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;