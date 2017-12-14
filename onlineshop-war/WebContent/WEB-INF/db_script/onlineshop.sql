DROP TABLE item;
DROP TABLE customer;
DROP TABLE category;
DROP TABLE condition;

CREATE TABLE customer(
id			NUMBER(19) PRIMARY KEY,
role   		VARCHAR2(45) NOT NULL,
firstname	VARCHAR2(40) NOT NULL,
lastname	VARCHAR2(40) NOT NULL,
street		VARCHAR2(100) NOT NULL,
zip			INTEGER NOT NULL,
city		VARCHAR2(40) NOT NULL,
country		VARCHAR2(40) NOT NULL,
email		VARCHAR2(40) NOT NULL UNIQUE,
password	VARCHAR2(10) NOT NULL
			CHECK(LENGTH(password)>=6)
);
GRANT SELECT, INSERT, UPDATE, DELETE
ON customer TO onlineshop_user;

CREATE UNIQUE INDEX customer_index
ON customer(
	email,
	password
);

CREATE TABLE category (
id			NUMBER(19) PRIMARY KEY,
description	VARCHAR2(1000) NOT NULL
);
GRANT SELECT, INSERT, UPDATE, DELETE
ON category TO onlineshop_user;

CREATE TABLE condition (
id			NUMBER(19) PRIMARY KEY,
description	VARCHAR2(1000) NOT NULL
);
GRANT SELECT, INSERT, UPDATE, DELETE
ON condition TO onlineshop_user;

CREATE TABLE item (
id			NUMBER(19) PRIMARY KEY,
title		VARCHAR2(40) NOT NULL,
description	VARCHAR2(1000) NOT NULL,
price		NUMBER(12,2) NOT NULL,
foto		BLOB,
category_id	NUMBER(19) NOT NULL,
condition_id NUMBER(19) NOT NULL,
seller_id	NUMBER(19) NOT NULL,
buyer_id	NUMBER(19),
sold		TIMESTAMP(3),
seller_ratingstars  INTEGER,
seller_ratingcomment VARCHAR2(1000),
buyer_ratingstars  INTEGER,
buyer_ratingcomment VARCHAR2(1000),
CONSTRAINT fk_category
	FOREIGN KEY (category_id) REFERENCES category (id),
CONSTRAINT fk_condition
	FOREIGN KEY (condition_id) REFERENCES condition (id),
CONSTRAINT fk_seller
	FOREIGN KEY (seller_id) REFERENCES customer (id),
CONSTRAINT fk_buyer
	FOREIGN KEY (buyer_id) REFERENCES customer (id)
);
GRANT SELECT, INSERT, UPDATE, DELETE
ON item TO onlineshop_user;

DROP SEQUENCE seq_customer;
CREATE SEQUENCE seq_customer;
GRANT ALL ON seq_customer TO onlineshop_user;

DROP SEQUENCE seq_category;
CREATE SEQUENCE seq_category;
GRANT ALL ON seq_category TO onlineshop_user;

DROP SEQUENCE seq_condition;
CREATE SEQUENCE seq_condition;
GRANT ALL ON seq_condition TO onlineshop_user;

DROP SEQUENCE seq_item;
CREATE SEQUENCE seq_item;
GRANT ALL ON seq_item TO onlineshop_user;

CREATE OR REPLACE TRIGGER tri_customer
BEFORE INSERT ON customer
FOR EACH ROW
BEGIN :NEW.id := seq_customer.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER tri_category
BEFORE INSERT ON category
FOR EACH ROW
BEGIN :NEW.id := seq_category.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER tri_condition
BEFORE INSERT ON condition
FOR EACH ROW
BEGIN :NEW.id := seq_condition.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER tri_item
BEFORE INSERT ON item
FOR EACH ROW
BEGIN :NEW.id := seq_item.NEXTVAL;
END;
/

COMMIT;

INSERT into onlineshop.category (description) values ('Auto');
INSERT into onlineshop.category (description) values ('Computer');
INSERT into onlineshop.category (description) values ('Haushalt');
INSERT into onlineshop.category (description) values ('Kleider');
INSERT into onlineshop.category (description) values ('Musik');
INSERT into onlineshop.category (description) values ('Sport');

INSERT into onlineshop.condition (description) values ('Neu');
INSERT into onlineshop.condition (description) values ('Gebraucht');
INSERT into onlineshop.condition (description) values ('Defekt');

INSERT into onlineshop.CUSTOMER (role, firstname, lastname, street, zip, city, country, email, password) values ('ROLE_ADMIN', 'Admin', 'Admin', 'Adminstrasse 1', 8080, 'Zürich', 'CH', 'admin@gmx.ch', '123456');

COMMIT;

