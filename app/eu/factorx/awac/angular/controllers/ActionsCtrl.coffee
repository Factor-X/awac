angular
.module('app.controllers')
.controller "ActionsCtrl", ($scope, displayFormMenu, modalService, downloadService, $filter) ->
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

    $scope.create = () ->
        modalService.show(modalService.CREATE_REDUCTION_ACTION, { typeOptions: $scope.typeOptions, gwpUnits: $scope.gwpUnits, cb: $scope.loadActions })

    $scope.loadActions()

