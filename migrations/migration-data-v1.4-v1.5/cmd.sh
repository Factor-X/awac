#!/bin/bash

export PGPASSWORD=play

# CGA-98
psql -h localhost -U play -d awac -w <  ./CGA-98.sql

# CGA-108

