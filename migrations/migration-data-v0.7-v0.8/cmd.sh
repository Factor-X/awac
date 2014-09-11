

#!/bin/bash

#create file

mkdir -p /tmp/migration/

rm /tmp/migration/migration_script.sql

touch /tmp/migration/migration_script.sql

# safety separator
echo ";" >> /tmp/migration/migration_script.sql

# create new 'awaccalculator' and 'mm_calculator_indicator' tables (in order to link indicators and calculators - JIRA CELDL-65)
cat ./CELDL-65-Indicator-Calculator-JoinTable.sql >> /tmp/migration/migration_script.sql

# create new 'awaccalculator' and 'mm_calculator_indicator' tables (in order to link indicators and calculators - JIRA CELDL-65)
cat ./CELDL-135-Scope-Reengineering.sql >> /tmp/migration/migration_script.sql

# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W








# call the importers (KEEP THIS AT THE END OF THE FILE!)
cd ../../
bash -c 'export TERM=dumb; sbt run < /dev/zero > /dev/null 2>/dev/null &'  
cd -

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
echo && 

kill -9 $(netstat -a -p -n 2>/dev/null | egrep ':9000\b' | grep 'LISTEN' | awk '{ print $7 }' | cut -d/ -f1 )