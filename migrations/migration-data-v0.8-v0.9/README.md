# Migration v0.8 - v0.9

1. Restore **awac-accept** DB in local

    ```sh
    $ ./deployment
    ```
    
2. Execute SQL's through **cmd.sh**

    ```sh
    $ cd migrations/migration-data-v0.8-v0.9
    $ ./cmd.sh
    $ cd ../..
    ```
    
    Under MacOS, Netstat and CURL seem to run weird, so the **cmd.sh does not contain the call to importers anymore.** -> see point 3.
    
3. Run the generators

    - Go into the folder
    
        ```sh
        $ cd generators
        ```
        
    - Surveys
    
        ```sh
        $ rake generate:surveys
        ```
        
    - Restart activator
        
    - CodeLabels
        
        ```sh
        $ rake codelabels:import
        ```
        
    - Indicators / Factors
    
        ```sh
        $ rake indicators_factors:import
        ```    
        
    - Restart activator
    
    - Surveys
    
         ```sh
         $ rake surveys:import:enterprise
         $ rake surveys:import:municipality
         ``` 
           
    - Restart activator
            
    - CodeLabels
        
        ```sh
        $ rake codelabels:import
        ```
        
    - Indicators / Factors
    
        ```sh
        $ rake indicators_factors:import
        ```
            
    - Restart activator
        
    - BADS
    
        ```sh
        $ rake bads:import:enterprise
        $ rake bads:import:municipality
        ```       
    
    
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  