angular
.module('app.controllers')
.controller "UserDataCtrl", ($scope, downloadService, translationService, messageFlash, modalService, $timeout) ->

    $scope.user =
        identifier: ""
        password: ""
        lastName: ""
        firstName: ""
        email: ""

    $scope.translate = (input) ->
        return translationService.get input

    #activate loading mode
    $scope.isLoading = true

    #get data
    downloadService.getJson 'user/profile', (data, status, headers, config) ->
        $scope.isLoading = false
        if data?
            $scope.user.identifier = data.identifier
            $scope.user.lastName = data.lastName
            $scope.user.firstName = data.firstName
            $scope.user.email = data.email
        else
            messageFlash.displayError data.message
        return

    #send data
    $scope.send = () ->
        downloadService.postJson 'user/profile/save', $scope.user, (data, status, headers, config) ->
            $scope.isLoading = false
            if data?
                messageFlash.displaySuccess translationService.get('USER_SAVE_SUCCESS')
            else
                messageFlash.displayError data.message
            return

    $scope.setNewEmail = (newEmail) ->
        $scope.user.email = newEmail

    $scope.changeEmail = () ->
        modalService.show(modalService.EMAIL_CHANGE, { oldEmail: $scope.user.email, cb : $scope.setNewEmail })

    $scope.changePassword = () ->
        modalService.show(modalService.PASSWORD_CHANGE, {})




