#!/bin/bash

#create file

mkdir -p /tmp/migration/

touch /tmp/migration/migration_script.sql

# safety separator
echo ";" >> /tmp/migration/migration_script.sql

# add question_set A1 to form 2
echo "insert into mm_form_questionset (form_id,questionset_id) values(2,1);" >> /tmp/migration/migration_script.sql

#remove mm_form_question_set
echo "delete from mm_form_questionset where form_id = 1;" >> /tmp/migration/migration_script.sql

#remove form1
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

# create person_id foreign key into account
echo "ALTER TABLE account ADD CONSTRAINT fk_account_person_id FOREIGN KEY (person_id) REFERENCES person (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;" >> /tmp/migration/migration_script.sql

# create foreign key account.person_id
echo "alter table account alter account set not null;" >> /tmp/migration/migration_script.sql

# insert person_id into account
echo "update account set person_id = person.id from person where person.id = account.id;" >> /tmp/migration/migration_script.sql

# remove foreign key account.id
echo "alter table account drop constraint fk_3kyk3bnync56s8n23hqj89q4d;" >> /tmp/migration/migration_script.sql
echo "ALTER TABLE account ALTER COLUMN id SET DEFAULT nextval('account_id_seq'::regclass);" >> /tmp/migration/migration_script.sql

# account.person_id = not null
echo "alter table account alter account set not null;" >> /tmp/migration/migration_script.sql

# create isAdmin to account
echo "alter table account add column is_admin Boolean not null default false;" >> /tmp/migration/migration_script.sql

# update administrator
echo "update account set is_admin = true from administrator where administrator.id = account.id;" >> /tmp/migration/migration_script.sql

# remove administrator table
echo "drop table administrator;" >> /tmp/migration/migration_script.sql

# remove administrator table
echo "alter table person add column default_language character varying(255) not null default 'FR';" >> /tmp/migration/migration_script.sql


# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W

# execute the units refactoring
cat /migrations/migration-data-v0.4-v0.5/unit_refactoring.sql | psql -h localhost -U play -d awac -W

# add technical segment columns to tables where it is not present
cat /migrations/migration-data-v0.4-v0.5/add_technical_segment.sql | psql -h localhost -U play -d awac -W
