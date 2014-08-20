angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, downloadService, $http, displayFormMenu) ->
    $scope.displayFormMenu = displayFormMenu

    $scope.graphs = {}

    downloadService.getJson "/awac/result/getReport/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, (data) ->
        $scope.o = data


        # All scopes
        totalOutOfScope = 0;
        totalScope1 = 0;
        totalScope2 = 0;
        totalScope3 = 0;
        for rl in $scope.o.reportLines
            totalOutOfScope += rl.outOfScopeValue
            totalScope1 += rl.scope1Value
            totalScope2 += rl.scope2Value
            totalScope3 += rl.scope3Value

        data = []
        data.push
            label: 'Scope 1',
            value: totalScope1
        data.push
            label: 'Scope 2',
            value: totalScope2
        data.push
            label: 'Scope 3',
            value: totalScope3
        data.push
            label: 'Out of scope',
            value: totalOutOfScope

        $scope.graphs.scopes = data


        # Scope 1
        data = []
        for rl in $scope.o.reportLines
            if rl.scope1Value > 0
                data.push
                    label: rl.indicatorName,
                    value: rl.scope1Value

        $scope.graphs.scope1 = data

        # Scope 2
        data = []
        for rl in $scope.o.reportLines
            if rl.scope2Value > 0
                data.push
                    label: rl.indicatorName,
                    value: rl.scope2Value

        $scope.graphs.scope2 = data

        # Scope 3
        data = []
        for rl in $scope.o.reportLines
            if rl.scope3Value > 0
                data.push
                    label: rl.indicatorName,
                    value: rl.scope3Value

        $scope.graphs.scope3 = data

        # Out of Scope
        data = []
        for rl in $scope.o.reportLines
            if rl.outOfScopeValue > 0
                data.push
                    label: rl.indicatorName,
                    value: rl.outOfScopeValue

        $scope.graphs.outOfScope = data

        console.log $scope.o
