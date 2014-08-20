angular
.module('app.controllers')
.controller "LoginCtrl", ($scope, downloadService, $http, $location, messageFlash) ->

    $scope.loginInfo =
        fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE"
        fieldType: "text"
        placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER"
        validationRegex: "^\\S{5,20}$"
        validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"
        field: ""
        isValid: false
        focus:true

    $scope.passwordInfo =
        fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE"
        fieldType: "password"
        validationRegex: "^\\S{5,20}$"
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        field: ""
        isValid: false

    $scope.allFieldValid = () ->
        if $scope.loginInfo.isValid && $scope.passwordInfo.isValid
            return true
        return false

    #send the request to the server
    $scope.send = () ->

        #active loading mode
        $scope.isLoading = true

        #send request
        promise = $http
            method: "POST"
            url: '/awac/login'
            headers:
                "Content-Type": "application/json"
            data:
                login: $scope.loginInfo.field
                password: $scope.passwordInfo.field

        promise.success (data, status, headers, config) ->
            $scope.$root.loginSuccess(data)
            messageFlash.displaySuccess "You are now connected"
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
