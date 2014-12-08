

#!/bin/bash

export PGPASSWORD=play

# add desrption to product
psql -h localhost -U play -d awac -w <  ./product.sql

# add new DateTimeQuestion entity, add datetime column in answer_value
psql -h localhost -U play -d awac -w <  ./datetimequestion.sql

# cleanup_old_drivers.sql
psql -h localhost -U play -d awac -w <  ./cleanup_old_drivers.sql
