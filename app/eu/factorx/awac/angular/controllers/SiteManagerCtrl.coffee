angular
.module('app.controllers')
.controller "SiteManagerCtrl", ($scope,translationService, modalService,downloadService,messageFlash) ->

    $scope.isLoading = {}
    if $scope.$root.periods?
        $scope.assignPeriod = $scope.$root.periods[0].key
    else
        $scope.$watch '$root.periods', ->
            $scope.assignPeriod = $scope.$root.periods[0].key
    $scope.isPeriodChecked = {}

    # load my organization
    modalService.show(modalService.LOADING)
    downloadService.getJson 'awac/organization/getMyOrganization', (result) ->
        console.log result
        if not result.success
            messageFlash.displayError 'Unable to load data...'
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


            $scope.$watch 'assignPeriod', ->
                for site in $scope.organization.sites
                    $scope.isPeriodChecked[site.id] = $scope.periodAssignTo(site)

                    console.log $scope.isPeriodChecked


            $scope.toForm = ->
                $scope.$parent.navToLastFormUsed()

            $scope.getSiteList = () ->
                return $scope.organization.sites

            $scope.editOrCreateSite = (site) ->
                params = {}
                if site?
                    params.site = site
                params.organization = $scope.organization
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
                        if period == $scope.assignPeriod
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
                        messageFlash.displayError 'Unable to load data...'
                    else
                        if !site.listPeriodAvailable?
                            site.listPeriodAvailable = []
                        site.listPeriodAvailable[site.listPeriodAvailable.length] = $scope.assignPeriod


            $scope.editOrCreateEvent = (event) ->
                params = {}
                if event?
                    params.event = event
                modalService.show(modalService.EDIT_EVENT, params)
        