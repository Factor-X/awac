angular
.module('app.controllers')
.controller "SiteManagerCtrl", ($scope, translationService, modalService, downloadService, messageFlash) ->
    $scope.isLoading = {}
    if $scope.$root.periods?
        $scope.assignPeriod = $scope.$root.periods[0].key
    else
        $scope.$watch '$root.periods', ->
            $scope.assignPeriod = $scope.$root.periods[0].key
    $scope.isPeriodChecked = {}

    $scope.selectedPeriodForEvent = $scope.$root.periods[0].key

    # load my organization
    modalService.show(modalService.LOADING)
    downloadService.getJson 'awac/organization/getMyOrganization', (result) ->
        console.log result

        if not result.success
            messageFlash.displayError translationService.get 'UNABLE_LOAD_DATA'
            modalService.close(modalService.LOADING)
        else
            modalService.close(modalService.LOADING)
            $scope.organization = result.data

            $scope.events = []

            data = {}
            data.organization = $scope.organization
            data.period = $scope.$root.periods[0]

            console.log "DATA"
            console.log data
            downloadService.postJson 'awac/organization/events/load', data, (result) ->
                $scope.events = result.data.organizationEventList
                console.log "RESULT"
                console.log result
                console.log result.data.organizationEventList


            $scope.$watchCollection 'assignPeriod', ->
                $scope.refreshPeriod()

            $scope.refreshPeriod = ->
                for site in $scope.organization.sites
                    $scope.isPeriodChecked[site.id] = $scope.periodAssignTo(site)


            $scope.toForm = ->
                $scope.$parent.navToLastFormUsed()

            $scope.getSiteList = () ->
                return $scope.organization.sites

            $scope.editOrCreateSite = (site) ->
                params = {}
                if site?
                    params.site = site
                params.organization = $scope.organization
                params.refreshMySites = ->
                    $scope.refreshMySites()
                    $scope.refreshPeriod()

                modalService.show(modalService.EDIT_SITE, params)

            $scope.addUsers = (site) ->
                params = {}
                if site?
                    params.site = site
                params.organization = $scope.organization
                modalService.show(modalService.ADD_USER_SITE, params)

            $scope.getEventList = () ->
                return $scope.events

            $scope.periodAssignTo = (site) ->
                if site.listPeriodAvailable?
                    for period in site.listPeriodAvailable
                        if period.key == $scope.assignPeriod
                            return true
                return false

            $scope.assignPeriodToSite = (site) ->
                $scope.isLoading[site.id] = true
                data = {}
                data.periodKeyCode = $scope.assignPeriod
                data.siteId = site.id
                data.assign = !$scope.periodAssignTo(site)

                console.log data
                downloadService.postJson 'awac/site/assignPeriodToSite', data, (result) ->
                    $scope.isLoading[site.id] = false
                    if not result.success
                        messageFlash.displayError translationService.get 'UNABLE_LOAD_DATA'
                    else
                        site.listPeriodAvailable = result.data.periodsList
                        $scope.refreshMySites()


            #
            # this function replace my site by organization site
            #
            $scope.refreshMySites = () ->
                mySites = []
                for site in $scope.organization.sites
                    for person in site.listPersons
                        if person.identifier == $scope.$root.currentPerson.identifier
                            mySites.push site

                $scope.$root.mySites = mySites

            $scope.editOrCreateEvent = (event) ->
                params = {}
                params.organization = $scope.organization
                params.events = $scope.events
                for period in $scope.$root.periods
                    if period.key == $scope.selectedPeriodForEvent
                        params.period = period
                if event?
                    params.event = event
                modalService.show(modalService.EDIT_EVENT, params)
        