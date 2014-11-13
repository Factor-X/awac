

#!/bin/bash

#create file

mkdir -p /tmp/migration/

rm /tmp/migration/migration_script.sql

touch /tmp/migration/migration_script.sql

# safety separator
echo ";" >> /tmp/migration/migration_script.sql

# admin interface
cat ./CELDL-76-admin_interface.sql >> /tmp/migration/migration_script.sql

# driver
cat ./CELDL-238-driver.sql >> /tmp/migration/migration_script.sql

# reducing action advices
cat ./CELDL-301-reducingactionadvices.sql >> /tmp/migration/migration_script.sql

# add product model
cat ./Add_product_model.sql >> /tmp/migration/migration_script.sql

# refactor mm_form_questionset
# cat ./mm_form_questionset_REFACTORING.sql >> /tmp/migration/migration_script.sql

# fix_answer_bug
cat ./fix_answer_bug.sql >> /tmp/migration/migration_script.sql






# -------------------------------------------------
# KEEP THIS SECTION AT THE END OF THE FILE

# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W

