
drop database if exists lottoDB;

create database lottoDB;

use lottoDB;

drop table if exists tbl_lottozahlen;

create table tbl_lottozahlen(
    
    L_ID int unsigned not null auto_increment,
    Ziehungsdatum date not null
		, constraint pk_lottozahlen primary key(L_ID, Ziehungsdatum),

    Ziehungstag enum('Mittwoch', 'Samstag') not null
        , constraint idx_lottozahlen_ziehungstag unique index(Ziehungstag),

	zahl_1 int unsigned not null,
	zahl_2 int unsigned not null,
	zahl_3 int unsigned not null,
	zahl_4 int unsigned not null,
	zahl_5 int unsigned not null,
	zahl_6 int unsigned not null,
	zusatzzahl int unsigned not null
);

drop table if exists tbl_kenozahlen;

create table tbl_kenozahlen(

    K_ID int unsigned not null auto_increment,
    Ziehungsdatum date not null
		, constraint pk_kenozahlen primary key(K_ID, Ziehungsdatum),

    Ziehungstag enum('Mittwoch', 'Samstag') not null
        , constraint idx_kenozahlen_ziehungstag unique index(Ziehungstag),

	zahl_1 int unsigned not null,
	zahl_2 int unsigned not null,
	zahl_3 int unsigned not null,
	zahl_4 int unsigned not null,
	zahl_5 int unsigned not null,
	zahl_6 int unsigned not null,
	zahl_7 int unsigned not null,
	zahl_8 int unsigned not null,
	zahl_9 int unsigned not null,
	zahl_10 int unsigned not null
);

drop table if exists tbl_eurojackpot;

create table tbl_eurojackpot(
	
	E_ID int unsigned not null auto_increment,
	Ziehungsdatum date not null
		, constraint pk_tbl_eurojackpot primary key(E_ID, Ziehungsdatum),

	zahl_1 int unsigned not null,
	zahl_2 int unsigned not null,
	zahl_3 int unsigned not null,
	zahl_4 int unsigned not null,
	zahl_5 int unsigned not null,
	eurozahl_1 int unsigned not null,
	eurozahl_2 int unsigned not null
);

