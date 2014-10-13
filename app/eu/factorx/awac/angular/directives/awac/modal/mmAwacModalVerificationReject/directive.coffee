#
# TODO deprecated => to adapt to the new modal service
#
angular
.module('app.directives')
.directive "mmAwacModalVerificationReject", (directiveService,downloadService,messageFlash) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-verification-reject.html"
    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.close = ->
            modalService.close modalService.VERIFICATION_REJECT

        $scope.comment = $scope.ngParams.comment

        $scope.save = ->
            data =
                questionSetKey: $scope.getParams().questionSetCode
                periodKey: $scope.$root.periodSelectedKey
                scopeId: $scope.$root.scopeSelectedId
                verification:
                    status: 'rejected'
                    comment:$scope.comment
            console.log '----------------------------------------'
            console.log data

            downloadService.postJson '/awac/verification/verify', data, (result) ->
                if result.success
                    $scope.getParams().refreshVerificationStatus(result.data)
                    $scope.close()
                else
                    messageFlash.displayError(result.data.message)

    link: (scope) ->

