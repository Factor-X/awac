angular
.module('app.controllers')
.controller "SiteManagerCtrl", ($scope,translationService, modalService,downloadService) ->

    $scope.isLoading = [{},{}]
    $scope.assignPeriod = [$scope.$root.periods[1].key, $scope.$root.periods[0].key]
    $scope.isPeriodChecked = [{},{}]

    $scope.$watch '$scope.assignPeriod', ->
        for site in $scope.$root.organization.sites
            $scope.isPeriodChecked[0][site.id] = $scope.periodAssignTo(0,site)
            $scope.isPeriodChecked[1][site.id] = $scope.periodAssignTo(1,site)

            console.log $scope.isPeriodChecked


    $scope.toForm = ->
        $scope.$parent.navToLastFormUsed()

    $scope.getSiteList = () ->
        return $scope.$root.organization.sites

    $scope.editOrCreateSite = (site) ->
        params = {}
        if site?
            params.site = site
        modalService.show(modalService.EDIT_SITE, params)

    $scope.addUsers = (site) ->
      params = {}
      if site?
        params.site = site
      modalService.show(modalService.ADD_USER_SITE, params)

    $scope.periodAssignTo = (nbPeriod,site) ->
        if site.listPeriodAvailable?
            for period in site.listPeriodAvailable
                if period == $scope.assignPeriod[nbPeriod]
                    return true
        return false

    $scope.assignPeriodToSite = (nbPeriod,site) ->
        data = {}
        data.periodKeyCode = $scope.assignPeriod[nbPeriod]
        data.siteId = site.id
        data.assign = !$scope.periodAssignTo(nbPeriod,site)

        console.log data
        downloadService.postJson 'awac/site/assignPeriodToSite', data, (result) ->


