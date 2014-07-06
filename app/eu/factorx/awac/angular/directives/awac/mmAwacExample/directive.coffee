angular
.module('app.controllers')
.controller "loginCtrl", ($scope, downloadService, translationService, $sce, $modal, $http) ->
  $scope.login = "Your login LL"
  $scope.password = "Your password"
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
      $scope.message = data.firstName + " " + data.lastName
      return

    promise.error (data, status, headers, config) ->
      $scope.message = "error : " + data.message
      return

    return false