# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table person (
  PERSON_TYPE               varchar(31) not null,
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
  last_update               datetime not null,
  age                       integer,
  vat_number                varchar(255),
  vies_verified             tinyint(1) default 0,
  vies_name                 varchar(255),
  vies_address              varchar(255),
  vies_request_id           varchar(255),
  vies_request_date         varchar(255),
  constraint ck_person_account_status check (account_status in (0,1)),
  constraint pk_person primary key (person_id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table person;

SET FOREIGN_KEY_CHECKS=1;

