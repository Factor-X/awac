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
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.scope1Value
            return total
            
        scope.getTotalScope2 = () ->
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.scope2Value
            return total

        scope.getTotalScope3 = () ->
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.scope3Value
            return total

        scope.getTotalOutOfScope = () ->
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.outOfScopeValue
            return total

        scope.getNumber: (rl) ->
            result = null
            index = 0
            for l in scope.ngModel.reportLines
                if l.scope1Value + l.scope2Value + l.scope3Value + l.outOfScopeValue > 0
                    index += 1
                    if l == rl
                        result = index
                        break
            console.log result
            result