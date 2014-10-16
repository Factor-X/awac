angular
.module('app.directives')
.directive "mmAwacModalVerificationConfirmation", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-verification-confirmation.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.save = (valid)->
            $scope.isLoading = true

            dto =
                scopeId: $scope.$root.scopeSelectedId
                periodKey: $scope.$root.periodSelectedKey

            if valid == true
                dto.newStatus =  'VERIFICATION_STATUS_WAIT_ASSIGNATION'
            else
                dto.newStatus = 'VERIFICATION_STATUS_REJECTED'

            downloadService.postJson "/awac/verification/setStatus", dto, (result) ->
                $scope.isLoading = false
                if not result.success
                    messageFlash.displayError(result.data.message)
                else
                    if valid == true
                        $scope.$root.verificationRequest.status ='VERIFICATION_STATUS_WAIT_ASSIGNATION'
                    else
                        $scope.$root.verificationRequest =null
                    $scope.close()

        $scope.close = ->
            modalService.close modalService.VERIFICATION_CONFIRMATION


