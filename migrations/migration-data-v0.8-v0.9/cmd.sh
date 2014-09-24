

#!/bin/bash

#create file

mkdir -p /tmp/migration/

rm /tmp/migration/migration_script.sql

touch /tmp/migration/migration_script.sql

# safety separator
echo ";" >> /tmp/migration/migration_script.sql

# create new multi site
cat ./CELDL-67-LockQuestionSet.sql >> /tmp/migration/migration_script.sql

# -------------------------------------------------
# KEEP THIS SECTION AT THE END OF THE FILE

# execute the script
cat /tmp/migration/migration_script.sql | psql -h localhost -U play -d awac -W

# call the importers
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
echo "http://127.0.0.1:9000/awac/admin/sites_associations/import ... " && 
curl -L http://127.0.0.1:9000/awac/admin/sites_associations/import && 
echo && 


kill -9 $(netstat -a -p -n 2>/dev/null | egrep ':9000\b' | grep 'LISTEN' | awk '{ print $7 }' | cut -d/ -f1 )

