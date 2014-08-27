#!/bin/bash

#create file

mkdir -p /tmp/migration/

rm /tmp/migration/migration_script.sql

touch /tmp/migration/migration_script.sql

# safety separator
echo ";" >> /tmp/migration/migration_script.sql

# add question_set A1 to form 2
echo "insert into mm_form_questionset (form_id,questionset_id) values(2,1);" >> /tmp/migration/migration_script.sql

#remove mm_form_question_set
echo "delete from mm_form_questionset where form_id = 1;" >> /tmp/migration/migration_script.sql

#remove form1
echo "delete from formprogress where form = 1;" >> /tmp/migration/migration_script.sql
echo "delete from form where id = 1;" >> /tmp/migration/migration_script.sql

#remove age from account
echo "alter table account drop column age;" >> /tmp/migration/migration_script.sql

#remove vat from account
echo "alter table account drop column vatnumber;" >> /tmp/migration/migration_script.sql
echo "alter table account drop column viesaddress;" >> /tmp/migration/migration_script.sql
echo "alter table account drop column viesname;" >> /tmp/migration/migration_script.sql
echo "alter table account drop column viesrequestdate;" >> /tmp/migration/migration_script.sql
echo "alter table account drop column viesrequestid;" >> /tmp/migration/migration_script.sql
echo "alter table account drop column viesverified;" >> /tmp/migration/migration_script.sql

# change foreign to for administrator
echo "alter table administrator drop constraint fk_jd0370oy76ntbbd6qi0ofdqjs;" >> /tmp/migration/migration_script.sql
echo "alter table account add constraint fk_account foreign key(id) references account(id);" >> /tmp/migration/migration_script.sql

# remove address to person
echo "alter table person drop column street;" >> /tmp/migration/migration_script.sql
echo "alter table person drop column postalcode;" >> /tmp/migration/migration_script.sql
echo "alter table person drop column city;" >> /tmp/migration/migration_script.sql
echo "alter table person drop column country;" >> /tmp/migration/migration_script.sql
echo "alter table person drop column lastUpdate;" >> /tmp/migration/migration_script.sql
echo "ALTER TABLE ONLY person
        ADD CONSTRAINT uk_fwmwi44u55bo4rvwsv0cln012 UNIQUE (email);" >> /tmp/migration/migration_script.sql

# add identifier to account
echo "alter table account ADD COLUMN identifier character varying(255) null;" >> /tmp/migration/migration_script.sql
echo "alter table account ADD COLUMN password character varying(255) null;" >> /tmp/migration/migration_script.sql

# inject identifier and password into account
echo "update account set password = person.password, identifier = person.identifier from person where person.id = account.id ;" >> /tmp/migration/migration_script.sql

 # edit coumn
 echo "alter table account alter identifier set not null;" >> /tmp/migration/migration_script.sql
 echo "alter table account alter password set not null;" >> /tmp/migration/migration_script.sql

 # remove column from person
echo "alter table person drop column password;" >> /tmp/migration/migration_script.sql
echo "alter table person drop column identifier;" >> /tmp/migration/migration_script.sql
echo "alter table person drop column accountstatus;" >> /tmp/migration/migration_script.sql

# add interface info
echo "ALTER TABLE account ADD COLUMN interface_code character varying(255) default 'enterprise' not null;" >> /tmp/migration/migration_script.sql

# add isActive column
echo "ALTER TABLE account ADD COLUMN active boolean default true;" >> /tmp/migration/migration_script.sql

# add needChangePassword column
echo "ALTER TABLE account ADD COLUMN need_change_password boolean default false;" >> /tmp/migration/migration_script.sql

# remove unused account
echo "delete from administrator where id = 1 or id = 2;" >> /tmp/migration/migration_script.sql
echo "delete from account where id = 1 or id = 2 ;" >> /tmp/migration/migration_script.sql
echo "delete from person where id = 1 or id = 2 ;" >> /tmp/migration/migration_script.sql

echo "delete from administrator where id = 3 or id = 4 or id = 5;" >> /tmp/migration/migration_script.sql
echo "delete from account where id = 3 or id = 4 or id = 5;" >> /tmp/migration/migration_script.sql
echo "delete from person where id = 3 or id = 4 or id = 5;" >> /tmp/migration/migration_script.sql
echo "delete from organization where id = 1;" >> /tmp/migration/migration_script.sql

