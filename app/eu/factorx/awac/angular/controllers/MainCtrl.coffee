angular
.module('app.controllers')
.controller "MainCtrl", ($scope, downloadService, translationService, $sce, $http, $location, $route, $routeParams) ->

    #
    # First loading
    #
    $scope.isLoading = ->
        for k of $scope.initialLoad
            return true  unless $scope.initialLoad[k]
        false

    $scope.initialLoad =
        translations: false

    $scope.$on "LOAD_FINISHED", (event, args) ->
        if args.type is "TRANSLATIONS"
            $scope.initialLoad.translations = args.success
        return

    translationService.initialize('fr')

    $scope.language = 'fr'

    $scope.$watch 'language', (lang) ->
        translationService.initialize(lang)

    #
    # Tabs
    #
    $scope.getMenuCurrentClass = (loc) ->
        if $location.path().substring(0, loc.length) == loc
            return "menu_current"
        else
            return ""
    # Tabs -- transition
    $scope.nav = (loc) ->
        $location.path(loc + "/" + $scope.period + "/" + $scope.scopeId)

    #
    # Periods
    #

    $scope.period = 0
    $scope.$watch 'period', () ->
        $routeParams.period = $scope.period
        if $route.current
            p = $route.current.$$route.originalPath

            for k,v of $routeParams
                p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v)

            $location.path(p)
    $scope.$watch 'scopeId', () ->
        $routeParams.period = $scope.period
        if $route.current
            p = $route.current.$$route.originalPath

            for k,v of $routeParams
                p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v)

            $location.path(p)
    #
    # Save
    #
    $scope.save = () ->
        $scope.$broadcast 'SAVE'

    #
    # Route Change
    #
    $scope.$on "$routeChangeSuccess", (event, current, previous) ->
        $scope.period = parseInt($routeParams.period)
        $scope.scopeId = parseInt($routeParams.scope)


#rootScope
angular.module('app').run ($rootScope, $location, $http,flash)->
    console.log("run  !!")

    #
    # Redirect user to login view if not logged in
    #
    if not $rootScope.currentPerson
        $location.path('/login')

    #
    # Is login
    #
    $rootScope.isLogin = () ->
        return $location.path().substring(0, 6) == "/login"

    #
    # logout the current user
    #
    $rootScope.logout = () ->
        console.log("logout ??")
        promise = $http
            method: "POST"
            url: 'logout'
            headers:
                "Content-Type": "application/json"
        promise.success (data, status, headers, config) ->
            $rootScope.currentPerson = null
            $location.path('/login')
            return

        promise.error (data, status, headers, config) ->
            $location.path('/login')
            return
    #
    # success after login => store some datas, display the path
    #
    $rootScope.loginSuccess = (data) ->
        $rootScope.periods = data.availablePeriods
        $rootScope.currentPerson = data.person
        $rootScope.organization = data.organization
        $location.path('/form1/' + data.defaultPeriod + '/' + data.organization.sites[0].scope)

    #
    # test if the user is currently connected on the server
    #
    $rootScope.testAuthentication = () ->
        promise = $http
            method: "POST"
            url: 'testAuthentication'
            headers:
                "Content-Type": "application/text"
        promise.success (data, status, headers, config) ->
            $rootScope.loginSuccess data
            return

        promise.error (data, status, headers, config) ->
            return
    #
    # call the test authentification function
    #
    $rootScope.testAuthentication()



    #
    # display a success message
    # TODO => display multiple messages
    $rootScope.displaySuccess = (message) ->
      flash.success = message

    #
    # display an error message
    # TODO => display multiple messages
    $rootScope.displayError = (message) ->
      flash.error = message


