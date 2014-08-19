#
# Modules
#

angular.module 'app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap']

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
    .when('/admin', {
            templateUrl: '$/angular/views/admin.html'
            controller: 'AdminCtrl'
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
    .when('/TAB2/:period/:scope', {
            templateUrl: '$/angular/views/TAB2.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB2'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/TAB3/:period/:scope', {
            templateUrl: '$/angular/views/TAB3.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB3'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/TAB4/:period/:scope', {
            templateUrl: '$/angular/views/TAB4.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB4'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/TAB5/:period/:scope', {
            templateUrl: '$/angular/views/TAB5.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB5'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/TAB6/:period/:scope', {
            templateUrl: '$/angular/views/TAB6.html'
            controller: 'FormCtrl'
            resolve:{
                formIdentifier: () ->
                    return 'TAB6'
                displayFormMenu: () ->
                    return true
            }
        }
    )
    .when('/TAB7/:period/:scope', {
            templateUrl: '$/angular/views/TAB7.html'
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
