define ["./module"], (controllers) ->
  "use strict"
  controllers.controller "ResultsCtrl", ($scope, downloadService, $http,displayFormMenu) ->

    $scope.displayFormMenu=displayFormMenu

    downloadService.getJson "result/getReport/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, (data) ->
        $scope.o = data

    $scope.temp = {}
    $scope.temp.browsers =
        _type: 'terms'
        missing: 0
        total: 454
        other: 0
        terms: [
            {
                term: 'Prod-A'
                count: 306
            } ,
            {
                term: 'Prod-B'
                count: 148
            } ,
            {
                term: 'Prod-C'
                count: 25
            }
        ]
