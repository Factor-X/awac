#
# Modules
#

angular.module 'app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'dangle']

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services', 'ngRoute', 'angular-flash.service',
                                   'angular-flash.flash-alert-directive',
                                   'angularFileUpload','tmh.dynamicLocale']

angular.module 'app', [
    'app.directives',
    'app.filters',
    'app.services',
    'app.controllers'
]


angular.module("tmh.dynamicLocale").config (tmhDynamicLocaleProvider)->
    tmhDynamicLocaleProvider.localeLocationPattern('assets/components/angular-i18n/angular-locale_{{locale}}.js')


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
    .when('/user_data/:period/:scope', {
            templateUrl: '$/angular/views/user_data.html'
            controller: 'UserDataCtrl'
        }
    )
    .when('/user_manager/:period/:scope', {
            templateUrl: '$/angular/views/user_manager.html'
            controller: 'UserManagerCtrl'
        }
    )
    .when('/site_manager/:period/:scope', {
            templateUrl: '$/angular/views/site_manager.html'
            controller: 'SiteManagerCtrl'
        }
    )
    .when('/form1/:period/:scope', {
            templateUrl: '$/angular/views/form1.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB1'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/form2/:period/:scope', {
            templateUrl: '$/angular/views/form2.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB2'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/form3/:period/:scope', {
            templateUrl: '$/angular/views/form3.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB3'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/form4/:period/:scope', {
            templateUrl: '$/angular/views/form4.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB4'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/form5/:period/:scope', {
            templateUrl: '$/angular/views/form5.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB5'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/form6/:period/:scope', {
            templateUrl: '$/angular/views/form6.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB6'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/form7/:period/:scope', {
            templateUrl: '$/angular/views/form7.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB7'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/results/:period/:scope', {
            templateUrl: '$/angular/views/results.html'
            controller: 'ResultsCtrl'
            resolve:{
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .otherwise({ redirectTo: '/login' })

    return
