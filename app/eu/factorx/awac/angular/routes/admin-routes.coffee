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
                controller: 'AdminAdvicesManagerCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/translation', {
                templateUrl: '$/angular/views/admin/translation.html'
                controller: 'AdminTranslationCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/average', {
                templateUrl: '$/angular/views/admin/average.html'
                controller: 'AdminAverageCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/organization_data', {
                templateUrl: '$/angular/views/admin/organization_data.html'
                controller: 'AdminOrganizationDataCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )


        .otherwise({ redirectTo: '/login' })

        return
