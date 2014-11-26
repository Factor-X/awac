

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

# add orderindex column to codesequivalence table
cat ./CELDL-236-CodesEquivalence_add_orderIndex_column.sql >> /tmp/migration/migration_script.sql

# factor_value_dates_to_integers
cat ./factor_value_dates_to_integers.sql >> /tmp/migration/migration_script.sql

# add topic column to codelabel table
cat ./CELDL-235-CodeLabel_add_topic_column.sql >> /tmp/migration/migration_script.sql

# update factor values to set 2000 everywhere
cat ./update_factor_values_to_set_2000_everywhere.sql >> /tmp/migration/migration_script.sql

# add_wysiwyg_document
cat ./add_wysiwyg_document.sql >> /tmp/migration/migration_script.sql

#remove the organization name constraint
cat ./remove_organization_name_unique_constraint.sql >> /tmp/migration/migration_script.sql

# add desrption to product
cat ./product.sql >> /tmp/migration/migration_script.sql


# -------------------------------------------------
# KEEP THIS SECTION AT THE END OF THE FILE

# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W

