angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, $window, downloadService, displayFormMenu, modalService) ->
    $scope.displayFormMenu = displayFormMenu

    modalService.show modalService.LOADING
    downloadService.getJson "/awac/result/getReport/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, (result) ->
        modalService.close modalService.LOADING
        if result.success
            $scope.o = result.data
        else
            # TODO ERROR HANDLING

    $scope.charts = {
        webUrl: "/awac/result/getWebForScope/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId
        donutUrl1: "/awac/result/getSvgDonutForScope/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId + "/1"
        donutUrl2: "/awac/result/getSvgDonutForScope/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId + "/2"
        donutUrl3: "/awac/result/getSvgDonutForScope/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId + "/3"
    }

    $scope.current_tab = 1;

    $scope.downloadAsXls = () ->
        $window.open '/awac/result/getReportAsXls/' + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, "Downloading report file...", null

    $scope.downloadPdf = () ->
