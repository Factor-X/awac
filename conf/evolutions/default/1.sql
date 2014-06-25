# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  person_id                 bigint auto_increment not null,
  identifier                varchar(255),
  password                  varchar(255),
  lastname                  varchar(255),
  firstname                 varchar(255),
  email                     varchar(255),
  street                    varchar(255),
  postalcode                varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  account_status            integer,
  car_id                    bigint,
  age                       integer,
  vat_number                varchar(255),
  vies_verified             tinyint(1) default 0,
  vies_name                 varchar(255),
  vies_address              varchar(255),
  vies_request_id           varchar(255),
  vies_request_date         varchar(255),
  last_update               datetime not null,
  constraint ck_account_account_status check (account_status in (0,1)),
  constraint pk_account primary key (person_id))
;

create table administrator (
  person_id                 bigint auto_increment not null,
  identifier                varchar(255),
  password                  varchar(255),
  lastname                  varchar(255),
  firstname                 varchar(255),
  email                     varchar(255),
  street                    varchar(255),
  postalcode                varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  account_status            integer,
  car_id                    bigint,
  last_update               datetime not null,
  constraint ck_administrator_account_status check (account_status in (0,1)),
  constraint pk_administrator primary key (person_id))
;

create table car (
  id                        bigint auto_increment not null,
  constraint pk_car primary key (id))
;

create table person (
  person_id                 bigint auto_increment not null,
  identifier                varchar(255),
  password                  varchar(255),
  lastname                  varchar(255),
  firstname                 varchar(255),
  email                     varchar(255),
  street                    varchar(255),
  postalcode                varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  account_status            integer,
  car_id                    bigint,
  last_update               datetime not null,
  constraint ck_person_account_status check (account_status in (0,1)),
  constraint pk_person primary key (person_id))
;

alter table account add constraint fk_account_car_1 foreign key (car_id) references car (id) on delete restrict on update restrict;
create index ix_account_car_1 on account (car_id);
alter table administrator add constraint fk_administrator_car_2 foreign key (car_id) references car (id) on delete restrict on update restrict;
create index ix_administrator_car_2 on administrator (car_id);
alter table person add constraint fk_person_car_3 foreign key (car_id) references car (id) on delete restrict on update restrict;
create index ix_person_car_3 on person (car_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table account;

drop table administrator;

drop table car;

drop table person;

SET FOREIGN_KEY_CHECKS=1;

