#
# Logging initialization
#
angular.module('app').run (loggerService) ->

    loggerService.initialize()

    $('body').keydown (evt) ->
        if evt.which == 32 and evt.altKey and evt.ctrlKey
            loggerElement = $('#logger')
            state = parseInt(loggerElement.attr('data-state'))
            loggerElement.attr('data-state', (state + 1) % 3)

    log = loggerService.get('initializer')
    log.info "Application is started"



angular
.module('app.controllers')
.controller "MainCtrl", ($scope, downloadService, translationService, $sce, $location, $route, $routeParams, modalService, $timeout) ->

    $scope.displayMenu = false
    $scope.displayLittleMenu = false
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
        if $scope.getMainScope?().validNavigation != undefined
            # ask a confirmation to quite the view
            result = $scope.getMainScope?().validNavigation?()

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
        console.log "NAV : " + loc
        canBeContinue = true

        # test if the main current scope have a validNavigation function and if this function return a false
        if $scope.getMainScope?().validNavigation != undefined && confirmed == false
            # ask a confirmation to quite the view
            result = $scope.getMainScope?().validNavigation?()

            if result.valid == false
                canBeContinue = false
                params = {}
                params.loc = loc
                modalService.show result.modalForConfirm, params
        if canBeContinue
            $location.path(loc + "/" + $scope.$root.periodSelectedKey + "/" + $scope.$root.scopeSelectedId)

    $scope.$on '$routeChangeSuccess', (event, args) ->
        $timeout(->
            $scope.computeDisplayMenu()
        , 0)


    $scope.computeDisplayMenu = ->
        if $scope.getMainScope()? && $scope.getMainScope().displayFormMenu? && $scope.getMainScope().displayFormMenu == true
            $scope.displayMenu = true
        else if $scope.getMainScope()? && $scope.getMainScope().displayLittleFormMenu? && $scope.getMainScope().displayLittleFormMenu == true
            $scope.displayLittleMenu = true
        else
            $scope.displayMenu = false


    #
    # nav to the last nav used
    #
    $scope.navToLastFormUsed = ->
        $scope.nav($scope.$root.getFormPath())

    #
    # Periods
    #
    $scope.periodKey = null
    $scope.$watch '$root.periodSelectedKey', () ->

        console.log "changement de la valeur de $root.periodSelectedKey : "+$scope.$root.periodSelectedKey

        $routeParams.period = $scope.$root.periodSelectedKey
        if $route.current
            p = $route.current.$$route.originalPath

            for k,v of $routeParams
                p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v)

            $location.path(p)

        #hide data to compare if the period is the same than the period to answer
        if $scope.$root.periodSelectedKey == $scope.periodToCompare
            $scope.periodToCompare = 'default'

        $scope.loadPeriodForComparison()
        $scope.loadFormProgress()

    $scope.$watch '$root.scopeSelectedId', () ->

        console.log "changement de la valeur de root.scopeSelectedId : "+$scope.$root.scopeSelectedId


        $routeParams.period = $scope.$root.periodSelectedKey
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
        if $routeParams.period?
            $scope.$root.periodSelectedKey = $routeParams.period
        if $routeParams.scope?
            $scope.$root.scopeSelectedId = parseInt($routeParams.scope)


    $scope.getMainScope = ->
        return mainScope = angular.element($('[ng-view]')[0]).scope()


    $scope.loadPeriodForComparison = ->
        if $scope.$root.periodToCompare? and not isNaN($scope.$root.periodToCompare)
            url = '/awac/answer/getPeriodsForComparison/' + $scope.$root.periodToCompare

            downloadService.getJson url, (result) ->
                if result.success
                    $scope.periodsForComparison = [
                        {'key': 'default', 'label': translationService.get('NO_PERIOD_SELECTED')}
                    ]
                    for period in result.data.periodDTOList
                        if period.key != $scope.$root.periodSelectedKey
                            $scope.periodsForComparison[$scope.periodsForComparison.length] = period
                else
                    messageFlash.displayError data.message


    $scope.getProgress = (form)->
        if $scope.formProgress != null
            for formProgress in $scope.formProgress
                if formProgress.form == form
                    return formProgress.percentage
        return 0


    $scope.formProgress = null

    $scope.loadFormProgress = ->
        console.log "$scope.loadFormProgress : "+$scope.$root.scopeSelectedId+" "+$scope.$root.periodSelectedKey
        if $scope.$root.scopeSelectedId? && $scope.$root.periodSelectedKey?
            downloadService.getJson "/awac/answer/formProgress/" + $scope.$root.periodSelectedKey + "/" + $scope.$root.scopeSelectedId, (result) ->
                if result.success
                    $scope.formProgress = result.data.listFormProgress
                else
                    messageFlash.displayError data.message

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


    $scope.getClassContent = ->
#        if $scope.$root.isLogin() == false
        if $scope.$root.hideHeader() == false
            if $scope.getMainScope()?
                if $scope.getMainScope().displayFormMenu? && $scope.getMainScope().displayFormMenu == true
                    return 'content-with-menu'
                else if $scope.getMainScope().displayLittleFormMenu? && $scope.getMainScope().displayLittleFormMenu == true
                    return 'content-with-little-menu'
                else
                    return 'content-without-menu'
        return ''


