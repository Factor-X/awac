#
# Routes
#
initializeEnterpriseRoutes = () ->

    angular
    .module('app')
    .run ($rootScope, $location) ->
        $rootScope.onLoginSuccess = (data) ->
            $location.path('/enterprise-tab2/' + data.defaultPeriod + '/' + data.organization.sites[0].scope)


    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider

        .when('/enterprise-tab2/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/TAB2.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB2'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/enterprise-tab3/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/TAB3.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB3'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/enterprise-tab4/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/TAB4.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB4'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/enterprise-tab5/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/TAB5.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB5'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/enterprise-tab6/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/TAB6.html'
                controller: 'FormCtrl'
                resolve:{
                    formIdentifier: () ->
                        return 'TAB6'
                    displayFormMenu: () ->
                        return true
                }
            }
        )
        .when('/enterprise-tab7/:period/:scope', {
                templateUrl: '$/angular/views/enterprise/TAB7.html'
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
