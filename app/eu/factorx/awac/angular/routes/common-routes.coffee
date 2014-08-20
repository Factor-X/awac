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
        .otherwise({ redirectTo: '/login' })

        return
