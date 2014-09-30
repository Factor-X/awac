#
# Modules
#

angular.module 'app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap']

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services', 'ngRoute',
                                   'angularFileUpload','tmh.dynamicLocale','ngTable']

angular.module 'app', [
    'app.directives',
    'app.filters',
    'app.services',
    'app.controllers'
]


angular.module("tmh.dynamicLocale").config (tmhDynamicLocaleProvider)->
    tmhDynamicLocaleProvider.localeLocationPattern('assets/components/angular-i18n/angular-locale_{{locale}}.js')


#
# Routes
#

defaultResolve =
    testConnection : ($http,$rootScope,$location, downloadService) ->

        # if the current user is null...
        if not $rootScope.currentPerson
            downloadService.postJson '/awac/testAuthentication', {interfaceName: $rootScope.instanceName}, (result) ->
                if result.success
                    $rootScope.loginSuccess result.data, !$rootScope.isLogin()
                else
                    $location.path '/login'

formResolve =
    # test if the user is currently connected
    testConnection : ($http,$rootScope,$location, downloadService,$route) ->
        # if the current user is null...
        if not $rootScope.currentPerson

            # send request to control connection
            downloadService.postJson '/awac/testAuthentication', {interfaceName: $rootScope.instanceName}, (result) ->

                # if success, add information into rootScope
                if result.success
                    $rootScope.loginSuccess result.data, !$rootScope.isLogin()
                    if $rootScope.testForm($route.current.params.period,$route.current.params.scope) == false
                        $location.path "/noScope"

                # if no connected, send to /login route
                else
                    $location.path '/login'
        else
            if $rootScope.testForm($route.current.params.period,$route.current.params.scope) == false
                $location.path '/noScope'

resultResolve =
    testConnection : ($http,$rootScope,$location, downloadService,$route) ->

        # if the current user is null...
        if not $rootScope.currentPerson

            downloadService.postJson '/awac/testAuthentication', {interfaceName: $rootScope.instanceName}, (result) ->
                if result.success
                    $rootScope.loginSuccess result.data, !$rootScope.isLogin()

                    # control data access
                    if $rootScope.testForm($route.current.params.period,$route.current.params.scope) == false
                        $location.path '/noScope'
                else
                    $location.path '/login'


initializeCommonRoutes(defaultResolve)
if document.querySelector("meta[name=app]")?
    iName = document.querySelector("meta[name=app]").getAttribute("content")
    if iName == "municipality"
        initializeMunicipalityRoutes(defaultResolve)
    if iName == "enterprise"
        initializeEnterpriseRoutes(defaultResolve)

angular
.module('app')
.run ($rootScope) ->
    $rootScope.instanceName = iName

