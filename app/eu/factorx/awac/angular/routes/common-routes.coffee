#
# Routes
#
initializeCommonRoutes = () ->
    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider

        .when('/login', {
                templateUrl: '$/angular/views/login.html'
                controller: 'LoginCtrl'
                anonymousAllowed: true
            }
        )
        .when('/admin', {
                templateUrl: '$/angular/views/admin.html'
                controller: 'AdminCtrl'
            }
        )
        .when('/user_data/:period/:scope', {
                templateUrl: '$/angular/views/user_data.html'
                controller: 'UserDataCtrl'
            }
        )
        .when('/user_manager/:period/:scope', {
                templateUrl: '$/angular/views/user_manager.html'
                controller: 'UserManagerCtrl'
            }
        )
        .when('/site_manager/:period/:scope', {
                templateUrl: '$/angular/views/site_manager.html'
                controller: 'SiteManagerCtrl'
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
                controller: 'FormCtrl'
                anonymousAllowed: true
            }
        )
        .otherwise({ redirectTo: '/login' })

        return
