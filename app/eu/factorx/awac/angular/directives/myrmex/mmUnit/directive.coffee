angular
.module('app.directives')
.directive "mmUnit", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngRequired: "="
        ngModel: "="
        ngOptions: "="
    templateUrl: "$/angular/templates/mm-unit.html"
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.select = (o) ->
            scope.sel = o
            scope.ngModel.unit = o.key

        scope.getValue = () ->
            for o in scope.ngOptions
                if o.key == scope.ngModel.unit
                    return o.value
            return null

        scope.isSelected = (key) ->
            scope.ngModel.unit == key