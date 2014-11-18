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
            if not  $scope.getParams().readOnly or $scope.getParams().readOnly == false
                data =
                    questionSetKey: $scope.getParams().questionSetCode
                    periodKey: $scope.$root.periodSelectedKey
                    scopeId: $scope.$root.scopeSelectedId
                    verification:
                        status: 'rejected'
                        comment:$scope.comment

                downloadService.postJson '/awac/verification/verify', data, (result) ->
                    if result.success
                        $scope.getParams().refreshVerificationStatus(result.data)
                        $scope.$root.$broadcast 'TEST_CLOSING_VALIDATION'
                        $scope.close()

    link: (scope) ->

