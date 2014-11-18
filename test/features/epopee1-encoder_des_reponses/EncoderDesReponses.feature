@TestingEncoderDesReponses
Feature: Encoder des reponses

  Narration : Un utilisateur consulte les réponses à fournir
    En tant qu’utilisateur du calculateur
    Je veux voir les réponses que je dois fournir
    De telle sorte que je puisse (me préparer à) y répondre
    Et que je puisse alors obtenir le bilan GES de mon organisation

  Scenario: J ai des réponses à fournir
    Given je suis un membre d’une organisation
    When j ai des réponses à des questions à fournir
    Then MYRMEX me propose de consulter les réponses à fournir

  Scenario: Afficher une question avec une réponse texte à fournir
    Given je suis un membre d’une organisation
    And j’ai une réponse texte à fournir au sein d’un formulaire
    When je consulte les réponses à fournir
    Then MYRMEX affiche la question suivie d’un champ de texte permettant d’y écrire la réponse sous forme de texte libre

  Scenario: Afficher une question avec une réponse nombre sans unité à fournir
    Given je suis un membre d’une organisation
    And j’ai une réponse nombre sans unité à fournir au sein d’un formulaire
    When je consulte les réponses à fournir
    Then MYRMEX affiche la question suivie d’un champ de nombre permettant d’y écrire la réponse sous forme de nombre uniquement



