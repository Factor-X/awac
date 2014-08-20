#
# TODO deprecated => to adapt to the new modal service
#
angular
.module('app.directives')
.directive "mmAwacModalLogin", (directiveService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-modal-login.html"
    controller: ($scope, downloadService, translationService, $sce, $modal, $http) ->

        #change option of the modal
        $('#modalLogin').modal({
            backdrop: 'static'
        })
        $('#modalLogin').modal('hide')

        #initialize the modal when it's displayed
        $('#modalLogin').on 'shown.bs.modal', (e) ->
            $scope.initialize()
            #refresh angular
            $scope.$apply()


        #initilize variables for the modal
        $scope.initialize = () ->
            #console.log("initialization !!")
            $scope.loginInfo =
                fieldTitle: "Your login"
                fieldType: "text"
                placeholder: "your login"
                validationMessage: "between 5 and 20 letters"
                field: ""
                isValid: false

            $scope.passwordInfo =
                fieldTitle: "Your password"
                fieldType: "password"
                validationMessage: "between 5 and 20 letters"
                field: ""
                isValid: false

            $scope.isLoading = false
            $scope.errorMessage = ""

        #intialize the modal
        $scope.initialize()

        #control is all field are valid
        $scope.allFieldValid = () ->
            if $scope.loginInfo.isValid && $scope.passwordInfo.isValid
                return true
            return false

        #send the request to the server
        $scope.send = () ->
            if $scope.allFieldValid()

                #remove the error message
                $scope.errorMessage = ""

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
                    $scope.$parent.setCurrentUser(data)
                    #close the modal
                    $('#modalLogin').modal('hide')
                    $scope.$apply()
                    return

                promise.error (data, status, headers, config) ->
                    #display the error message
                    $scope.errorMessage = "Error : " + data.message
                    #disactive loading mode
                    $scope.isLoading = false
                    return

            return false
    link: (scope) ->

