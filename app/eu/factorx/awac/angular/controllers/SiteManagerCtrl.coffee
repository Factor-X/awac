angular
.module('app.controllers')
.controller "SiteManagerCtrl", ($scope,translationService, modalService,downloadService, $rootScope) ->

    $scope.isLoading = [{},{}]
    $scope.assignPeriod = [$scope.$root.periods[1].key, $scope.$root.periods[0].key]
    $scope.isPeriodChecked = [{},{}]

    $scope.events = []

    data = {}
    data.organization = $rootScope.organization
    data.period = $scope.$root.periods[0]

    console.log "DATA"
    console.log data
    downloadService.postJson 'awac/organization/events/load', data, (result) ->
      $scope.events = result.data.organizationEventList
      console.log "RESULT"
      console.log result
      console.log result.data.organizationEventList


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

    $scope.getEventList = () ->
      return $scope.events

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

    $scope.editOrCreateEvent = (site) ->
      params = {}
      if site?
        params.site = site
      modalService.show(modalService.EDIT_EVENT, params)


#  link: (scope) ->
#    console.log("entering SiteManagerCtrl")
#    scope.getAssociatedUsers()
