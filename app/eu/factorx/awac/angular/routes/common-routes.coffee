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
                    testConnection : ($http,$rootScope,$location, downloadService) ->
                        # if the current user is null...
                        if $rootScope.currentUser
                            $location.path '/noScope'
                        else
                            console.log "je suis le test connection"
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
                resolve : defaultResolve
            }
        )
        .when('/user_data/:period/:scope', {
                templateUrl: '$/angular/views/user_data.html'
                controller: 'UserDataCtrl'
                resolve : defaultResolve
            }
        )
        .when('/user_manager/:period/:scope', {
                templateUrl: '$/angular/views/user_manager.html'
                controller: 'UserManagerCtrl'
                resolve : defaultResolve
            }
        )
        .when('/site_manager/:period/:scope', {
                templateUrl: '$/angular/views/site_manager.html'
                controller: 'SiteManagerCtrl'
                resolve : defaultResolve
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
                resolve :angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                    displayFormMenu: () ->
                        return false
                }, defaultResolve)
            }
        )
        .otherwise({ redirectTo: '/login' })

        return
