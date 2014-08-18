angular
.module('app.controllers')
.controller "UserDataCtrl", ($scope, downloadService, $http, translationService, messageFlash, modalService, $timeout) ->

    $scope.isLoading = false

    $scope.identifierInfo =
        fieldTitle: "USER_IDENTIFIER"
        disabled: true
        field: $scope.$root.currentPerson.identifier

    $scope.passwordInfo =
        fieldTitle: "USER_PASSWORD"
        fieldType: "password"
        disabled: true
        field: "***"

    $scope.lastNameInfo =
        fieldTitle: "USER_LASTNAME"
        validationRegex: "^\\S{1,255}$"
        validationMessage: "LASTNAME_VALIDATION_WRONG_LENGTH"
        field: $scope.$root.currentPerson.lastName
        hideIsValidIcon: true
        isValid: true
        focus: true

    $scope.firstNameInfo =
        fieldTitle: "USER_FIRSTNAME"
        fieldType: "text"
        validationRegex: "^\\S{1,255}$"
        validationMessage: "FIRSTNAME_VALIDATION_WRONG_LENGTH"
        field: $scope.$root.currentPerson.firstName
        hideIsValidIcon: true
        isValid: true

    $scope.emailInfo =
        fieldTitle: "USER_EMAIL"
        disabled: true
        field: $scope.$root.currentPerson.email

    $scope.allFieldValid = () ->
        if $scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid
            return true
        return false

    #refresh user data
    $scope.$root.refreshUserData();

    #send data
    $scope.send = () ->
        if !$scope.allFieldValid
            return false

        $scope.isLoading = true

        promise = $http
            method: "POST"
            url: 'user/profile/save'
            headers:
                "Content-Type": "application/json"
            data:
                identifier: $scope.identifierInfo.field
                lastName: $scope.lastNameInfo.field
                firstName: $scope.firstNameInfo.field
                email: $scope.emailInfo.field

        promise.success (data, status, headers, config) ->
            messageFlash.displaySuccess "CHANGES_SAVED"
            $scope.$root.currentPerson.lastName = $scope.lastNameInfo.field
            $scope.$root.currentPerson.firstName = $scope.firstNameInfo.field
            $scope.isLoading = false
            return

        promise.error (data, status, headers, config) ->
            messageFlash.displayError data.message
            $scope.isLoading = false
            return

        return false

    $scope.setNewEmail = (newEmail) ->
        $scope.emailInfo.field = newEmail
        $scope.$root.currentPerson.email = newEmail

    $scope.changeEmail = () ->
        modalService.show(modalService.EMAIL_CHANGE, { oldEmail: $scope.emailInfo.field, cb : $scope.setNewEmail })

    $scope.changePassword = () ->
        modalService.show(modalService.PASSWORD_CHANGE, {})
