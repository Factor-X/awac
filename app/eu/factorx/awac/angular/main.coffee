#
# Modules
#

angular.module 'app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'dangle']

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services', 'ngRoute', 'angular-flash.service',
                                   'angular-flash.flash-alert-directive',
                                   'angularFileUpload']

angular.module 'app', [
    'app.directives',
    'app.filters',
    'app.services',
    'app.controllers'
]

#
# Routes
#

angular
.module('app.controllers')
.config ($routeProvider) ->
    $routeProvider
    .when('/login', {
            templateUrl: '$/angular/views/login.html'
            controller: 'LoginCtrl'
        }
    )
    .when('/form1/:period/:scope', {
            templateUrl: '$/angular/views/form1.html'
            controller: 'FormCtrl'
            resolve:
                formIdentifier: () ->
                    return 'TAB1'
        }
    )
    .when('/form2/:period/:scope', {
            templateUrl: '$/angular/views/form2.html'
            controller: 'FormCtrl'
            resolve:
                formIdentifier: () ->
                    return 'TAB2'
        }
    )
    .when('/form3/:period/:scope', {
            templateUrl: '$/angular/views/form3.html'
            controller: 'FormCtrl'
            resolve:
                formIdentifier: () ->
                    return 'TAB3'
        }
    )
    .when('/form4/:period/:scope', {
            templateUrl: '$/angular/views/form4.html'
            controller: 'FormCtrl'
            resolve:
                formIdentifier: () ->
                    return 'TAB4'
        }
    )
    .when('/form5/:period/:scope', {
            templateUrl: '$/angular/views/form5.html'
            controller: 'FormCtrl'
            resolve:
                formIdentifier: () ->
                    return 'TAB5'
        }
    )
    .when('/form6/:period/:scope', {
            templateUrl: '$/angular/views/form6.html'
            controller: 'FormCtrl'
            resolve:
                formIdentifier: () ->
                    return 'TAB6'
        }
    )
    .when('/form7/:period/:scope', {
            templateUrl: '$/angular/views/form7.html'
            controller: 'FormCtrl'
            resolve:
                formIdentifier: () ->
                    return 'TAB7'
        }
    )
    .when('/results/:period/:scope', {
            templateUrl: '$/angular/views/results.html'
            controller: 'ResultsCtrl'
        }
    )
    .otherwise({ redirectTo: '/login' })

    return
