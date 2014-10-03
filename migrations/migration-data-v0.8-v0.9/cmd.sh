

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



# -------------------------------------------------
# KEEP THIS SECTION AT THE END OF THE FILE

# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W

# call the importers

echo -n "check port 9000."
while true; do
	echo -n '.'
	netstat -lnt | egrep ':9000\b' > /dev/null && echo "found :-)" && break
	sleep 1
done
echo ''
	
echo "http://127.0.0.1:9000/awac/admin/codelabels/reset ... " && 
curl -L http://127.0.0.1:9000/awac/admin/codelabels/reset &&
echo && 
echo "http://127.0.0.1:9000/awac/admin/indicators_factors/reset ... " && 
curl -L http://127.0.0.1:9000/awac/admin/indicators_factors/reset && 
echo



