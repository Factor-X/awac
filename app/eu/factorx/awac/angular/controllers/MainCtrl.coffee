angular
.module('app.controllers')
.controller "MainCtrl", ($scope, downloadService, translationService, $sce, $location, $route, $routeParams, modalService, tmhDynamicLocale) ->

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
                return translationService.get('MODAL_CONFIRMATION_EXIT_FORM_MESSAGE')

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
            #if !$scope.$$phase
            #    $scope.$apply()

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
        {'key': 'default', 'label': translationService.get('NO_PERIOD_SELECTED')}
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
        url = '/awac/answer/getPeriodsForComparison/' + $scope.scopeId
        if $scope.scopeId? and not isNaN($scope.scopeId)

            downloadService.getJson url, (result) ->
                if result.success
                    $scope.periodsForComparison = [
                        {'key': 'default', 'label': translationService.get('NO_PERIOD_SELECTED')}
                    ]
                    for period in result.data.periodDTOList
                        if period.key != $scope.periodKey
                            $scope.periodsForComparison[$scope.periodsForComparison.length] = period
                else
                    # TODO ERROR HANDLING


    $scope.getProgress = (form)->
        if $scope.formProgress != null
            for formProgress in $scope.formProgress
                if formProgress.form == form
                    return formProgress.percentage
        return 0


    $scope.formProgress = null

    $scope.loadFormProgress = ->
        if $scope.scopeId? && $scope.periodKey?
            downloadService.getJson "/awac/answer/formProgress/" + $scope.periodKey + "/" + $scope.scopeId, (result) ->
                if result.success
                    $scope.formProgress = result.data.listFormProgress
                else
                    # TODO ERROR HANDLING

    $scope.$on "REFRESH_LAST_SAVE_TIME", (event, args) ->
        if args != undefined
            #console.log "TIME : " + args.time

            if args.time == null
                date = null
            else
                date = new Date(args.time)

                # adapt for the current time zone
                minuteToAdd = new Date().getTimezoneOffset()
                date = new Date(date.getTime() - minuteToAdd * 60000)
        else
            date = new Date()

        $scope.lastSaveTime = date


    $scope.displayMenu = ->
        if $scope.getMainScope()? && $scope.getMainScope().displayFormMenu? && $scope.getMainScope().displayFormMenu == true
            return true
        return false

    $scope.getClassContent = ->
        if $scope.$root.isLogin() == false
            if $scope.getMainScope()?
                if $scope.getMainScope().displayFormMenu? && $scope.getMainScope().displayFormMenu == true
                    return 'content-with-menu'
                else
                    return 'content-without-menu'
        return ''


#rootScope
angular.module('app').run ($rootScope, $location, downloadService, messageFlash, $timeout,translationService)->

    $rootScope.languages = []
    $rootScope.languages[0] = {
        value:'fr'
        label:'Français'
    }
    $rootScope.languages[1] = {
        value:'en'
        label:'English'
    }
    $rootScope.languages[2] = {
        value:'nl'
        label:'Neederlands'
    }

    translationService.initialize('fr')

    $rootScope.language = 'fr'

    $rootScope.$watch 'language', (lang) ->
        translationService.initialize(lang)
        tmhDynamicLocale.set(lang.toLowerCase())



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

        downloadService.postJson '/awac/logout', null, (result) ->
            if result.success
                $rootScope.currentPerson = null
                $location.path('/login')
            else
                # TODO ERROR HANDLING
                $location.path('/login')

    #
    # success after login => store some datas, display the path
    #
    $rootScope.loginSuccess = (data) ->
        $rootScope.periods = data.availablePeriods
        $rootScope.currentPerson = data.person
        $rootScope.organization = data.organization
        $rootScope.users = data.organization.users
        $rootScope.onLoginSuccess(data)

    #
    # get user
    #
    $rootScope.getUserByIdentifier = (identifier) ->
        for user in $rootScope.users
            if user.identifier == identifier
                return user
        return null

    #
    # refresh user data
    #
    $rootScope.refreshUserData = () ->

        downloadService.getJson '/awac/user/profile', (result) ->
            if result.success
                $rootScope.currentPerson = result.data
            else
                messageFlash.displayError result.data.message

    #
    # test if the user is currently connected on the server
    #
    downloadService.postJson '/awac/testAuthentication', {interfaceName:$rootScope.instanceName}, (result) ->
        if result.success
            $rootScope.loginSuccess result.data
        else
            # TODO ERROR HANDLING

    #
    # Get notifications and show them every hour
    #
    $rootScope.refreshNotifications = () ->
        # fetch and display
        downloadService.getJson '/awac/notifications/get_notifications', (result) ->
            if result.success
                for n in result.data.notifications
                    messageFlash.display(n.kind.toLowerCase(), n.messageFr, { hideAfter: 3600 })
            else
                # TODO ERROR HANDLING
        # schedule a refresh
        $timeout $rootScope.refreshNotifications, 3600 * 1000

    $rootScope.refreshNotifications()
