angular
.module('app.directives')
.directive "mmAwacModalPasswordChange", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='

    templateUrl: "$/angular/templates/mm-awac-modal-password-change.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.validatePasswordConfirmField = () ->
            if ($scope.newPasswordInfo.isValid? == true > 0 && $scope.newPasswordInfo.field == $scope.newPasswordConfirmInfo.field)
                return true
            return false

        $scope.oldPasswordInfo =
            inputName:'password'
            fieldTitle: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_TITLE"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_PLACEHOLDER"
            validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            field: ""
            focus: ->
                return true

        $scope.newPasswordInfo =
            inputName:'password'
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_PLACEHOLDER"
            validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            field: ""

        $scope.newPasswordConfirmInfo =
            inputName:'password'
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_PLACEHOLDER"
            validationFct: $scope.validatePasswordConfirmField
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
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

            data =
                oldPassword: $scope.oldPasswordInfo.field
                newPassword: $scope.newPasswordInfo.field

            downloadService.postJson '/awac/user/password/save', data, (result) ->
                if result.success
                    messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
                    $scope.close()
                else
                    $scope.isLoading = false

            return false

        $scope.close = ->
            modalService.close modalService.PASSWORD_CHANGE

    link: (scope) ->

