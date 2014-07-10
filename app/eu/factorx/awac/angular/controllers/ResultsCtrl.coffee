angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, downloadService, $http) ->
    $scope.periodId = 24
    $scope.scopeId = 27

    #downloadService.getJson "result/" + $scope.periodId + "/" + $scope.scopeId, (data) ->
    #    $scope.o = data


    $scope.o = {}
    $scope.o.browsers =
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
