#
# Modules
#

angular.module 'app.directives', ['ngAnimate',
                                  'ngSanitize',
                                  'ui.bootstrap',
                                  'ui.bootstrap.datetimepicker',
                                  'ngCkeditor',
                                  'ui.sortable',
                                  'RecursionHelper']

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services',
                                   'ngRoute',
                                   'angularFileUpload',
                                   'tmh.dynamicLocale',
                                   'ngTable']

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

logoutResolve=
    logout:($rootScope,downloadService) ->
        console.log 'logout !! '
        $rootScope.currentPerson = null
        $rootScope.periodSelectedKey = null
        $rootScope.scopeSelectedId = null
        $rootScope.mySites = null
        $rootScope.organizationName = null
        downloadService.postJson '/awac/logout', null, (result) ->
            location.href = '/' + $rootScope.instanceName


defaultResolve =
    testConnection: ($http, $rootScope, $location, downloadService) ->

        # if the current user is null...
        if not $rootScope.currentPerson
            console.log 'test authentication because not curennt person : defaultResolve'
            downloadService.postJson '/awac/testAuthentication', {interfaceName: $rootScope.instanceName}, (result) ->
                if result.success
                    $rootScope.loginSuccess result.data, !$rootScope.isLogin()
                else
                    console.log 'to login  : defaultResolve'
                    $location.path '/login'

formResolve =
# test if the user is currently connected
    testConnection: ($http, $rootScope, $location, downloadService, $route) ->
        # if the current user is null...
        if not $rootScope.currentPerson

            # send request to control connection
            console.log 'test authentication because not curennt person : formResolve'

            downloadService.postJson '/awac/testAuthentication', {interfaceName: $rootScope.instanceName}, (result) ->

                # if success, add information into rootScope
                if result.success
                    $rootScope.loginSuccess result.data, !$rootScope.isLogin()
                    if $rootScope.testForm($route.current.params.period, $route.current.params.scope) == false
                        $location.path "/noScope"

                    # if no connected, send to /login route
                else
                    console.log 'to login  : formResolve'
                    $location.path '/login'
        else
            if $rootScope.testForm($route.current.params.period, $route.current.params.scope) == false
                $location.path '/noScope'

resultResolve =
    testConnection: ($http, $rootScope, $location, downloadService, $route) ->

        # if the current user is null...
        if not $rootScope.currentPerson
            console.log 'test authentication because not curennt person defaultResolve : resultResolve'
            downloadService.postJson '/awac/testAuthentication', {interfaceName: $rootScope.instanceName}, (result) ->
                if result.success
                    $rootScope.loginSuccess result.data, !$rootScope.isLogin()

                    # control data access
                    if $rootScope.testForm($route.current.params.period, $route.current.params.scope) == false
                        $location.path '/noScope'
                else
                    console.log 'to login  : resultResolve'
                    $location.path '/login'


if document.querySelector("meta[name=app]")?
    iName = document.querySelector("meta[name=app]").getAttribute("content")
    if iName == "municipality"
        initializeMunicipalityRoutes(defaultResolve)
    else if iName == "enterprise"
        initializeEnterpriseRoutes(defaultResolve)
    else if iName == "verification"
        initializeVerificationRoutes(defaultResolve)
    else if iName == "littleemitter"
        initializeLittleEmitterRoutes(defaultResolve)
    else if iName == "event"
        initializeEventRoutes(defaultResolve)
    else if iName == "household"
        initializeHouseholdRoutes(defaultResolve)
    else if iName == "admin"
        initializeAdminRoutes(defaultResolve)

initializeCommonRoutes(defaultResolve)

angular
.module('app')
.run ($rootScope) ->
    $rootScope.instanceName = iName

#
# Automation of file download
#
angular
.module('app')
.config ($httpProvider) ->
    $httpProvider.interceptors.push () ->
        'request': (config) ->
            return config
        'response': (response) ->
            if response.data.__type == "eu.factorx.awac.dto.awac.get.DownloadFileDTO"
                setTimeout(()->
                    byteCharacters = atob(response.data.base64)
                    byteNumbers = new Array(byteCharacters.length)
                    for i in [0...byteCharacters.length]
                        byteNumbers[i] = byteCharacters.charCodeAt(i)
                    byteArray = new Uint8Array(byteNumbers)
                    blob = new Blob([byteArray], { type: response.data.mimeType })
                    filename = response.data.filename

                    saveAs(blob, filename)
                , 0)
            return response