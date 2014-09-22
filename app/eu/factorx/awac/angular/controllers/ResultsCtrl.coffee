angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, $window, downloadService, displayFormMenu, modalService, messageFlash,translationService) ->
    $scope.displayFormMenu = displayFormMenu

    $scope.$root.$watch('mySites', (nv) ->
        $scope.mySites = $scope.$root.mySites
        for s in $scope.mySites
            if "" + s.id == "" + $scope.$root.scopeSelectedId
                s.$selected = true
        console.log '-----------'
    , true);


    $scope.$watch('mySites|filter:{$selected:true}', (sites) ->
        console.log "sites == "
        console.log sites
        if sites.length > 0
            $scope.o = undefined
            $scope.totalEmissions = undefined
            $scope.totalScope1 = undefined;
            $scope.totalScope2 = undefined;
            $scope.totalScope3 = undefined;
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

                    $scope.totalEmissions = 0;
                    $scope.totalScope1 = 0;
                    $scope.totalScope2 = 0;
                    $scope.totalScope3 = 0;
                    for line in $scope.o.reportDTOs.R_1.reportLines
                        $scope.totalScope1 += line.scope1Value
                        $scope.totalScope2 += line.scope2Value
                        $scope.totalScope3 += line.scope3Value

                        $scope.totalEmissions += line.scope1Value
                        $scope.totalEmissions += line.scope2Value
                        $scope.totalEmissions += line.scope3Value
                        $scope.totalEmissions += line.outOfScopeValue

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
