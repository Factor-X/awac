#
# Routes
#
initializeMunicipalityRoutes = (defaultResolve) ->
    angular
    .module('app')
    .run ($rootScope, $location) ->
        $rootScope.onFormPath = (period, scope) ->
            $location.path($rootScope.getFormPath() + '/' + period + '/' + scope)
        $rootScope.getFormPath = ()->
            return '/form/TAB_C1'

    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider
        .when('/form/:form/:period/:scope', {
                templateUrl: ($routeParams) ->
                    '$/angular/views/municipality/' + $routeParams.form + '.html'
                controller: 'FormCtrl'
                resolve: angular.extend({
                    formIdentifier: ($route) ->
                        return $route.current.params.form
                    displayFormMenu: () ->
                        return true
                    helpPage: () ->
                        return 'help_form'
                }, formResolve)

            }
        )
        .when('/results/:period/:scope', {
                templateUrl: '$/angular/views/results.html'
                controller: 'ResultsCtrl'
                resolve: angular.extend({
                    displayFormMenu: () ->
                        return true
                    helpPage: () ->
                        return 'help_results'
                }, defaultResolve)
            }
        )
        .when('/actions/:period/:scope', {
                templateUrl: '$/angular/views/actions.html'
                controller: 'ActionsCtrl'
                resolve: angular.extend({
                    displayFormMenu: () ->
                        return true
                    helpPage: () ->
                        return 'help_actions'
                }, defaultResolve)
            }
        )
        .otherwise({ redirectTo: '/login' })

        return

