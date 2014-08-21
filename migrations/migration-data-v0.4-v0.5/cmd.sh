#!/bin/bash

#create file


# safety separator
echo ";" >> /tmp/migration/useful_data.sql

# add question_set A1 to form 2
echo "insert into mm_form_questionset (form_id,questionset_id) values(2,1);" >> /tmp/migration/useful_data.sql

#remove mm_form_question_set
echo "delete from mm_form_questionset where form_id = 1;" >> /tmp/migration/useful_data.sql

#remove form1
echo "delete from form where id = 1;" >> /tmp/migration/useful_data.sql

#remove age from account
echo "alter table account drop column age;" >> /tmp/migration/useful_data.sql

#remove vat from account
echo "alter table account drop column vatnumber;" >> /tmp/migration/useful_data.sql
echo "alter table account drop column viesaddress;" >> /tmp/migration/useful_data.sql
echo "alter table account drop column viesname;" >> /tmp/migration/useful_data.sql
echo "alter table account drop column viesrequestdate;" >> /tmp/migration/useful_data.sql
echo "alter table account drop column viesrequestid;" >> /tmp/migration/useful_data.sql
echo "alter table account drop column viesverified;" >> /tmp/migration/useful_data.sql

# change foreign to for administrator
echo "alter table administrator drop constraint fk_jd0370oy76ntbbd6qi0ofdqjs;" >> /tmp/migration/useful_data.sql
echo "alter table account add constraint fk_account foreign key(id) references account(id);" >> /tmp/migration/useful_data.sql

# remove address to person
echo "alter table person drop column street;" >> /tmp/migration/useful_data.sql
echo "alter table person drop column postalcode;" >> /tmp/migration/useful_data.sql
echo "alter table person drop column city;" >> /tmp/migration/useful_data.sql
echo "alter table person drop column country;" >> /tmp/migration/useful_data.sql
echo "alter table person drop column lastUpdate;" >> /tmp/migration/useful_data.sql

# execute the script
cat /tmp/migration/useful_data.sql | psql -h localhost -U play -d awac -W

# execute the units refactoring
cat /migrations/migration-data-v0.4-v0.5/unit_refactoring.sql | psql -h localhost -U play -d awac -W
