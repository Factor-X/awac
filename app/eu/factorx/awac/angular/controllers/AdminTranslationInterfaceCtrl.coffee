angular
.module('app.controllers')
.controller "AdminTranslationInterfaceCtrl", ($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper) ->

    $scope.codeLabelsGroups = []

    $scope.loadCodeLabels = () ->
        downloadService.getJson "awac/admin/translations/interface/load", (result) ->
            if result.success
                codeLabels = codeLabelHelper.sortCodeLabelsByKey result.data.codeLabels
                codeLabelsByTopic = _.groupBy codeLabels, (codeLabel) ->
                    return codeLabel.topic
                for topic, topicCodeLabels of codeLabelsByTopic
                    $scope.codeLabelsGroups.push({topic: topic, codeLabels: topicCodeLabels})

            console.log($scope.codeLabelsGroups)
            return
        return

    $scope.save = () ->
        data = {}
        data.codeLabels = []
        for topic, codeLabels of $scope.codeLabelsGroups
            data.codeLabels.push codeLabels
        downloadService.postJson "/awac/admin/translations/interface/save", data, (result) ->
            $scope.isLoading = false
            if result.success
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"


    $scope.loadCodeLabels()