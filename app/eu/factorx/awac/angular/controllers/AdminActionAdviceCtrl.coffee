angular
.module('app.controllers')
.controller "AdminActionAdviceCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu

    #launch download
    modalService.show(modalService.LOADING)
    downloadService.getJson "/awac/admin/advice/load", (result) ->
        modalService.close(modalService.LOADING)
        console.log "bvlabla"
        #if result.success == true
