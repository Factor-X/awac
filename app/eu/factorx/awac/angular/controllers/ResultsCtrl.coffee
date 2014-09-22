angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, $window, downloadService, displayFormMenu, modalService, messageFlash,translationService) ->
    $scope.displayFormMenu = displayFormMenu

    $scope.$watch('$root.mySites|filter:{$selected:true}', (nv) ->
        $scope.mySites = $scope.$root.mySites
    , true);


    $scope.$watch('mySites|filter:{$selected:true}', (sites) ->
        console.log "sites == "
        console.log sites
        if sites.length > 0
            $scope.o = undefined
            modalService.show modalService.LOADING
            dto =
                __type: 'eu.factorx.awac.dto.awac.post.GetReportParametersDTO'
                periodKey: $scope.$root.periodSelectedKey
                scopesIds: sites.map((s) ->
                    s.id
                )

            downloadService.postJson '/awac/result/getReport', dto, (result) ->
                console.log result
                modalService.close modalService.LOADING
                if result.success
                    $scope.o = result.data
                else
                    messageFlash.displayError translationService.get 'RESULT_LOADING_FAILED'
    , true);


    $scope.current_tab = 1;

    $scope.siteSelectionIsEmpty = () ->
        filtered = $scope.mySites.filter (s) ->
            return s.$selected
        return filtered.length == 0

    $scope.downloadAsXls = () ->
        $window.open '/awac/result/getReportAsXls/' + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, translationService.get 'RESULT_DOWNLOAD_START', null

    $scope.downloadPdf = () ->
