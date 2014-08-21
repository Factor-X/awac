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

    $scope.allFieldValid = () ->
        if $scope.loginInfo.isValid && $scope.passwordInfo.isValid
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

    $scope.test = () ->
        $('#modalLogin').modal('show')
