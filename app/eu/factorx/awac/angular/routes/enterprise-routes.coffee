#
# Routes
#
initializeEnterpriseRoutes = (defaultResolve) ->



    angular
    .module('app')
    .run ($rootScope, $location) ->
        $rootScope.onFormPath = (period,scope) ->
            $location.path($rootScope.getFormPath()+'/' + period + '/' + scope)
        $rootScope.getFormPath = ()->
            return '/form/TAB2'

    angular
    .module('app')
    .config ($routeProvider) ->

        $routeProvider
        .when('/form/:form/:period/:scope', {
                templateUrl: ($routeParams) ->
                    '$/angular/views/enterprise/'+$routeParams.form+'.html'
                controller: 'FormCtrl'
                resolve :angular.extend({
                    formIdentifier: ($route) ->
                        return $route.current.params.form
                    displayFormMenu: () ->
                        return true
                }, formResolve)
            }
        )
        .when('/results/:period/:scope', {
                templateUrl: '$/angular/views/results.html'
                controller: 'ResultsCtrl'
                resolve :angular.extend({
                    displayFormMenu: () ->
                        return true
                }, resultResolve)
            }
        )
        .otherwise({ redirectTo: '/login' })

        return
