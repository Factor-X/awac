angular
.module('app.controllers')
.controller "AdminAverageCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile, $window) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu
    $scope.isLoading = false

    $scope.naceCodes = [
        key: null
        label: 'tous les NACEs'
    ,
        key: 'SECTEURPRIMAIRE'
        label: 'Industrie primaire, hormis le secteur agricole'
    ,
        key: 'SECTEURSECONDAIRE'
        label: 'Production de biens intermédiaires'
    ,
        key: 'SECTEURSECONDAIRE'
        label: 'Production de biens de consommation'
    ,
        key: 'SECTEURTERTIAIRE'
        label: 'Tertiaire'
    ]

    $scope.onlyVerifiedForm = true

    downloadService.getJson '/awac/admin/average/naceCodes', (result) ->
        if result.success
            for codeList in result.data.list
                for code in codeList.codeLabels
                    $scope.naceCodes.push {key: codeList.code + "/" + code.key, label: code.label}

    $scope.interfaceNames = [
        "enterprise"
        "municipality"
        "household"
        "event"
        "littleemitter"
    ]

    $scope.results =
        interface: null
        period: null

    $scope.naceCode = null


    $scope.allFieldValid = () ->
        for key in Object.keys($scope.results)
            if key != '$$hashKey'
                if !$scope.results[key]?
                    return false
        return true


    $scope.askAverage = (interfaceName) ->
        $scope.isLoading = true
        if $scope.naceCode?
            naceCodeListKey = $scope.naceCode.split("/")[0]
            if $scope.naceCode.split("/").length > 1
                naceCodeKey = $scope.naceCode.split("/")[1];
        data =
            interfaceName: $scope.results.interface
            periodKey: $scope.results.period
            naceCodeListKey: naceCodeListKey
            naceCodeKey: naceCodeKey
            onlyVerifiedForm: $scope.onlyVerifiedForm
        console.log data
        downloadService.postJson '/awac/admin/average/computeAverage', data, (result) ->
            $scope.isLoading = false
            if result.success
                if !!result.data && !!result.data.message
                    messageFlash.displayInfo result.data.message
                else
                    messageFlash.displayInfo "STATS_SUCCESS_MESSAGE"
