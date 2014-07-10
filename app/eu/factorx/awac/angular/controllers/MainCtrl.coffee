angular
.module('app.controllers')
.config ($routeProvider) ->
    $routeProvider
    .when('/login', {
            templateUrl: '$/angular/templates/mm-awac-login.html'
            controller: 'LoginCtrl'
        })
    .when('/form1/:period', {
            templateUrl: '$/angular/templates/mm-awac-form1.html'
            controller: 'Form1Ctrl'
        })
    .when('/form2/:period', {
            templateUrl: '$/angular/templates/mm-awac-form2.html'
            controller: 'Form2Ctrl'
        })
    .when('/form3/:period', {
            templateUrl: '$/angular/templates/mm-awac-form3.html'
            controller: 'Form3Ctrl'
        })
    .when('/form4/:period', {
            templateUrl: '$/angular/templates/mm-awac-form4.html'
            controller: 'Form4Ctrl'
        })
    .when('/form5/:period', {
            templateUrl: '$/angular/templates/mm-awac-form5.html'
            controller: 'Form5Ctrl'
        })
    .when('/form6/:period', {
            templateUrl: '$/angular/templates/mm-awac-form6.html'
            controller: 'Form6Ctrl'
        })
    .when('/results/:period', {
            templateUrl: '$/angular/templates/mm-awac-results.html'
            controller: 'ResultsCtrl'
        })
    .otherwise({ redirectTo: '/login' })

    return

.controller "MainCtrl", ($scope, downloadService, translationService, $sce, $modal, $http, $location, $route, $routeParams) ->

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
        $location.path(loc + "/" + $scope.period)

    #
    # Periods
    #
    $scope.periods = [
        {
            key: 2013,
            value: 24
        },
        {
            key: 2012,
            value: 23
        },
        {
            key: 2011,
            value: 22
        }
    ]
    $scope.period = 0
    $scope.$watch 'period', () ->
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
