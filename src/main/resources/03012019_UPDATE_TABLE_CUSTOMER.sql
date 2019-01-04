use directs;
ALTER TABLE `customer`
	ADD `cus_country` varchar(255) DEFAULT NULL;
    
UPDATE `directs`.`customer` SET `cus_country` = 'FRANCE'