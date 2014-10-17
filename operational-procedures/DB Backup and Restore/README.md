# AWAC DB backup and restore procedures: how-to

**A full backup** is made on Heroku Cloud every day at 4:00 AM for both awac-dev and awac-accpet environments.

## Cronos 

**Cronos server** leads the backup procedure.

Cronos initiates the backup cloud procedure every day at 4:00 AM based on crontab job scheduling.
Cronos downloads the cloud backup and save it on a local file system within Factor-X IT NAS.

## AWAC Backup

log -> log files
awac-dev -> awac-dev cloud backup images.
awac-accept -> awac-accept claud backup images.

**To run a backup please issue de following command on zeus@Cronos user.

```sh
cd ~/backup
./backup.sh
```

**To run a restore please issue de following command on zeus@Cronos user.
*note that for security reasons, the restore procedure is only automated to work with awac-dev. 

## AWAC Restore
```sh
cd ~/backup
./restore.sh
```

## Help guide

**some tips and hints

## Show list of backups on Heroku cloud
```sh
heroku pgbackups --app awac-dev
```

## Create new backup instance and replace old one ,on Heroku Cloud 
```sh
heroku pgbackups:capture --expire --app awac-dev
```
## Download last backup instance from Heroku Cloud
 
wget $(heroku pgbackups:url --app awzc-dev | grep https)  

## Create backup url to restore
*note $backupid is one of the values presented in the ID column of "heroku pgbackups --app awac-dev" command

```sh
heroku pgbackups:url $backupid
```

## Restoring a given backup instance
*note $backupid is one of the values presented in the ID column of "heroku pgbackups --app awac-dev" command
 
 ```sh
heroku maintenance:on --app awac-dev
export backupurl = $(heroku pgbackups:url $backupid)
heroku pgbackups:restore HEROKU_POSTGRESQL_AMBER_URL $backupurl --app awac-dev
heroku maintenance:off --app awac-dev
```




