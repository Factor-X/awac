#
# Routes
#
initializeVerificationRoutes = (defaultResolve) ->
    angular
    .module('app')
    .run ($rootScope, $location) ->
        $rootScope.onFormPath = (period, scope) ->
            $location.path($rootScope.getFormPath() + '/' + period + '/' + scope)
        $rootScope.getDefaultRoute = ()->
            return '/verification'

    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider
        .when('/manage', {
                templateUrl: '$/angular/views/verification/manage.html'
                controller: 'VerificationManageCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                    helpPage: () ->
                        return 'help:manage'
                }, defaultResolve)
            }
        )
        .when('/verification', {
                templateUrl: '$/angular/views/verification/verification.html'
                controller: 'VerificationVerificationCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                    helpPage: () ->
                        return 'help:verification'
                }, defaultResolve)
            }
        )
        .when('/submit', {
                templateUrl: '$/angular/views/verification/submit.html'
                controller: 'VerificationSubmitCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                    helpPage: () ->
                        return 'help:submit'
                }, defaultResolve)
            }
        )
        .when('/verification_registration/:key', {
                templateUrl: '$/angular/views/verification_registration.html'
                controller: 'VerificationRegistrationCtrl'
                anonymousAllowed: true
            }
        )
        .when('/archive', {
                templateUrl: '$/angular/views/verification/archive.html'
                controller: 'VerificationArchiveCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                    helpPage: () ->
                        return 'help:archive'
                }, defaultResolve)
            }
        )
        .otherwise({ redirectTo: '/login' })

        return
