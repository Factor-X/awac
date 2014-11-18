

#!/bin/bash

#create file

mkdir -p /tmp/migration/

rm /tmp/migration/migration_script.sql

touch /tmp/migration/migration_script.sql

# safety separator
echo ";" >> /tmp/migration/migration_script.sql

# create verification
cat ./CELDL-74-verification.sql >> /tmp/migration/migration_script.sql

# create new multi site
cat ./CELDL-77-Reducing-Actions.sql >> /tmp/migration/migration_script.sql

# create new multi site
cat ./CELDL-251-FormChanges.sql >> /tmp/migration/migration_script.sql


# new periods
cat ./CELDL-116-periods.sql >> /tmp/migration/migration_script.sql


# percentage fix bug
cat ./CELDL-260-percentage.sql >> /tmp/migration/migration_script.sql

# site association bug fix bug
cat ./CELDL-268-remove-unique-constraint-accout-site-association.sql >> /tmp/migration/migration_script.sql




# -------------------------------------------------
# KEEP THIS SECTION AT THE END OF THE FILE

# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W

