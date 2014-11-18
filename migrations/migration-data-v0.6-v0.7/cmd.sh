

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

echo " CREATE TABLE mm_site_period(
          site_id bigint NOT NULL,
          period_id bigint NOT NULL,
          CONSTRAINT fk_g9f4tnhyxrvp53v2vuc9r2m2k FOREIGN KEY (site_id)
              REFERENCES site (id) MATCH SIMPLE
              ON UPDATE NO ACTION ON DELETE NO ACTION,
          CONSTRAINT fk_nn5ipj5ssh6ulm9ipliocbx5q FOREIGN KEY (period_id)
              REFERENCES period (id) MATCH SIMPLE
              ON UPDATE NO ACTION ON DELETE NO ACTION
        )
        WITH (
          OIDS=FALSE
        );
        ALTER TABLE mm_site_period
         OWNER TO play;" >> /tmp/migration/migration_script.sql

# create new 'awaccalculator' and 'mm_calculator_indicator' tables (in order to link indicators and calculators - JIRA CELDL-65)
cat ./CELDL-65-Indicator-Calculator-JoinTable.sql >> /tmp/migration/migration_script.sql

# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W