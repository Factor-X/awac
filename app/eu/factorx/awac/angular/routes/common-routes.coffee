#
# Routes
#
initializeCommonRoutes = (defaultResolve) ->
    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider
        .when('/login', {
                templateUrl: '$/angular/views/login.html'
                controller: 'LoginCtrl'
                anonymousAllowed: true
                resolve:
                    testConnection: ($http, $rootScope, $location, downloadService) ->
                        # if the current user is null...
                        if $rootScope.currentPerson?
                            $rootScope.toDefaultForm()
                        else
                            console.log "testAuthentication to initializeCommonRoutes "
                            downloadService.postJson '/awac/testAuthentication', {interfaceName: $rootScope.instanceName}, (result) ->
                                if result.success
                                    $rootScope.loginSuccess result.data, !$rootScope.isLogin()
                                    $rootScope.toDefaultForm()
                                    return true
                                else
                                    return true
            }
        )
        .when('/admin', {
                templateUrl: '$/angular/views/admin.html'
                controller: 'AdminCtrl'
                resolve: defaultResolve
            }
        )
        .when('/user_data', {
                templateUrl: '$/angular/views/user_data.html'
                controller: 'UserDataCtrl'
                resolve: angular.extend({
                    helpPage: () ->
                        return 'help_user_data'
                }, defaultResolve)
            }
        )
        .when('/user_manager', {
                templateUrl: '$/angular/views/user_manager.html'
                controller: 'UserManagerCtrl'
                resolve: angular.extend({
                    helpPage: () ->
                        return 'help_user_manager'
                }, defaultResolve)

            }
        )
        .when('/site_manager', {
                templateUrl: '$/angular/views/site_manager.html'
                controller: 'SiteManagerCtrl'
                resolve: angular.extend({
                    helpPage: () ->
                        return 'help_site_manager'
                }, defaultResolve)

            }
        )
        .when('/registration/:key', {
                templateUrl: '$/angular/views/user_registration.html'
                controller: 'RegistrationCtrl'
                anonymousAllowed: true
            }
        )
        .when('/noScope', {
                templateUrl: '$/angular/views/no_scope.html'
                controller: 'NoScopeCtrl'
                anonymousAllowed: true
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .otherwise({ redirectTo: '/login' })

        return
