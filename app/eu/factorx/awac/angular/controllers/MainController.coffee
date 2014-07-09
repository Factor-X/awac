angular
.module('app.controllers')
.config ($routeProvider) ->
    $routeProvider
    .when('/form1', {
            templateUrl: '$/angular/templates/mm-awac-form1.html'
            controller: 'Form1Ctrl'
        })
    .when('/form2', {
            templateUrl: '$/angular/templates/mm-awac-form2.html'
            controller: 'Form2Ctrl'
        })
    .when('/form3', {
            templateUrl: '$/angular/templates/mm-awac-form3.html'
            controller: 'Form3Ctrl'
        })
    .when('/form4', {
            templateUrl: '$/angular/templates/mm-awac-form4.html'
            controller: 'Form4Ctrl'
        })
    .when('/form5', {
            templateUrl: '$/angular/templates/mm-awac-form5.html'
            controller: 'Form5Ctrl'
        })
    .when('/form6', {
            templateUrl: '$/angular/templates/mm-awac-form6.html'
            controller: 'Form6Ctrl'
        })
    .when('/results', {
            templateUrl: '$/angular/templates/mm-awac-results.html'
            controller: 'ResultsCtrl'
        })
    .otherwise({ redirectTo: '/form1' })

    return

.controller "MainCtrl", ($scope, downloadService, translationService, $sce, $modal, $http, $location, $route) ->
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

    $scope.language = 'fr';

    $scope.$watch 'language', (lang) ->
        translationService.initialize(lang)

    #$scope.prettyPrint = (o) ->
    #    return $sce.trustAsHtml(hljs.highlight('json', JSON.stringify(o, null, '  ')).value);


    $scope.msg = "HELLO"

    $scope.logout = () ->
        console.log("logout ??")
        promise = $http
            method: "POST"
            url: 'logout'
            headers:
                "Content-Type": "application/json"
        promise.success (data, status, headers, config) ->
            $scope.currentUser = null
            $('#modalLogin').modal('show')
            return

        promise.error (data, status, headers, config) ->
            return

    $scope.currentUser = null

    $scope.setCurrentUser = (user) ->
        $scope.currentUser = user

    $scope.getCurrentUser = () ->
        return $scope.currentUser

    $scope.testAuthentication = () ->
        promise = $http
            method: "POST"
            url: 'testAuthentication'
            headers:
                "Content-Type": "application/text"

        promise.success (data, status, headers, config) ->
            $scope.setCurrentUser(data)
            return

        promise.error (data, status, headers, config) ->
            $('#modalLogin').modal('show')
            $scope.$apply()
            return

    #test the authentication of the user
    $scope.testAuthentication()

    $scope.getMenuCurrentClass = (loc) ->
        if $location.path() == loc
            return "menu_current"
        else
            return ""

    $scope.nav = (loc) ->
        $location.path(loc)
        console.log $route
        console.log $location

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
    $scope.period = 24
    # $scope.$watch 'period', () ->



    $scope.save = () ->
        $scope.$broadcast 'SAVE'


