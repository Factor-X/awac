angular
.module('app.controllers')
.controller "SiteManagerCtrl", ($scope,translationService, modalService) ->

    $scope.toForm = ->
        $scope.$parent.navToLastFormUsed()

    $scope.getSiteList = () ->
        return $scope.$root.organization.sites

    $scope.editOrCreateSite = (site) ->
        params = {}
        if site?
            params.site = site
        modalService.show(modalService.EDIT_SITE, params)

