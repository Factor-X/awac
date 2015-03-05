#!/bin/bash

export PGPASSWORD=play

# Added 2 translations (descriptions for the actions panel)
psql -h localhost -U play -d awac -w <  ./CGA-98.sql

psql -h localhost -U play -d awac -w <  ./CGA-96.sql

psql -h localhost -U play -d awac -w <  ./CGA-3.sql




