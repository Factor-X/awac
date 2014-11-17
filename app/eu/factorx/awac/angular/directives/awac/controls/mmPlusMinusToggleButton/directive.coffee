angular
.module('app.directives')
.directive "mmPlusMinusToggleButton", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-plus-minus-toggle-button.html"
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.toggle = () ->
            if scope.ngModel
                scope.ngModel = false
            else
                scope.ngModel = true


