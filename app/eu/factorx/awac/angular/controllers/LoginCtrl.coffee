angular
.module('app.controllers')
.controller "LoginCtrl", ($scope, downloadService, $location, messageFlash) ->
    $scope.loginInfo =
        fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE"
        fieldType: "text"
        placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER"
        validationRegex: "^\\S{5,20}$"
        validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"
        field: ""
        isValid: false
        focus: true

    $scope.passwordInfo =
        fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE"
        fieldType: "password"
        validationRegex: "^\\S{5,20}$"
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        field: ""
        isValid: false

    $scope.forgotPasswordInfo =
        fieldTitle: "IDENTIFIENT_OR_EMAIL"
        fieldType: "text"
        validationRegex: "^\\S+$"
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        field: ""
        isValid: false

    $scope.connectionFieldValid = () ->
        if $scope.loginInfo.isValid && $scope.passwordInfo.isValid
            return true
        return false

    $scope.forgotPasswordFieldValid = () ->
        if $scope.forgotPasswordInfo.isValid && $scope.forgotPasswordInfo.isValid
            return true
        return false

    #send the request to the server
    $scope.send = () ->

        #active loading mode
        $scope.isLoading = true

        #send request
        downloadService.postJson '/awac/login', { login: $scope.loginInfo.field, password: $scope.passwordInfo.field }, (result) ->
            if result.success
                $scope.$root.loginSuccess(result.data)
                messageFlash.displaySuccess "You are now connected"
            else
                #display the error message
                messageFlash.displayError result.data.message
                #disactive loading mode
                $scope.isLoading = false

        return false

    $scope.sendForgotPassword = () ->

        #active loading mode
        $scope.isLoading = true

        #send request
        promise = $http
            method: "POST"
            url: '/awac/forgotPassword'
            headers:
                "Content-Type": "application/json"
            data:
                identifier: $scope.forgotPasswordInfo.field

        promise.success (data, status, headers, config) ->
            messageFlash.displaySuccess translationService.get('LOGIN_FORGOT_PASSWORD_SUCCESS')
            return

        promise.error (data, status, headers, config) ->
            #display the error message
            messageFlash.displayError data.message
            #disactive loading mode
            $scope.isLoading = false
            return

        return false

    $scope.test = () ->
        $('#modalLogin').modal('show')
