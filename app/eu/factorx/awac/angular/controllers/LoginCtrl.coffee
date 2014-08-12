angular
.module('app.controllers')
.controller "LoginCtrl", ($scope, downloadService, $http, $location, messageFlash) ->
    console.log("je suis le logincontroller")

    $scope.loginInfo =
        fieldTitle: "Your login"
        fieldType: "text"
        placeholder: "your login"
        validationRegex: "^\\S{5,20}$"
        validationMessage: "between 5 and 20 letters and without withe spaces"
        field: ""
        isValid: false

    $scope.passwordInfo =
        fieldTitle: "Your password"
        fieldType: "password"
        validationRegex: "^\\S{5,20}$"
        validationMessage: "between 5 and 20 letters and without withe spaces"
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
            url: 'login'
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
