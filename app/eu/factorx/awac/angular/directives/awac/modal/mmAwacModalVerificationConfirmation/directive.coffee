angular
.module('app.directives')
.directive "mmAwacModalVerificationConfirmation", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-verification-confirmation.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.fields = {

            myPassword:
                fieldType: "password"
                inputName: 'password'
                fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE"
                placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER"
                validationRegex: "^\\S{5,20}$"
                validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
                hideIsValidIcon: true
                focus: ->
                    return true

        }


        $scope.allFieldValid = () ->
            for key in Object.keys($scope.fields)
                if key != '$$hashKey'
                    if !$scope.fields[key].isValid? || $scope.fields[key].isValid == false
                        return false
            return true


        $scope.save = (valid)->
            if $scope.allFieldValid()
                $scope.isLoading = true

                dto =
                    scopeId: $scope.$root.scopeSelectedId
                    periodKey: $scope.$root.periodSelectedKey
                    password:$scope.fields.myPassword.field

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


