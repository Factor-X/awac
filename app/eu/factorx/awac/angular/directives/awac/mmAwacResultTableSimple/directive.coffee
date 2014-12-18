angular
.module('app.directives')
.directive "mmAwacResultTableSimple", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
        ngTypeMap: '='
        ngIdealMap: '='
    templateUrl: "$/angular/templates/mm-awac-result-table-simple.html"
    replace: true
    transclude: true
    link: (scope, element) ->
        directiveService.autoScopeImpl scope

        typicalResultLabelKeys =
            household: 'TYPICAL_HOUSEHOLD_TITLE'
            littleemitter: 'TYPICAL_LITTLEEMITTER_TITLE'
            event: 'TYPICAL_EVENT_TITLE'

        idealResultLabelKeys =
            household: 'IDEAL_HOUSEHOLD_TITLE'
            littleemitter: 'IDEAL_LITTLEEMITTER_TITLE'
            event: 'IDEAL_EVENT_TITLE'

        scope.showAll = true

        scope.getLeftTotal = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.leftScope1Value
                total += rl.leftScope2Value
                total += rl.leftScope3Value
            total

        scope.getRightTotal = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.rightScope1Value
                total += rl.rightScope2Value
                total += rl.rightScope3Value
            total

        scope.getTypicalResultTotal = () ->
            return undefined unless scope.ngTypeMap
            total = 0;
            for k, v of scope.ngTypeMap
                total += parseFloat(v)
            return total

        scope.getIdealResultTotal = () ->
            return undefined unless scope.ngIdealMap
            total = 0;
            for k, v of scope.ngIdealMap
                total += parseFloat(v)
            return total

        scope.getTypicalResultLabelKey = () ->
            return typicalResultLabelKeys[scope.$root.instanceName]

        scope.getIdealResultLabelKey = () ->
            return idealResultLabelKeys[scope.$root.instanceName]