#rootScope
angular.module('app').run ($rootScope, $location, downloadService, messageFlash, $timeout, translationService, tmhDynamicLocale, $routeParams)->
    $rootScope.languages = []
    $rootScope.languages[0] = {
        value: 'fr'
        label: 'Français'
    }
    $rootScope.languages[1] = {
        value: 'en'
        label: 'English'
    }
    $rootScope.languages[2] = {
        value: 'nl'
        label: 'Neederlands'
    }

    translationService.initialize('fr')

    $rootScope.language = 'fr'

    $rootScope.$watch 'language', (lang) ->
        translationService.initialize(lang)
        tmhDynamicLocale.set(lang.toLowerCase())
        #TODO save the langauge changement


    #GHO route 03/09/2014
    $rootScope.$on '$routeChangeStart', (event, current) ->


        $rootScope.key = $routeParams.key
        # Check if the user is logged in

        if not $rootScope.currentPerson
            #
            # test if the user is currently connected on the server
            #
            downloadService.postJson '/awac/testAuthentication', {interfaceName: $rootScope.instanceName}, (result) ->
                if result.success
                    $rootScope.loginSuccess result.data, !$rootScope.isLogin()
                else
                    if not current.$$route.anonymousAllowed
                        #redirect to login page
                        console.log "redirect to login "
                        $location.path '/login'
        else
            console.log("default router processing")

    #
    # Redirect user to login view if not logged in
    #
    #if not $rootScope.currentPerson
    #    console.log("entering not current person")
    #    $location.path('/login')

    #
    # getRegisterKey
    #
    $rootScope.getRegisterKey = () ->
        return $rootScope.key


    #
    # Is login
    # should not be used anymore
    # prefer showHeader method
    #
    $rootScope.isLogin = () ->
        return $location.path().substring(0, 6) == "/login"

    #
    # evaluation conditions whenever show a header in pages (mains enterprise/municipality directives)
    #
    #
    $rootScope.hideHeader = () ->
        #console.log("hideHeader:" + $location.path().substring(0, 13))
        return ($location.path().substring(0, 6) == "/login" || $location.path().substring(0, 13) == "/registration")


    #
    # logout the current user
    #
    $rootScope.logout = () ->
        downloadService.postJson '/awac/logout', null, (result) ->
            if result.success
                $rootScope.currentPerson = null
                $location.path('/login')
            else
                messageFlash.displayError data.message
                $location.path('/login')

    #
    # success after login => store some datas, display the path
    #
    $rootScope.loginSuccess = (data, skipRedirect) ->
        console.log "connection date : "
        console.log data

        $rootScope.periods = data.availablePeriods
        $rootScope.currentPerson = data.person
        $rootScope.organizationName = data.organizationName
        $rootScope.mySites = data.mySites

        # try to load default scope / period
        #default scope
        ###
        if data.defaultSiteId?
            for site in $rootScope.mySites
                if site.id == data.defaultSiteId
                    $rootScope.scopeSelectedId = data.defaultSiteId

                    #control default period
                    if data.defaultPeriod?
                        if site.listPeriodAvailable? site.listPeriodAvailable.length >0
                            for period in  site.listPeriodAvailable
                                if period.key == data.defaultPeriod
                                    $rootScope.periodSelectedKey = data.defaultPeriod
        ###

        console.log "? : "+$rootScope.scopeSelectedId
        if !$rootScope.scopeSelectedId?
            console.log "!1:"+$rootScope.mySites.length
            if $rootScope.mySites.length > 0
                $rootScope.scopeSelectedId = $rootScope.mySites[0].id
                console.log "!1 : "+$rootScope.scopeSelectedId

        if $rootScope.scopeSelectedId? &&  !$rootScope.periodSelectedKey?
            for site in $rootScope.mySites
                if site.id == $rootScope.scopeSelectedId
                    if site.listPeriodAvailable? && site.listPeriodAvailable.length > 0
                        $rootScope.periodSelectedKey = site.listPeriodAvailable[0].key

        if $rootScope.scopeSelectedId?
            $rootScope.computePeriod($rootScope.mySites[0].scope)

        if not skipRedirect
            if $rootScope.scopeSelectedId? &&  $rootScope.periodSelectedKey?
                $rootScope.onFormPath($rootScope.periodSelectedKey,$rootScope.scopeSelectedId)
            else
                $location.path("noScope")



    $rootScope.computePeriod = (scopeId) ->
        for site in $rootScope.mySites
            if site.scope == scopeId
                $rootScope.availablePeriods = site.listPeriodAvailable
                console.log "$rootScope.availablePeriods"
                console.log $rootScope.availablePeriods


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
    # downloadService.postJson '/awac/testAuthentication', {interfaceName: $rootScope.instanceName}, (result) ->
    #     if result.success
    #         $rootScope.loginSuccess result.data
    #     else
    #         # TODO ERROR HANDLING

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
                messageFlash.displayError data.message
                # schedule a refresh
        $timeout $rootScope.refreshNotifications, 3600 * 1000

    $rootScope.refreshNotifications()
