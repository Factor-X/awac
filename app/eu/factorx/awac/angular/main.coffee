angular.module 'app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'dangle']

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services', 'ngRoute','angular-flash.service', 'angular-flash.flash-alert-directive']

angular.module 'app', [
    'app.directives',
    'app.filters',
    'app.services',
    'app.controllers'
]

angular
.module('app.controllers')
.config ($routeProvider) ->
    $routeProvider
    .when('/login', {
            templateUrl: '$/angular/templates/mm-awac-login.html'
            controller: 'LoginCtrl'
        })
    .when('/form1/:period/:scope', {
            templateUrl: '$/angular/templates/mm-awac-form1.html'
            controller: 'Form1Ctrl'
        })
    .when('/form2/:period/:scope', {
            templateUrl: '$/angular/templates/mm-awac-form2.html'
            controller: 'Form2Ctrl'
        })
    .when('/form3/:period/:scope', {
            templateUrl: '$/angular/templates/mm-awac-form3.html'
            controller: 'Form3Ctrl'
        })
    .when('/form4/:period/:scope', {
            templateUrl: '$/angular/templates/mm-awac-form4.html'
            controller: 'Form4Ctrl'
        })
    .when('/form5/:period/:scope', {
            templateUrl: '$/angular/templates/mm-awac-form5.html'
            controller: 'Form5Ctrl'
        })
    .when('/form6/:period/:scope', {
            templateUrl: '$/angular/templates/mm-awac-form6.html'
            controller: 'Form6Ctrl'
        })
    .when('/form7/:period/:scope', {
            templateUrl: '$/angular/templates/mm-awac-form7.html'
            controller: 'Form7Ctrl'
        })
    .when('/results/:period/:scope', {
            templateUrl: '$/angular/templates/mm-awac-results.html'
            controller: 'ResultsCtrl'
        })
    .otherwise({ redirectTo: '/login' })

    return