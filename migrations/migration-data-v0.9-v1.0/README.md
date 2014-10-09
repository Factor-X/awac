# Migration v0.8 - v1.0

1. Restore **awac-accept** DB in local

    ```sh
    $ ./deployment.sh
    ```
    
2. Execute SQL's through **cmd.sh**

    ```sh
    $ cd migrations/migration-data-v0.9-v1.0
    $ ./cmd.sh
    $ cd ../..
    ```
    
    Under MacOS, Netstat and CURL seem to run weird, so the **cmd.sh does not contain the call to importers anymore.** -> see point 4.


3. Start activator


4. Run the generators

    - Go into the folder
    
        ```sh
        $ cd generators
        ```
        
    - Run CodeLabels & Indicators / Factors importers
        
        ```sh
        $ rake codelabels:import
        $ rake indicators_factors:import
        ```


  