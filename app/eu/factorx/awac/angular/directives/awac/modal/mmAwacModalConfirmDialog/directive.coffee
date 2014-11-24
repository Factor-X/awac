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
                if !!$scope.getParams().confirmParams && $scope.getParams().confirmParams.length > 0
                    if $scope.getParams().confirmParams.length == 1
                        $scope.getParams().onConfirm($scope.getParams().confirmParams[0])
                    if $scope.getParams().confirmParams.length == 2
                        $scope.getParams().onConfirm($scope.getParams().confirmParams[0],$scope.getParams().confirmParams[1])
                else
                    $scope.getParams().onConfirm()
            $scope.close()

    link: (scope) ->
