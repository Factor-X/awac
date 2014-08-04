angular
.module('app.controllers')
.controller "MainCtrl", ($scope, downloadService, translationService, $sce, $http, $location, $route, $routeParams, modalService) ->

    #
    # First loading
    #
    $scope.isLoading = ->
        for k of $scope.initialLoad
            return true  unless $scope.initialLoad[k]
        false

    $scope.initialLoad =
        translations: false

    #
    # Initialize
    #
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

    #
    # ask confirmation before leave the site
    #
    window.onbeforeunload = (event) ->
        canBeContinue = true

        # test if the main current scope have a validNavigation function and if this function return a false
        if $scope.getMainScope().validNavigation != undefined
            # ask a confirmation to quite the view
            result = $scope.getMainScope().validNavigation()

            if result.valid == false
                return "Certaines données n'ont pas été sauvegardées. Êtes-vous sûr de vouloir quittez cette page ?"

    #
    # use the nav from an event
    # use the args.loc to specify the target loc
    #
    $scope.$on 'NAV', (event, args) ->
        $scope.nav(args.loc, args.confirmed)


    #
    # Tabs -- transition
    # loc : the localisation targeted
    # confirmed : the modification of localisation was already confirmed by the user
    #
    $scope.nav = (loc, confirmed = false) ->
        canBeContinue = true

        # test if the main current scope have a validNavigation function and if this function return a false
        if $scope.getMainScope().validNavigation != undefined && confirmed == false
            # ask a confirmation to quite the view
            result = $scope.getMainScope().validNavigation()

            if result.valid == false
                canBeContinue = false
                params = {}
                params.loc = loc
                modalService.show result.modalForConfirm, params
        if canBeContinue
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

        #hide data to compare if the period is the same than the period to answer
        if $scope.period == $scope.periodToCompare
            $scope.periodToCompare = null

        $scope.loadPeriodForComparison()
        $scope.loadFormProgress()

    $scope.$watch 'scopeId', () ->
        $routeParams.period = $scope.period
        if $route.current
            p = $route.current.$$route.originalPath

            for k,v of $routeParams
                p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v)

            $location.path(p)

        $scope.loadPeriodForComparison()
        $scope.loadFormProgress()

    $scope.periodToCompare = null


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


    $scope.getMainScope = ->
        return mainScope = angular.element($('[ng-view]')[0]).scope()


    $scope.loadPeriodForComparison = ->
        console.log('$scope.scopeId ==')
        console.log($scope.scopeId)

        url = 'answer/getPeriodsForComparison/' + $scope.scopeId
        if $scope.scopeId != null && $scope.scopeId != undefined && $scope.scopeId != NaN && $scope.scopeId != 'NaN'

            promise = $http
                method: "GET"
                url: url
                headers:
                    "Content-Type": "application/json"
            promise.success (data, status, headers, config) ->
                $scope.$root.periodsForComparison = []
                for period in data.periodDTOList
                    if period.id != $routeParams.period
                        $scope.$root.periodsForComparison[$scope.$root.periodsForComparison.length] = period

                if $scope.$root.periodsForComparison == 0
                    $scope.$root.periodsForComparison = [
                        {'id': null, 'label': '-'}
                    ]
                return

            promise.error (data, status, headers, config) ->
                return

    $scope.getProgress = (form)->
        if $scope.formProgress != null
            for formProgress in $scope.formProgress
                if formProgress.form == form
                    return formProgress.percentage
        return 0


    $scope.formProgress = null

    $scope.loadFormProgress = ->
        if $scope.scopeId != undefined && $scope.scopeId != null && $scope.period != null && $scope.period != undefined
            promise = $http
                method: "GET"
                url: "answer/formProgress/" + $scope.period + "/" + $scope.scopeId
                headers:
                    "Content-Type": "application/json"
            promise.success (data, status, headers, config) ->
                $scope.formProgress = data.listFormProgress
                return


    #lastSaveTime TEMP
    $scope.lastSaveTime = new Date() #.getTimezoneOffset()
    console.log "Date.getTimezoneOffset"
    console.log new Date().getTimezoneOffset()


#rootScope
angular.module('app').run ($rootScope, $location, $http, flash)->
    $rootScope.periodsForComparison = [
        {'id': null, 'label': '-'}
    ]

    #
    # Redirect user to login view if not logged in
    #
    if not $rootScope.currentPerson
        $rootScope.referrer = $location.path()
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
        $rootScope.users = data.organization.users

        if $rootScope.referrer
            $location.path($rootScope.referrer)
        else
            $location.path('/form1/' + data.defaultPeriod + '/' + data.organization.sites[0].scope)


    #get user
    $rootScope.getUserByIdentifier = (identifier) ->
        for user in $rootScope.users
            if user.identifier == identifier
                return user
        return null

    #
    # test if the user is currently connected on the server
    #
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
    # parameter to display / hide the modal background
    #
    $rootScope.displayModalBackground = false
