angular
.module('app.directives')
.directive "mmAwacModalEmailChange", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='

    templateUrl: "$/angular/templates/mm-awac-modal-email-change.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.passwordInfo =
            fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE"
            fieldType: "password"
            placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER"
            validationRegex: "^\\S{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            field: ""
            isValid: false
            focus: true

        $scope.oldEmailInfo =
            fieldTitle: "EMAIL_CHANGE_FORM_OLD_EMAIL_FIELD_TITLE"
            fieldType: "text"
            field: $scope.getParams().oldEmail
            isValid: true
            disabled: true

        $scope.newEmailInfo =
            fieldTitle: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_TITLE"
            fieldType: "text"
            placeholder: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_PLACEHOLDER"
            validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
            field: ""
            isValid: false

        $scope.allFieldValid = () ->
            if $scope.passwordInfo.isValid && $scope.newEmailInfo.isValid
                return true
            return false

        #send the request to the server
        $scope.save = () ->
            if $scope.allFieldValid()
                $scope.isLoading = true
                downloadService.postJson 'user/email/save', {password: $scope.passwordInfo.field, oldEmail: $scope.oldEmailInfo.field, newEmail: $scope.newEmailInfo.field}, (data, status, headers, config) ->
                    $scope.isLoading = false
                    if data?
                        messageFlash.displaySuccess translationService.get "ANSWERS_SAVED"
                        $scope.close()
                        if $scope.getParams().cb?
                            $scope.getParams().cb($scope.newEmailInfo.field)
                    else
                        messageFlash.displayError data.message
                    return
                return

        $scope.close = ->
            modalService.close modalService.EMAIL_CHANGE

    link: (scope) ->

