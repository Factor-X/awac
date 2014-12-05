angular
.module('app.controllers')
.controller "AdminTranslationLinkedListCtrl", ($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper, displayLittleFormMenu, $location) ->

    $scope.displayLittleFormMenu = displayLittleFormMenu
    $scope.linkedLists = []
    $scope.activitySourcesLabels = []
    $scope.activityTypesLabels = []
    $scope.waitingData = true
    $scope.isLoading = false

    $scope.itemToAdd =
        key: ""
        labelEn: ""
        labelFr: ""
        labelNl: ""
        activitySourceKey: ""
        activityTypeKey: ""
        clear: ->
            this.key = ""
            this.labelEn = ""
            this.labelFr = ""
            this.labelNl = ""
            this.activitySourceKey = ""
            this.activityTypeKey = ""
            return

    $scope.sortableOptions =
        axis: 'y'

    $scope.loadLinkedLists = () ->
        downloadService.getJson "awac/admin/translations/linkedlists/load", (result) ->
            if result.success
                $scope.linkedLists = $scope.sortLinkedListsItems(result.data.linkedLists)
                $scope.initialLinkedLists = angular.copy($scope.linkedLists)
                codeLabels = result.data.codeLabels
                $scope.activitySourcesLabels = codeLabelHelper.sortCodeLabelsByOrder(_.findWhere(codeLabels, {code: 'ActivitySource'}).codeLabels)
                $scope.activityTypesLabels = codeLabelHelper.sortCodeLabelsByOrder(_.findWhere(codeLabels, {code: 'ActivityType'}).codeLabels)
            $scope.waitingData = false
            return
        return

    updateOrderIndexes = (linkedLists) ->
        # update orderIndex field
        for linkedList in linkedLists
            for index, item of linkedList.items
                item.orderIndex = +index + 1
        return linkedLists

    $scope.save = ->
        $scope.isLoading = true

        data = {linkedLists: updateOrderIndexes($scope.linkedLists)}

        # post data
        downloadService.postJson "awac/admin/translations/linkedlists/save", data, (result) ->
            $scope.isLoading = false
            if result.success
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
                $scope.loadLinkedLists()
            return

        return

    $scope.sortLinkedListsItems = (linkedLists) ->
        for linkedList in linkedLists
            linkedList.items = _.sortBy linkedList.items, (item) ->
                return item.orderIndex
        return linkedLists

    $scope.addItem = (linkedList) ->
        if ! (!!$scope.itemToAdd.key && !!$scope.itemToAdd.labelEn && !!$scope.itemToAdd.activitySourceKey && !!$scope.itemToAdd.activityTypeKey)
            messageFlash.displayError "Echec de l'ajout: les champs 'clé', 'Libellé EN', 'ActivitySource' et 'ActivityType' sont obligatoires!"
            return
        if !! _.findWhere(linkedList.items, {'key': $scope.itemToAdd.key})
            messageFlash.displayError "Echec de l'ajout: la clé '" + $scope.itemToAdd.key + "' est déjà présente dans cette liste!"
            return
        if !! _.findWhere(linkedList.items, {'activitySourceKey': $scope.itemToAdd.activitySourceKey, 'activityTypeKey': $scope.itemToAdd.activityTypeKey})
            messageFlash.displayError "Echec de l'ajout: un élément avec la même combinaison ActivityType - ActivitySource est déjà présent dans cette liste!"
            return
        linkedList.items.push(angular.copy($scope.itemToAdd))
        $scope.itemToAdd.clear()
        return

    $scope.getActivitySourceLabel = (key) ->
        return key + " - " + codeLabelHelper.getLabelByKey($scope.activitySourcesLabels, key)

    $scope.getActivityTypeLabel = (key) ->
        return key + " - " + codeLabelHelper.getLabelByKey($scope.activityTypesLabels, key)

    $scope.ignoreChanges = false

    $scope.$root.$on '$locationChangeStart', (event, next, current) ->
        return unless !$scope.ignoreChanges
        eq = angular.equals($scope.initialLinkedLists, updateOrderIndexes($scope.linkedLists))
        if !eq
            event.preventDefault()

            # show confirm
            params =
                titleKey: "CONFIRM_EXIT_TITLE"
                messageKey: "CONFIRM_EXIT_MESSAGE"
                onConfirm: () ->
                    $scope.ignoreChanges = true
                    $location.path(next.split('#')[1])
            modalService.show modalService.CONFIRM_DIALOG, params

    $scope.loadLinkedLists()
