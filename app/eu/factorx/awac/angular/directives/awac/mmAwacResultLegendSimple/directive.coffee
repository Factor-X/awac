angular
.module('app.directives')
.directive "mmAwacResultLegendSimple", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
        ngIdeal: '='
        ngType: '='
        ngIdealColor: '='
        ngTypeColor: '='
    templateUrl: "$/angular/templates/mm-awac-result-legend-simple.html"
    replace: true
    link: (scope, element) ->
        directiveService.autoScopeImpl scope

        scope.getNumber = (indicator) ->
            return undefined unless scope.ngModel
            return undefined unless indicator
            result = null
            index = 0
            for l in scope.ngModel.reportLines
                index += 1
                if l.indicatorName == indicator
                    result = index
                    break
            result

        scope.getLeftTotal = (indicator) ->
            return undefined unless scope.ngModel
            return undefined unless indicator
            result = null
            index = 0
            for l in scope.ngModel.reportLines
                if l.indicatorName == indicator
                    result = l.leftScope1Value + l.leftScope2Value + l.leftScope3Value
                    break
            result

        scope.getRightTotal = (indicator) ->
            return undefined unless scope.ngModel
            return undefined unless indicator
            result = null
            index = 0
            for l in scope.ngModel.reportLines
                if l.indicatorName == indicator
                    result = l.rightScope1Value + l.rightScope2Value + l.rightScope3Value
                    break
            result
