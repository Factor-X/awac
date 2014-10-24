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
.controller "MainCtrl", ($scope, downloadService, translationService, $sce, $location, $route, $routeParams, modalService, $timeout, messageFlash,$compile,$element,$window) ->

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
    # inject the menu
    #
    if $scope.$root.instanceName == "municipality" || $scope.$root.instanceName == "enterprise"
        directive = $compile("<mm-awac-" + $scope.$root.instanceName + "-menu></mm-awac-" + $scope.$root.instanceName + "_menu>")($scope)
        p=$('.inject-menu:first',$element)
        $(directive).insertAfter p

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
        if $scope.getMainScope()? && $scope.getMainScope?().validNavigation != undefined
            # ask a confirmation to quite the view
            result = $scope.getMainScope?().validNavigation?()

            if result.valid == false
                return translationService.get('MODAL_CONFIRMATION_EXIT_FORM_MESSAGE')


    $scope.$on '$routeChangeSuccess', (event, args) ->
        $timeout(->
            $scope.computeDisplayMenu()
        , 0)


    $scope.computeDisplayMenu = ->
        if $scope.getMainScope()? && $scope.getMainScope().displayFormMenu? && $scope.getMainScope().displayFormMenu == true
            $scope.displayMenu = true
        else
            $scope.displayMenu = false

        if $scope.getMainScope()? && $scope.getMainScope().displayLittleFormMenu? && $scope.getMainScope().displayLittleFormMenu == true
            $scope.displayLittleMenu = true
        else
            $scope.displayLittleMenu = false



    #
    # Periods
    #
    $scope.periodKey = null

    $scope.$watch '$root.scopeSelectedId', (o,n) ->
        if o != n
            $scope.computeScopeAndPeriod()

    $scope.$watch '$root.periodSelectedKey', (o,n) ->
        if o != n
            $scope.computeScopeAndPeriod()

    $scope.computeScopeAndPeriod = ->
        console.log '----->>>'+$scope.$root.periodSelectedKey+"-"+$scope.$root.scopeSelectedId
        if $scope.$root.periodSelectedKey? && $scope.$root.scopeSelectedId?

            $routeParams.form = $route.current.params.form

            if $route.current
                p = $route.current.$$route.originalPath

                if p == "/noScope"
                    url = $scope.$root.getDefaultRoute()
                    $scope.$root.nav url
                else
                    p=p.replace(new RegExp("\\/:period\\b", 'g'), '')
                    p=p.replace(new RegExp("\\/:scope\\b", 'g'), '')
                    for k,v of $routeParams
                        p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v)

                    if p != $route.current.$$route.originalPath
                        $scope.$root.nav(p)

            $scope.$root.computeAvailablePeriod()
            $scope.loadPeriodForComparison()
            $scope.loadFormProgress()
            $scope.$root.testCloseable()



    $scope.periodsForComparison = [
        {'key': 'default', 'label': translationService.get('NO_PERIOD_SELECTED')}
    ]

    $scope.$root.periodToCompare = 'default'


    #
    # Save
    #
    $scope.save = () ->
        $scope.$broadcast 'SAVE'
        $scope.$root.$broadcast("REFRESH_LAST_SAVE_TIME")


    $scope.getMainScope = ->
        return mainScope = angular.element($('[ng-view]')[0]).scope()

    $scope.loadPeriodForComparison = ->
        if !!$scope.$root.scopeSelectedId
            url = '/awac/answer/getPeriodsForComparison/' + $scope.$root.scopeSelectedId

            downloadService.getJson url, (result) ->
                if result.success
                    $scope.periodsForComparison = [
                        {'key': 'default', 'label': translationService.get('NO_PERIOD_SELECTED')}
                    ]
                    currentComparisonFounded=false
                    for period in result.data.periodDTOList
                        if period.key != $scope.$root.periodSelectedKey
                            $scope.periodsForComparison.push period
                            if period.key == $scope.$root.periodToCompare
                                currentComparisonFounded = true

                    #hide data to compare if the period is the same than the period to answer
                    if currentComparisonFounded == false || $scope.$root.periodSelectedKey == $scope.$root.periodToCompare
                        $scope.$root.periodToCompare = 'default'


    $scope.getProgress = (form)->
        #.log "GET PROSGRS ; "
        #console.log $scope.formProgress
        if $scope.formProgress != null
            for formProgress in $scope.formProgress
                if formProgress.form == form
                    return formProgress.percentage
        return 0


    $scope.formProgress = null

    $scope.loadFormProgress = ->
        #console.log "$scope.loadFormProgress : "+$scope.$root.scopeSelectedId+" "+$scope.$root.periodSelectedKey
        if !!$scope.$root.scopeSelectedId and !!$scope.$root.periodSelectedKey
            downloadService.getJson "/awac/answer/formProgress/" + $scope.$root.periodSelectedKey + "/" + $scope.$root.scopeSelectedId, (result) ->
                if result.success
                    #console.log result.data
                    $scope.formProgress = result.data.listFormProgress

    $scope.$on "REFRESH_LAST_SAVE_TIME", (event, args) ->
        if args != undefined

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

    $scope.requestVerification = ->
        modalService.show 'REQUEST_VERIFICATION'

    $scope.cancelVerification = ->
        data =
            url:"/awac/verification/setStatus"
            desc:'CANCEL_VERIFICATION_DESC'
            successMessage:'CHANGES_SAVED'
            title:'CANCEL_VERIFICATION_TITLE'
            data:
                scopeId:$scope.$root.scopeSelectedId
                periodKey:$scope.$root.periodSelectedKey
                newStatus:'VERIFICATION_STATUS_REJECTED'
            afterSave:->
                $scope.$root.verificationRequest =null
                $scope.$root.$broadcast 'CLEAN_VERIFICATION'
        modalService.show modalService.PASSWORD_CONFIRMATION, data

    $scope.confirmVerifier = ->
        modalService.show modalService.VERIFICATION_CONFIRMATION

    $scope.consultVerificationFinalComment = ->
        if $scope.$root.verificationRequest?
            data =
                comment: $scope.$root.verificationRequest.verificationRejectedComment
            modalService.show modalService.VERIFICATION_FINALIZATION_VISUALIZATION, data

    $scope.resubmitVerification =->
        data =
            url:"/awac/verification/setStatus"
            desc:"VERIFICATION_RESUBMIT_DESC"
            successMessage:"CHANGES_SAVED"
            title:"VERIFICATION_RESUBMIT_TITLE"
            data:
                scopeId:$scope.$root.scopeSelectedId
                periodKey:$scope.$root.periodSelectedKey
                newStatus:'VERIFICATION_STATUS_VERIFICATION'
            afterSave:->
                $scope.$root.verificationRequest?.status ='VERIFICATION_STATUS_REJECTED'
        modalService.show modalService.PASSWORD_CONFIRMATION, data

    $scope.consultVerification =->
        modalService.show modalService.VERIFICATION_DOCUMENT

    #
    # used by menu
    #
    $scope.navTo = (target) ->
        $scope.$root.nav(target)


