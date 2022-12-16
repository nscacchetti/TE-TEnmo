BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfers CASCADE;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfers_id CASCADE;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL UNIQUE,
	balance numeric(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT PK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id),
-- 	https://www.w3schools.com/sql/sql_unique.asp
	CONSTRAINT UC_User UNIQUE (user_id)
);

CREATE SEQUENCE seq_transfers_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE transfers (
transfer_id int NOT null DEFAULT nextval ('seq_transfers_id'),
	from_user_id int NOT null,
	to_user_id int NOT null,
	transfer_amt numeric(13,2) NOT null,
	CONSTRAINT CHK_users CHECK (to_user_id <> from_user_id),
	CONSTRAINT PK_transfers PRIMARY KEY (transfer_id),
	CONSTRAINT FK_transfers_from_user FOREIGN KEY (from_user_id) REFERENCES tenmo_user (user_id),
	CONSTRAINT FK_transfers_to_user FOREIGN KEY (to_user_id) REFERENCES tenmo_user (user_id)
);


COMMIT;
