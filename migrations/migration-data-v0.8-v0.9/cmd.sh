

#!/bin/bash

#create file

mkdir -p /tmp/migration/

rm /tmp/migration/migration_script.sql

touch /tmp/migration/migration_script.sql

# safety separator
echo ";" >> /tmp/migration/migration_script.sql

# create new multi site
cat ./CELDL-67-LockQuestionSet.sql >> /tmp/migration/migration_script.sql


# transfert-interface-to-organization
cat ./CELDL-190-transfert-interface-to-organization.sql >> /tmp/migration/migration_script.sql

# transfert data and progressbar from site to organization for municipality organization
# drop sites
cat ./CELDL-185-replace-scope-municipality-by-organization.sql >> /tmp/migration/migration_script.sql

# link between form and calculator
cat ./CELDL-70-closingForm.sql >> /tmp/migration/migration_script.sql

# add 'statistics_allowed' column in organization table
cat ./CELDL-211-Alter-Organization-Table.sql >> /tmp/migration/migration_script.sql
# set default value for organizationel_structure for all site
cat ./TAE-64-creation-site.sql >> /tmp/migration/migration_script.sql



# -------------------------------------------------
# KEEP THIS SECTION AT THE END OF THE FILE

# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W

