angular
.module('app.directives')
.directive "mmAwacModalPasswordChange", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='

    templateUrl: "$/angular/templates/mm-awac-modal-password-change.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.oldPasswordInfo =
            fieldTitle: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_TITLE"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_PLACEHOLDER"
            validationRegex: "^\\S{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            field: ""
            isValid: false
            focus: true

        $scope.newPasswordInfo =
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_PLACEHOLDER"
            validationRegex: "^\\S{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            field: ""
            isValid: false

        $scope.newPasswordInfoRepeat =
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_REPEAT_FIELD_TITLE"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_REPEAT_FIELD_PLACEHOLDER"
            validationRegex: "^\\S{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            field: ""
            isValid: false

        $scope.allFieldValid = () ->
            if $scope.newPasswordInfo.isValid && $scope.newPasswordInfoRepeat.isValid && ($scope.newPasswordInfo.field == $scope.newPasswordInfoRepeat.field)
                return true
            return false

        #send the request to the server
        $scope.save = () ->
            if $scope.allFieldValid()
                $scope.isLoading = true
                downloadService.postJson 'user/password/save', {oldPassword: $scope.oldPasswordInfo.field, newPassword: $scope.newPasswordInfo.field}, (data, status, headers, config) ->
                    $scope.isLoading = false
                    if data?
                        messageFlash.displaySuccess translationService.get "ANSWERS_SAVED"
                        $scope.close()
                    else
                        messageFlash.displayError data.message
                    return
                return

        $scope.close = ->
            modalService.close modalService.PASSWORD_CHANGE

    link: (scope) ->

