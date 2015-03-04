# Migration v1.4 - v1.5

1. Restore **awac-accept** DB in local

    ```sh
    $ ./deployment.sh
    ```

2. Execute SQL's through **cmd.sh**

    ```sh
    $ cd migrations/migration-data-v1.4-v1.5
    $ ./cmd.sh
    $ cd ../..
    ```

3. Start activator and test

