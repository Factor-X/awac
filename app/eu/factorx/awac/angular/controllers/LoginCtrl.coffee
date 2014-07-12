angular
.module('app.controllers')
.controller "LoginCtrl", ($scope, downloadService, $http, $location) ->


    $scope.login = 'user1'
    $scope.password = 'password'

    #send the request to the server
    $scope.send = () ->
        #remove the error message
        $scope.errorMessage = ""

        #active loading mode
        $scope.isLoading = true

        #send request
        promise = $http
            method: "POST"
            url: 'login'
            headers:
                "Content-Type": "application/json"
            data:
                login: $scope.login
                password: $scope.password

        promise.success (data, status, headers, config) ->
            $scope.$parent.setPeriods(data.availablePeriods)
            $scope.$parent.setCurrentUser(data.user)
            $scope.$parent.setCurrentOrganization(data.organization)
            $location.path('/form1/' + data.defaultPeriod + '/' + data.organization.sites[0].scope)
            return

        promise.error (data, status, headers, config) ->
            #display the error message
            $scope.errorMessage = "Error : " + data.message
            #disactive loading mode
            $scope.isLoading = false
            return

        return false