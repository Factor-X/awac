angular
.module('app.controllers')
.controller "AdminTranslationInterfaceCtrl", ($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper, displayLittleFormMenu) ->

    $scope.displayLittleFormMenu = displayLittleFormMenu

    $scope.codeLabelsByList = {}
    $scope.codeLabelsGroups = []
    $scope.waitingData = true
    $scope.isLoading = false

    $scope.sortableOptions =
        axis: 'y'
        stop: (e, ui) ->
            for index of $scope.subList.items
                $scope.subList.items[index].orderIndex = +index;

    $scope.loadCodeLabels = () ->
        downloadService.getJson "/awac/admin/translations/codelabels/load/TRANSLATIONS_INTERFACE", (result) ->
            if result.success
                $scope.codeLabelsByList = result.data.codeLabelsByList
                codeLabels = codeLabelHelper.sortCodeLabelsByOrder $scope.codeLabelsByList["TRANSLATIONS_INTERFACE"]
                codeLabelsByTopic = _.groupBy codeLabels, (codeLabel) ->
                    return codeLabel.topic || ""
                for topic, topicCodeLabels of codeLabelsByTopic
                    $scope.codeLabelsGroups.push({topic: topic, codeLabels: topicCodeLabels})
            $scope.waitingData = false
            return
        return

    $scope.save = () ->
        $scope.isLoading = true
        # create new array of code labels
        codeLabels = []
        for i, codeLabelsGroup of $scope.codeLabelsGroups
            codeLabels = codeLabels.concat(codeLabelsGroup.codeLabels)
        $scope.codeLabelsByList["TRANSLATIONS_INTERFACE"] = codeLabels

        data = {codeLabelsByList: $scope.codeLabelsByList}

        downloadService.postJson "/awac/admin/translations/codelabels/save", data, (result) ->
            $scope.isLoading = false
            if result.success
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"


    $scope.loadCodeLabels()