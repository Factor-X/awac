# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table air_travel_info (
  id                        bigint auto_increment not null,
  answer_id                 bigint not null,
  value1                    varchar(255),
  value2                    varchar(255),
  constraint pk_air_travel_info primary key (id))
;

create table air_travels_group_answer (
  id                        bigint auto_increment not null,
  data_owner_id             bigint not null,
  period_id                 bigint not null,
  question_id               bigint not null,
  verification_status       integer,
<<<<<<< HEAD
  unit_id                   bigint not null,
  constraint pk_air_travels_group_answer primary key (id))
;

create table boolean_answer (
  id                        bigint auto_increment not null,
  data_owner_id             bigint not null,
  period_id                 bigint not null,
  question_id               bigint not null,
  verification_status       integer,
  unit_id                   bigint not null,
  VALX                      tinyint(1) default 0,
  constraint pk_boolean_answer primary key (id))
;

create table campaign (
  id                        bigint auto_increment not null,
  period_id                 bigint not null,
  constraint pk_campaign primary key (id))
;

create table double_answer (
  id                        bigint auto_increment not null,
  data_owner_id             bigint not null,
  period_id                 bigint not null,
  question_id               bigint not null,
  verification_status       integer,
  unit_id                   bigint not null,
  VALX                      double,
  constraint pk_double_answer primary key (id))
;

create table factor (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  unit_in_id                bigint,
  unit_out_id               bigint,
  constraint pk_factor primary key (id))
;

create table factor_value (
  id                        bigint auto_increment not null,
  value                     double,
  date_in                   datetime,
  date_out                  datetime,
  factor_id                 bigint not null,
  constraint pk_factor_value primary key (id))
;

create table form (
  id                        bigint auto_increment not null,
  campaign_id               bigint not null,
  constraint pk_form primary key (id))
;

create table form_question (
  id                        bigint auto_increment not null,
  form_id                   bigint not null,
  question_id               bigint not null,
  constraint pk_form_question primary key (id))
;

create table indicator (
  id                        bigint auto_increment not null,
  constraint pk_indicator primary key (id))
;

create table organization (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_organization primary key (id))
=======
  scope_id                  bigint not null,
  unit_id                   bigint not null,
  constraint pk_air_travels_group_answer primary key (id))
;

create table boolean_answer (
  id                        bigint auto_increment not null,
  data_owner_id             bigint not null,
  period_id                 bigint not null,
  question_id               bigint not null,
  verification_status       integer,
  scope_id                  bigint not null,
  unit_id                   bigint not null,
  value                     tinyint(1) default 0,
  constraint pk_boolean_answer primary key (id))
;

create table campaign (
  id                        bigint auto_increment not null,
  period_id                 bigint not null,
  constraint pk_campaign primary key (id))
;

create table code_answer (
  id                        bigint auto_increment not null,
  data_owner_id             bigint not null,
  period_id                 bigint not null,
  question_id               bigint not null,
  verification_status       integer,
  scope_id                  bigint not null,
  unit_id                   bigint not null,
  constraint pk_code_answer primary key (id))
;

create table double_answer (
  id                        bigint auto_increment not null,
  data_owner_id             bigint not null,
  period_id                 bigint not null,
  question_id               bigint not null,
  verification_status       integer,
  scope_id                  bigint not null,
  unit_id                   bigint not null,
  value                     double,
  constraint pk_double_answer primary key (id))
;

create table factor (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  unit_in_id                bigint,
  unit_out_id               bigint,
  constraint pk_factor primary key (id))
;

create table factor_value (
  id                        bigint auto_increment not null,
  value                     double,
  date_in                   datetime,
  date_out                  datetime,
  factor_id                 bigint not null,
  constraint pk_factor_value primary key (id))
;

create table form (
  id                        bigint auto_increment not null,
  campaign_id               bigint not null,
  constraint pk_form primary key (id))
;

create table form_question (
  id                        bigint auto_increment not null,
  form_id                   bigint not null,
  question_id               bigint not null,
  constraint pk_form_question primary key (id))
;

create table indicator (
  id                        bigint auto_increment not null,
  constraint pk_indicator primary key (id))
>>>>>>> branch 'master' of https://github.com/Factor-X/awac
;

create table period (
  id                        bigint auto_increment not null,
  label                     varchar(255),
  constraint pk_period primary key (id))
;

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

<<<<<<< HEAD
create table product (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  organization_id           bigint not null,
  constraint pk_product primary key (id))
;

