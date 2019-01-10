use directs;
ALTER TABLE `directs`.`invoice` 
	ADD COLUMN `inv_other_expenses` DECIMAL(19,3) NULL DEFAULT '0.000',
	ADD COLUMN 
		`inv_remarks` varchar(255) DEFAULT NULL,
	ADD COLUMN 
		`inv_sum_in_letter` bit(1) DEFAULT 0;
