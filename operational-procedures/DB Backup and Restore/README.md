# AWAC DB backup and restore procedures: how-to

**a full backup** is made on Heroku Cloud every day at 4:00 AM for both awac-dev and awac-accpet environments.

## Cronos 

**Cronos server** leads the backup procedure.

Cronos initiates the backup cloud procedure every day at 4:00 AM based on crontab job scheduling.
Cronos downloads the cloud backup and save it on a local file system within Factor-X IT NAS.

## AWAC Backup

## AWAC Restore



