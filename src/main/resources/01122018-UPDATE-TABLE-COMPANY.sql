use directs;

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `cmp_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
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
