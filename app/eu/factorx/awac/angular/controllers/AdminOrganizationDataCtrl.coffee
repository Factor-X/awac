angular
.module('app.controllers')
.controller "AdminOrganizationDataCtrl", ($scope, $compile, downloadService, modalService, messageFlash, displayLittleFormMenu) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu

    downloadService.getJson "/awac/admin/organizationData", (result) ->
        if result.success
            console.log result.data
            $scope.computeData(result.data)

    $scope.data = {
        enterprise:
            total:0
            shareData:0
            sites:0
            products:0
            closedForm:{}
        municipality:
            total:0
            shareData:0
            sites:0
            products:0
            closedForm:{}
        event:
            total:0
            shareData:0
            sites:0
            products:0
            closedForm:{}
        household:
            total:0
            shareData:0
            sites:0
            products:0
            closedForm:{}
        littleEmitter:
            total:0
            shareData:0
            sites: 0
            products: 0
            closedForm: {}
    }

    $scope.computeData = (data) ->
        for org in data.list

            $scope.data[org.organization.interfaceName].total++
            if org.organization.statisticsAllowed == true
                $scope.data[org.organization.interfaceName].shareData++
            $scope.data[org.organization.interfaceName].sites+=org.siteNb
            $scope.data[org.organization.interfaceName].products+=org.productNb
            if org.closedFormMap?
                for key in Object.keys(org.closedFormMap)
                    if !$scope.data[org.organization.interfaceName].closedForm[key]
                        $scope.data[org.organization.interfaceName].closedForm[key] =org.closedFormMap[key]
                    else
                        $scope.data[org.organization.interfaceName].closedForm[key]+=org.closedFormMap[key]

        console.log $scope.data

    $scope.sendEmail = (calculatorTarget, onlyOrganizationSharedData) ->
        params =
            calculatorTarget:calculatorTarget
            onlyOrganizationSharedData:onlyOrganizationSharedData
        modalService.show modalService.SEND_EMAIL, params