create table question (
  id                        bigint auto_increment not null,
  label                     varchar(255),
  unit_category             varchar(255),
  constraint pk_question primary key (id))
;

create table report (
  id                        bigint auto_increment not null,
  constraint pk_report primary key (id))
;

create table string_answer (
  id                        bigint auto_increment not null,
  data_owner_id             bigint not null,
  period_id                 bigint not null,
  question_id               bigint not null,
  verification_status       integer,
  unit_id                   bigint not null,
  VALX                      varchar(255),
  constraint pk_string_answer primary key (id))
;

create table unit (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  category_id               bigint,
  constraint pk_unit primary key (id))
;

create table unit_category (
  id                        bigint auto_increment not null,
  code                      varchar(255),
  constraint pk_unit_category primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
=======
create table question (
  id                        bigint auto_increment not null,
  label                     varchar(255),
  unit_category             varchar(255),
  constraint pk_question primary key (id))
;

create table report (
  id                        bigint auto_increment not null,
  constraint pk_report primary key (id))
;

create table scope (
  scope_type                integer(31) not null,
  id                        bigint auto_increment not null,
  name                      varchar(255),
  organization_id           bigint not null,
  constraint pk_scope primary key (id))
;

create table string_answer (
  id                        bigint auto_increment not null,
  data_owner_id             bigint not null,
  period_id                 bigint not null,
  question_id               bigint not null,
  verification_status       integer,
  scope_id                  bigint not null,
  unit_id                   bigint not null,
  value                     varchar(255),
  constraint pk_string_answer primary key (id))
;

create table unit (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_unit primary key (id))
;

create table unit_category (
  id                        bigint auto_increment not null,
  code                      varchar(255),
  constraint pk_unit_category primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  organization_id           bigint not null,
>>>>>>> branch 'master' of https://github.com/Factor-X/awac
  constraint pk_user primary key (id))
;


create table report_indicator (
  report_id                      bigint not null,
  indicator_id                   bigint not null,
  constraint pk_report_indicator primary key (report_id, indicator_id))
;
alter table air_travel_info add constraint fk_air_travel_info_answer_1 foreign key (answer_id) references air_travels_group_answer (id) on delete restrict on update restrict;
create index ix_air_travel_info_answer_1 on air_travel_info (answer_id);
alter table air_travels_group_answer add constraint fk_air_travels_group_answer_dataOwner_2 foreign key (data_owner_id) references user (id) on delete restrict on update restrict;
create index ix_air_travels_group_answer_dataOwner_2 on air_travels_group_answer (data_owner_id);
alter table air_travels_group_answer add constraint fk_air_travels_group_answer_period_3 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_air_travels_group_answer_period_3 on air_travels_group_answer (period_id);
alter table air_travels_group_answer add constraint fk_air_travels_group_answer_question_4 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_air_travels_group_answer_question_4 on air_travels_group_answer (question_id);
<<<<<<< HEAD
alter table air_travels_group_answer add constraint fk_air_travels_group_answer_unit_5 foreign key (unit_id) references unit (id) on delete restrict on update restrict;
create index ix_air_travels_group_answer_unit_5 on air_travels_group_answer (unit_id);
alter table boolean_answer add constraint fk_boolean_answer_dataOwner_6 foreign key (data_owner_id) references user (id) on delete restrict on update restrict;
create index ix_boolean_answer_dataOwner_6 on boolean_answer (data_owner_id);
alter table boolean_answer add constraint fk_boolean_answer_period_7 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_boolean_answer_period_7 on boolean_answer (period_id);
alter table boolean_answer add constraint fk_boolean_answer_question_8 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_boolean_answer_question_8 on boolean_answer (question_id);
alter table boolean_answer add constraint fk_boolean_answer_unit_9 foreign key (unit_id) references unit (id) on delete restrict on update restrict;
create index ix_boolean_answer_unit_9 on boolean_answer (unit_id);
alter table campaign add constraint fk_campaign_period_10 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_campaign_period_10 on campaign (period_id);
alter table double_answer add constraint fk_double_answer_dataOwner_11 foreign key (data_owner_id) references user (id) on delete restrict on update restrict;
create index ix_double_answer_dataOwner_11 on double_answer (data_owner_id);
alter table double_answer add constraint fk_double_answer_period_12 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_double_answer_period_12 on double_answer (period_id);
alter table double_answer add constraint fk_double_answer_question_13 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_double_answer_question_13 on double_answer (question_id);
alter table double_answer add constraint fk_double_answer_unit_14 foreign key (unit_id) references unit (id) on delete restrict on update restrict;
create index ix_double_answer_unit_14 on double_answer (unit_id);
alter table factor add constraint fk_factor_unitIn_15 foreign key (unit_in_id) references unit (id) on delete restrict on update restrict;
create index ix_factor_unitIn_15 on factor (unit_in_id);
alter table factor add constraint fk_factor_unitOut_16 foreign key (unit_out_id) references unit (id) on delete restrict on update restrict;
create index ix_factor_unitOut_16 on factor (unit_out_id);
alter table factor_value add constraint fk_factor_value_factor_17 foreign key (factor_id) references factor (id) on delete restrict on update restrict;
create index ix_factor_value_factor_17 on factor_value (factor_id);
alter table form add constraint fk_form_campaign_18 foreign key (campaign_id) references campaign (id) on delete restrict on update restrict;
create index ix_form_campaign_18 on form (campaign_id);
alter table form_question add constraint fk_form_question_form_19 foreign key (form_id) references form (id) on delete restrict on update restrict;
create index ix_form_question_form_19 on form_question (form_id);
alter table form_question add constraint fk_form_question_question_20 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_form_question_question_20 on form_question (question_id);
alter table product add constraint fk_product_organization_21 foreign key (organization_id) references organization (id) on delete restrict on update restrict;
create index ix_product_organization_21 on product (organization_id);
alter table string_answer add constraint fk_string_answer_dataOwner_22 foreign key (data_owner_id) references user (id) on delete restrict on update restrict;
create index ix_string_answer_dataOwner_22 on string_answer (data_owner_id);
alter table string_answer add constraint fk_string_answer_period_23 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_string_answer_period_23 on string_answer (period_id);
alter table string_answer add constraint fk_string_answer_question_24 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_string_answer_question_24 on string_answer (question_id);
alter table string_answer add constraint fk_string_answer_unit_25 foreign key (unit_id) references unit (id) on delete restrict on update restrict;
create index ix_string_answer_unit_25 on string_answer (unit_id);
alter table unit add constraint fk_unit_category_26 foreign key (category_id) references unit_category (id) on delete restrict on update restrict;
create index ix_unit_category_26 on unit (category_id);
=======
alter table air_travels_group_answer add constraint fk_air_travels_group_answer_scope_5 foreign key (scope_id) references scope (id) on delete restrict on update restrict;
create index ix_air_travels_group_answer_scope_5 on air_travels_group_answer (scope_id);
alter table air_travels_group_answer add constraint fk_air_travels_group_answer_unit_6 foreign key (unit_id) references unit (id) on delete restrict on update restrict;
create index ix_air_travels_group_answer_unit_6 on air_travels_group_answer (unit_id);
alter table boolean_answer add constraint fk_boolean_answer_dataOwner_7 foreign key (data_owner_id) references user (id) on delete restrict on update restrict;
create index ix_boolean_answer_dataOwner_7 on boolean_answer (data_owner_id);
alter table boolean_answer add constraint fk_boolean_answer_period_8 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_boolean_answer_period_8 on boolean_answer (period_id);
alter table boolean_answer add constraint fk_boolean_answer_question_9 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_boolean_answer_question_9 on boolean_answer (question_id);
alter table boolean_answer add constraint fk_boolean_answer_scope_10 foreign key (scope_id) references scope (id) on delete restrict on update restrict;
create index ix_boolean_answer_scope_10 on boolean_answer (scope_id);
alter table boolean_answer add constraint fk_boolean_answer_unit_11 foreign key (unit_id) references unit (id) on delete restrict on update restrict;
create index ix_boolean_answer_unit_11 on boolean_answer (unit_id);
alter table campaign add constraint fk_campaign_period_12 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_campaign_period_12 on campaign (period_id);
alter table code_answer add constraint fk_code_answer_dataOwner_13 foreign key (data_owner_id) references user (id) on delete restrict on update restrict;
create index ix_code_answer_dataOwner_13 on code_answer (data_owner_id);
alter table code_answer add constraint fk_code_answer_period_14 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_code_answer_period_14 on code_answer (period_id);
alter table code_answer add constraint fk_code_answer_question_15 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_code_answer_question_15 on code_answer (question_id);
alter table code_answer add constraint fk_code_answer_scope_16 foreign key (scope_id) references scope (id) on delete restrict on update restrict;
create index ix_code_answer_scope_16 on code_answer (scope_id);
alter table code_answer add constraint fk_code_answer_unit_17 foreign key (unit_id) references unit (id) on delete restrict on update restrict;
create index ix_code_answer_unit_17 on code_answer (unit_id);
alter table double_answer add constraint fk_double_answer_dataOwner_18 foreign key (data_owner_id) references user (id) on delete restrict on update restrict;
create index ix_double_answer_dataOwner_18 on double_answer (data_owner_id);
alter table double_answer add constraint fk_double_answer_period_19 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_double_answer_period_19 on double_answer (period_id);
alter table double_answer add constraint fk_double_answer_question_20 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_double_answer_question_20 on double_answer (question_id);
alter table double_answer add constraint fk_double_answer_scope_21 foreign key (scope_id) references scope (id) on delete restrict on update restrict;
create index ix_double_answer_scope_21 on double_answer (scope_id);
alter table double_answer add constraint fk_double_answer_unit_22 foreign key (unit_id) references unit (id) on delete restrict on update restrict;
create index ix_double_answer_unit_22 on double_answer (unit_id);
alter table factor add constraint fk_factor_unitIn_23 foreign key (unit_in_id) references unit (id) on delete restrict on update restrict;
create index ix_factor_unitIn_23 on factor (unit_in_id);
alter table factor add constraint fk_factor_unitOut_24 foreign key (unit_out_id) references unit (id) on delete restrict on update restrict;
create index ix_factor_unitOut_24 on factor (unit_out_id);
alter table factor_value add constraint fk_factor_value_factor_25 foreign key (factor_id) references factor (id) on delete restrict on update restrict;
create index ix_factor_value_factor_25 on factor_value (factor_id);
alter table form add constraint fk_form_campaign_26 foreign key (campaign_id) references campaign (id) on delete restrict on update restrict;
create index ix_form_campaign_26 on form (campaign_id);
alter table form_question add constraint fk_form_question_form_27 foreign key (form_id) references form (id) on delete restrict on update restrict;
create index ix_form_question_form_27 on form_question (form_id);
alter table form_question add constraint fk_form_question_question_28 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_form_question_question_28 on form_question (question_id);
alter table site add constraint fk_site_organization_29 foreign key (organization_id) references organization (id) on delete restrict on update restrict;
create index ix_site_organization_29 on site (organization_id);
alter table product add constraint fk_product_organization_30 foreign key (organization_id) references organization (id) on delete restrict on update restrict;
create index ix_product_organization_30 on product (organization_id);
alter table string_answer add constraint fk_string_answer_dataOwner_31 foreign key (data_owner_id) references user (id) on delete restrict on update restrict;
create index ix_string_answer_dataOwner_31 on string_answer (data_owner_id);
alter table string_answer add constraint fk_string_answer_period_32 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_string_answer_period_32 on string_answer (period_id);
alter table string_answer add constraint fk_string_answer_question_33 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_string_answer_question_33 on string_answer (question_id);
alter table string_answer add constraint fk_string_answer_scope_34 foreign key (scope_id) references scope (id) on delete restrict on update restrict;
create index ix_string_answer_scope_34 on string_answer (scope_id);
alter table string_answer add constraint fk_string_answer_unit_35 foreign key (unit_id) references unit (id) on delete restrict on update restrict;
create index ix_string_answer_unit_35 on string_answer (unit_id);
alter table user add constraint fk_user_organization_36 foreign key (organization_id) references organization (id) on delete restrict on update restrict;
create index ix_user_organization_36 on user (organization_id);
>>>>>>> branch 'master' of https://github.com/Factor-X/awac



alter table report_indicator add constraint fk_report_indicator_report_01 foreign key (report_id) references report (id) on delete restrict on update restrict;

alter table report_indicator add constraint fk_report_indicator_indicator_02 foreign key (indicator_id) references indicator (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table air_travel_info;

drop table air_travels_group_answer;

drop table boolean_answer;

drop table campaign;

<<<<<<< HEAD
drop table double_answer;

drop table factor;

drop table factor_value;

drop table form;

drop table form_question;

drop table indicator;

drop table organization;
=======
drop table code_answer;

drop table double_answer;

drop table factor;

drop table factor_value;

drop table form;

drop table form_question;

drop table indicator;
>>>>>>> branch 'master' of https://github.com/Factor-X/awac

drop table period;

drop table person;

<<<<<<< HEAD
drop table product;

drop table question;

drop table report;

drop table report_indicator;
=======
drop table question;

drop table report;

drop table report_indicator;

drop table scope;
>>>>>>> branch 'master' of https://github.com/Factor-X/awac

drop table string_answer;

drop table unit;

drop table unit_category;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

