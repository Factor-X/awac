angular
.module('app.controllers')
.controller "MainCtrl", ($scope, downloadService, translationService, $sce, $http, $location, $route, $routeParams) ->

    #
    # Redirect user to login view if not logged in
    #
    if not $scope.currentUser
        $location.path('/login')

    #
    # Is login
    #
    $scope.isLogin = () ->
        return $location.path().substring(0, 6) == "/login"

    $scope.logout = () ->
        console.log("logout ??")
        promise = $http
            method: "POST"
            url: 'logout'
            headers:
                "Content-Type": "application/json"
        promise.success (data, status, headers, config) ->
            $scope.currentUser = null
            $location.path('/login')
            return

        promise.error (data, status, headers, config) ->
            $location.path('/login')
            return

    $scope.setCurrentUser = (user) ->
        $scope.currentUser = user

    $scope.getCurrentUser = () ->
        return $scope.currentUser

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
    $scope.setPeriods = (periods) ->
        $scope.periods = periods
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
    #
    #
    $scope.setCurrentOrganization = (org) ->
        $scope.organization = org

    #
    # Route Change
    #
    $scope.$on "$routeChangeSuccess", (event, current, previous) ->
        $scope.period = parseInt($routeParams.period)
        $scope.scopeId = parseInt($routeParams.scope)
