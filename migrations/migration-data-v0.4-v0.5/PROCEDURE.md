# Procedure

Steps to migrate AWAC **v0.4** to **v0.5**

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

		V0.5 NOTE
		---------
		Regarding the v0.4 to v0.5 migration, the importers that have to be launched are:
		- CodeLabelImporter (re-import all code lists and translations)
		- AwacMunicipalityInitialData (import municipality survey data: forms, question sets, questions)
		- AwacDataImporter (re-import all enterprise-related indicators & factors)
		
		You may achieve this by using the console, or the administration controller by going to these urls (works only on dev env.):
		- http://localhost:9000/awac/admin/codelabels/reset
		- http://localhost:9000/awac/admin/indicators_factors/reset
		- http://localhost:9000/awac/admin/municipality_survey/create
	

    => database ok

5. create a branch v0.5
    ```sh
    $ git  checkout -b v0.5
    ```

6. deactivate "Invite user" button into "user manager"
    HOW TO : user_maanger.jade, l.5 : add ng-show="false"
7. deactivate "forgot password" into login
    HOW TO : add ng-show="false" into the second tab
    ```sh
    $
    ```
    
    => application ready to deploy
    
8. deploy the application
    ```sh
    $ git push awac-accept v0.5:master -f
    ```

9. erase the production DB with the local DB
    ```sh
    $
    ```

10. start the new application
    ```sh
    $
    ```
