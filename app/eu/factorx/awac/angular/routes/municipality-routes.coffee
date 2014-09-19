#
# Routes
#
initializeMunicipalityRoutes = (defaultResolve) ->

    angular
    .module('app')
    .run ($rootScope, $location) ->
        $rootScope.onFormPath = (period,scope) ->
            $location.path($rootScope.getFormPath()+'/' + period + '/' + scope)
        $rootScope.getFormPath = ()->
            return '/municipality-tab1'



    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider

        .when('/municipality-tab1/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB_C1.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB_C1'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-tab2/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB_C2.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB_C2'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-tab3/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB_C3.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB_C3'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-tab4/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB_C4.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB_C4'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/municipality-tab5/:period/:scope', {
                templateUrl: '$/angular/views/municipality/TAB_C5.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB_C5'
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

