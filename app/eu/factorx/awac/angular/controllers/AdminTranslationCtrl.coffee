angular
.module('app.controllers')
.controller "AdminTranslationCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu

    $scope.subLists = []
    $scope.codeLabels = {}

    $scope.loadSubLists = () ->
        downloadService.getJson "awac/admin/translations/sublists/load", (result) ->
            if result.success
                $scope.subLists = result.data.sublists
                for i, clList of result.data.codeLabels
                    codeLabels = []
                    for j, codeLabel of clList.codeLabels
                        codeLabels.push({key: codeLabel.key, label: codeLabel.key + " - " + codeLabel.label})
                    $scope.codeLabels[clList.code] = $scope.sortCodeLabelsByKey(codeLabels)
                console.log($scope.codeLabels)

    $scope.sortCodeLabelsByKey = (codeLabels) ->
            res = _.sortBy(codeLabels, (codeLabel) ->
                return parseInt(codeLabel.key.match(/\d+/), 10)
            )
            return res

    $scope.loadSubLists()