angular
.module('app.directives')
.directive "mmAwacModalPasswordChange", (directiveService, $http, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='

    templateUrl: "$/angular/templates/mm-awac-modal-password-change.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.validatePasswordConfirmField = () ->
            if $scope.newPasswordConfirmInfo.field.match("^\\S{5,20}$")
                if ($scope.newPasswordInfo.field == $scope.newPasswordConfirmInfo.field)
                    return true
                $scope.newPasswordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_CONFIRMATION"
            else
                $scope.newPasswordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_LENGTH"
            return false

        $scope.oldPasswordInfo =
            fieldTitle: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_TITLE"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_PLACEHOLDER"
            validationRegex: "^\\S{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            field: ""
            hideIsValidIcon: true
            focus: true

        $scope.newPasswordInfo =
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_PLACEHOLDER"
            validationRegex: "^\\S{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            hideIsValidIcon: true
            field: ""

        $scope.newPasswordConfirmInfo =
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_PLACEHOLDER"
            validationFct: $scope.validatePasswordConfirmField
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            hideIsValidIcon: true
            field: ""

        $scope.allFieldValid = () ->
            if $scope.oldPasswordInfo.isValid && $scope.newPasswordInfo.isValid && $scope.newPasswordConfirmInfo.isValid
                 return true
            return false

        #send the request to the server
        $scope.save = () ->

            if !$scope.allFieldValid()
                return false

            $scope.isLoading = true

            promise = $http
                method: "POST"
                url: '/awac/user/password/save'
                headers:
                    "Content-Type": "application/json"
                data:
                    oldPassword: $scope.oldPasswordInfo.field
                    newPassword: $scope.newPasswordInfo.field

            promise.success (data, status, headers, config) ->
                messageFlash.displaySuccess "CHANGES_SAVED"
                $scope.close()
                return

            promise.error (data, status, headers, config) ->
                messageFlash.displayError data.message
                $scope.isLoading = false
                return

            return false

        $scope.close = ->
            modalService.close modalService.PASSWORD_CHANGE

    link: (scope) ->

