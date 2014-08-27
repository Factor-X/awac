# Procedure

steps to migrate AWAC v0.4 to v0.5
-> modification of the data structure
-> add some informations into database

## Files

 cmd.sh => contains the migration script for the database structure

 add_technical_segment.sql (used by cmd.sh)  => add the technical segment to some table into database

 unit_refactoring.sql => edit some information into unit and unit_category table

## Steps

1. stop the production application
    HOW TO : ?

2. import production database to local
    ```sh
    $ pg_dump --no-privileges --no-owner --no-reconnect -h ec2-54-217-206-100.eu-west-1.compute.amazonaws.com -d ddd59omo17fsbr -U u9q6jlsnjtkir0 -W
    ```
    
3. execute the cmd.sh script

    ```sh
    $ ./cmd.sh
    ```

4. use importers with console to add information for the municipality forms and new activity lists and translations
    ```sh
    $
    ```

    => database ok

5. create a branch v0.5
    ```sh
    $ git  checkout -b v0.5
    ```

6. deactivate "Invite user" button into "user manager"
    HOW TO : user_maanger.jade, l.5 : add ng-show="false"
    => application ready to deploy
    
7. deploy the application
    ```sh
    $ git push awac-accept v0.5:master -f
    ```

8. erase the production DB with the local DB
    ```sh
    $
    ```

9. start the new application
    ```sh
    $
    ```
