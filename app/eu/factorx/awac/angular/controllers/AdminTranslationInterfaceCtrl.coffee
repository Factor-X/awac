angular
.module('app.controllers')
.controller "AdminTranslationInterfaceCtrl", ($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper) ->

    $scope.codeLabels = {}

    $scope.loadCodeLabels = () ->
        downloadService.getJson "awac/admin/translations/interface/load", (result) ->
            if result.success
                codeLabels = codeLabelHelper.sortCodeLabelsByKey result.data.codeLabels
                $scope.codeLabels = _.groupBy codeLabels, (codeLabel) ->
                    return codeLabel.topic

            console.log("$scope.codeLabels", $scope.codeLabels)
            return
        return

    $scope.loadCodeLabels()