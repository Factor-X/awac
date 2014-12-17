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

        typicalResultLabelKeyByInterfaceType =
            household: 'TYPICAL_HOUSEHOLD_TITLE'
            littleemitter: 'TYPICAL_LITTLEEMITTER_TITLE'
            event: 'TYPICAL_EVENT_TITLE'

        idealResultLabelKeyByInterfaceType =
            household: 'IDEAL_HOUSEHOLD_TITLE'
            littleemitter: 'IDEAL_LITTLEEMITTER_TITLE'
            event: 'IDEAL_EVENT_TITLE'

        scope.showAll = true

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

        scope.getLeftTotal = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.leftScope1Value
                total += rl.leftScope2Value
                total += rl.leftScope3Value
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

        scope.getRightTotal = () ->
            return undefined unless scope.ngModel
            total = 0;
            for rl in scope.ngModel.reportLines
                total += rl.rightScope1Value
                total += rl.rightScope2Value
                total += rl.rightScope3Value
            total

        scope.getTypeMapTotal = () ->
            return undefined unless scope.ngTypeMap
            total = 0;
            for k, v of scope.ngTypeMap
                total += parseFloat(v)
            return total

        scope.getIdealMapTotal = () ->
            return undefined unless scope.ngIdealMap
            total = 0;
            for k, v of scope.ngIdealMap
                total += parseFloat(v)
            return total

        scope.getTypicalResultLabelKey = () ->
            return typicalResultLabelKeyByInterfaceType[scope.$root.instanceName]

        scope.getIdealResultLabelKey = () ->
            return idealResultLabelKeyByInterfaceType[scope.$root.instanceName]
