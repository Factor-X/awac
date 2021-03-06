#
# Routes
#
initializeLittleEmitterRoutes = (defaultResolve) ->
    angular
    .module('app')
    .run ($rootScope, $location) ->
        $rootScope.onFormPath = (period, scope) ->
            $location.path($rootScope.getFormPath() + '/' + period + '/' + scope)
        $rootScope.getFormPath = ()->
            return '/form/TAB_P1'
        $rootScope.getDefaultRoute = ()->
            return $rootScope.getFormPath()

    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider
        .when('/form/:form/:period/:scope', {
                templateUrl: ($routeParams) ->
                    '$/angular/views/littleemitter/' + $routeParams.form + '.html'
                controller: 'FormCtrl'
                resolve: angular.extend({
                    helpPage: () ->
                        return 'help:form'
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
                        return 'help:results'
                }, resultResolve)

            }
        )
        .when('/actions/:period/:scope', {
                templateUrl: '$/angular/views/actions.html'
                controller: 'ActionsCtrl'
                resolve: angular.extend({
                    displayFormMenu: () ->
                        return true
                    helpPage: () ->
                        return 'help:actions'
                }, defaultResolve)

            }
        )

        return
