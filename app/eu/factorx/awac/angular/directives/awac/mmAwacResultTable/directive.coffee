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

        scope.getLeftTotalScope1 = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.leftScope1Value
            total

        scope.getLeftTotalScope2 = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.leftScope2Value
            total

        scope.getLeftTotalScope3 = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.leftScope3Value
            total

        scope.getLeftTotalOutOfScope = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.leftOutOfScopeValue
            total

        scope.getRightTotalScope1 = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.rightScope1Value
            total

        scope.getRightTotalScope2 = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.rightScope2Value
            total

        scope.getRightTotalScope3 = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.rightScope3Value
            total

        scope.getRightTotalOutOfScope = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.rightOutOfScopeValue
            total

