#
# Routes
#
initializeEnterpriseRoutes = () ->

    angular
    .module('app')
    .run ($rootScope, $location) ->
        $rootScope.onLoginSuccess = (data) ->
            $location.path('/enterprise-form2/' + data.defaultPeriod + '/' + data.organization.sites[0].scope)


    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider

        .when('/enterprise-form2/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/form2.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB2'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/enterprise-form3/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/form3.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB3'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/enterprise-form4/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/form4.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB4'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/enterprise-form5/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/form5.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB5'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/enterprise-form6/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/form6.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB6'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/enterprise-form7/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/form7.html'
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
