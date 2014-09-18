angular
.module('app.directives')
.directive "mmAwacResultTable", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-result-table.html"
    replace: true
    transclude: true
    link: (scope, element) ->
        directiveService.autoScopeImpl scope

        scope.getTotalScope1 = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.scope1Value
            total
            
        scope.getTotalScope2 = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.scope2Value
            total

        scope.getTotalScope3 = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.scope3Value
            total

        scope.getTotalOutOfScope = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.outOfScopeValue
            total

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
