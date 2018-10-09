 CREATE TABLE direct 
  ( 
     id          BIGINT GENERATED BY DEFAULT AS IDENTITY, 
     name 		VARCHAR(255), 
     abv     	FLOAT
  );
  
   CREATE TABLE user 
  ( 
     id          INTEGER GENERATED BY DEFAULT AS IDENTITY, 
     username 		VARCHAR(255), 
     password     	VARCHAR(255)
  );
  
   CREATE TABLE CITIES 
  ( 
     id          BIGINT GENERATED BY DEFAULT AS IDENTITY, 
     name 		VARCHAR(255), 
     population   	INTEGER
  );
  
  
CREATE TABLE task 
  ( 
     id          INTEGER GENERATED BY DEFAULT AS IDENTITY, 
     description VARCHAR(255), 
     is_done     BOOLEAN, 
     target_date TIMESTAMP, 
     PRIMARY KEY (id) 
  );   
  
   CREATE TABLE contact 
  (                   
     ct_id         		INTEGER GENERATED BY DEFAULT AS IDENTITY, 
     ct_gender    		VARCHAR(10),
     ct_first_name		VARCHAR(100) NOT NULL, 
     ct_last_name    	VARCHAR(100),
     ct_phone_number	 VARCHAR(20),
     ct_email			VARCHAR(100),
     ct_cmp_id INTEGER,
     PRIMARY KEY (ct_id),
     CONSTRAINT fk_contact_company_id FOREIGN KEY (cmp_ct_id) REFERENCES company(cmp_id)
  ); 
  
  CREATE TABLE payment 
  (                   
     pay_id         INTEGER GENERATED BY DEFAULT AS IDENTITY, 
     pay_type		VARCHAR(100) NOT NULL, 
     pay_amount     FLOAT,
     pay_bank		VARCHAR(20),
     pay_inv_id		INTEGER,
     PRIMARY KEY (pay_id),
     CONSTRAINT fk_payment_invoice_id FOREIGN KEY (pay_inv_id) REFERENCES invoice(inv_id), 
  ); 
  
  CREATE TABLE company 
  ( 
     cmp_id         INTEGER GENERATED BY DEFAULT AS IDENTITY, 
     cmp_name		VARCHAR(100) NOT NULL, 
     cmp_address    VARCHAR(150),
     cmp_city 		VARCHAR(100),
     cmp_phone_number VARCHAR(20),
     cmp_fax_number VARCHAR(20),
     cmp_tva_number VARCHAR(20) NOT NULL,
     PRIMARY KEY (cmp_id) 
  );  
  
  create table invoice
(
	inv_id         		INTEGER GENERATED BY DEFAULT AS IDENTITY,
	inv_number 			VARCHAR(20) NOT NULL,
	inv_amount			FLOAT,
	inv_created_date  	TIMESTAMP, 
	inv_Issue_date  	TIMESTAMP,
	inv_Return_date 	TIMESTAMP,
	inv_created_author	VARCHAR(20),
	inv_cmp_id 			INTEGER,
	inv_pay_down    	BIT,
	PRIMARY KEY (inv_id),
	CONSTRAINT fk_company_invoice_id FOREIGN KEY (inv_cmp_id) REFERENCES company(cmp_id),
	
	
); 
  

  create table bordereau
(
	br_id         		INTEGER GENERATED BY DEFAULT AS IDENTITY,
	br_number 			VARCHAR(20) NOT NULL,
	br_type				VARCHAR(20) NOT NULL,
	br_created_date  	TIMESTAMP,
	br_treatment_date  	TIMESTAMP,	
	br_details		 	INTEGER,
	br_created_author	VARCHAR(20),
	br_inv_id		INTEGER,
	br_cmp_id 			INTEGER,
	PRIMARY KEY (br_id),
	CONSTRAINT fk_company_invoice_id FOREIGN KEY (br_cmp_id) REFERENCES company(cmp_id),
	CONSTRAINT fk_invoice_bordereau_id FOREIGN KEY (br_inv_id) REFERENCES invoice(inv_id)
	
); 

create table CONTRACT_PRODUCT
{
	ctr_id		INTEGER,
	pr_id 		INTEGER,
};
   
  
  