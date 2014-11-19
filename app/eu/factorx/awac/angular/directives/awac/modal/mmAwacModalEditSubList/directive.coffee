angular
.module('app.directives')
.directive "mmAwacModalEditSubList", (directiveService, downloadService, translationService, messageFlash, $upload, $window, codeLabelHelper) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='

    templateUrl: "$/angular/templates/mm-awac-modal-edit-sub-list.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.codeLabels = $scope.getParams().codeLabels
        $scope.subList = angular.copy($scope.getParams().subList)
        $scope.updated = false

        $scope.sortableOptions =
            axis: 'y'
            stop: (e, ui) ->
                for index of $scope.subList.items
                    $scope.subList.items[index].orderIndex = +index;

        $scope.save = ->
            $scope.isLoading = true
            downloadService.postJson '/awac/admin/translations/sublists/save', $scope.subList, (result) ->
                $scope.isLoading = false
                if result.success
                    messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
                    angular.extend($scope.getParams().subList, result.data)
                    $scope.updated = false
                    $scope.close()
                return
            return

        $scope.close = ->
            modalService.close modalService.EDIT_SUB_LIST
            return

        $scope.onModelChange = (newItems, oldItems) ->
            # check for duplicates
            duplicatedKey = $scope.findDuplicatedKey newItems
            if duplicatedKey != null
                # if there is a duplicate, display error & undo current change
                messageFlash.displayError "L'élément '" + duplicatedKey + "' est déjà présent dans la liste !"
                angular.extend(newItems, oldItems)
                return false

            # check if list items were actually updated
            $scope.updated = !angular.equals(newItems, $scope.getParams().subList.items)
            return

        $scope.findDuplicatedKey = (subListItems) ->
            for index, item of subListItems
                if _.where(subListItems, { key: item.key }).length > 1
                    return item.key
            return null

        $scope.$watch 'subList.items', $scope.onModelChange, true

        link: (scope) ->
