#!/bin/bash

export PGPASSWORD=play

psql -h localhost -U play -d awac -w <  ./CGA-98.sql

psql -h localhost -U play -d awac -w <  ./CGA-110.sql

psql -h localhost -U play -d awac -w <  ./CGA-96.sql

psql -h localhost -U play -d awac -w <  ./CGA-3.sql

psql -h localhost -U play -d awac -w <  ./CGA-108.sql
