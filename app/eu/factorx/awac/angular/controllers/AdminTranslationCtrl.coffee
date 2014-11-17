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

    $scope.undoChanges = () ->
        $scope.loadSubLists()
        return

    $scope.saveChanges = () ->
        data = {
            sublists: $scope.sublists
        }
        $scope.isLoading = true
        downloadService.postJson '/awac/admin/translations/sublists/save', data, (result) ->
            $scope.isLoading = false
            if result.success
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
            return
        return

    $scope.sortableOptions = {
        update: (e, ui) ->
            console.log(ui.item.scope())
    }

    $scope.addKey = (sublist) ->
        sublist.keys.push(sublist.keyToAdd)
        sublist.keyToAdd = ""

    $scope.loadSubLists()