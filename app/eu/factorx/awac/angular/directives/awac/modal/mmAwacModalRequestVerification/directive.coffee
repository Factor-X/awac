angular
.module('app.directives')
.directive "mmAwacModalRequestVerification", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-request-verification.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.fields = {

            email:
                inputName: 'email'
                fieldTitle: "REQUEST_VERIFICATION_EMAIL_VERIFIER"
                placeholder: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_PLACEHOLDER"
                validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
                validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
                focus: ->
                    return true

            comment:
                fieldTitle: "MODAL_QUESTION_COMMENT_TITLE"
                fieldType: 'textarea'
                validationRegex: "^.{0,65000}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_TEXT"
                hideIsValidIcon: true

            myPhoneNumber:
                fieldTitle: "MY_PHONE_NUMBER"
                validationRegex: "^.{0,20}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_STRING"
                hideIsValidIcon: true

            myPassword:
                fieldType: "password"
                inputName: 'password'
                fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE"
                placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER"
                validationRegex: "^\\S{5,20}$"
                validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        }

        $scope.allFieldValid = () ->
            for key in Object.keys($scope.fields)
                if key != '$$hashKey'
                    if !$scope.fields[key].isValid? || $scope.fields[key].isValid == false
                        return false
            return true

        #send the request to the server
        $scope.save = () ->
            data = {}
            data.password = $scope.fields.myPassword.field
            data.periodKey = $scope.$root.periodSelectedKey
            data.scopeId = $scope.$root.scopeSelectedId
            data.email = $scope.fields.email.field
            data.comment = $scope.fields.comment.field
            data.phoneNumber = $scope.fields.myPhoneNumber.field

            console.log data

            $scope.isLoading = true

            downloadService.postJson '/awac/verification/createRequest', data, (result) ->
                if result.success

                    #display success message
                    messageFlash.displaySuccess translationService.get "REQUEST_SEND"
                    if not $scope.$root.verificationRequest
                        $scope.$root.verificationRequest = {}
                    $scope.$root.verificationRequest.status = 'VERIFICATION_STATUS_WAIT_VERIFIER_REGISTRATION'

                    #close window
                    $scope.close()
                else
                    #display success message
                    messageFlash.displayError result.data.message
                    $scope.isLoading = false

            return false

        $scope.close = ->
            modalService.close modalService.REQUEST_VERIFICATION

    link: (scope) ->

