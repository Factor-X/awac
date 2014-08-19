angular
.module('app.controllers')
.controller "ResultsCtrl", ($scope, downloadService, $http, displayFormMenu) ->
    $scope.displayFormMenu = displayFormMenu

    $scope.graphs = {}

    downloadService.getJson "result/getReport/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, (data) ->
        $scope.o = data


        # All scopes
        totalScope1 = 0;
        totalScope2 = 0;
        totalScope3 = 0;
        for rl in $scope.o.reportLines
            totalScope1 += rl.scope1Value
            totalScope2 += rl.scope2Value
            totalScope3 += rl.scope3Value

        $scope.graphs.allScopes =
            _type: 'terms'
            missing: 0
            visible: (totalScope1 + totalScope2 + totalScope3 > 0)
            total: totalScope1 + totalScope2 + totalScope3
            other: 0
            terms: [
                {
                    term: 'Scope 1'
                    count: totalScope1
                } ,
                {
                    term: 'Scope 2'
                    count: totalScope2
                } ,
                {
                    term: 'Scope 3'
                    count: totalScope3
                }
            ]


        # Scope 1
        data = []
        for rl in $scope.o.reportLines
            if rl.scope1Value > 0
                data.push
                    label: rl.indicatorName,
                    value: rl.scope1Value

        $scope.graphs.scope1 = data

        # Scope 1
        data = []
        for rl in $scope.o.reportLines
            if rl.scope2Value > 0
                data.push
                    label: rl.indicatorName,
                    value: rl.scope2Value

        $scope.graphs.scope2 = data

        # Scope 1
        data = []
        for rl in $scope.o.reportLines
            if rl.scope3Value > 0
                data.push
                    label: rl.indicatorName,
                    value: rl.scope3Value

        $scope.graphs.scope3 = data


