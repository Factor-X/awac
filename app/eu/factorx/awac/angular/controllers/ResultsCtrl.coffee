angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, $window, $filter, downloadService, modalService, messageFlash, translationService) ->
    $scope.displayFormMenu = true

    $scope.verificationRequests = []

    $scope.$root.$watch 'mySites', (nv) ->
        console.log 'watch $root.mySites'
        $scope.mySites = angular.copy $scope.$root.mySites
        for s in $scope.mySites
            if "" + s.id == "" + $scope.$root.scopeSelectedId
                s.selected = true
    , true

    $scope.getVerificationRequestStatus = ->
        if $scope.$root.verificationRequest?
            return $scope.$root.verificationRequest.status

    $scope.getVerificationRequestOrganizationVerifierName = ->
        return $scope.$root.verificationRequest?.organizationVerifier?.name

    $scope.$watch '$root.periodToCompare', () ->
        $scope.reload()

    $scope.$watch 'mySites', () ->
        console.log 'watch mySites'
        $scope.reload()
    , true


    downloadService.getJson '/awac/verification/verificationRequests/' + $scope.$root.periodSelectedKey, (result) ->
        if not result.success
            messageFlash.displayError result.data.message
        else
            console.log 'verif request : '
            console.log result.data.list
            $scope.verificationRequests = result.data.list


    $scope.downloadVerificationReport = (verificationRequest)->
        url = '/awac/file/download/' + verificationRequest.verificationSuccessFileId
        $window.open(url)

    $scope.exportPdf = () ->
        sites = $scope.mySites.filter((e) ->
            return e.selected
        )

        dto =
            __type: 'eu.factorx.awac.dto.awac.post.GetReportParametersDTO'
            periodKey: $scope.$root.periodSelectedKey
            scopesIds: sites.map((s) ->
                s.id
            )

        if $scope.$root.periodToCompare != 'default'
            dto.comparedPeriodKey = $scope.$root.periodToCompare

        $scope.pdfLoading = true

        downloadService.postJson '/awac/result/getReportAsPdf', dto, (result) ->
            $scope.pdfLoading = false

            messageFlash.displayInfo 'PREPARING_DOCUMENT'


    $scope.exportXls = () ->
        sites = $scope.mySites.filter((e) ->
            return e.selected
        )

        dto =
            __type: 'eu.factorx.awac.dto.awac.post.GetReportParametersDTO'
            periodKey: $scope.$root.periodSelectedKey
            scopesIds: sites.map((s) ->
                s.id
            )

        if $scope.$root.periodToCompare != 'default'
            dto.comparedPeriodKey = $scope.$root.periodToCompare

        $scope.xlsLoading = true

        downloadService.postJson '/awac/result/getReportAsXls', dto, (result) ->
            $scope.xlsLoading = false

            messageFlash.displayInfo 'PREPARING_DOCUMENT'


    $scope.dataURItoBlob = (dataURI) ->

        # convert base64 to raw binary data held in a string
        # doesn't handle URLEncoded DataURIs - see SO answer #6850276 for code that does this
        byteString = atob(dataURI.split(",")[1])

        # separate out the mime component
        mimeString = dataURI.split(",")[0].split(":")[1].split(";")[0]

        # write the bytes of the string to an ArrayBuffer
        ab = new ArrayBuffer(byteString.length)
        ia = new Uint8Array(ab)
        i = 0

        while i < byteString.length
            ia[i] = byteString.charCodeAt(i)
            i++

        # write the ArrayBuffer to a blob, and you're done
        bb = new BlobBuilder()
        bb.append ab
        return bb.getBlob(mimeString)


    $scope.reload = () ->
        sites = $scope.mySites.filter((e) ->
            return e.selected
        )

        if sites.length > 0

            $scope.o = undefined
            $scope.leftTotalEmissions = undefined
            $scope.leftTotalScope1 = undefined
            $scope.leftTotalScope2 = undefined
            $scope.leftTotalScope3 = undefined
            $scope.rightTotalEmissions = undefined
            $scope.rightTotalScope1 = undefined
            $scope.rightTotalScope2 = undefined
            $scope.rightTotalScope3 = undefined
            modalService.show modalService.LOADING

            dto =
                __type: 'eu.factorx.awac.dto.awac.post.GetReportParametersDTO'
                periodKey: $scope.$root.periodSelectedKey
                scopesIds: sites.map((s) ->
                    s.id
                )

            if $scope.$root.periodToCompare != 'default'
                dto.comparedPeriodKey = $scope.$root.periodToCompare


            downloadService.postJson '/awac/result/getReport', dto, (result) ->
                modalService.close modalService.LOADING
                if result.success
                    $scope.o = result.data


                    if $scope.$root.instanceName == 'municipality'
                        $scope.o.reportDTOs.R_1 = $scope.o.reportDTOs.RCo_1
                        $scope.o.reportDTOs.R_2 = $scope.o.reportDTOs.RCo_2
                        $scope.o.reportDTOs.R_3 = $scope.o.reportDTOs.RCo_3
                        $scope.o.reportDTOs.R_4 = $scope.o.reportDTOs.RCo_4
                        $scope.o.reportDTOs.R_5 = $scope.o.reportDTOs.RCo_5

                        $scope.o.leftSvgDonuts.R_1 = $scope.o.leftSvgDonuts.RCo_1
                        $scope.o.leftSvgDonuts.R_2 = $scope.o.leftSvgDonuts.RCo_2
                        $scope.o.leftSvgDonuts.R_3 = $scope.o.leftSvgDonuts.RCo_3
                        $scope.o.leftSvgDonuts.R_4 = $scope.o.leftSvgDonuts.RCo_4
                        $scope.o.leftSvgDonuts.R_5 = $scope.o.leftSvgDonuts.RCo_5

                        $scope.o.rightSvgDonuts.R_1 = $scope.o.rightSvgDonuts.RCo_1
                        $scope.o.rightSvgDonuts.R_2 = $scope.o.rightSvgDonuts.RCo_2
                        $scope.o.rightSvgDonuts.R_3 = $scope.o.rightSvgDonuts.RCo_3
                        $scope.o.rightSvgDonuts.R_4 = $scope.o.rightSvgDonuts.RCo_4
                        $scope.o.rightSvgDonuts.R_5 = $scope.o.rightSvgDonuts.RCo_5

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

                        $scope.o.svgHistogramsCEF.R_1 = $scope.o.svgHistogramsCEF.RCo_1
                        $scope.o.svgHistogramsCEF.R_2 = $scope.o.svgHistogramsCEF.RCo_2
                        $scope.o.svgHistogramsCEF.R_3 = $scope.o.svgHistogramsCEF.RCo_3
                        $scope.o.svgHistogramsCEF.R_4 = $scope.o.svgHistogramsCEF.RCo_4
                        $scope.o.svgHistogramsCEF.R_5 = $scope.o.svgHistogramsCEF.RCo_5

                        $scope.o.reportCEFDTOs.R_1 = $scope.o.reportCEFDTOs.RCo_1
                        $scope.o.reportCEFDTOs.R_2 = $scope.o.reportCEFDTOs.RCo_2
                        $scope.o.reportCEFDTOs.R_3 = $scope.o.reportCEFDTOs.RCo_3
                        $scope.o.reportCEFDTOs.R_4 = $scope.o.reportCEFDTOs.RCo_4
                        $scope.o.reportCEFDTOs.R_5 = $scope.o.reportCEFDTOs.RCo_5

                    if $scope.$root.instanceName == 'event'
                        $scope.o.reportDTOs.R_1 = $scope.o.reportDTOs.REv_1
                        $scope.o.reportDTOs.R_2 = $scope.o.reportDTOs.REv_2
                        $scope.o.reportDTOs.R_3 = $scope.o.reportDTOs.REv_3
                        $scope.o.reportDTOs.R_4 = $scope.o.reportDTOs.REv_4
                        $scope.o.reportDTOs.R_5 = $scope.o.reportDTOs.REv_5

                        $scope.o.leftSvgDonuts.R_1 = $scope.o.leftSvgDonuts.REv_1
                        $scope.o.leftSvgDonuts.R_2 = $scope.o.leftSvgDonuts.REv_2
                        $scope.o.leftSvgDonuts.R_3 = $scope.o.leftSvgDonuts.REv_3
                        $scope.o.leftSvgDonuts.R_4 = $scope.o.leftSvgDonuts.REv_4
                        $scope.o.leftSvgDonuts.R_5 = $scope.o.leftSvgDonuts.REv_5

                        $scope.o.rightSvgDonuts.R_1 = $scope.o.rightSvgDonuts.REv_1
                        $scope.o.rightSvgDonuts.R_2 = $scope.o.rightSvgDonuts.REv_2
                        $scope.o.rightSvgDonuts.R_3 = $scope.o.rightSvgDonuts.REv_3
                        $scope.o.rightSvgDonuts.R_4 = $scope.o.rightSvgDonuts.REv_4
                        $scope.o.rightSvgDonuts.R_5 = $scope.o.rightSvgDonuts.REv_5

                        $scope.o.svgWebs.R_1 = $scope.o.svgWebs.REv_1
                        $scope.o.svgWebs.R_2 = $scope.o.svgWebs.REv_2
                        $scope.o.svgWebs.R_3 = $scope.o.svgWebs.REv_3
                        $scope.o.svgWebs.R_4 = $scope.o.svgWebs.REv_4
                        $scope.o.svgWebs.R_5 = $scope.o.svgWebs.REv_5

                        $scope.o.svgHistograms.R_1 = $scope.o.svgHistograms.REv_1
                        $scope.o.svgHistograms.R_2 = $scope.o.svgHistograms.REv_2
                        $scope.o.svgHistograms.R_3 = $scope.o.svgHistograms.REv_3
                        $scope.o.svgHistograms.R_4 = $scope.o.svgHistograms.REv_4
                        $scope.o.svgHistograms.R_5 = $scope.o.svgHistograms.REv_5

                        $scope.o.svgHistogramsCEF.R_1 = $scope.o.svgHistogramsCEF.REv_1
                        $scope.o.svgHistogramsCEF.R_2 = $scope.o.svgHistogramsCEF.REv_2
                        $scope.o.svgHistogramsCEF.R_3 = $scope.o.svgHistogramsCEF.REv_3
                        $scope.o.svgHistogramsCEF.R_4 = $scope.o.svgHistogramsCEF.REv_4
                        $scope.o.svgHistogramsCEF.R_5 = $scope.o.svgHistogramsCEF.REv_5

                        $scope.o.reportCEFDTOs.R_1 = $scope.o.reportCEFDTOs.REv_1
                        $scope.o.reportCEFDTOs.R_2 = $scope.o.reportCEFDTOs.REv_2
                        $scope.o.reportCEFDTOs.R_3 = $scope.o.reportCEFDTOs.REv_3
                        $scope.o.reportCEFDTOs.R_4 = $scope.o.reportCEFDTOs.REv_4
                        $scope.o.reportCEFDTOs.R_5 = $scope.o.reportCEFDTOs.REv_5

                    if $scope.$root.instanceName == 'household'
                        $scope.o.reportDTOs.R_1 = $scope.o.reportDTOs.RMe_1
                        $scope.o.reportDTOs.R_2 = $scope.o.reportDTOs.RMe_2
                        $scope.o.reportDTOs.R_3 = $scope.o.reportDTOs.RMe_3
                        $scope.o.reportDTOs.R_4 = $scope.o.reportDTOs.RMe_4
                        $scope.o.reportDTOs.R_5 = $scope.o.reportDTOs.RMe_5

                        $scope.o.leftSvgDonuts.R_1 = $scope.o.leftSvgDonuts.RMe_1
                        $scope.o.leftSvgDonuts.R_2 = $scope.o.leftSvgDonuts.RMe_2
                        $scope.o.leftSvgDonuts.R_3 = $scope.o.leftSvgDonuts.RMe_3
                        $scope.o.leftSvgDonuts.R_4 = $scope.o.leftSvgDonuts.RMe_4
                        $scope.o.leftSvgDonuts.R_5 = $scope.o.leftSvgDonuts.RMe_5

                        $scope.o.rightSvgDonuts.R_1 = $scope.o.rightSvgDonuts.RMe_1
                        $scope.o.rightSvgDonuts.R_2 = $scope.o.rightSvgDonuts.RMe_2
                        $scope.o.rightSvgDonuts.R_3 = $scope.o.rightSvgDonuts.RMe_3
                        $scope.o.rightSvgDonuts.R_4 = $scope.o.rightSvgDonuts.RMe_4
                        $scope.o.rightSvgDonuts.R_5 = $scope.o.rightSvgDonuts.RMe_5

                        $scope.o.svgWebs.R_1 = $scope.o.svgWebs.RMe_1
                        $scope.o.svgWebs.R_2 = $scope.o.svgWebs.RMe_2
                        $scope.o.svgWebs.R_3 = $scope.o.svgWebs.RMe_3
                        $scope.o.svgWebs.R_4 = $scope.o.svgWebs.RMe_4
                        $scope.o.svgWebs.R_5 = $scope.o.svgWebs.RMe_5

                        $scope.o.svgHistograms.R_1 = $scope.o.svgHistograms.RMe_1
                        $scope.o.svgHistograms.R_2 = $scope.o.svgHistograms.RMe_2
                        $scope.o.svgHistograms.R_3 = $scope.o.svgHistograms.RMe_3
                        $scope.o.svgHistograms.R_4 = $scope.o.svgHistograms.RMe_4
                        $scope.o.svgHistograms.R_5 = $scope.o.svgHistograms.RMe_5

                        $scope.o.svgHistogramsCEF.R_1 = $scope.o.svgHistogramsCEF.RMe_1
                        $scope.o.svgHistogramsCEF.R_2 = $scope.o.svgHistogramsCEF.RMe_2
                        $scope.o.svgHistogramsCEF.R_3 = $scope.o.svgHistogramsCEF.RMe_3
                        $scope.o.svgHistogramsCEF.R_4 = $scope.o.svgHistogramsCEF.RMe_4
                        $scope.o.svgHistogramsCEF.R_5 = $scope.o.svgHistogramsCEF.RMe_5

                        $scope.o.reportCEFDTOs.R_1 = $scope.o.reportCEFDTOs.RMe_1
                        $scope.o.reportCEFDTOs.R_2 = $scope.o.reportCEFDTOs.RMe_2
                        $scope.o.reportCEFDTOs.R_3 = $scope.o.reportCEFDTOs.RMe_3
                        $scope.o.reportCEFDTOs.R_4 = $scope.o.reportCEFDTOs.RMe_4
                        $scope.o.reportCEFDTOs.R_5 = $scope.o.reportCEFDTOs.RMe_5

                    if $scope.$root.instanceName == 'littleemitter'
                        $scope.o.reportDTOs.R_1 = $scope.o.reportDTOs.RPE_1
                        $scope.o.reportDTOs.R_2 = $scope.o.reportDTOs.RPE_2
                        $scope.o.reportDTOs.R_3 = $scope.o.reportDTOs.RPE_3
                        $scope.o.reportDTOs.R_4 = $scope.o.reportDTOs.RPE_4
                        $scope.o.reportDTOs.R_5 = $scope.o.reportDTOs.RPE_5

                        $scope.o.leftSvgDonuts.R_1 = $scope.o.leftSvgDonuts.RPE_1
                        $scope.o.leftSvgDonuts.R_2 = $scope.o.leftSvgDonuts.RPE_2
                        $scope.o.leftSvgDonuts.R_3 = $scope.o.leftSvgDonuts.RPE_3
                        $scope.o.leftSvgDonuts.R_4 = $scope.o.leftSvgDonuts.RPE_4
                        $scope.o.leftSvgDonuts.R_5 = $scope.o.leftSvgDonuts.RPE_5

                        $scope.o.rightSvgDonuts.R_1 = $scope.o.rightSvgDonuts.RPE_1
                        $scope.o.rightSvgDonuts.R_2 = $scope.o.rightSvgDonuts.RPE_2
                        $scope.o.rightSvgDonuts.R_3 = $scope.o.rightSvgDonuts.RPE_3
                        $scope.o.rightSvgDonuts.R_4 = $scope.o.rightSvgDonuts.RPE_4
                        $scope.o.rightSvgDonuts.R_5 = $scope.o.rightSvgDonuts.RPE_5

                        $scope.o.svgWebs.R_1 = $scope.o.svgWebs.RPE_1
                        $scope.o.svgWebs.R_2 = $scope.o.svgWebs.RPE_2
                        $scope.o.svgWebs.R_3 = $scope.o.svgWebs.RPE_3
                        $scope.o.svgWebs.R_4 = $scope.o.svgWebs.RPE_4
                        $scope.o.svgWebs.R_5 = $scope.o.svgWebs.RPE_5

                        $scope.o.svgHistograms.R_1 = $scope.o.svgHistograms.RPE_1
                        $scope.o.svgHistograms.R_2 = $scope.o.svgHistograms.RPE_2
                        $scope.o.svgHistograms.R_3 = $scope.o.svgHistograms.RPE_3
                        $scope.o.svgHistograms.R_4 = $scope.o.svgHistograms.RPE_4
                        $scope.o.svgHistograms.R_5 = $scope.o.svgHistograms.RPE_5

                        $scope.o.svgHistogramsCEF.R_1 = $scope.o.svgHistogramsCEF.RPE_1
                        $scope.o.svgHistogramsCEF.R_2 = $scope.o.svgHistogramsCEF.RPE_2
                        $scope.o.svgHistogramsCEF.R_3 = $scope.o.svgHistogramsCEF.RPE_3
                        $scope.o.svgHistogramsCEF.R_4 = $scope.o.svgHistogramsCEF.RPE_4
                        $scope.o.svgHistogramsCEF.R_5 = $scope.o.svgHistogramsCEF.RPE_5

                        $scope.o.reportCEFDTOs.R_1 = $scope.o.reportCEFDTOs.RPE_1
                        $scope.o.reportCEFDTOs.R_2 = $scope.o.reportCEFDTOs.RPE_2
                        $scope.o.reportCEFDTOs.R_3 = $scope.o.reportCEFDTOs.RPE_3
                        $scope.o.reportCEFDTOs.R_4 = $scope.o.reportCEFDTOs.RPE_4
                        $scope.o.reportCEFDTOs.R_5 = $scope.o.reportCEFDTOs.RPE_5

                    $scope.leftTotalEmissions = 0;
                    $scope.leftTotalScope1 = 0;
                    $scope.leftTotalScope2 = 0;
                    $scope.leftTotalScope3 = 0;
                    $scope.rightTotalEmissions = 0;
                    $scope.rightTotalScope1 = 0;
                    $scope.rightTotalScope2 = 0;
                    $scope.rightTotalScope3 = 0;

                    for line in $scope.o.reportDTOs.R_1.reportLines
                        $scope.leftTotalScope1 += line.leftScope1Value
                        $scope.leftTotalScope2 += line.leftScope2Value
                        $scope.leftTotalScope3 += line.leftScope3Value
                        $scope.rightTotalScope1 += line.rightScope1Value
                        $scope.rightTotalScope2 += line.rightScope2Value
                        $scope.rightTotalScope3 += line.rightScope3Value

                        $scope.leftTotalEmissions += line.leftScope1Value
                        $scope.leftTotalEmissions += line.leftScope2Value
                        $scope.leftTotalEmissions += line.leftScope3Value
                        # $scope.leftTotalEmissions += line.leftOutOfScopeValue

                        $scope.rightTotalEmissions += line.rightScope1Value
                        $scope.rightTotalEmissions += line.rightScope2Value
                        $scope.rightTotalEmissions += line.rightScope3Value
    # $scope.rightTotalEmissions += line.rightOutOfScopeValue


    $scope.current_tab = 1;

    $scope.siteSelectionIsEmpty = () ->
        filtered = $scope.mySites.filter (s) ->
            return s.selected
        return filtered.length == 0

    $scope.downloadAsXls = () ->
        $window.open '/awac/result/getReportAsXls/' + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, translationService.get 'RESULT_DOWNLOAD_START', null

    $scope.downloadPdf = () ->
