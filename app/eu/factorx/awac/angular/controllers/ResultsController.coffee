angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, downloadService, $http) ->
    $scope.periodId = 24
    $scope.scopeId = 27

    downloadService.getJson "result/" + $scope.periodId + "/" + $scope.scopeId, (data) ->
        $scope.o = data

