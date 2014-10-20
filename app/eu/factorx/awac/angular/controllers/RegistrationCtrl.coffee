angular
.module('app.controllers')
.controller "RegistrationCtrl", ($scope, downloadService, messageFlash, $compile, $timeout, modalService, translationService, $routeParams) ->


    #logout the lasted user
    downloadService.postJson '/awac/logout', null, (result) ->

    $scope.loading = false

    $scope.tabActive = []

    $scope.enterEvent = ->
        if $scope.tabActive[0] == true
            $scope.send()

    $scope.fields = {
        loginInfo:
            fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE"
            fieldType: "text"
            placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER"
            validationRegex: "^\\S{5,20}$"
            validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"
            field: ""
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
            isValid: true

        firstNameInfo:
            fieldTitle: "USER_FIRSTNAME"
            fieldType: "text"
            validationRegex: "^.{1,255}$"
            validationMessage: "USER_FIRSTNAME_WRONG_LENGTH"
            isValid: true

        emailInfo:
            fieldTitle: "USER_EMAIL"
            validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
    }

    $scope.connectionFieldValid = () ->
        for key in Object.keys($scope.fields)
            if key != '$$hashKey'
                field = $scope.fields[key]
                if not field.isValid || field.isValid == false
                    return false
        return true

    #send the request to the server
    $scope.send = () ->

        #active loading mode
        $scope.isLoading = true

        personDTO =
            identifier: $scope.fields.loginInfo.field
            lastName: $scope.fields.lastNameInfo.field
            firstName: $scope.fields.firstNameInfo.field
            email: $scope.fields.emailInfo.field

        data =
            person: personDTO
            password: $scope.fields.passwordInfo.field
            key: $routeParams.key

        #send request
        downloadService.postJson '/awac/register', data, (result) ->
            if result.success
                messageFlash.displaySuccess translationService.get 'REGISTRATION_SUCCESS'
                $scope.isLoading = false
                console.log 'registration'
                $scope.$root.nav('/login')
            else
                messageFlash.displaySuccess translationService.get 'REGISTRATION_FAILED'
                $scope.isLoading = false
        #disactive loading mode

        return false

    $scope.injectRegistrationDirective = ->
        if $scope.$root?
            if $scope.$root.instanceName == 'enterprise'
                directiveName = "mm-awac-registration-enterprise"
            else if $scope.$root.instanceName == 'municipality'
                directiveName = "mm-awac-registration-municipality"

            directive = $compile("<" + directiveName + "></" + directiveName + ">")($scope)
            $('.inject-registration-form').append(directive)

    $timeout(->
        $scope.injectRegistrationDirective()
    , 0)
