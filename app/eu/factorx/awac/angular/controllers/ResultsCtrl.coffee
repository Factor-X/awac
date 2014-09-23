angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, $window, downloadService, displayFormMenu, modalService, messageFlash,translationService) ->
    $scope.displayFormMenu = displayFormMenu

    $scope.$root.$watch('mySites', (nv) ->
        $scope.mySites = $scope.$root.mySites
        for s in $scope.mySites
            if "" + s.id == "" + $scope.$root.scopeSelectedId
                s.$selected = true
    , true);


    $scope.$watch('mySites|filter:{$selected:true}', (sites) ->
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


                    if $scope.$root.instanceName == 'municipality'
                        $scope.o.reportDTOs.R_1 = $scope.o.reportDTOs.RCo_1
                        $scope.o.reportDTOs.R_2 = $scope.o.reportDTOs.RCo_2
                        $scope.o.reportDTOs.R_3 = $scope.o.reportDTOs.RCo_3
                        $scope.o.reportDTOs.R_4 = $scope.o.reportDTOs.RCo_4
                        $scope.o.reportDTOs.R_5 = $scope.o.reportDTOs.RCo_5

                        $scope.o.svgDonuts.R_1 = $scope.o.svgDonuts.RCo_1
                        $scope.o.svgDonuts.R_2 = $scope.o.svgDonuts.RCo_2
                        $scope.o.svgDonuts.R_3 = $scope.o.svgDonuts.RCo_3
                        $scope.o.svgDonuts.R_4 = $scope.o.svgDonuts.RCo_4
                        $scope.o.svgDonuts.R_5 = $scope.o.svgDonuts.RCo_5

                        $scope.o.svgWebs.R_1 = $scope.o.svgWebs.RCo_1
                        $scope.o.svgWebs.R_2 = $scope.o.svgWebs.RCo_2
                        $scope.o.svgWebs.R_3 = $scope.o.svgWebs.RCo_3
                        $scope.o.svgWebs.R_4 = $scope.o.svgWebs.RCo_4
                        $scope.o.svgWebs.R_5 = $scope.o.svgWebs.RCo_5

                        $scope.o.svgHistograms.R_1 = $scope.o.svgHistograms.RCo_1
                        $scope.o.svgHistograms.R_2 = $scope.o.svgHistograms.RCo_2
                        $scope.o.svgHistograms.R_3 = $scope.o.svgHistograms.RCo_3
                        $scope.o.svgHistograms.R_4 = $scope.o.svgHistograms.RCo_4
                        $scope.o.svgHistograms.R_5 = $scope.o.svgHistograms.RCo_5


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
