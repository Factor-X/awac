angular
.module('app.controllers')
.controller "AdminTranslationSubListCtrl", ($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper, displayLittleFormMenu) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu
    $scope.subLists = []
    $scope.codeLabels = {}
    $scope.isLoading = false
    $scope.waitingData = true

    $scope.itemToAdd =
        key: ""
        clear: ->
            this.key = ""
            return

    $scope.loadSubLists = () ->
        downloadService.getJson "awac/admin/translations/sublists/load", (result) ->
            if result.success
                $scope.subLists = $scope.sortLinkedListsItems(result.data.sublists)
                $scope.subLists = _.sortBy $scope.subLists, (list) ->
                    return list.codeList
                for codeLabelList in result.data.codeLabels
                    $scope.codeLabels[codeLabelList.code] = codeLabelHelper.sortCodeLabelsByNumericKey codeLabelList.codeLabels
                $scope.waitingData = false
            return
        return

    $scope.save = ->
        $scope.isLoading = true

        # update orderIndex field
        for subList in $scope.subLists
            for index, item of subList.items
                item.orderIndex = +index + 1
        data = {sublists: $scope.subLists}

        # post data
        downloadService.postJson "awac/admin/translations/sublists/save", data, (result) ->
            $scope.isLoading = false
            if result.success
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
                angular.extend($scope.subLists, $scope.sortLinkedListsItems(result.data.sublists))
                $scope.subLists = _.sortBy $scope.subLists, (list) ->
                    return list.codeList
            return

        return

    $scope.addItem = (subList) ->
        if ! (!!$scope.itemToAdd.key)
            messageFlash.displayError "Veuillez sélectionner un élément de la liste!"
            return
        if !! _.findWhere(subList.items, {'key': $scope.itemToAdd.key})
            messageFlash.displayError "Echec de l'ajout: la clé '" + $scope.itemToAdd.key + "' est déjà présente dans cette liste!"
            return
        subList.items.push(angular.copy($scope.itemToAdd))
        $scope.itemToAdd.clear()
        return

    $scope.sortLinkedListsItems = (linkedLists) ->
        for linkedList in linkedLists
            linkedList.items = _.sortBy linkedList.items, (item) ->
                return item.orderIndex
        return linkedLists

    $scope.getLabelByKey = (codeList, key) ->
        return codeLabelHelper.getLabelByKey($scope.codeLabels[codeList], key)

    $scope.loadSubLists()