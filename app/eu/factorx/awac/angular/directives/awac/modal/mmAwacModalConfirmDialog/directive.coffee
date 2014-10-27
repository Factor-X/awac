angular
.module('app.directives')
.directive "mmAwacModalConfirmDialog", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-confirm-dialog.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.titleKey = $scope.getParams().titleKey
        $scope.messageKey = $scope.getParams().messageKey

        $scope.close = ->
            modalService.close modalService.CONFIRM_DIALOG

        $scope.confirm = () ->
            if !!$scope.getParams().onConfirm
                $scope.getParams().onConfirm()
            $scope.close()

    link: (scope) ->
