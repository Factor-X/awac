angular
.module('app.controllers')
.controller "UserDataCtrl", ($scope, downloadService, translationService, messageFlash, modalService, $timeout) ->

    $scope.isLoading = false

    $scope.identifierInfo =
        fieldTitle: "USER_IDENTIFIER"
        disabled: true
        field: $scope.$root.currentPerson.identifier

    $scope.passwordInfo =
        fieldTitle: "USER_PASSWORD"
        fieldType: "password"
        disabled: true
        field: "*****"

    $scope.lastNameInfo =
        fieldTitle: "USER_LASTNAME"
        validationRegex: "^.{1,255}$"
        validationMessage: "USER_LASTNAME_WRONG_LENGTH"
        field: $scope.$root.currentPerson.lastName
        hideIsValidIcon: true
        isValid: true
        focus: ->
            return true

    $scope.firstNameInfo =
        fieldTitle: "USER_FIRSTNAME"
        fieldType: "text"
        validationRegex: "^.{1,255}$"
        validationMessage: "USER_FIRSTNAME_WRONG_LENGTH"
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

        data =
            identifier: $scope.identifierInfo.field
            lastName: $scope.lastNameInfo.field
            firstName: $scope.firstNameInfo.field
            email: $scope.emailInfo.field

        downloadService.postJson '/awac/user/profile/save', data, (result) ->
            if result.success
                messageFlash.displaySuccess "CHANGES_SAVED"
                $scope.$root.currentPerson.lastName = $scope.lastNameInfo.field
                $scope.$root.currentPerson.firstName = $scope.firstNameInfo.field
                $scope.isLoading = false
            else
                messageFlash.displayError result.data.message
                $scope.isLoading = false


        return false

    $scope.setNewEmail = (newEmail) ->
        $scope.emailInfo.field = newEmail
        $scope.$root.currentPerson.email = newEmail

    $scope.changeEmail = () ->
        modalService.show(modalService.EMAIL_CHANGE, { oldEmail: $scope.emailInfo.field, cb : $scope.setNewEmail })

    $scope.changePassword = () ->
        modalService.show(modalService.PASSWORD_CHANGE, {})
