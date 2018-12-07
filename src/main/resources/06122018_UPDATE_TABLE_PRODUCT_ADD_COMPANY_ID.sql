use directs;

ALTER TABLE `product`
	ADD `pr_company_id` INT(10) UNSIGNED DEFAULT NULL,
    ADD CONSTRAINT `fk_company_products`
	FOREIGN KEY(`pr_company_id`) REFERENCES `directs`.`company`(`cmp_id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION;
    
UPDATE `directs`.`product` SET `pr_company_id` = '1' WHERE (`pr_id` = '1');