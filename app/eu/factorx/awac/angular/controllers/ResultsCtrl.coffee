angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, $window, downloadService, displayFormMenu, modalService) ->
    $scope.displayFormMenu = displayFormMenu

    modalService.show modalService.LOADING
    downloadService.postJson '/awac/result/getReport', { periodKey: $scope.$parent.periodKey, scopesIds: [$scope.$parent.scopeId] }, (result) ->
        modalService.close modalService.LOADING
        if result.success
            $scope.o = result.data
        else
            # TODO ERROR HANDLING

    $scope.sites = $rootScope.mySites

    $scope.current_tab = 1;

    $scope.downloadAsXls = () ->
        $window.open '/awac/result/getReportAsXls/' + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, "Downloading report file...", null

    $scope.downloadPdf = () ->
