angular
.module('app.controllers')
.controller "AdminOrganizationDataCtrl", ($scope, $compile, downloadService, modalService, messageFlash, displayLittleFormMenu) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu

    downloadService.getJson "/awac/admin/organizationData", (result) ->
        if result.success
            console.log result.data
            $scope.computeData(result.data)

    downloadService.getJson "/awac/admin/organizationData/getLanguagesData", (result) ->
        if result.success
            for k,v of result.data.map
                if $scope.data[k]
                    $scope.data[k].frEnabled = v.frEnabled
                    $scope.data[k].nlEnabled = v.nlEnabled
                    $scope.data[k].enEnabled = v.enEnabled


    $scope.data = {
        enterprise:
            total: 0
            shareData: 0
            sites: 0
            products: 0
            closedForm: {}
            frEnabled: false
            nlEnabled: false
            enEnabled: false
        municipality:
            total: 0
            shareData: 0
            sites: 0
            products: 0
            closedForm: {}
            frEnabled: false
            nlEnabled: false
            enEnabled: false
        event:
            total: 0
            shareData: 0
            sites: 0
            products: 0
            closedForm: {}
            frEnabled: false
            nlEnabled: false
            enEnabled: false
        household:
            total: 0
            shareData: 0
            sites: 0
            products: 0
            closedForm: {}
            frEnabled: false
            nlEnabled: false
            enEnabled: false
        littleemitter:
            total: 0
            shareData: 0
            sites: 0
            products: 0
            closedForm: {}
            frEnabled: false
            nlEnabled: false
            enEnabled: false
    }

    $scope.computeData = (data) ->
        for org in data.list

            $scope.data[org.organization.interfaceName].total++
            if org.organization.statisticsAllowed == true
                $scope.data[org.organization.interfaceName].shareData++
            $scope.data[org.organization.interfaceName].sites += org.siteNb
            $scope.data[org.organization.interfaceName].products += org.productNb

            if org.closedFormMap?
                for key in Object.keys(org.closedFormMap)
                    if !$scope.data[org.organization.interfaceName].closedForm[key]
                        $scope.data[org.organization.interfaceName].closedForm[key] = org.closedFormMap[key]
                    else
                        $scope.data[org.organization.interfaceName].closedForm[key] += org.closedFormMap[key]

        console.log $scope.data

    $scope.sendEmail = (calculatorTarget, onlyOrganizationSharedData) ->
        params =
            calculatorTarget: calculatorTarget
            onlyOrganizationSharedData: onlyOrganizationSharedData
        modalService.show modalService.SEND_EMAIL, params

    $scope.toggleLanguageEnabled = (calculator, lang) ->
        modalService.show(modalService.LOADING)
        downloadService.getJson "/awac/admin/organizationData/toggleLanguage/" + calculator + "/" + lang, (result) ->
            modalService.close(modalService.LOADING)
            if result.success
                $scope.data[calculator][lang + 'Enabled'] = !$scope.data[calculator][lang + 'Enabled']
