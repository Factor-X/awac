angular
.module('app.directives')
.directive "mmAwacModalConnectionPasswordChange", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-connection-password-change.html"

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

        $scope.newPasswordInfo =
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_PLACEHOLDER"
            validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            hideIsValidIcon: true
            field: ""
            focus: ->
                return true

        $scope.newPasswordConfirmInfo =
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_PLACEHOLDER"
            validationFct: $scope.validatePasswordConfirmField
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            hideIsValidIcon: true
            field: ""

        $scope.allFieldValid = () ->
            if $scope.newPasswordInfo.isValid && $scope.newPasswordConfirmInfo.isValid
                 return true
            return false

        #send the request to the server
        $scope.save = () ->

            if !$scope.allFieldValid()
                return false

            $scope.isLoading = true

            data =
                login: $scope.getParams().login
                password: $scope.getParams().password
                interfaceName: $scope.$root.instanceName
                newPassword: $scope.newPasswordInfo.field

            downloadService.postJson '/awac/login', data, (result) ->
                if result.success
                    $scope.$root.loginSuccess(result.data)
                    messageFlash.displaySuccess "You are now connected"
                    $scope.isLoading = false
                    $scope.close()
                else
                    #display the error message
                    messageFlash.displayError result.data.message
                    #disactive loading mode
                    $scope.isLoading = false

            return false

        $scope.close = ->
            modalService.close modalService.CONNECTION_PASSWORD_CHANGE

    link: (scope) ->

