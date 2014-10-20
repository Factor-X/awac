angular
.module('app.controllers')
.controller "VerificationRegistrationCtrl", ($scope,downloadService,modalService,messageFlash,$routeParams,translationService) ->

    $scope.isLoading = false

    $scope.fields =

        identifierInfo:
            fieldTitle: "USER_IDENTIFIER"
            inputName: 'identifier'
            validationRegex: "[a-zA-Z0-9-]{5,20}"
            validationMessage: "IDENTIFIER_CHECK_WRONG"

        lastNameInfo:
            fieldTitle: "USER_LASTNAME"
            inputName: 'lastName'
            validationRegex: "^.{1,255}$"
            validationMessage: "USER_LASTNAME_WRONG_LENGTH"

        firstNameInfo:
            fieldTitle: "USER_FIRSTNAME"
            inputName: 'firstName'
            fieldType: "text"
            validationRegex: "^.{1,255}$"
            validationMessage: "USER_FIRSTNAME_WRONG_LENGTH"
            focus: ->
                true

        emailInfo:
            fieldTitle: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_TITLE"
            inputName: 'email'
            validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"

        passwordInfo:
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE"
            inputName: 'password'
            fieldType: "password"
            validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"

        passwordConfirmInfo:
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE"
            inputName: 'password'
            fieldType: "password"
            validationFct: $scope.validatePasswordConfirmField
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"

        organizationNameInfo:
            fieldTitle: "ORGANIZATION_NAME"
            fieldType: "text"
            validationRegex: "^.{1,255}$"
            validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"


    $scope.allFieldValid = () ->
        for key in Object.keys($scope.fields)
            if key != '$$hashKey'
                if !$scope.fields[key].isValid? || $scope.fields[key].isValid == false
                    return false
        return true

    #send the request to the server
    $scope.registration = () ->
        if $scope.allFieldValid()

            #active loading mode
            $scope.isLoading = true

            data = {}
            data.person = {}
            data.person.email = $scope.fields.emailInfo.field
            data.person.identifier = $scope.fields.identifierInfo.field
            data.person.firstName = $scope.fields.firstNameInfo.field
            data.person.lastName = $scope.fields.lastNameInfo.field
            data.password = $scope.fields.passwordInfo.field
            data.organizationName = $scope.fields.organizationNameInfo.field
            data.person.defaultLanguage = $scope.$root.language
            data.key = $routeParams.key


            #send request
            downloadService.postJson '/awac/registration/validator', data, (result) ->
                if result.success
                    $scope.$root.loginSuccess(result.data)
                    messageFlash.displaySuccess translationService.get "CONNECTION_MESSAGE_SUCCESS"
                    $scope.isLoading = false
                else
                    #disactive loading mode
                    $scope.isLoading = false

            return false