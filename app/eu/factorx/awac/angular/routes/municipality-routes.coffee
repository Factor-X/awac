#
# Routes
#
initializeMunicipalityRoutes = () ->

    angular
    .module('app')
    .run ($rootScope, $location) ->
        $rootScope.onLoginSuccess = (data) ->
            $location.path('/municipality-form1/' + data.defaultPeriod + '/' + data.organization.sites[0].scope)



    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider

        .when('/municipality-form1/:period/:scope', {
                templateUrl: '$/angular/views/municipality/form1.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB1'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-form2/:period/:scope', {
                templateUrl: '$/angular/views/municipality/form2.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB2'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-form3/:period/:scope', {
                templateUrl: '$/angular/views/municipality/form3.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB3'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-form4/:period/:scope', {
                templateUrl: '$/angular/views/municipality/form4.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB4'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-form5/:period/:scope', {
                templateUrl: '$/angular/views/municipality/form5.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB5'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-form6/:period/:scope', {
                templateUrl: '$/angular/views/municipality/form6.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB6'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-form7/:period/:scope', {
                templateUrl: '$/angular/views/municipality/form7.html'
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

