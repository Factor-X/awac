angular
.module('app.controllers')
.controller "loginCtrl", ($scope, downloadService, translationService, $sce, $modal, $http) ->
  $scope.login = "user1"
  $scope.password = "password"
  $scope.send = () ->
    promise = $http
      method: "POST"
      url: 'login'
      headers:
        "Content-Type": "application/json"
      data:
        login: $scope.login
        password: $scope.password

    promise.success (data, status, headers, config) ->
      console.info(data)
      $scope.message = data.person.firstName + " " + data.person.lastName
      return

    promise.error (data, status, headers, config) ->
      $scope.message = "error : " + data.message
      return

    return false