use directs;
ALTER TABLE `company`
	ADD `cmp_country` varchar(255) DEFAULT NULL;
    
UPDATE `directs`.`company` SET `cmp_country` = 'FRANCE' WHERE (`cmp_id` = '1');