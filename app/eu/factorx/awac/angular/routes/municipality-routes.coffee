#
# Routes
#
initializeMunicipalityRoutes = () ->

    angular
    .module('app')
    .run ($rootScope, $location) ->
        $rootScope.onLoginSuccess = (data) ->
            $location.path('/municipality-tab1/' + data.defaultPeriod + '/' + data.organization.sites[0].scope)



    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider

        .when('/municipality-tab1/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB1.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB1'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-tab2/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB2.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB2'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-tab3/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB3.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB3'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-tab4/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB4.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB4'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-tab5/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB5.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB5'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-tab6/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB6.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB6'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-tab7/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB7.html'
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

