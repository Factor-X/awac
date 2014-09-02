

#!/bin/bash

#create file

mkdir -p /tmp/migration/

rm /tmp/migration/migration_script.sql

touch /tmp/migration/migration_script.sql

# safety separator
echo ";" >> /tmp/migration/migration_script.sql

# add columns from site
echo "alter table site add column description TEXT;" >> /tmp/migration/migration_script.sql
echo "alter table site add column nace_code character varying(255);" >> /tmp/migration/migration_script.sql

echo "alter table site add column siteal_structure character varying(255);" >> /tmp/migration/migration_script.sql
echo "alter table site add column economic_interest character varying(255);" >> /tmp/migration/migration_script.sql
echo "alter table site add column operating_policy character varying(255);" >> /tmp/migration/migration_script.sql
echo "alter table site add column accounting_treatment character varying(255);" >> /tmp/migration/migration_script.sql
echo "alter table site add column percent_owned decimal default 100;" >> /tmp/migration/migration_script.sql

echo "update site set percent_owned = 100;" >> /tmp/migration/migration_script.sql

# create new invitation table
cat ./CELDL-101-InviteNewUsers.sql >> /tmp/migration/migration_script.sql


# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W