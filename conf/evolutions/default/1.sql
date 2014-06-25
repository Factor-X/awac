# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table audit_info (
  id                        bigint auto_increment not null,
  data_validator_id         bigint,
  data_locker_id            bigint,
  data_verifier_id          bigint,
  verification_status       integer,
  constraint pk_audit_info primary key (id))
;

create table campaign (
  id                        bigint auto_increment not null,
  period_id                 bigint not null,
  constraint pk_campaign primary key (id))
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

create table product (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  organization_id           bigint not null,
  constraint pk_product primary key (id))
;

create table question (
  id                        bigint auto_increment not null,
  code                      varchar(255),
  unit_category             varchar(255),
  constraint pk_question primary key (id))
;

create table question_answer (
  id                        bigint auto_increment not null,
  data_owner_id             bigint not null,
  period_id                 bigint not null,
  question_id               bigint not null,
  audit_info_id             bigint,
  scope_id                  bigint not null,
  unit_id                   bigint not null,
  answer_value              varchar(255),
  constraint pk_question_answer primary key (id))
;

create table report (
  id                        bigint auto_increment not null,
  constraint pk_report primary key (id))
;

create table scope (
  id                        bigint auto_increment not null,
  scope_type                integer,
  organization_id           bigint,
  site_id                   bigint,
  product_id                bigint,
  constraint pk_scope primary key (id))
;

create table site (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  organization_id           bigint not null,
  constraint pk_site primary key (id))
;

create table unit (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  category_id               bigint not null,
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
  constraint pk_user primary key (id))
;


create table report_indicator (
  report_id                      bigint not null,
  indicator_id                   bigint not null,
  constraint pk_report_indicator primary key (report_id, indicator_id))
;
alter table audit_info add constraint fk_audit_info_dataValidator_1 foreign key (data_validator_id) references user (id) on delete restrict on update restrict;
create index ix_audit_info_dataValidator_1 on audit_info (data_validator_id);
alter table audit_info add constraint fk_audit_info_dataLocker_2 foreign key (data_locker_id) references user (id) on delete restrict on update restrict;
create index ix_audit_info_dataLocker_2 on audit_info (data_locker_id);
alter table audit_info add constraint fk_audit_info_dataVerifier_3 foreign key (data_verifier_id) references user (id) on delete restrict on update restrict;
create index ix_audit_info_dataVerifier_3 on audit_info (data_verifier_id);
alter table campaign add constraint fk_campaign_period_4 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_campaign_period_4 on campaign (period_id);
alter table factor add constraint fk_factor_unitIn_5 foreign key (unit_in_id) references unit (id) on delete restrict on update restrict;
create index ix_factor_unitIn_5 on factor (unit_in_id);
alter table factor add constraint fk_factor_unitOut_6 foreign key (unit_out_id) references unit (id) on delete restrict on update restrict;
create index ix_factor_unitOut_6 on factor (unit_out_id);
alter table factor_value add constraint fk_factor_value_factor_7 foreign key (factor_id) references factor (id) on delete restrict on update restrict;
create index ix_factor_value_factor_7 on factor_value (factor_id);
alter table form add constraint fk_form_campaign_8 foreign key (campaign_id) references campaign (id) on delete restrict on update restrict;
create index ix_form_campaign_8 on form (campaign_id);
alter table form_question add constraint fk_form_question_form_9 foreign key (form_id) references form (id) on delete restrict on update restrict;
create index ix_form_question_form_9 on form_question (form_id);
alter table form_question add constraint fk_form_question_question_10 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_form_question_question_10 on form_question (question_id);
alter table product add constraint fk_product_organization_11 foreign key (organization_id) references organization (id) on delete restrict on update restrict;
create index ix_product_organization_11 on product (organization_id);
alter table question_answer add constraint fk_question_answer_dataOwner_12 foreign key (data_owner_id) references user (id) on delete restrict on update restrict;
create index ix_question_answer_dataOwner_12 on question_answer (data_owner_id);
alter table question_answer add constraint fk_question_answer_period_13 foreign key (period_id) references period (id) on delete restrict on update restrict;
create index ix_question_answer_period_13 on question_answer (period_id);
alter table question_answer add constraint fk_question_answer_question_14 foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_question_answer_question_14 on question_answer (question_id);
alter table question_answer add constraint fk_question_answer_auditInfo_15 foreign key (audit_info_id) references audit_info (id) on delete restrict on update restrict;
create index ix_question_answer_auditInfo_15 on question_answer (audit_info_id);
alter table question_answer add constraint fk_question_answer_scope_16 foreign key (scope_id) references scope (id) on delete restrict on update restrict;
create index ix_question_answer_scope_16 on question_answer (scope_id);
alter table question_answer add constraint fk_question_answer_unit_17 foreign key (unit_id) references unit (id) on delete restrict on update restrict;
create index ix_question_answer_unit_17 on question_answer (unit_id);
alter table scope add constraint fk_scope_organization_18 foreign key (organization_id) references organization (id) on delete restrict on update restrict;
create index ix_scope_organization_18 on scope (organization_id);
alter table scope add constraint fk_scope_site_19 foreign key (site_id) references site (id) on delete restrict on update restrict;
create index ix_scope_site_19 on scope (site_id);
alter table scope add constraint fk_scope_product_20 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_scope_product_20 on scope (product_id);
alter table site add constraint fk_site_organization_21 foreign key (organization_id) references organization (id) on delete restrict on update restrict;
create index ix_site_organization_21 on site (organization_id);
alter table unit add constraint fk_unit_category_22 foreign key (category_id) references unit_category (id) on delete restrict on update restrict;
create index ix_unit_category_22 on unit (category_id);
alter table user add constraint fk_user_organization_23 foreign key (organization_id) references organization (id) on delete restrict on update restrict;
create index ix_user_organization_23 on user (organization_id);



alter table report_indicator add constraint fk_report_indicator_report_01 foreign key (report_id) references report (id) on delete restrict on update restrict;

alter table report_indicator add constraint fk_report_indicator_indicator_02 foreign key (indicator_id) references indicator (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table audit_info;

drop table campaign;

drop table factor;

drop table factor_value;

drop table form;

drop table form_question;

drop table indicator;

drop table organization;

drop table period;

drop table person;

drop table product;

drop table question;

drop table question_answer;

drop table report;

drop table report_indicator;

drop table scope;

drop table site;

drop table unit;

drop table unit_category;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

