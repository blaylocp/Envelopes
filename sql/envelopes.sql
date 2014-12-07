DROP DATABASE Envelopes;

CREATE DATABASE Envelopes;

USE Envelopes;


CREATE TABLE envelope
( category_id INT PRIMARY KEY AUTO_INCREMENT
, category_name VARCHAR(20) NOT NULL
, envelope_amount Decimal(10,2)
, customer_username VARCHAR(20)
);