# email is unique
echo "ALTER TABLE ONLY person ADD CONSTRAINT uk_fwmwi44u55bo4rvwsv0cln012 UNIQUE (email);" >> /tmp/migration/migration_script.sql

# organization is needed for account
echo "alter table account alter organization_id set not null;" >> /tmp/migration/migration_script.sql

# convert existing account to admin
echo "insert into administrator (id) values (6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19);" >> /tmp/migration/migration_script.sql

# add email to existing account
echo "update person set email = 'aaa@zzz.eee' where id = 6;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'aaa@zzz.rrrr' where id = 7;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'cecile.batungwanayo@spw.wallonie.be' where id = 8;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'mdetroye@bemis.com' where id = 9;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'm.marlot@hch.be' where id = 10;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'pierre.feron@akzonobel.com' where id = 11;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'l.vandingenen@gerresheimer.com' where id = 12;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'michel.hiraux@ellipse-ise.eu' where id = 13;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'marc.cornu@ampacet.com' where id = 14;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'patrick.dupont@jindalfilms.com' where id = 15;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'coolsaet.p@mydibel.be' where id = 16;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'gaelle.hubert@erametgroup.com' where id = 17;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'bohdan.soroka@laborelec.com' where id = 18;" >> /tmp/migration/migration_script.sql
echo "update person set email = 'jeanclaude.parmentier@eu.agc.com' where id = 19;" >> /tmp/migration/migration_script.sql

# person.email not null
echo "alter table person alter email set not null;" >> /tmp/migration/migration_script.sql

#create person_id into account
echo "ALTER TABLE account ADD COLUMN person_id bigint null;" >> /tmp/migration/migration_script.sql

# create person_id foreign key into account
echo "ALTER TABLE account ADD CONSTRAINT fk_account_person_id FOREIGN KEY (person_id) REFERENCES person (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;" >> /tmp/migration/migration_script.sql

# insert person_id into account
echo "update account set person_id = person.id from person where person.id = account.id;" >> /tmp/migration/migration_script.sql

# account.person_id cannot be null
echo "alter table account alter person_id set not null;" >> /tmp/migration/migration_script.sql

# remove foreign key account.id
echo "alter table account drop constraint fk_3kyk3bnync56s8n23hqj89q4d;" >> /tmp/migration/migration_script.sql

#create a new sequence for account
echo "CREATE SEQUENCE account_id_seq
          INCREMENT 1
          MINVALUE 1
          MAXVALUE 9223372036854775807
          START 19
          CACHE 1;
        ALTER TABLE account_id_seq
          OWNER TO play;" >> /tmp/migration/migration_script.sql


echo "ALTER TABLE account ALTER COLUMN id SET DEFAULT nextval('account_id_seq'::regclass);" >> /tmp/migration/migration_script.sql

# create isAdmin to account
echo "alter table account add column is_admin Boolean not null default false;" >> /tmp/migration/migration_script.sql

# update administrator
echo "update account set is_admin = true from administrator where administrator.id = account.id;" >> /tmp/migration/migration_script.sql

# remove administrator table
echo "drop table administrator;" >> /tmp/migration/migration_script.sql

# remove administrator table
echo "alter table person add column default_language character varying(2) not null default 'FR';" >> /tmp/migration/migration_script.sql


#create notification key
echo "CREATE TABLE notification (
    id bigint NOT NULL,
    creationdate timestamp without time zone,
    creationuser character varying(255),
    lastupdatedate timestamp without time zone,
    lastupdateuser character varying(255),
    kind character varying(255),
    messageen character varying(255),
    messagefr character varying(255),
    messagenl character varying(255),
    since timestamp without time zone,
    until timestamp without time zone);" >> /tmp/migration/migration_script.sql

echo "CREATE SEQUENCE notification_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1;" >> /tmp/migration/migration_script.sql

echo "ALTER TABLE ONLY notification
        ADD CONSTRAINT notification_pkey PRIMARY KEY (id);" >> /tmp/migration/migration_script.sql

echo "ALTER TABLE ONLY notification ALTER COLUMN id SET DEFAULT nextval('notification_id_seq'::regclass);" >> /tmp/migration/migration_script.sql

#create systemAdministrator
echo "CREATE TABLE systemadministrator (
        id bigint NOT NULL);" >> /tmp/migration/migration_script.sql

