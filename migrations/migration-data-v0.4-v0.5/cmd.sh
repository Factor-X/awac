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

# execute the script
cat /tmp/migration/useful_data.sql | psql -h localhost -U play -d awac -W

# execute the units refactoring
cat /migrations/migration-data-v0.4-v0.5/unit_refactoring.sql | psql -h localhost -U play -d awac -W
