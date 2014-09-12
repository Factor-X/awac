

#!/bin/bash

#create file

mkdir -p /tmp/migration/

rm /tmp/migration/migration_script.sql

touch /tmp/migration/migration_script.sql

# safety separator
echo ";" >> /tmp/migration/migration_script.sql

# create new invitation table
cat ./CELDL-101-InviteNewUsers.sql >> /tmp/migration/migration_script.sql

# create new 'awaccalculator' and 'mm_calculator_indicator' tables (in order to link indicators and calculators - JIRA CELDL-65)
cat ./CELDL-65-Indicator-Calculator-JoinTable.sql >> /tmp/migration/migration_script.sql

# move A205 & A208 from tab 6 to tab 4 
cat ./UpdateQuestionSetA205_A208.sql >> /tmp/migration/migration_script.sql

# reengineering of scope-related entities (set scope as parent class of organization, site & product)
cat ./CELDL-135-Scope-Reengineering.sql >> /tmp/migration/migration_script.sql

# reengineering of answer value-related entities (set single-table inheritance then drop unused tables)
cat ./CELDL-135-AnswerValue-Reengineering.sql >> /tmp/migration/migration_script.sql





# -------------------------------------------------
# KEEP THIS SECTION AT THE END OF THE FILE

# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W

# call the importers
