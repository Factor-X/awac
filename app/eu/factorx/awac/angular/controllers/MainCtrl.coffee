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
    $scope.isMenuCurrentlySelected = (loc) ->
        if $location.path().substring(0, loc.length) == loc
            return true
        else
            return false

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
            $location.path(loc + "/" + $scope.periodKey + "/" + $scope.scopeId)

            # used to recompute the displaying of the menu
            if !$scope.$$phase
                $scope.$apply()

    #
    # Periods
    #
    $scope.periodKey = null
    $scope.$watch 'periodKey', () ->
        $routeParams.period = $scope.periodKey
        if $route.current
            p = $route.current.$$route.originalPath

            for k,v of $routeParams
                p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v)

            $location.path(p)

        #hide data to compare if the period is the same than the period to answer
        if $scope.periodKey == $scope.periodToCompare
            $scope.periodToCompare = 'default'

        $scope.loadPeriodForComparison()
        $scope.loadFormProgress()

    $scope.$watch 'scopeId', () ->
        $routeParams.period = $scope.periodKey
        if $route.current
            p = $route.current.$$route.originalPath

            for k,v of $routeParams
                p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v)

            $location.path(p)

        $scope.loadPeriodForComparison()
        $scope.loadFormProgress()

    $scope.periodsForComparison = [
        {'key': 'default', 'label': 'aucune periode'}
    ]
    $scope.periodToCompare = 'default'


    #
    # Save
    #
    $scope.save = () ->
        $scope.$broadcast 'SAVE'
        $scope.$root.$broadcast("REFRESH_LAST_SAVE_TIME")

    #
    # Route Change
    #
    $scope.$on "$routeChangeSuccess", (event, current, previous) ->
        $scope.periodKey = $routeParams.period
        $scope.scopeId = parseInt($routeParams.scope)


    $scope.getMainScope = ->
        return mainScope = angular.element($('[ng-view]')[0]).scope()


    $scope.loadPeriodForComparison = ->

        url ='answer/getPeriodsForComparison/' + $scope.scopeId
        if $scope.scopeId? && $scope.scopeId != NaN  && $scope.scopeId != 'NaN'

            promise = $http
                method: "GET"
                url: url
                headers:
                    "Content-Type": "application/json"
            promise.success (data, status, headers, config) ->

                $scope.periodsForComparison = [
                    {'key': 'default', 'label': 'aucune periode'}
                ]
                for period in data.periodDTOList
                    if period.key != $scope.periodKey
                        $scope.periodsForComparison[$scope.periodsForComparison.length] = period

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
        if $scope.scopeId? && $scope.periodKey?
            promise = $http
                method:"GET"
                url: "answer/formProgress/"+$scope.periodKey+"/"+$scope.scopeId
                headers:"Content-Type": "application/json"
            promise.success (data, status, headers, config) ->
                $scope.formProgress = data.listFormProgress
                return

    $scope.$on "REFRESH_LAST_SAVE_TIME", (event, args) ->
        if args != undefined
            console.log "TIME : "+args.time

            if args.time == null
                date = null
            else
                date = new Date(args.time)

                # adapt for the current time zone
                minuteToAdd = new Date().getTimezoneOffset()
                date = new Date(date.getTime() - minuteToAdd*60000)
        else
            date = new Date()

        $scope.lastSaveTime = date


    $scope.displayMenu =->

        if $scope.getMainScope()? && $scope.getMainScope().displayFormMenu? &&  $scope.getMainScope().displayFormMenu == true
            return true
        return false

    $scope.getClassContent = ->
        if $scope.$root.isLogin() == false
            if $scope.getMainScope()?
                if $scope.getMainScope().displayFormMenu? &&  $scope.getMainScope().displayFormMenu == true
                    return 'content-with-menu'
                else
                    return 'content-without-menu'
        return ''

#rootScope
angular.module('app').run ($rootScope, $location, $http, flash)->


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

        $location.path('/form1/' + data.defaultPeriod + '/' + data.organization.sites[0].scope)

    #
    # get user
    #
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

