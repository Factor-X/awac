# Migration v0.8 - v1.0

1. Restore **awac-dev** DB in local

    ```sh
    $ ./deployment.sh
    ```

2. Execute SQL's through **cmd.sh**

    ```sh
    $ cd migrations/migration-data-v1.3-v1.4
    $ ./cmd.sh
    $ cd ../..
    ```

    Under MacOS, Netstat and CURL seem to run weird, so the **cmd.sh does not contain the call to importers anymore.** -> see point 4.


3. Generate the surveys

    - Go into the folder

        ```sh
        $ cd generators
        ```

    - Surveys

        ```sh
        $ rake generate:surveys
        ```

4. Start activator


5. Execute SQL's through **cmd_end.sh**

    ```sh
    $ cd ../migrations/migration-data-v1.3-v1.4
    $ ./cmd_end.sh
    $ cd ../..
    ```

6. Run the generators

    - Go into the folder

        ```sh
        $ cd generators
        ```

    - Run CodeLabels & Indicators / Factors importers

        ```sh
        $ rake codelabels:import
        $ rake indicators_factors:import
        ```

    - Surveys

         ```sh
         $ rake surveys:import:enterprise
         $ rake surveys:import:municipality
         $ rake surveys:import:household
         $ rake surveys:import:event
         $ rake surveys:import:little_emitter
         ```

