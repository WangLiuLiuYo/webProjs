create database vbanktables;
use vbanktables;
SET SQL_SAFE_UPDATES = 0;
create table users(
                    username char(12) not null,
                    password char(12) not null default '123456',
                    email char(20),
                    phone char(11) not null,
                    cardId char(18) not null,
                    primary key(phone)
);



