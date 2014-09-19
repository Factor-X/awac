angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, $window, downloadService, displayFormMenu, modalService) ->
    $scope.displayFormMenu = displayFormMenu

    $scope.$watch('$root.mySites|filter:{$selected:true}', (nv) ->
        $scope.mySites = $scope.$root.mySites
        console.log("mySites ==")
        console.log($scope.mySites)
    , true);


    $scope.$watch('mySites|filter:{$selected:true}', (sites) ->

        modalService.show modalService.LOADING

        dto =
            __type: 'eu.factorx.awac.dto.awac.post.GetReportParametersDTO'
            periodKey: $scope.$root.periodSelectedKey
            scopesIds: sites.map((s) -> s.id)

        downloadService.postJson '/awac/result/getReport', dto, (result) ->
            console.log result
            modalService.close modalService.LOADING
            if result.success
                $scope.o = result.data
            else
                # TODO ERROR HANDLING
    , true);


    $scope.current_tab = 1;

    $scope.downloadAsXls = () ->
        $window.open '/awac/result/getReportAsXls/' + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, "Downloading report file...", null

    $scope.downloadPdf = () ->
