angular
.module('app.controllers')
.controller "UserDataCtrl", ($scope, downloadService, translationService, messageFlash, modalService) ->

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
                $scope.isLoading = false


        return false

    $scope.setNewEmail = (newEmail) ->
        $scope.emailInfo.field = newEmail
        $scope.$root.currentPerson.email = newEmail

    $scope.changeEmail = () ->
        modalService.show(modalService.EMAIL_CHANGE, { oldEmail: $scope.emailInfo.field, cb : $scope.setNewEmail })

    $scope.changePassword = () ->
        modalService.show(modalService.PASSWORD_CHANGE, {})

    $scope.toForm = ->
        $scope.$root.navToLastFormUsed()

    ###################################### ANONYMOUS BEHAVIOUR ########################################

    # for anonymous
    $scope.anonymous = {
        loginInfo:
            fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE"
            fieldType: "text"
            placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER"
            validationRegex: "^\\S{5,20}$"
            validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"
            field: $scope.$root.currentPerson.identifier
            isValid: false
            focus: ->
                return true

        passwordInfo:
            fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE"
            fieldType: "password"
            validationRegex: "^\\S{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            field: ""
            isValid: false

        lastNameInfo:
            fieldTitle: "USER_LASTNAME"
            validationRegex: "^.{1,255}$"
            validationMessage: "USER_LASTNAME_WRONG_LENGTH"
            field: $scope.$root.currentPerson.lastName
            isValid: true

        firstNameInfo:
            fieldTitle: "USER_FIRSTNAME"
            fieldType: "text"
            validationRegex: "^.{1,255}$"
            validationMessage: "USER_FIRSTNAME_WRONG_LENGTH"
            field: $scope.$root.currentPerson.firstName
            isValid: true

        emailInfo:
            fieldTitle: "USER_EMAIL"
            validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
            field: $scope.$root.currentPerson.email
    }

    #Validate anonymous fields
    $scope.anonymousFieldValid = () ->
        for key in Object.keys($scope.anonymous)
            if key != '$$hashKey'
                field = $scope.anonymous[key]
                if not field.isValid || field.isValid == false
                    return false
        return true

    #send anonymous user data
    $scope.sendAnonymous = () ->
        if !$scope.anonymousFieldValid()
            return false

        $scope.isLoading = true

        data =
            identifier: $scope.anonymous.loginInfo.field
            password: $scope.anonymous.passwordInfo.field
            lastName: $scope.anonymous.lastNameInfo.field
            firstName: $scope.anonymous.firstNameInfo.field
            email: $scope.anonymous.emailInfo.field

        downloadService.postJson '/awac/user/profile/anonymous/save', data, (result) ->
            if result.success
                messageFlash.displaySuccess "CHANGES_SAVED"
                messageFlash.displaySuccess "Please login with your new credentials"
                #logout
                $scope.$root.nav('/login')
                $scope.$root.currentPerson = null
                $scope.$root.periodSelectedKey=null
                $scope.$root.scopeSelectedId=null
                $scope.isLoading = false
            else
                $scope.isLoading = false

        return false

    #verify whether anonymous account
    $scope.isAnonymousUser = () ->
        console.log angular.copy $scope.$root.currentPerson
        console.log angular.copy $scope.$root.currentPerson.identifier
        if $scope.$root.currentPerson?.identifier.indexOf("anonymous_user") != -1
            console.log("currentUser is an anonymous user")
            true
        else
            console.log("currentUser is not anonymous user")
            false