angular
.module('app.controllers')
.controller "createProductCtrl", ($scope, downloadService, translationService, $sce, $modal, $http) ->
  $scope.productName = ""
  $scope.send = () ->
    promise = $http
      method: "POST"
      url: 'product/create'
      headers:
        "Content-Type": "application/json"
      data:
        name: $scope.productName

    promise.success (data, status, headers, config) ->
      $scope.message = "Your product "+data.name+" was created"
      return

    promise.error (data, status, headers, config) ->
      $scope.message = "error : " + data.message
      return

    return false