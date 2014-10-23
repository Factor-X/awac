angular
.module('app.controllers')
.controller "AdminDriverManageCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu


    #launch download
    modalService.show(modalService.LOADING)
    downloadService.getJson "/awac/admin/driver/all", (result) ->
        modalService.close(modalService.LOADING)
        console.log result.data
        #if result.success == true
