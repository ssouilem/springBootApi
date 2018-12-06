use directs;


ALTER TABLE `customer`
	ADD `cus_company_id` INT(10) UNSIGNED DEFAULT NULL,
    ADD CONSTRAINT `fk_company_customers`
	FOREIGN KEY(`cus_company_id`) REFERENCES `directs`.`company`(`cmp_id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION;
    
UPDATE `directs`.`customer` SET `cus_company_id` = '1' WHERE (`cus_id` = '1');
UPDATE `directs`.`customer` SET `cus_company_id` = '1' WHERE (`cus_id` = '2');