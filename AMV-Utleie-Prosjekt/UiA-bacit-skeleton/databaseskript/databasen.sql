create database if not exists AMV_Velferden;
use AMV_Velferden;
create table if not EXISTS Ansatt
(
    Ansatt_ID integer UNIQUE NOT NULL auto_increment,
    Fornavn varchar(255) not null ,
    Etternavn varchar(255) not null ,
    Telefon varchar(255) not null,
    Epost varchar(255) not null,
    Passord varchar(255) not null,
    Aktiv_ansatt tinyint(1),
    Admin tinyint(1),
    Fagforening tinyint(1),
    Regler_for_utleie tinyint(1),


    CONSTRAINT Ansatt_PK PRIMARY KEY (Ansatt_ID)
);

create table Files
(
    Id          integer UNIQUE auto_increment,
    Name        varchar(255) not null,
    Content     LONGBLOB     not null,
    ContentType varchar(255) not null,
    CONSTRAINT PRIMARY KEY (Id)

);

create table if not EXISTS Bilde
(
    Bilde_ID integer not null unique auto_increment,
    Bilde_Blob LONGBLOB,
    Beskrivelse varchar (255),

    CONSTRAINT Bilde_PK PRIMARY KEY (Bilde_ID)
);

create table if not EXISTS Utstyr_Kategori
(
    Utstyr_Kategori_ID integer UNIQUE NOT NULL auto_increment,
    Kategori_Navn varchar(255) not null ,


    CONSTRAINT Utstyr_PK PRIMARY KEY (Utstyr_Kategori_ID)
);

create table if not EXISTS Utstyr_Type
(
    Utstyr_Type_ID integer UNIQUE NOT NULL auto_increment,
    Utstyr_Kategori_ID integer default 6,
    Bilde_ID integer default 4,
    Utstyr_Navn varchar(255) not null ,
    Beskrivelse varchar(255) not null,
    Antall integer (11) not null,
    Pris_Forste_Dag int (11) default 0,
    Pris integer(11) not null,
    Spesielle_Krav tinyint(1),
    Aktiv_Type tinyint(1),
    Max_Laanbare_Dager integer(11),

    constraint Utstyr_PK primary key (Utstyr_Type_ID),
    constraint Bilde_ID_FK foreign key (Bilde_ID) references Bilde (Bilde_ID),
    constraint Utstyr_Kategori_ID foreign key (Utstyr_Kategori_ID) references  Utstyr_Kategori (Utstyr_Kategori_ID)
);

create table if not EXISTS Utstyr_Enhet
(
    Utstyr_Enhet_ID     integer UNIQUE NOT NULL auto_increment,
    Utstyr_Type_ID      integer NOT NULL,
    Aktiv_Enhet         tinyint(1),


    CONSTRAINT Utstyr_Enhet_ID PRIMARY KEY (Utstyr_Enhet_ID),
    CONSTRAINT Utstyr_ID_FK foreign key (Utstyr_Type_ID) references Utstyr_Type (Utstyr_Type_ID)
);

create table if not EXISTS Reservasjon
(
    Reservasjon_ID integer unique not null auto_increment,
    Ansatt_ID integer not null ,
    Utstyr_Enhet_ID integer not null ,
    Dato_Laant date not null ,
    Dato_Levert date ,
    Betalt tinyint(1),
    Totalpris integer(11) default 0,
    Skademelding varchar(255),
    Betalingsmetode varchar(255) not null,
    Dager_Reservert integer(11) not null,


    constraint Reservasjon_PK primary key (Reservasjon_ID),
    constraint Ansatt_ID_FK foreign key (Ansatt_ID) references Ansatt (Ansatt_ID),
    constraint Utstyr_Enhet_ID_FK foreign key (Utstyr_Enhet_ID) references Utstyr_Enhet (Utstyr_Enhet_ID)
);

create or replace view Admin_v as select * from Ansatt where admin =1;

create or replace view UtstyrForRetur_V as
select Reservasjon_ID, Ansatt.Ansatt_ID, Reservasjon.Utstyr_Enhet_ID, Utstyr_Type.Utstyr_Type_ID, Utstyr_Navn, Fornavn, Etternavn, Telefon, Dato_Laant, Dager_Reservert
from Reservasjon join Ansatt on Reservasjon.Ansatt_ID = Ansatt.Ansatt_ID
join Utstyr_Enhet on Reservasjon.Utstyr_Enhet_ID = Utstyr_Enhet.Utstyr_Enhet_ID
join Utstyr_Type on Utstyr_Enhet.Utstyr_Type_ID = Utstyr_Type.Utstyr_Type_ID
where Reservasjon.Dato_Levert is null
  and DATEDIFF(CURRENT_DATE, Dato_Laant) >= 0;