#rootScope
angular.module('app').run ($rootScope, $location, downloadService, messageFlash, $timeout, translationService, tmhDynamicLocale, $routeParams, $route, modalService)->
    $rootScope.languages = []

    $rootScope.closeableForms = false
    $rootScope.closedForms =false

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
        return ($location.path().substring(0, 6) == "/login" || $location.path().substring(0, 13) == "/registration"  || $location.path().substring(0, 26) == "/verification_registration")


    #
    # logout the current user
    #
    $rootScope.logout = () ->
        downloadService.postJson '/awac/logout', null, (result) ->
            console.log 'logout !! '
            $rootScope.currentPerson = null
            $rootScope.periodSelectedKey=null
            $rootScope.scopeSelectedId=null
            $rootScope.mySites=null
            $rootScope.currentPerson = null
            $rootScope.organizationName = null
            $location.path('/login')



    $rootScope.testForm = (period,scope) ->
        if $rootScope.mySites?
            for site in $rootScope.mySites
                if parseFloat(site.id) == parseFloat(scope)
                    if $rootScope.instanceName == 'enterprise'
                        for periodToFind in  site.listPeriodAvailable
                            if period+"" == periodToFind.key+""
                                return true
                    else
                        for periodToFind in  $rootScope.periods
                            if period+"" == periodToFind.key+""
                                return true
        return false
    #
    # success after login => store some datas, display the path
    #
    $rootScope.loginSuccess = (data, skipRedirect) ->

        console.log "DATA connection : "
        console.log angular.copy data

        $rootScope.periods = data.availablePeriods
        $rootScope.currentPerson = data.person
        $rootScope.organizationName = data.organizationName
        $rootScope.mySites = data.myScopes
        # try to load default scope / period
        #default scope

        if !!data.defaultSiteId && !!data.defaultPeriod
            if $rootScope.testForm(data.defaultSiteId,data.defaultPeriod) == true
                scopeSelectedId =data.defaultSiteId
                periodSelectedKey =data.defaultPeriod
        else if data.defaultSiteId?
            for scope in $rootScope.mySites
                if scope.id == data.defaultSiteId
                    scopeSelectedId =data.defaultSiteId

        if !scopeSelectedId?
            if $rootScope.mySites.length > 0
                scopeSelectedId = $rootScope.mySites[0].id

        if scopeSelectedId? && !periodSelectedKey?
            for site in $rootScope.mySites
                if site.id == scopeSelectedId
                    if $rootScope.instanceName == 'enterprise'
                        if site.listPeriodAvailable? && site.listPeriodAvailable.length > 0
                            periodSelectedKey = site.listPeriodAvailable[0].key
                    else
                        periodSelectedKey = $rootScope.periods[0].key

        $rootScope.scopeSelectedId =scopeSelectedId
        $rootScope.periodSelectedKey =periodSelectedKey

        if $rootScope.scopeSelectedId?
            $rootScope.computeAvailablePeriod($rootScope.mySites[0].scope)

        if not skipRedirect
            $rootScope.toDefaultForm()

    $rootScope.toDefaultForm = () ->
        console.log "to default form "
        $rootScope.nav $rootScope.getDefaultRoute()

    $rootScope.$watch "mySites", ->
        $rootScope.computeAvailablePeriod()

    $rootScope.computeAvailablePeriod = (scopeId) ->

        if $rootScope.instanceName == 'enterprise'
            if !scopeId?
                scopeId = $rootScope.scopeSelectedId
            if scopeId?
                for site in $rootScope.mySites
                    if site.scope == scopeId
                        $rootScope.availablePeriods = site.listPeriodAvailable

                currentPeriodFounded=false
                if $rootScope.availablePeriods?
                    for period in $rootScope.availablePeriods
                        if period.key == $rootScope.periodSelectedKey
                            currentPeriodFounded = true
                    if not currentPeriodFounded
                        if $rootScope.availablePeriods.length>0
                            $rootScope.periodSelectedKey = $rootScope.availablePeriods[0].key
                else
                    $rootScope.availablePeriods = null
                    $location.path("noScope")
        else if $rootScope.instanceName == 'municipality'
            $rootScope.availablePeriods = $rootScope.periods


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

    #
    # Get notifications and show them every hour
    #
    $rootScope.refreshNotifications = () ->
        # fetch and display
        downloadService.getJson '/awac/notifications/get_notifications', (result) ->
            if result.success
                for n in result.data.notifications
                    messageFlash.display(n.kind.toLowerCase(), n.messageFr, { hideAfter: 3600 })
        # schedule a refresh
        $timeout $rootScope.refreshNotifications, 3600 * 1000

    $rootScope.refreshNotifications()

    #
    # Route Change
    #
    $rootScope.$on "$routeChangeSuccess", (event, current, previous) ->
        if $routeParams.period?
            $rootScope.periodSelectedKey = $routeParams.period
        if $routeParams.scope?
            $rootScope.scopeSelectedId = parseInt($routeParams.scope)


    #
    # nav to the last nav used
    #
    $rootScope.navToLastFormUsed = ->
        if $rootScope.getFormPath?
            $rootScope.nav($rootScope.getFormPath())
        else
            $rootScope.nav($rootScope.getDefaultRoute())

    #
    # Tabs -- transition
    # loc : the localisation targeted
    # confirmed : the modification of localisation was already confirmed by the user
    #
    $rootScope.nav = (loc, confirmed = false) ->
        console.log "NAV : " + loc
        canBeContinue = true

        # test if the main current scope have a validNavigation function and if this function return a false
        if $rootScope.getMainScope()? && $rootScope.getMainScope().validNavigation != undefined && confirmed == false

            # ask a confirmation to quite the view
            result = $rootScope.getMainScope?().validNavigation?()

            if result.valid == false
                canBeContinue = false
                params = {}
                params.loc = loc
                modalService.show result.modalForConfirm, params
        if canBeContinue
            # if this is a form, unlock it
            if $rootScope.getMainScope()?.formIdentifier? && $rootScope.currentPerson?
                downloadService.getJson "/awac/answer/unlockForm/"+$rootScope.getMainScope().formIdentifier+ "/" + $rootScope.periodSelectedKey + "/" + $rootScope.scopeSelectedId, (result)->

            routeWithScopeAndPeriod = ['/form','/results','/actions']
            for route in routeWithScopeAndPeriod
                if loc.substring(0, route.length) == route
                    console.log "nav to => "+loc + "/" + $rootScope.periodSelectedKey + "/" + $rootScope.scopeSelectedId
                    $location.path(loc + "/" + $rootScope.periodSelectedKey + "/" + $rootScope.scopeSelectedId)
                    return
            console.log "nav to => "+loc
            $location.path(loc)

            return

    $rootScope.getMainScope = ->
        return mainScope = angular.element($('[ng-view]')[0]).scope()

    $rootScope.testCloseable = ->
        if $rootScope.instanceName != 'verification'
            if !!$rootScope.periodSelectedKey and !!$rootScope.scopeSelectedId
                downloadService.getJson "/awac/answer/testClosing/"+$rootScope.periodSelectedKey + "/" + $rootScope.scopeSelectedId, (result)->
                    if result.success
                        $rootScope.closeableForms = result.data.closeable
                        $rootScope.closedForms =result.data.closed
                        $rootScope.verificationRequest = result.data.verificationRequest

    $rootScope.closeForms = ->
        if $rootScope.periodSelectedKey? and $rootScope.scopeSelectedId?
            modalService.show(modalService.CONFIRM_CLOSING)



    $rootScope.showHelp = () ->
        if $route.current.locals.helpPage?
            modalService.show(modalService.HELP, {template: $route.current.locals.helpPage})

    $rootScope.showConfidentiality = ->
        modalService.show(modalService.HELP, {template: 'confidentiality'})


    $rootScope.getVerificationRequestStatus = ->
        return $rootScope.verificationRequest?.status
