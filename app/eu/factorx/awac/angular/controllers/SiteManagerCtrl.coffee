angular
.module('app.controllers')
.controller "SiteManagerCtrl", ($scope, translationService, modalService, downloadService, messageFlash) ->
    $scope.isLoading = [
        {},
        {}
    ]
    if $scope.$root.periods?
        $scope.assignPeriod = [$scope.$root.periods[1].key, $scope.$root.periods[0].key]
    $scope.isPeriodChecked = [
        {},
        {}
    ]

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

    $scope.$watch '$scope.assignPeriod', ->
        for site in $scope.organization.sites
            $scope.isPeriodChecked[0][site.id] = $scope.periodAssignTo(0, site)
            $scope.isPeriodChecked[1][site.id] = $scope.periodAssignTo(1, site)

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

    $scope.periodAssignTo = (nbPeriod, site) ->
        if site.listPeriodAvailable?
            for period in site.listPeriodAvailable
                if period == $scope.assignPeriod[nbPeriod]
                    return true
        return false

    $scope.assignPeriodToSite = (nbPeriod, site) ->
        data = {}
        data.periodKeyCode = $scope.assignPeriod[nbPeriod]
        data.siteId = site.id
        data.assign = !$scope.periodAssignTo(nbPeriod, site)

        console.log data
        downloadService.postJson 'awac/site/assignPeriodToSite', data, (result) ->

    link: (scope) ->
        console.log("entering SiteManagerCtrl")
#    scope.getAssociatedUsers()
