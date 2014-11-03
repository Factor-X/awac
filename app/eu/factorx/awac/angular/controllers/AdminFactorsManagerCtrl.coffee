angular
.module('app.controllers')
.controller "AdminFactorsManagerCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu
    $scope.factors = null

    modalService.show(modalService.LOADING)

    downloadService.getJson "/awac/admin/factors/all", (result) ->
        modalService.close(modalService.LOADING)
        if result.success == true
            $scope.factors = result.data.list

    $scope.save = ->
        modalService.show modalService.LOADING
        downloadService.postJson '/awac/admin/factors/update', {list: $scope.factors}, (result) ->
            modalService.close modalService.LOADING

    $scope.remove = (factor) ->