echo "ALTER TABLE ONLY systemadministrator
    ADD CONSTRAINT fk_rkca7sfx1qlw8tep2y4pj1day FOREIGN KEY (id) REFERENCES account(id);" >> /tmp/migration/migration_script.sql

#encrypt password
echo "update account set password = '3640622fc183ef1c7a1719f6fc4bd766b8fa01a5f4cf6442d5ce2a62f4f5852fe3825fc2485f469c' where identifier = 'user1';" >> /tmp/migration/migration_script.sql
echo "update account set password = '3640622fc183ef1c7a1719f6fc4bd766b8fa01a5f4cf6442d5ce2a62f4f5852fe3825fc2485f469c' where identifier = 'user2';" >> /tmp/migration/migration_script.sql
echo "update account set password = '73917cbaacd4ffac191ca1ac3280f6194685c8daa35a23578fa0a365794b7a4c1a809819a35fb8fa' where identifier = 'user-awac';" >> /tmp/migration/migration_script.sql
echo "update account set password = '6453343570b00684eabd8e23b4733f8a666e844fed5320ba5fde35c075dc00def1cecad8383e7c03' where identifier = 'user-bemis';" >> /tmp/migration/migration_script.sql
echo "update account set password = '05888eeff0837194cc51b962709df0157494b47b04b53b71606253186d9d69af213168c1a773ff3e' where identifier = 'user-hch';" >> /tmp/migration/migration_script.sql
echo "update account set password = '90691309f9682048bd59eda3a7bacfe3d04cef9c2aaf346f04b8cd15b6d1ac9e1937bdbff9e249dc' where identifier = 'user-akzonobel';" >> /tmp/migration/migration_script.sql
echo "update account set password = '3ec0ece715a968c6e5697930b38a8d87fa866d28fb617238f6ada2a4c5c6a859a4d69d767446a3d0' where identifier = 'User-gerresheimer';" >> /tmp/migration/migration_script.sql
echo "update account set password = '0fe32d927bb8ede71e76ce5cb75a9e061a7c4e115037bc03445469a406bf09c29b9eeddb6e0d99d8' where identifier = 'user-Ellipse';" >> /tmp/migration/migration_script.sql
echo "update account set password = 'dc635f46bc1139543d6ed6c646c50c3b21b00db9c3897c55d9a70f5b2150a86356f227d8e4666470' where identifier = 'user-ampacet';" >> /tmp/migration/migration_script.sql
echo "update account set password = '4e8f5d1da99c5fe51c189832d99f4350530daec5a7c327ea5244b9f9f1e097715d24e34aca401d5e' where identifier = 'user-jindalfilms';" >> /tmp/migration/migration_script.sql
echo "update account set password = '707d978ff279e415b763613d77e0be7b9bfb9aa4761b59c8019e2cc2ac59d09d00debd6df5d778cc' where identifier = 'user-mydibel';" >> /tmp/migration/migration_script.sql
echo "update account set password = '0b58da8e10328be537660f63e759205ee45d61c4b1538289a20421d4186423625736ccdcb2e814c9' where identifier = 'user-erachem';" >> /tmp/migration/migration_script.sql
echo "update account set password = 'd68bcb128082e91d0d3088168b686ac183c1ac6960acea43847d1dfd5889c73b6c547361f4fcdcae' where identifier = 'user-laborelec';" >> /tmp/migration/migration_script.sql
echo "update account set password = 'd62db6a3e46406b53306f319767586607817de492f541bc175c413b0b44236e58bb96956a083f16a' where identifier = 'user-agc';" >> /tmp/migration/migration_script.sql
echo "update account set password = '6c9f7bdab40df70e166e761392a0309f191d8096fe61e19853dc55336098ace7a0a69e5af46c218e' where identifier = 'user-climact';" >> /tmp/migration/migration_script.sql

echo "insert into scope (type, organization_id) values
        (1,2),
        (1,3),
        (1,4),
        (1,5),
        (1,6),
        (1,7),
        (1,8),
        (1,9),
        (1,10),
        (1,11),
        (1,12),
        (1,13),
        (1,14);" >> /tmp/migration/migration_script.sql


# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W

# execute the units refactoring
cat ./unit_refactoring.sql | psql -h localhost -U play -d awac -W

# add technical segment columns to tables where it is not present
cat ./add_technical_segment.sql | psql -h localhost -U play -d awac -W
