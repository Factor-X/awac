#!/bin/bash

export PGPASSWORD=play

# add desrption to product
psql -h localhost -U play -d awac -w <  ./help_pages.sql


