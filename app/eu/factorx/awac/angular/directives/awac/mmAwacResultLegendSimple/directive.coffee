angular
.module('app.directives')
.directive "mmAwacResultLegendSimple", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
        ngMode: '='
    templateUrl: "$/angular/templates/mm-awac-result-legend-simple.html"
    replace: true
    link: (scope, element) ->
        directiveService.autoScopeImpl scope

        scope.getNumber = (rl) ->
            return undefined unless scope.ngModel
            return undefined unless rl
            result = null
            index = 0
            for l in scope.ngModel.reportLines
                if l.leftScope1Value + l.leftScope2Value + l.leftScope3Value + l.leftOutOfScopeValue + l.rightScope1Value + l.rightScope2Value + l.rightScope3Value + l.rightOutOfScopeValue > 0
                    index += 1
                    if l.indicatorName == rl.indicatorName
                        result = index
                        break
            result
