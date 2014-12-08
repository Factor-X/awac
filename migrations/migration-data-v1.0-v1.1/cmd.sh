

#!/bin/bash

export PGPASSWORD=play

# admin interface
psql -h localhost -U play -d awac -w < ./CELDL-76-admin_interface.sql 

# driver
psql -h localhost -U play -d awac -w < ./CELDL-238-driver.sql 

# reducing action advices
psql -h localhost -U play -d awac -w < ./CELDL-301-reducingactionadvices.sql 

# add product model
psql -h localhost -U play -d awac -w < ./Add_product_model.sql 

# refactor mm_form_questionset
# psql -h localhost -U play -d awac -w < ./mm_form_questionset_REFACTORING.sql 

# fix_answer_bug
psql -h localhost -U play -d awac -w < ./fix_answer_bug.sql 

# add orderindex column to codesequivalence table
psql -h localhost -U play -d awac -w < ./CELDL-236-CodesEquivalence_add_orderIndex_column.sql 

# factor_value_dates_to_integers
psql -h localhost -U play -d awac -w < ./factor_value_dates_to_integers.sql 

# add topic column to codelabel table
psql -h localhost -U play -d awac -w < ./CELDL-235-CodeLabel_add_topic_column.sql 

# update factor values to set 2000 everywhere
psql -h localhost -U play -d awac -w < ./update_factor_values_to_set_2000_everywhere.sql 

# add_wysiwyg_document
psql -h localhost -U play -d awac -w < ./add_wysiwyg_document.sql 

# add_main_factor_unit
psql -h localhost -U play -d awac -w < ./add_main_factor_unit.sql 
#remove the organization name constraint
psql -h localhost -U play -d awac -w <  ./remove_organization_name_unique_constraint.sql



# -------------------------------------------------
# KEEP THIS SECTION AT THE END OF THE FILE

# add_languages_to_awac_calculator
psql -h localhost -U play -d awac -w < ./add_languages_to_awac_calculator.sql 


