angular
.module('app.controllers')
.controller "AdminTranslationSingleListCtrl", ($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper) ->

    $scope.state = $scope.$parent.state

    $scope.baseLists = []
    $scope.codeLabels = {}
    $scope.waitingData = true
    $scope.isLoading = false
    $scope.baseListLabel = ""

    $scope.loadCodeLabels = () ->
        if ($scope.state == 'error-messages')
            baseList = 'TRANSLATIONS_ERROR_MESSAGES'
            $scope.baseListLabel = "Messages d'erreurs"
        else if ($scope.state == 'emails')
            baseList = 'TRANSLATIONS_EMAIL_MESSAGE'
            $scope.baseListLabel = "E-Mails"

        downloadService.getJson "/awac/admin/translations/codelabels/load/" + baseList, (result) ->
            if result.success
                $scope.codeLabelsByList = result.data.codeLabelsByList
                $scope.codeLabels = codeLabelHelper.sortCodeLabelsByOrder $scope.codeLabelsByList[baseList]
            $scope.waitingData = false
            return
        return

    $scope.save = () ->
        $scope.isLoading = true

        data = {codeLabelsByList: $scope.codeLabelsByList}

        downloadService.postJson "/awac/admin/translations/codelabels/save", data, (result) ->
            $scope.isLoading = false
            if result.success
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"


    $scope.loadCodeLabels()