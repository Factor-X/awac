angular
.module('app.controllers')
.controller "AdminTranslationSingleListCtrl", ($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper, displayLittleFormMenu, state, $location) ->

    $scope.displayLittleFormMenu = displayLittleFormMenu
    $scope.state = state

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
                $scope.initialCodeLabels = angular.copy($scope.codeLabels)
            $scope.waitingData = false
            return
        return

    $scope.save = () ->
        $scope.isLoading = true

        data = {codeLabelsByList: $scope.codeLabelsByList}

        downloadService.postJson "/awac/admin/translations/codelabels/save", data, (result) ->
            $scope.isLoading = false
            if result.success
                $scope.loadCodeLabels()
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"

    $scope.ignoreChanges = false

    $scope.$root.$on '$locationChangeStart', (event, next, current) ->
        return unless !$scope.ignoreChanges
        eq = angular.equals($scope.initialCodeLabels, $scope.codeLabels)
        if !eq
            event.preventDefault()

            # show confirm
            params =
                titleKey: "DIVERS_CANCEL_CONFIRMATION_TITLE"
                messageKey: "DIVERS_CANCEL_CONFIRMATION_MESSAGE"
                onConfirm: () ->
                    $scope.ignoreChanges = true
                    $location.path(next.split('#')[1])
            modalService.show modalService.CONFIRM_DIALOG, params


    $scope.loadCodeLabels()