use directs;

ALTER TABLE `directs`.`invoice` 
ADD COLUMN `inv_amount_pending` DECIMAL(19,3) NULL DEFAULT 0 AFTER `inv_cus_id`;
