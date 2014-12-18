# Procédure de migration d'AWAC-ACCEPT de la v1.1 vers la v1.4

1. Lancer les divers **cmd.sh** des versions suivantes *(dans l’ordre donné)*:
    - 1.1 - 1.2
    - 1.2 - 1.3
    - 1.3 - 1.4

2. Lancer les generates suivants:
    - rake generate:surveys
    - rake codelabels:import
    - rake indicators_factors:import
 
3. Lancer les divers **cmd_end.sh** des versions suivantes *(dans l’ordre donné)*:
    - 1.3 - 1.4
    - 1.2 - 1.3 *(ce dernier doit générer une erreur)*

4. Lancer les imports de surveys suivants:
    - rake surveys:import:enterprise
    - rake surveys:import:municipality
    - rake surveys:import:household
    - rake surveys:import:event
    - rake surveys:import:little_emitter
 
5. Lancer les divers **cmd_end.sh** qui a raté précédemment:
    - 1.2 - 1.3 *(il ne doit plus y avoir d’erreur maintenant)*
