angular
.module('app.controllers')
.controller "AdminAdvicesManagerCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, codeLabelHelper) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu

    $scope.actionAdvices = []
    $scope.typeCodeLabels = []
    $scope.baseIndicatorCodeLabels = []
    $scope.interfaceTypeCodeLabels = []

    $scope.loadActionAdvices = () ->
        downloadService.getJson "awac/admin/advices/load", (result) ->
            if result.success
                $scope.actionAdvices = result.data.reducingActionAdvices
                codeLists = result.data.codeLists
                $scope.typeCodeLabels = _.findWhere(codeLists, {code: 'REDUCING_ACTION_TYPE'}).codeLabels
                $scope.baseIndicatorCodeLabels = _.findWhere(codeLists, {code: 'BASE_INDICATOR'}).codeLabels
                $scope.interfaceTypeCodeLabels = _.findWhere(codeLists, {code: 'INTERFACE_TYPE'}).codeLabels

    $scope.createActionAdvice = () ->
        params =
            typeCodeLabels: $scope.typeCodeLabels
            baseIndicatorCodeLabels: $scope.baseIndicatorCodeLabels
            interfaceTypeCodeLabels: $scope.interfaceTypeCodeLabels
            cb: $scope.loadActionAdvices
        modalService.show(modalService.CREATE_OR_EDIT_REDUCTION_ACTION_ADVICE, params)

    $scope.updateActionAdvice = (actionAdvice) ->
        params =
            typeCodeLabels: $scope.typeCodeLabels
            baseIndicatorCodeLabels: $scope.baseIndicatorCodeLabels
            interfaceTypeCodeLabels: $scope.interfaceTypeCodeLabels
            actionAdvice: actionAdvice
        modalService.show(modalService.CREATE_OR_EDIT_REDUCTION_ACTION_ADVICE, params)

    $scope.deleteActionAdvice = (actionAdvice) ->
        $scope.isLoading = true

        downloadService.postJson "awac/admin/advices/delete", actionAdvice, (result) ->
            $scope.isLoading = false
            if result.success
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
                for index, e of $scope.actionAdvices
                    if e.id == actionAdvice.id
                        $scope.actionAdvices.splice(index, 1);
                        break

    $scope.confirmDelete = (action) ->
        params =
            titleKey: "REDUCING_ACTION_ADVICE_DELETE_CONFIRMATION_TITLE"
            messageKey: "REDUCING_ACTION_ADVICE_DELETE_CONFIRMATION_MESSAGE"
            onConfirm: () ->
                $scope.deleteActionAdvice(action)
        modalService.show(modalService.CONFIRM_DIALOG, params)

    $scope.exportActionAdvicesToXls = () ->
        $window.open '/awac/admin/advices/exportToXls', translationService.get 'RESULT_DOWNLOAD_START', null

    $scope.getTypeLabel = (typeKey) ->
        return codeLabelHelper.getLabelByKey($scope.typeCodeLabels, typeKey)

    $scope.getBaseIndicatorLabel = (baseIndicatorKey) ->
        return baseIndicatorKey + " - " + codeLabelHelper.getLabelByKey($scope.baseIndicatorCodeLabels,
            baseIndicatorKey)

    $scope.getInterfaceTypeLabel = (interfaceTypeKey) ->
        return codeLabelHelper.getLabelByKey($scope.interfaceTypeCodeLabels, interfaceTypeKey)

    $scope.loadActionAdvices()

