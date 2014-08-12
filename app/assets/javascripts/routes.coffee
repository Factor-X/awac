define ["./app"], (app) ->
  "use strict"
  app.config [
    "$routeProvider"
    ($routeProvider) ->

      $routeProvider.when "/login",
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/login.html'
        controller: 'LoginCtrl'

      $routeProvider.when '/user_data/:period/:scope',
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/user_data.html'
        controller: 'UserDataCtrl'

      $routeProvider.when '/user_manager/:period/:scope',
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/user_manager.html'
        controller: 'UserManagerCtrl'

      $routeProvider.when '/site_manager/:period/:scope',
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/site_manager.html'
        controller: 'SiteManagerCtrl'

      $routeProvider.when '/form1/:period/:scope',
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/form1.html'
        controller: 'FormCtrl'
        resolve:
          formIdentifier: ->
            return 'TAB1'
          displayFormMenu: ->
            return true

      $routeProvider.when '/form2/:period/:scope',
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/form2.html'
        controller: 'FormCtrl'
        resolve:
          formIdentifier: ->
            return 'TAB2'
          displayFormMenu: ->
            return true

      $routeProvider.when '/form3/:period/:scope',
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/form3.html'
        controller: 'FormCtrl'
        resolve:
          formIdentifier: ->
            return 'TAB3'
          displayFormMenu: ->
            return true

      $routeProvider.when '/form4/:period/:scope',
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/form4.html'
        controller: 'FormCtrl'
        resolve:
          formIdentifier: ->
            return 'TAB4'
          displayFormMenu: ->
            return true

      $routeProvider.when '/form5/:period/:scope',
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/form5.html'
        controller: 'FormCtrl'
        resolve:
          formIdentifier: ->
            return 'TAB5'
          displayFormMenu: ->
            return true

      $routeProvider.when '/form6/:period/:scope',
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/form6.html'
        controller: 'FormCtrl'
        resolve:
          formIdentifier: ->
            return 'TAB6'
          displayFormMenu: ->
            return true

      $routeProvider.when '/form7/:period/:scope',
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/form7.html'
        controller: 'FormCtrl'
        resolve:
          formIdentifier: ->
            return 'TAB7'
          displayFormMenu: ->
            return true

      $routeProvider.when '/results/:period/:scope',
        templateUrl: 'assets/html/sources/app/eu/factorx/awac/angular/views/results.html'
        controller: 'ResultsCtrl'
        resolve:
          displayFormMenu: ->
            return true

      $routeProvider.otherwise redirectTo: "/login"
  ]