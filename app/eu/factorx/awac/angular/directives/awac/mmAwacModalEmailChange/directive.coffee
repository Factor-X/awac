angular
.module('app.directives')
.directive "mmAwacModalEmailChange", (directiveService, $http, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='

    templateUrl: "$/angular/templates/mm-awac-modal-email-change.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.passwordInfo =
            field: ""
            fieldType: "password"
            fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE"
            placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER"
            validationRegex: "^\\S{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            hideIsValidIcon: true
            focus: true

        $scope.oldEmailInfo =
            field: $scope.getParams().oldEmail
            fieldTitle: "EMAIL_CHANGE_FORM_OLD_EMAIL_FIELD_TITLE"
            disabled: true

        $scope.newEmailInfo =
            field: ""
            fieldTitle: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_TITLE"
            placeholder: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_PLACEHOLDER"
            validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
            hideIsValidIcon: true

        $scope.allFieldValid = () ->
            if $scope.passwordInfo.isValid && $scope.newEmailInfo.isValid
                return true
            return false

        #send the request to the server
        $scope.save = () ->

            if !$scope.allFieldValid()
                return false

            $scope.isLoading = true

            promise = $http
                method: "POST"
                url: '/awac/user/email/save'
                headers:
                    "Content-Type": "application/json"
                data:
                    password: $scope.passwordInfo.field
                    newEmail: $scope.newEmailInfo.field

            promise.success (data, status, headers, config) ->
                messageFlash.displaySuccess "CHANGES_SAVED"
                $scope.close()
                if $scope.getParams().cb?
                    $scope.getParams().cb($scope.newEmailInfo.field)
                return

            promise.error (data, status, headers, config) ->
                messageFlash.displayError data.message
                $scope.isLoading = false
                return

            return false

        $scope.close = ->
            modalService.close modalService.EMAIL_CHANGE

    link: (scope) ->

