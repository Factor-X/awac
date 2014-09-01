# Procedure

Steps to migrate AWAC to one upper version 

## Steps

All commands are given from the root play directory.

1. Establish a release note with:
    - all new features
    - all bug fixes
    - all changes of behavior in the application
    - all removed/disabled features (should not happen)

2. Set the production instance to maitenance mode to avoid side effects

    ```sh
    $ heroku maintenance:on -a awac-accept
    ``` 

3. import production database to local and make a backup (backups are NOT COMMITTED !!! and this is intended)

    **This tool requires:**
        - pg_dump and psql
        - the heroku toolbelt
        - an account on heroku with admin access to the instance
        - curl

    ```sh
    $ ./deployment.sh
    ```
    
4. execute the cmd.sh script which contains all migrations (SQL + through controller)

    ```sh
    $ cd migrations/migration-data-v0.4-v0.5
    $ ./cmd.sh
    $ cd ../..
    ```
5. Re-run the tests

    ```sh
    $ activator test
    ```

6. Send the local database to production

    ```sh
    $ ./deployment.sh --step4
    ```

7. Deploy the application

    ```sh
    $ heroku repo:purge_cache -a awac-accept
    $ git push awac-accept local_branch:master -f
    ```
    - **-f** is used to ignore parenting problems between remote and local repositories (REQUIRED)

    **Note:** If an error *"SOURCE"* or *"GZIP"* occurs during the deployment, juste re-run it. This is a known problem in Play2, will be fixed in the future.

8. Put the production instance online

    ```sh
    $ heroku maintenance:off -a awac-accept
    ```

9. Test the deployed instance (old and new features).
   - Technique:
       - Verify that **public/javascript/app.js** is in the last version by comparing with the local version. If not, see notes.
       - Verify at least one new and one old logins
       - Verify all translations
   - Features:
       - Check all features of the release note are present in the deployed version
   - Cross check: ask a 2nd person to test.

10. Everything ok ? Make a tag with the version ( for example: "v0.5" ) and keep the SQL backup in safety on at least 2 external hard drives (what about the synology ?).

11. May the force be with you

## Notes

- It frequently appears that the deployed app is **not the last version**. This is a cache problem in the Heroku Slug system. Re-run the **cache purge** again and again (yes... on that point heroku sucks and there is currently no fix, use the force Luke).

- The **deployment is slow**, yeah we know... however it should work (heroku allows a compiling of max 15 minutes, that time elapsed, the compiler slug gets killed, brutaly... no mercy)



