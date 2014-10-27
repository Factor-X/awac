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
        if not result.success
            modalService.close(modalService.LOADING)
        else
            modalService.close(modalService.LOADING)
            $scope.organization = result.data

            $scope.$watchCollection 'assignPeriod', ->
                $scope.refreshPeriod()

            $scope.refreshPeriod = ->
                for site in $scope.organization.sites
                    $scope.isPeriodChecked[site.id] = $scope.periodAssignTo(site)


            $scope.toForm = ->
                $scope.$root.scopeSelectedId =  $scope.$root.mySites[0].id
                $scope.$root.periodSelectedKey = $scope.$root.mySites[0].listPeriodAvailable[0].key
                $scope.$root.navToLastFormUsed()

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

                downloadService.postJson 'awac/site/assignPeriodToSite', data, (result) ->
                    $scope.isLoading[site.id] = false
                    if result.success
                        site.listPeriodAvailable = result.data.periodsList
                        $scope.refreshMySites()

            #
            # this function replace my site by organization site
            #
            $scope.refreshMySites = () ->
                mySites = []
                for site in $scope.organization.sites
                    if site.listPersons?
                        for person in site.listPersons
                            if person.identifier == $scope.$root.currentPerson.identifier
                                mySites.push site
                console.log "voilà tes nouveaux sites : "
                console.log $scope.$root.mySites
                $scope.$root.mySites = mySites