#
# Routes
#
initializeAdminRoutes = (defaultResolve) ->
    angular
    .module('app')
    .run ($rootScope, $location) ->
        $rootScope.onFormPath = (period, scope) ->
            $location.path($rootScope.getFormPath() + '/' + period + '/' + scope)
        $rootScope.getDefaultRoute = ()->
            return '/driver'

    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider
        .when('/driver', {
                templateUrl: '$/angular/views/admin/driver.html'
                controller: 'AdminDriverManageCtrl'
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/factors', {
                templateUrl: '$/angular/views/admin/factors.html'
                controller: 'AdminFactorsManagerCtrl'
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/advice', {
                templateUrl: '$/angular/views/admin/manage_advices.html'
                controller: 'AdminActionAdviceCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .otherwise({ redirectTo: '/login' })

        return
