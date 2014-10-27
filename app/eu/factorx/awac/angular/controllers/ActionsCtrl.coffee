angular
.module('app.controllers')
.controller "ActionsCtrl", ($scope, $window, displayFormMenu, modalService, downloadService, $filter, messageFlash, translationService) ->
    $scope.displayFormMenu = displayFormMenu

    $scope.actions = []
    $scope.typeOptions = []
    $scope.statusOptions = []
    $scope.gwpUnits = []

    $scope.loadActions = () ->
        downloadService.getJson "awac/actions/load", (result) ->
            if result.success
                $scope.actions = result.data.reducingActions
                codeLists = result.data.codeLists
                $scope.typeOptions = _.findWhere(codeLists, {code: 'REDUCING_ACTION_TYPE'}).codeLabels
                $scope.statusOptions = _.findWhere(codeLists, {code: 'REDUCING_ACTION_STATUS'}).codeLabels
                $scope.gwpUnits = result.data.gwpUnits

    $scope.createAction = () ->
        params =
            typeOptions: $scope.typeOptions
            statusOptions: $scope.statusOptions
            gwpUnits: $scope.gwpUnits
            cb: $scope.loadActions
        modalService.show(modalService.CREATE_REDUCTION_ACTION, params)

    $scope.updateAction = (action) ->
        params =
            typeOptions: $scope.typeOptions
            statusOptions: $scope.statusOptions
            gwpUnits: $scope.gwpUnits
            action: action
        modalService.show(modalService.CREATE_REDUCTION_ACTION, params)

    $scope.markActionAsDone = (action) ->
        $scope.isLoading = true

        data = angular.copy(action)
        data.statusKey = "2"
        data.completionDate = new Date()

        downloadService.postJson '/awac/actions/save', data, (result) ->
            $scope.isLoading = false
            if result.success
                angular.extend(action, result.data)
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"

    $scope.deleteAction = (action) ->
        $scope.isLoading = true

        downloadService.postJson "awac/actions/delete", action, (result) ->
            $scope.isLoading = false
            if result.success
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
                for index, e of $scope.actions
                    if e.id == action.id
                        $scope.actions.splice(index, 1);
                        break

    $scope.confirmDelete = (action) ->
        params =
            titleKey: "REDUCING_ACTION_DELETE_CONFIRMATION_TITLE"
            messageKey: "REDUCING_ACTION_DELETE_CONFIRMATION_MESSAGE"
            onConfirm: () ->
                $scope.deleteAction(action)
        modalService.show(modalService.CONFIRM_DIALOG, params)

    $scope.exportActionsToXls = () ->
        $window.open '/awac/actions/exportToXls', translationService.get 'RESULT_DOWNLOAD_START', null

    $scope.getScopeName = (scopeTypeKey, scopeId) ->
        if (scopeTypeKey == "1")
            return $scope.$root.organizationName
        else
            return $scope.$root.mySites.filter((e) ->
                ("" + e.id) == ("" + scopeId)
            )[0].name

    $scope.getTypeLabel = (typeKey) ->
        return _.findWhere($scope.typeOptions, {key: typeKey }).label

    $scope.getStatusLabel = (statusKey) ->
        return _.findWhere($scope.statusOptions, {key: statusKey }).label

    $scope.getGwpUnitSymbol = (gwpUnitCodeKey) ->
        return if (gwpUnitCodeKey != null) then _.findWhere($scope.gwpUnits, {code: gwpUnitCodeKey }).name

    $scope.$watch '$root.mySites', (n, o) ->
        if !!n
            $scope.loadActions()

