angular
.module('app.directives')
.directive "mmAwacResultLegend", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
        ngMode: '='
    templateUrl: "$/angular/templates/mm-awac-result-legend.html"
    replace: true
    link: (scope, element) ->
        directiveService.autoScopeImpl scope

        scope.getNumber = (rl) ->
            return undefined unless scope.ngModel
            result = null
            index = 0
            for l in scope.ngModel.reportLines
                if l.scope1Value + l.scope2Value + l.scope3Value + l.outOfScopeValue > 0
                    index += 1
                    if l.indicatorName == rl.indicatorName
                        result = index
                        break
            result
