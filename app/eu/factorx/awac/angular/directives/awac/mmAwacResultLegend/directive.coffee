angular
.module('app.directives')
.directive "mmAwacResultLegend", (directiveService, $filter) ->
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
            return undefined unless rl
            considerAll = scope.isSimple()
            result = null
            index = 0
            for l in scope.ngModel.reportLines
                if considerAll or (l.leftScope1Value + l.leftScope2Value + l.leftScope3Value + l.leftOutOfScopeValue + l.rightScope1Value + l.rightScope2Value + l.rightScope3Value + l.rightOutOfScopeValue > 0)
                    index += 1
                    if l.indicatorName == rl.indicatorName
                        result = index
                        break
            result

        scope.getReportLines = () ->
            return undefined unless scope.ngModel
            considerAll = scope.isSimple()
            if considerAll
                return scope.ngModel.reportLines
            else
                return $filter('filter')(scope.ngModel.reportLines, {color: '!!'})

        scope.isSimple = () ->
            return (scope.$root.instanceName == 'household' || scope.$root.instanceName == 'event' || scope.$root.instanceName == 'littleemitter')
