#!/bin/bash

reset

# fetch the data from awac-accept
pg_dump --data-only --column-inserts -h ec2-54-217-208-158.eu-west-1.compute.amazonaws.com -d d2onsan1iugu6d -U pmouzzkdtqbmmi -w | egrep -o '^INSERT INTO questionsetanswer.*$|^INSERT INTO question_answer.*$|^INSERT INTO answer_value.*$|^SELECT pg_catalog.setval.*$|^INSERT INTO booleananswervalue.*$|^INSERT INTO codeanswervalue.*$|^INSERT INTO doubleanswervalue.*$|^INSERT INTO integeranswervalue.*$|^INSERT INTO stringanswervalue.*$' > /tmp/migration/useful_data.sql

# replace MW.h
sed -i 's/\(INSERT INTO answer_value (id, booleandata, doubledata, longdata, stringdata1, stringdata2, questionanswer_id) VALUES ([^,]*,[^,]*,[^,]*,\) 216,/\1 53,/g' /tmp/migration/useful_data.sql

# replace kW.h
sed -i 's/\(INSERT INTO answer_value (id, booleandata, doubledata, longdata, stringdata1, stringdata2, questionanswer_id) VALUES ([^,]*,[^,]*,[^,]*,\) 215,/\1 228,/g' /tmp/migration/useful_data.sql

# safety separator
echo ";" >> /tmp/migration/useful_data.sql

# convert percentages to decimals (87 => 0.87)
echo "UPDATE answer_value SET doubledata = (doubledata / 100.0) WHERE id in (select av.id from answer_value av JOIN doubleanswervalue dav on dav.id = av.id JOIN question_answer qa on qa.id = av.questionanswer_id JOIN question q on q.id = qa.question_id JOIN percentagequestion pq on pq.id = q.id);" >> /tmp/migration/useful_data.sql

# convert answers to A227, A241 and A247 from number to text
echo "UPDATE answer_value SET stringdata1 = CAST(doubledata AS TEXT) WHERE id in (select av.id from answer_value av JOIN question_answer qa on qa.id = av.questionanswer_id JOIN question q on q.id = qa.question_id WHERE q.code in ('A227', 'A241', 'A247'));" >> /tmp/migration/useful_data.sql

# clean up empty answers
echo "delete from stringanswervalue WHERE id in (select id from  answer_value WHERE comment IS NULL and booleandata IS NULL and doubledata IS NULL and longdata IS NULL and stringdata1 IS NULL and stringdata2 IS NULL);" >> /tmp/migration/useful_data.sql
echo "delete from codeanswervalue WHERE id in (select id from  answer_value WHERE comment IS NULL and booleandata IS NULL and doubledata IS NULL and longdata IS NULL and stringdata1 IS NULL and stringdata2 IS NULL);" >> /tmp/migration/useful_data.sql
echo "delete from doubleanswervalue WHERE id in (select id from  answer_value WHERE comment IS NULL and booleandata IS NULL and doubledata IS NULL and longdata IS NULL and stringdata1 IS NULL and stringdata2 IS NULL);" >> /tmp/migration/useful_data.sql
echo "delete from integeranswervalue WHERE id in (select id from  answer_value WHERE comment IS NULL and booleandata IS NULL and doubledata IS NULL and longdata IS NULL and stringdata1 IS NULL and stringdata2 IS NULL);" >> /tmp/migration/useful_data.sql
echo "delete from booleananswervalue WHERE id in (select id from  answer_value WHERE comment IS NULL and booleandata IS NULL and doubledata IS NULL and longdata IS NULL and stringdata1 IS NULL and stringdata2 IS NULL);" >> /tmp/migration/useful_data.sql
echo "delete from answer_value WHERE comment IS NULL and booleandata IS NULL and doubledata IS NULL and longdata IS NULL and stringdata1 IS NULL and stringdata2 IS NULL;" >> /tmp/migration/useful_data.sql

# execute the script
cat /tmp/migration/useful_data.sql | psql -h localhost -U play -d awac -W