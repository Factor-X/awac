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

    #active loading mode
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

    $scope.send = () ->
        downloadService.postJson 'user/profile/save', $scope.user, (data, status, headers, config) ->
            $scope.isLoading = false
            if data?
                messageFlash.displaySuccess "Your changes have been saved !"
            else
                messageFlash.displayError data.message
            return

    $scope.saveNewEmail = (data, cb) ->
        console.log 'saveNewEmail - password: ' + data.password
        console.log 'saveNewEmail - oldEmail: ' + data.oldEmail
        console.log 'saveNewEmail - newEmail:' + data.newEmail

        downloadService.postJson 'user/email/save', {password: data.password, oldEmail: data.oldEmail, newEmail: data.newEmail}, (data, status, headers, config) ->
            $scope.isLoading = false
            if data?
                messageFlash.displaySuccess "Your changes have been saved !"
                cb(true)
            else
                messageFlash.displayError data.message
                cb(false)
            return

        return


    $scope.changeEmail = () ->
        modalService.show(modalService.EMAIL_CHANGE, { saveNewEmail: $scope.saveNewEmail, oldEmail: $scope.user.email })




